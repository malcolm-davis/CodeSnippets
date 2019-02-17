package code.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockMain {

    public static void main(String[] args) {
        System.out.println("Start: LockMain ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        LockMain test = new LockMain();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        System.out.println("no lock");

        lock.lock();
        System.out.println("lock");

        lock.unlock();
        System.out.println("unlock");

        lock.unlock();
        System.out.println("unlock");

        lock.unlock();
        System.out.println("unlock");

    }

    final Lock lock = new ReentrantLock();

}
