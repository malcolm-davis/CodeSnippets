package code.logging;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.slf4j.LoggerFactory;

public class WriteReadLog {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Start: WriteReadLog ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        WriteReadLog test = new WriteReadLog();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() throws InterruptedException {
        final String LOG_FILE = "c:/temp/sample.logging.txt";

        FilenameLogging.initLogging(new File(LOG_FILE));

        Thread readLogThread = new Thread() {
            @Override
            public void run() {
                BufferedInputStream reader = null;
                try {
                    boolean running = true;
                    reader = new BufferedInputStream(new FileInputStream(LOG_FILE) );

                    while( running ) {
                        if( reader.available() > 0 ) {
                            System.out.print( (char)reader.read() );
                        }
                        else {
                            try {
                                sleep( 500 );
                            }
                            catch( InterruptedException ex ) {
                                running = false;
                            }
                        }
                    }
                } catch (Exception excep) {
                    excep.printStackTrace();
                } finally {
                    if(reader!=null) {
                        try { reader.close(); } catch (IOException excep) { excep.printStackTrace(); }
                    }
                }
            }
        };

        readLogThread.start();

        LOG.debug("debug message");
        LOG.info("info message");

        Thread.sleep(1000);

        LOG.debug("more messaging");
        LOG.info("more messaging");

        Thread.sleep(1000);
    }


    private static final org.slf4j.Logger LOG =
            LoggerFactory.getLogger(WriteReadLog.class.getName());

}
