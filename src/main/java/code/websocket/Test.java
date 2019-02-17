package code.websocket;


public class Test {

    public static void main(String[] args) {
        System.out.println("Start: Test ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        Test test = new Test();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private static final String STATUS_LABEL_CAPTION = "<iron-icon icon=\"%1$s\"></iron-icon> %2$s";
    private void run() {
        System.out.println(String.format(STATUS_LABEL_CAPTION, "av:volume-off", "caption"));
    }

}
