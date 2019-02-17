package code.time;

import java.util.concurrent.TimeUnit;

public class TimeConversion {

    public static void main(String[] args) {
        System.out.println("Start: TimeConversion ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        TimeConversion test = new TimeConversion();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        long elapsed = 27216000;

        long hours = TimeUnit.MILLISECONDS.toHours(elapsed);

        long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsed) - TimeUnit.HOURS.toMinutes(hours);

        long seconds= TimeUnit.MILLISECONDS.toSeconds(elapsed) - (TimeUnit.HOURS.toSeconds(hours) + TimeUnit.MINUTES.toSeconds(minutes));

        System.out.println(String.format("%02d:%02d:%02d",hours, minutes, seconds));


//        long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsed);
//
//        // seconds
//        elapsed -= TimeUnit.MINUTES.toMillis(minutes);
//        long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsed);
//
//        System.out.println("" +  minutes + (double)seconds/(double)100);
//
//        long millis = elapsed;
//        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
//            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
//            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
//        System.out.println(hms);
//
//        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
//        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
//
//
//        String.format("%02d:%02d:%02d",
//            TimeUnit.MILLISECONDS.toHours(millis),
//            TimeUnit.MILLISECONDS.toMinutes(millis) -
//            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), // The change is in this line
//            TimeUnit.MILLISECONDS.toSeconds(millis) -
//            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }

}
