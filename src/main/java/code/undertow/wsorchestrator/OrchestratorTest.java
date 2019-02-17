package code.undertow.wsorchestrator;

import java.io.IOException;
import java.util.Scanner;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * OrchestratorTest acts as a client to the workers running.
 *
 * Start WsWorkerTest first.  WsWorkerTest acts as a worker waiting for client (Orchestrator) to make request.
 *
 * @author Malcolm G. Davis
 * @version 1.0
 */
public class OrchestratorTest {

    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("Start: OrchestratorTest ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        OrchestratorTest test = new OrchestratorTest();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    @SuppressWarnings("static-method")
    private void run() throws IOException {
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

        OrchestratorWsService service = OrchestratorWsService.newService("localhost:9400/websocket", new WorkerListener() {
            @Override
            public void onFileReceived(String payloadText) {
                System.out.println("onFileReceived");
                System.out.println(payloadText);
            }

            @Override
            public void onMessageReceived(String text) {
                System.out.println("onMessageReceived");
                System.out.println(text);
            }

        });

        System.out.println("Initialized");
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("> ");
        while(true) {
            String key= terminalInput.nextLine();
            if( "quit".equalsIgnoreCase(key) ) {
                service.close();
                System.exit(0);
                terminalInput.close();
                System.out.println("System exited.  Bye bye.");
            } else {
                System.out.println("Invalid command");
            }
            System.out.print("> ");
        }
    }

    private static final String LOG_PATTERN = "%d{yyyy-MM-dd HH:mm:ss,sss}:  %p: %t: %c: %M: %L: %m%n";
}
