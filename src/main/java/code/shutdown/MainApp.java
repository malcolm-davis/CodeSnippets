package code.shutdown;

import java.util.Scanner;

//The Shutdown class is a sample class to illustrate the
//use of the addShutdownHook method
class Shutdown {

    private Thread thread = null;

    public Shutdown() {
        thread = new Thread("Sample thread") {
            @Override
            public void run() {
                while (true) {
                    System.out.println("[Sample thread] Sample thread speaking...");
                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException ie) {
                        break;
                    }
                }
                System.out.println("[Sample thread] Stopped");
            }
        };
        thread.start();
    }

    public void stopThread() {
        thread.interrupt();
    }
}

//The ShutdownThread is the thread we pass to the addShutdownHook method
class ShutdownThread extends Thread {

    private Shutdown shutdown = null;

    public ShutdownThread(Shutdown shutdown) {
        super();
        this.shutdown = shutdown;
    }

    @Override
    public void run() {
        System.out.println("[Shutdown thread] Shutting down");
        shutdown.stopThread();
        System.out.println("[Shutdown thread] Shutdown complete");
    }
}

//And finally a Main class which tests the two classes
//We let the sample thread run for 10 seconds and then
//force a Shutdown with System.exit(0). You may stop the
//program early by pressing CTRL-C.
public class MainApp {

    public static void main(String[] args) {
        Shutdown shutdown = new Shutdown();
        try {
            Runtime.getRuntime().addShutdownHook(new ShutdownThread(shutdown));
            System.out.println("[Main thread] Shutdown hook added");
        } catch (Throwable t) {
            // we get here when the program is run with java
            // version 1.2.2 or older
            System.out.println("[Main thread] Could not add Shutdown hook");
        }

        Scanner terminalInput = new Scanner(System.in);
        System.out.print("> ");
        while(true) {
            String key= terminalInput.nextLine();
            if( "quit".equalsIgnoreCase(key) ) {
                System.exit(0);
                terminalInput.close();
            } else {
                System.out.println("Invalid command");
            }
            System.out.print("> ");
        }
    }
}