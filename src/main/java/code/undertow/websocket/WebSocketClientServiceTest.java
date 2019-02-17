package code.undertow.websocket;

import java.io.IOException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class WebSocketClientServiceTest {

    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("Start: WebSocketClientServiceTest ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        WebSocketClientServiceTest test = new WebSocketClientServiceTest();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    @SuppressWarnings("static-method")
    private void run() throws InterruptedException, IOException {
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

        WebSocketClientService.get().setService("localhost:8080/websocket");

        WebSocketClientService.get().initialize();

        Thread.sleep(4000);

        if( WebSocketClientService.get().isConnected() ) {
            System.out.println("Connected to server.");
            WebSocketClientService.get().postMessage("sending file");
            WebSocketClientService.get().postFile("C:/temp/_coord/_testresults.2017.07.18/2017.08.07.17.52.57/2017.08.07.17.52.57.result.txt");
            Thread.sleep(40000);
            WebSocketClientService.get().close();
        } else {
            System.out.println("Did not connect to server.");
        }
    }

    private static final String LOG_PATTERN = "%d{yyyy-MM-dd HH:mm:ss,sss}:  %p: %t: %c: %M: %L: %m%n";
}
