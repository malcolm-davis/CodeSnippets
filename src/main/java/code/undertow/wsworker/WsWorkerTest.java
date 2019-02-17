package code.undertow.wsworker;

import static io.undertow.Handlers.path;
import static io.undertow.Handlers.websocket;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.xnio.Pooled;

import io.undertow.Undertow;
import io.undertow.websockets.WebSocketConnectionCallback;
import io.undertow.websockets.core.AbstractReceiveListener;
import io.undertow.websockets.core.BufferedBinaryMessage;
import io.undertow.websockets.core.BufferedTextMessage;
import io.undertow.websockets.core.CloseMessage;
import io.undertow.websockets.core.StreamSourceFrameChannel;
import io.undertow.websockets.core.WebSocketChannel;
import io.undertow.websockets.core.WebSockets;
import io.undertow.websockets.spi.WebSocketHttpExchange;

/**
 * WsWorkerTest simulates a WS server receiving and responding to the client requesting info.
 *
 * @author Malcolm G. Davis
 * @version 1.0
 */
public class WsWorkerTest {

    public static void main(final String[] args) {
        System.out.println("WsWorkerTest init");
        Logger root = Logger.getRootLogger();
        root.getLoggerRepository().resetConfiguration();

        root.setLevel(Level.INFO);

        try {
            PatternLayout layout = new PatternLayout(LOG_PATTERN);
            ConsoleAppender consoleAppender = new ConsoleAppender();
            consoleAppender.setLayout(layout);
            consoleAppender.setThreshold(Level.ERROR);
            consoleAppender.activateOptions();
            root.addAppender(consoleAppender);
        } catch (Exception excep) {
        }

        final AtomicReference<String> result = new AtomicReference<>();

        // Undertow server = Undertow.builder().addHttpListener(9400, "0.0.0.0")
        Undertow server = Undertow.builder().addHttpListener(9400, "localhost")
                .setHandler(path().addPrefixPath("/websocket", websocket(new WebSocketConnectionCallback() {
                    @Override
                    public void onConnect(WebSocketHttpExchange exchange, WebSocketChannel channel) {
                        getWebSocketChannelList().add(channel);

                        channel.getReceiveSetter().set(new AbstractReceiveListener() {
                            @Override
                            protected void onFullBinaryMessage(final WebSocketChannel channel, BufferedBinaryMessage message) throws IOException {
                                System.out.println("Server received full binary message");
                                Pooled<ByteBuffer[]> pulledData = message.getData();
                                try {
                                    ByteBuffer[] resource = pulledData.getResource();
                                    ByteBuffer byteBuffer = WebSockets.mergeBuffers(resource);
                                    String msg = new String(byteBuffer.array());
                                    System.out.println("\tSending message to decoder: "+msg);
                                    //writeToDecoder(msg);
                                } finally {
                                    pulledData.discard();
                                    pulledData.free();
                                }
                                //message.getData().free();
                            }

                            @Override
                            protected void onFullTextMessage(WebSocketChannel channel, BufferedTextMessage message) {
                                String data = message.getData();
                                result.set(data);
                                System.out.println("onFullTextMessage()=" + result.get());
                                WebSockets.sendText("received=" + result.get(), channel, null);
//                                for (WebSocketChannel session : channel.getPeerConnections()) {
//                                    WebSockets.sendText(message.getData(), session, null);
//                                }
                            }

                            @Override
                            protected void onError(WebSocketChannel channel, Throwable error) {
                                System.out.println("onError()=" + error.getMessage());
                                //error.printStackTrace();
                            }

                            @Override
                            protected void onCloseMessage(CloseMessage cm, WebSocketChannel channel) {
                                System.out.println("onCloseMessage()=" + cm.getReason());
                            }

                            @Override
                            protected void onPing(WebSocketChannel webSocketChannel, StreamSourceFrameChannel channel) throws IOException {
                                System.out.println("onPing()");
                                bufferFullMessage(channel);
                            }

                            @Override
                            protected void onClose(WebSocketChannel webSocketChannel, StreamSourceFrameChannel channel) throws IOException {
                                System.out.println("onClose()");
                                bufferFullMessage(channel);
                                if(webSocketChannel!=null) {
                                    getWebSocketChannelList().remove(webSocketChannel);
                                }
                            }

                            @Override
                            protected void onPong(WebSocketChannel webSocketChannel, StreamSourceFrameChannel messageChannel) throws IOException {
                                System.out.println("onPong()");
                                bufferFullMessage(messageChannel);
                            }

                            @Override
                            protected void onText(WebSocketChannel webSocketChannel, StreamSourceFrameChannel messageChannel) throws IOException {
                                System.out.println("onText()");
                                bufferFullMessage(messageChannel);
                            }
                        });
                        channel.resumeReceives();
                    }
                }))).build();
        server.start();
        System.out.println("WsWorkerTest startup complete");

        Scanner terminalInput = new Scanner(System.in);
        System.out.print("> ");
        while(true) {
            String key= terminalInput.nextLine();
            if( "quit".equalsIgnoreCase(key) ) {
                server.stop();
                System.exit(0);
                terminalInput.close();
                System.out.println("System exited.  Bye bye.");
            } else if( "send".equalsIgnoreCase(key) ) {
                for (WebSocketChannel channel : getWebSocketChannelList()) {
                    WebSockets.sendText("Post to clients", channel, null);
                }
                System.out.println("WsWorkerTest send action complete\r\n");
            } else if( "file".equalsIgnoreCase(key) ) {
                for (WebSocketChannel channel : getWebSocketChannelList()) {
                    try {
                        postFile(channel, "C:/temp/_coord/_testresults.2017.07.18/2017.08.07.17.52.57/2017.08.07.17.52.57.result.txt");
                    } catch (IOException excep) {
                        excep.printStackTrace();
                    }
                }
                System.out.println("WsWorkerTest file action complete\r\n");
            } else {
                System.out.println("Invalid command\r\n");
            }
            System.out.print("> ");
        }
    }

    public static void postFile(WebSocketChannel channel, String filename) throws IOException {
        byte[]bytes = FileUtils.readFileToByteArray(new File(filename));
        ByteBuffer data = ByteBuffer.wrap(bytes);
        WebSockets.sendBinary(data, channel, null);
    }

    static List<WebSocketChannel> getWebSocketChannelList() {
        if (m_WebSocketChannelList == null) {
            m_WebSocketChannelList = new ArrayList<WebSocketChannel>();
        }
        return m_WebSocketChannelList;
    }

    private static List<WebSocketChannel>m_WebSocketChannelList;

    private static final String LOG_PATTERN = "%d{yyyy-MM-dd HH:mm:ss,sss}:  %p: %t: %c: %M: %L: %m%n";
}