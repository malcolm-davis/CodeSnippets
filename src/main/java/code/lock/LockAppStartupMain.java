package code.lock;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class LockAppStartupMain {

    public static void main(String[] args) {
        System.out.println("Start: LockMain ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        LockAppStartupMain test = new LockAppStartupMain();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run()  {
        System.out.println("checkIfRunning");
        if( !checkIfRunning() ) {
            System.out.println("Already running.");
            return ;
        }
        System.out.println("lock obtained");
        try { Thread.sleep(20000); } catch (Exception excep) { }
    }

    private static final int PORT = 9999;

    private static ServerSocket socket;

    private static boolean checkIfRunning() {
        try {
            // Bind to localhost adapter with a zero connection queue
            socket = new ServerSocket(PORT,0,InetAddress.getByAddress(new byte[] {127,0,0,1}));
        } catch (BindException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
        return true;
    }

}
