package code.datetime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.elasolutions.utils.date.DateTimeSpan;

public class Last90Days {

    public static void main(String[] args) {
        System.out.println("Start: TimeConert ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        Last90Days test = new Last90Days();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date endDate = new Date();
        Date startDate = DateTimeSpan.subDate(endDate, 0, 3, 0);

        String startDateString = df.format(startDate);
        String endDateString = df.format(endDate);

        System.out.println("start="+startDateString);
        System.out.println("end="+endDateString);
    }

}
