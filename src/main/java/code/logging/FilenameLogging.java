package code.logging;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.slf4j.LoggerFactory;

public class FilenameLogging {

    public static void main(String[] args) {
        System.out.println("Start: Filename ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        FilenameLogging test = new FilenameLogging();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd.k.m", Locale.US);
        String logfilename = (df.format(new Date())) + ".txt";

        URL[]urls = ((URLClassLoader)Thread.currentThread().getContextClassLoader()).getURLs();
        File logpath = new File(urls[0].getFile() + File.separator + logfilename);
        //File logpath = new File("c:/temp/"+ logfilename);
        System.out.println(logpath.getAbsolutePath());

        initLogging(logpath);
        LOG.debug("debug message");
        LOG.info("info message");
        LOG.error("error message");

    }

    public static void  initLogging(File file)  {
        Logger root = Logger.getRootLogger();
        root.getLoggerRepository().resetConfiguration();

        root.setLevel(Level.INFO);

        try {
            PatternLayout layout = new PatternLayout(LOG_PATTERN);
            if(file!=null) {
                FileAppender fileAppender = new FileAppender(layout, file.getPath(), true);
                fileAppender.setLayout(layout);
                fileAppender.setThreshold(Level.INFO);
                fileAppender.activateOptions();
                fileAppender.setName("Tester");
                root.addAppender(fileAppender);
            }

            ConsoleAppender consoleAppender = new ConsoleAppender();
            consoleAppender.setLayout(layout);
            consoleAppender.setThreshold(Level.ERROR);
            consoleAppender.activateOptions();
            root.addAppender(consoleAppender);
        } catch (Exception excep) {
        }
    }

    private static final String LOG_PATTERN = "%d{yyyy-MM-dd HH:mm:ss,sss}:  %p: %t: %c: %M: %L: %m%n";

    private static final org.slf4j.Logger LOG =
            LoggerFactory.getLogger(FilenameLogging.class.getName());
}
