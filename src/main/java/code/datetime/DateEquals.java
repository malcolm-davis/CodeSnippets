package code.datetime;

import java.util.Date;
import java.util.Objects;

public class DateEquals {
    public static void main(String[] args) {

        Date date1 = new Date(2016, 11, 20);
        Date date2 = new Date(2016, 11, 20);

        boolean isEquals = date1.equals(date2);
        System.out.println("date 1 and date 2 are equals ? :- " + isEquals);

        date2 = new Date(2016, 11, 12);

        isEquals = date1.equals(date2);
        System.out.println("date 1 and date 2 are equals ? :- " + isEquals);

        System.out.println(Objects.equals(date1, date2));
    }
}
