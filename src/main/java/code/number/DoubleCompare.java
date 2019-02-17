package code.number;

import java.math.BigDecimal;

public class DoubleCompare {

    public static void main(String[] args) {
        System.out.println("Start: DoubleCompare ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        DoubleCompare test = new DoubleCompare();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        double one = 1.0001;
        double oneAgain = 1.00010;
        System.out.println(one==oneAgain);

        BigDecimal foo = new BigDecimal(one);
        BigDecimal bar = new BigDecimal(oneAgain);
        System.out.println(foo.equals(bar));
    }

}
