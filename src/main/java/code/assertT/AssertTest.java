package code.assertT;


public class AssertTest {

    public static void main(String[] args) {
        System.out.println("Start: AssertTest ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        AssertTest test = new AssertTest();
        String standaonleObject = "";
        String fedObject = "";
        test.run(standaonleObject, fedObject);

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run(String standaonleObject, String fedObject) {

        assert standaonleObject != null || fedObject!=null;

        System.out.println("Got here");
    }

}
