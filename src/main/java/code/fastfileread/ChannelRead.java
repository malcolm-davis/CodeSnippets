package code.fastfileread;


public class ChannelRead {

    public static void main(String[] args) {
        System.out.println("Start: ChannelRead ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        ChannelRead test = new ChannelRead();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {

    }

}
