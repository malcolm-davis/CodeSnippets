package code.higharch;


public class TestCat {

    public static void main(String[] args) {
        System.out.println("Start: TestCat ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        TestCat test = new TestCat();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        new Cat();
    }

}
