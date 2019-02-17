package code.classType;


public class ClassType {
    public static void main(String[] args) {
        System.out.println("Start: ClassType ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        ClassType test = new ClassType();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        System.out.println(Double.class.getSimpleName());
    }

}
