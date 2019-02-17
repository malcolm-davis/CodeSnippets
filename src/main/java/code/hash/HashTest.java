package code.hash;

import java.util.HashMap;
import java.util.Map;

public class HashTest {

    public static void main(String[] args) {
        System.out.println("Start: HashTest ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        HashTest test = new HashTest();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        Map map = new HashMap<>();
        map.put("key", null);
    }

}
