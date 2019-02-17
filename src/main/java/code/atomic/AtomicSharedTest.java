package code.atomic;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicSharedTest {

    public static void main(String[] args) {
        System.out.println("Start: AtomicSharedTest ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        AtomicSharedTest test = new AtomicSharedTest();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        Map<String, AtomicReference<Boolean>>map = new HashMap<String, AtomicReference<Boolean>>();

        AtomicReference<Boolean> flag = new AtomicReference<>();
        flag.set(Boolean.TRUE);
        map.put("01", flag);
        map.put("02", flag);
        map.put("03", flag);
        map.put("04", flag);

        AtomicReference<Boolean> flag2 = new AtomicReference<>();
        flag2.set(Boolean.FALSE);
        map.put("11", flag2);
        map.put("22", flag2);
        map.put("33", flag2);
        map.put("44", flag2);

        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        System.out.println(map.get("01"));
        System.out.println(map.get("02"));
        flag.set(Boolean.FALSE);
        System.out.println(map.get("03"));
        System.out.println(map.get("04"));

        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        System.out.println(map.get("11"));
        System.out.println(map.get("22"));
        flag2.set(Boolean.TRUE);
        System.out.println(map.get("33"));
        System.out.println(map.get("44"));

    }

}
