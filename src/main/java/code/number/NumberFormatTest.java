package code.number;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumberFormatTest {

    public static void main(String[] args) {
        System.out.println("Start: NumberFormatTest ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        NumberFormatTest test = new NumberFormatTest();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        NumberFormat nf = NumberFormat.getInstance();
        formatter = new DecimalFormat("#0.00");
        formatter.setGroupingUsed(false);


        format(15.2);
        format(15.23);
        format(15.234);
        format(15.2345);
        format(15.23456);
        format(5.23456);
    }

    void format(double value) {
        System.out.println("-----------------------------------");
        System.out.println(value);
        System.out.println(formatter.format(value));
        System.out.println(Double.valueOf(value).toString());

    }


    NumberFormat formatter = null;
}
