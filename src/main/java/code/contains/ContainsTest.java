package code.contains;


public class ContainsTest {

    public static void main(String[] args) {
        System.out.println("Start: ContainsTest ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        ContainsTest test = new ContainsTest();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        String TEST = "Test,CallStation : Conext Menu Smoke Test,AutomationBot";

        System.out.println(TEST.contains("Test,"));
        System.out.println(TEST.contains("test,"));

    }

}
