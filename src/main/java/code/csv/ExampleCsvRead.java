package code.csv;

import java.io.FileReader;
import java.io.Reader;
import java.util.concurrent.TimeUnit;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class ExampleCsvRead {

    public static void main(String[] args) throws Exception {
        System.out.println("Start: ExampleCsvRead ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        ExampleCsvRead test = new ExampleCsvRead();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis() - start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() throws Exception {
        Reader in = new FileReader("C:/Users/malcolm/Downloads/B1919_Data_STOKESI_2.csv");
        Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in);
        for (CSVRecord record : records) {
            String columnOne = record.get(0);
            String date = columnOne.substring(2);
            String hours = date.substring(0, 2);
            String minutes = date.substring(3, 5);
            String seconds = date.substring(6, 8);
            String milliseconds = date.substring(9);

            // System.out.print(date+" = " + hours + " " + minutes + " " + seconds + " " + milliseconds + " = " );

            long hoursMil = TimeUnit.HOURS.toMillis(Integer.valueOf(hours).intValue());
            long minutesMil = TimeUnit.MINUTES.toMillis(Integer.valueOf(minutes).intValue());
            long secondsMil = TimeUnit.SECONDS.toMillis(Integer.valueOf(seconds).intValue());

            long timeInMilliseconds = hoursMil + minutesMil + secondsMil + Integer.valueOf(milliseconds).intValue();
            // System.out.println(timeInMilliseconds);



            System.out.println(timeInMilliseconds+","+record.get(1)+","+record.get(2)+","+record.get(3)+","+record.get(4)+","+record.get(5)+","+record.get(6));
        }

    }

}
