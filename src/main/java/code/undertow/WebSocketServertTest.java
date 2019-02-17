package code.undertow;

import static io.undertow.Handlers.path;
import static io.undertow.Handlers.resource;
import static io.undertow.Handlers.websocket;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import io.undertow.Undertow;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.websockets.WebSocketConnectionCallback;
import io.undertow.websockets.core.AbstractReceiveListener;
import io.undertow.websockets.core.BufferedTextMessage;
import io.undertow.websockets.core.WebSocketChannel;
import io.undertow.websockets.core.WebSockets;
import io.undertow.websockets.spi.WebSocketHttpExchange;

public class WebSocketServertTest {

    public static void main(final String[] args) {
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

        Undertow server = Undertow.builder().addListener(8080, "0.0.0.0").setHandler(path().addPrefixPath("/websocket",
            websocket(new WebSocketConnectionCallback() {
                @Override
                public void onConnect(WebSocketHttpExchange exchange, WebSocketChannel channel) {
                    channel.getReceiveSetter().set(new AbstractReceiveListener() {
                        @Override
                        protected void onFullTextMessage(WebSocketChannel channel, BufferedTextMessage message) {
                            WebSockets.sendText(message.getData(), channel, null);
                        }
                    });
                    channel.resumeReceives();
                }
            })).addPrefixPath("/",
                resource(new ClassPathResourceManager(WebSocketServertTest.class
                    .getClassLoader(), WebSocketServertTest.class.getPackage()))
                .addWelcomeFiles("index.html")))
                .build();
        server.start();

    }

    private static final String LOG_PATTERN = "%d{yyyy-MM-dd HH:mm:ss,sss}:  %p: %t: %c: %M: %L: %m%n";

}