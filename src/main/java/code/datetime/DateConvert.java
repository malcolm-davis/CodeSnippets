package code.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConvert {

    public static void main(String[] args) {
        System.out.println("Start: TimeConert ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        DateConvert test = new DateConvert();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        String startDate= "2017-03-01";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        try {
            Date date = df.parse(startDate);

            System.out.println("start="+startDate);
            System.out.println("date.getTime="+date.getTime());
            System.out.println("date="+date.toGMTString());
            System.out.println("date.getMonth="+date.getMonth());

            String newDateString = df.format(date);
            System.out.println("newDateString="+newDateString);
        } catch (ParseException excep) {
            excep.printStackTrace();
        }
    }

}
