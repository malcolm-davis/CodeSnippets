package code.shell;


public class TestClass {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("TestClass arguments");

        int cnt=0;
        for (String arg : args) {
            cnt++;
            if(cnt!=args.length) {
                System.out.println("\t"+arg);
            }
        }

        System.out.println("Start: TestClass ");
        System.out.println("\tTest output 1");
        Thread.sleep(1000);
        System.out.println("\tTest output 2");
        Thread.sleep(1000);
        System.out.println("\tTest output 3");
        Thread.sleep(1000);
        System.out.println("\tTest output 4");
        Thread.sleep(1000);
        System.out.println("\tTest output 5");
        Thread.sleep(1000);
        System.out.println("\tTest output 6");
        Thread.sleep(1000);
        System.out.println("Finished: TestClass");
        System.exit(0);
    }


}
