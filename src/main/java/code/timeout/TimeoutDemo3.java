package code.timeout;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TimeoutDemo3 {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<String> future = executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                // TODO Auto-generated method stub
                while (!Thread.interrupted()) {
                    // Do your long running task here.
                    Thread.sleep(4000); // Just to demo a long running task of 4 seconds.
                    break;
                }
                return "Ready!";
            }
        });

        try {
            System.out.println("Started..");
            System.out.println(future.get(3, TimeUnit.SECONDS));
            System.out.println("Finished!");
        } catch (TimeoutException e) {
            future.cancel(true);
            System.out.println("Terminated!");
        }

        executor.shutdownNow();
    }
}