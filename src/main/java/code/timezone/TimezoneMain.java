package code.timezone;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

public class TimezoneMain {

    public static void main(String[] args) {
        System.out.println("Start: TimezoneMain ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        TimezoneMain test = new TimezoneMain();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {

        ZonedDateTime zdtUtc = ZonedDateTime.of(
            2015, 2, 1, 14, 30, 0, 0, ZoneId.of("UTC").normalized());
        System.out.println(zdtUtc.toString());

        ZonedDateTime zdtZ = ZonedDateTime.of(
            2015, 2, 1, 14, 30, 0, 0, ZoneId.of("Z").normalized());
        System.out.println(zdtZ.toString());

        Date now = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy.mm.dd'T'HH:mm:ss:S X");
        System.out.println(now.getTime());
        System.out.println(df.format(now));

        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        System.out.println(df.format(now));
    }

}
