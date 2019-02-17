package code.console;

import static org.fusesource.jansi.Ansi.ansi;
import static org.fusesource.jansi.Ansi.Color.GREEN;
import static org.fusesource.jansi.Ansi.Color.RED;

import org.fusesource.jansi.AnsiConsole;

public class Console {

    public static void main(String[] args) {
        System.out.println("Start: Console ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        Console test = new Console();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis() - start));
        System.out.println("Finished.");
        System.exit(0);
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    private void run() {
        //System.out.println(ANSI_GREEN + "This text has a green background but default text!" + ANSI_RESET);

        AnsiConsole.systemInstall();
        System.out.println( ansi().fg(RED).a("Hello").fg(GREEN).a(" World").reset() );

        System.out.println( ansi().render("@|red Hello2|@ @|green World2|@") );
    }

}
