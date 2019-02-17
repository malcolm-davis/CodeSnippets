package code.datetime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatDate {

    public static void main(String[] args) {
        System.out.println("Start: FormatDate ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        FormatDate test = new FormatDate();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd.k.m", Locale.US);
        System.out.println(df.format(new Date()));

    }

}
