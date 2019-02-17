package code.sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortTime {
    public static void main(String[] args) {
        System.out.println("Start: SortTime ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        SortTime test = new SortTime();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        List<String>list = new ArrayList<String>();
        list.add("2017.07.16 - 10.45.00");
        list.add("2017.07.18 - 10.45.00");
        list.add("2017.07.19 - 10.45.00");
        list.add("2017.05.02 - 10.45.00");
        list.add("2017.06.20 - 10.45.00");
        list.add("2017.07.21 - 10.45.00");
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });
        for (String string : list) {
            System.out.println(string);
        }
    }

}
