package code.mysql;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;

public class ReadSqlTimingLog {

    public static void main(String[] args) throws IOException {
        System.out.println("Start: SqlInsert");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        ReadSqlTimingLog test = new ReadSqlTimingLog();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() throws IOException {
        List<String>persistObjList = new ArrayList<String>();
        List<String>insertList = new ArrayList<String>();
        List<String>updateCallHistory = new ArrayList<String>();
        List<String>updateOther = new ArrayList<String>();

        Map<String,String>txnLookupMap = new HashMap<String,String>();
        int errorCnt = 0;
        int successCnt = 0;
        String previousLine = null;
        //LineIterator it = FileUtils.lineIterator(new File("C:/ecx/cluster2.mysqld-slow.log"), "UTF-8");
        LineIterator it = FileUtils.lineIterator(new File("C:/ecx/cluster2.mysqld-slow.log"), "UTF-8");
        int cnt=0;
        boolean checkNext = false;

        String sql = null;
        boolean longRunningTime = false;
        double queryTime = 0.0d;
        int beginIndex = "# Query_time: ".length();
        while (it.hasNext()) {
            String line = it.nextLine();

            if( StringUtils.startsWithIgnoreCase(line, "# Query_time:") ) {
                // # Query_time: 0.072218  Lock_time: 0.000282  Rows_sent: 37  Rows_examined: 20406  Rows_affected: 0
                int endIndex = line.indexOf(" Lock_time: ");
                String time = line.subSequence(beginIndex, endIndex).toString();
                queryTime = Double.valueOf(time.trim()).doubleValue();
                if( queryTime>10 ) {
                    longRunningTime = true;
                }
            }

            if( StringUtils.startsWithIgnoreCase(line, "select ") && longRunningTime) {
                longRunningTime = false;

                System.out.println(""+queryTime + "\t" +line);
            }
        }
    }

}
