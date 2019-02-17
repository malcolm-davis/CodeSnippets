package code.undertow.websocket;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.LoggerFactory;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;

public enum WebSocketClientService {
    INSTANCE;

    public static WebSocketClientService get() {
        return INSTANCE;
    }

    public void setService(String serviceUrl) {
        m_serviceUrl = serviceUrl;
    }

    public void initialize() {
        if(m_serviceUrl==null) {
            throw new IllegalStateException("Null value for serviceUrl.  Call setService() with URI prior to initialize()");
        }

        try {
            webSocket = factory.createSocket("ws://" + m_serviceUrl);
            connectWebsocket();
        } catch (Exception e) {
            LOG.error("Could not connect to URI="+m_serviceUrl, e);
            return ;
        }

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                webSocket.sendPing("Are you there?");
            }
        }, 1000, 10000);

        webSocket.addListener(new WebSocketAdapter() {
            @Override
            public void onDisconnected(WebSocket websocket,
                    WebSocketFrame serverCloseFrame,
                    WebSocketFrame clientCloseFrame, boolean closedByServer)
                            throws Exception {
                reconnect();
            }

            @Override
            public void onConnected(WebSocket websocket,
                    Map<String, List<String>> headers) throws Exception {
                super.onConnected(websocket, headers);
                LOG.info(websocket.toString() + " connected.");
            }

            @Override
            public void onConnectError(WebSocket websocket,
                    WebSocketException exception) throws Exception {
                super.onConnectError(websocket, exception);
                LOG.error("Error connection", exception);
                reconnect();
            }

            @Override
            public void onTextMessage(WebSocket websocket, String text)
                    throws Exception {
//                Map<String, String> messageMap = gson.fromJson(text, Map.class);
//                System.out.println(messageMap);
                System.out.println("onTextMessage="+text);
            }

            @Override
            public void onBinaryFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
                System.out.println("Server received full binary message");
                System.out.println(frame.getPayloadText());
            }
        });

    }

    public void postMessage(String message) {
        if(webSocket==null) {
            throw new IllegalStateException("webSocket is not initialized.");
        }
        webSocket.sendText(message);
    }

    public void postFile(String filename) throws IOException {
        if(webSocket==null) {
            throw new IllegalStateException("webSocket is not initialized.");
        }
        byte[]bytes = FileUtils.readFileToByteArray(new File(filename));
        webSocket.sendBinary(bytes);
    }

    public void close() {
        if(webSocket!=null) {
            webSocket.disconnect();
        }
        webSocket = null;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(webSocket,
            ToStringStyle.MULTI_LINE_STYLE);
    }

    public boolean isConnected() {
        return webSocket.isOpen();
    }

    void reconnect() throws IOException {
        LOG.info("Attempting reconnect");
        webSocket = webSocket.recreate();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    connectWebsocket();
                } catch (Exception e) {
                    LOG.error("Could not reconnect to URI="+m_serviceUrl, e);
                }
            }
        }, 5000);
    }

    void connectWebsocket() {
        webSocket.connectAsynchronously();
    }

    private final WebSocketFactory factory =
            new WebSocketFactory().setConnectionTimeout(5000);

    WebSocket webSocket;

    String m_serviceUrl;

    final Timer timer = new Timer();

    static final org.slf4j.Logger LOG = LoggerFactory.getLogger(WebSocketClientService.class.getName());


}
