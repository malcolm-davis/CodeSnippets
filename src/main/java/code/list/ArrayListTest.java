package code.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ArrayListTest {

    public static void main(String[] args) {
        System.out.println("Start: ArrayListTest ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        ArrayListTest test = new ArrayListTest();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        List<String>list = new ArrayList<String>();
//        list.add("some");
//        list.add("string");
//        list.addAll(null);

        //Arrays.deepEquals(a1, a2)

        addAllIfNotNull(list, null);

        for (String string : list) {
            System.out.println(string);
        }
    }


    public static <E> void addAllIfNotNull(List<E> list, Collection<? extends E> c) {
        if (c != null) {
            list.addAll(c);
        }
    }
}
