package code.datetime;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class DecimalFormatDemo {

    public static void main(String[] args) {
        System.out.println("Start: DecimalFormatDemo ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        DecimalFormatDemo test = new DecimalFormatDemo();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        // DecimalFormat format = new java.text.DecimalFormat("##0.0%");
        DecimalFormat format = new java.text.DecimalFormat("##0.0");
        System.out.println(format.format(Double.valueOf(23.446634)));
        System.out.println(format.format(Double.valueOf(3.446634)));
        System.out.println(format.format(Double.valueOf(2223.446634)));
        System.out.println(format.format(Double.valueOf(3.44)));
        System.out.println(format.format(Double.valueOf(23.4)));
        System.out.println(format.format(Double.valueOf(23.)));
        System.out.println(format.format(Double.valueOf(0)));

        System.out.println("*************************************");
        NumberFormat formatNumber = NumberFormat.getNumberInstance(Locale.US);
        System.out.println(formatNumber.format(Long.valueOf(0)));
        System.out.println(formatNumber.format(Long.valueOf(100)));
        System.out.println(formatNumber.format(Long.valueOf(3634)));
        System.out.println(formatNumber.format(Long.valueOf(1000)));
        System.out.println(formatNumber.format(Long.valueOf(10000)));
        System.out.println(formatNumber.format(Long.valueOf(44444444)));

        System.out.println("*************************************");
        NumberFormat formatNumber2 = new java.text.DecimalFormat("##");
        System.out.println(formatNumber2.format(0d));
        System.out.println(formatNumber2.format(100d));
        System.out.println(formatNumber2.format(3634d));
        System.out.println(formatNumber2.format(1000d));
        System.out.println(formatNumber2.format(10000d));
        System.out.println(formatNumber2.format(6094443214d));

        System.out.println("*************************************");
        System.out.println(fmt(0d));
        System.out.println(fmt(100d));
        System.out.println(fmt(3634d));
        System.out.println(fmt(1000d));
        System.out.println(fmt(10000d));
        System.out.println(fmt(6094443214d));
    }


    public static String fmt(double d) {
        if(d == (long) d) {
            return String.format("%d",(long)d);
        }
        return String.format("%s",d);
    }
}
