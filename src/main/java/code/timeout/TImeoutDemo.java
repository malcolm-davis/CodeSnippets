package code.timeout;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class TImeoutDemo {

    public static void main(String[] args) {
        System.out.println("Start: TImeoutDemo ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        TImeoutDemo test = new TImeoutDemo();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        final Future<String> handler = executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return requestDataFromModem();
            }
        });

        try {
            handler.get(Duration.ofMillis(4000).toMillis(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            handler.cancel(true);
            System.out.println("Timeout e=" + e.getMessage());
        }

        executor.shutdownNow();
    }

    String requestDataFromModem() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException excep) {
            // TODO Auto-generated catch block
            excep.printStackTrace();
        }
        return null;
    }

}
