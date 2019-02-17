package code.fastfileread;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;

public class ApacheIo {

    public static void main(String[] args) throws IOException {
        System.out.println("Start: ApacheIo ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        ApacheIo test = new ApacheIo();
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
        LineIterator it = FileUtils.lineIterator(new File("C:/ecx/_dev22binlog/binlog.sql"), "UTF-8");
        try {

            int cnt=0;
            boolean checkNext = false;
            while (it.hasNext()) {
                String line = it.nextLine();

                if( checkNext && !line.contains("/*!*/;") ) {
                    System.out.println("ERROR: checkNext failed");
                    System.out.println("\tpreviousLine=" + previousLine);
                    System.out.println("\tline=" + line);
                    errorCnt++;
                } else {
                    successCnt++;
                }
                checkNext = false;
                previousLine = line;

                if( StringUtils.startsWithIgnoreCase(line, "INSERT INTO ") ) {
                    if( line.startsWith("INSERT INTO PersistentGridObject") ) {
                        continue ;
                    }

                    checkNext = true;
//                    line.
//                    txnLookupMap
                    insertList.add(line);

                    if( line.startsWith("insert into CallHistory ") ) {
                        String key = parse(line);
                        txnLookupMap.put(key, line);
                        if( key.contains("'") ) {
                            System.out.println(line);
                        }
                    }
                } else if(StringUtils.startsWithIgnoreCase(line,  "update ")) {
                    checkNext = true;
                    if(StringUtils.startsWithIgnoreCase(line,  "update CallHistory ")) {
                        updateCallHistory.add(line);
                    } else {
                        updateOther.add(line);
                    }

                    insertList.add(line);
                } else if(StringUtils.startsWithIgnoreCase(line, "delete from ")) {
                    checkNext = true;
                    // DELETE FROM PersistentGridObject where id = '0c4f0296-424a-4b17-8fd4-26413da2f614'
                    persistObjList.add(
                        line.substring("DELETE FROM PersistentGridObject where id = '".length(), line.length()-1));
                }

                cnt++;
                if( (cnt%10000)==0 ) {
                    System.out.println("cnt="+cnt);


                }
                // do something with line
            }
        } finally {
            LineIterator.closeQuietly(it);
        }

//        System.out.println("persistObjList count=" + persistObjList.size());
//        for (String  gridObject : persistObjList) {
//            System.out.println(gridObject);
//        }
//
//        System.out.println("trxList count=" + trxList.size());
//        for (String  trx : trxList) {
//            System.out.println(trx);
//        }

//        System.out.println("trxList count=" + trxList.size());
//        for (String  trx : trxList) {
//            System.out.println(trx);
//        }

        System.out.println("\r\n****************************************************************\r\n");
        System.out.println("successCnt=" + successCnt);
        System.out.println("errorCnt=" + errorCnt);
        System.out.println("insert count=" + insertList.size());
        System.out.println("updateOther count=" + updateOther.size());
        System.out.println("updateCallHistory count=" + updateCallHistory.size());
        System.out.println("persistObjList count=" + persistObjList.size());
//        for (String  trx : updateCallHistory) {
//            String key = parse(trx);
//            if( txnLookupMap.get(key)==null ) {
//                System.out.println("Insert not found for id=" + key);
//            }
//            // System.out.println(trx);
//        }

    }

    public String parse(String line) {
        int end = line.lastIndexOf("'");
        int start = end - 36;
        String key = line.substring(start, end);
        return key;
    }

}
