package code.datetime;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class DurationDemo {

    public static void main(String[] args) {
        System.out.println("Start: DurationDemo ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        DurationDemo test = new DurationDemo();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        Duration dur30H = Duration.parse("PT30H"); // 30h
        System.out.println(dur30H.toString());
        System.out.println(dur30H.toHours());
        System.out.println(dur30H.toMinutes());

        System.out.println("-------------------------------------------------");
        Duration dur = Duration.of((1000*60*65), ChronoUnit.MILLIS);
        System.out.println(dur.toString());
        System.out.println(dur.toHours());
        System.out.println(dur.toMinutes());

        System.out.println("-------------------------------------------------");
        System.out.println(formatDuration(dur));
        System.out.println(formatDuration2(dur));




//        Duration.of(4000, TemporalUnit.)
//        System.out.println(LocalTime.MIDNIGHT.plus(dur30H).format(DateTimeFormatter.ofPattern("HH:mm:ss")));
//
//        System.out.println(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
//
//        System.out.println("\n--- Period.between --- ");
//        LocalDate oldDate = LocalDate.ofEpochDay(1488348000000L);
//        LocalDate newDate = LocalDate.of(2018, Month.FEBRUARY, 2);
//        Period period = Period.between(oldDate, newDate);
//        System.out.print(period.getYears() + " years,");
//        System.out.print(period.getMonths() + " months,");
//        System.out.print(period.getDays() + " days");

//        System.out.println(DateTimeFormatter.ofPattern("HH:mm:ss").toFormat().format(dur30H));
//        Duration dur4H = Duration.parse("PT4H"); // 4h
//        Duration travelTime = Duration.parse("P1D"); // 1D
//        boolean result = travelTime.compareTo(dur30H) <= 0 && travelTime.compareTo(dur4H) >= 0; //
    }
    public static String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        String positive = String.format(
            "%d:%02d:%02d",
            absSeconds / 3600,
            (absSeconds % 3600) / 60,
            absSeconds % 60);
        return seconds < 0 ? "-" + positive : positive;
    }

    public static String formatDuration2(Duration duration) {
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        String positive = String.format(
            "%d hr %02d min",
            absSeconds / 3600, (absSeconds % 3600) / 60);
        return seconds < 0 ? "-" + positive : positive;
    }

}
