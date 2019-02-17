package code.assertT;


public class Substring {

    public static void main(String[] args) {
        System.out.println("Start: Substring ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        Substring test = new Substring();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        String name = "2017.02.14.09.33.00.result.txt.bzip";
        int endIndex = name.indexOf(".bzip");
        if(endIndex>0) {
            System.out.println(name.substring(0, endIndex));
        }
    }

}
