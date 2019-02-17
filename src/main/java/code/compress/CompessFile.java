package code.compress;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.elasolutions.utils.file.SyncCompressFileWrite;

public class CompessFile {
    public static void main(String[] args) throws Exception {
        System.out.println("Start: CompessFile ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        CompessFile test = new CompessFile();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() throws Exception  {
        File fileIn = new File("C:\\ecx\\test\\performance_website_data\\2018.06.05.09.44.01.result.xml.txt");
        File fileCompress = new File("C:\\ecx\\test\\performance_website_data\\2018.06.05.09.44.01.result.xml.bzip");

        List<String>toCompressList = org.apache.commons.io.FileUtils.readLines(fileIn, StandardCharsets.UTF_8);

        SyncCompressFileWrite write = SyncCompressFileWrite.newCompression(fileCompress);

        for (String line : toCompressList) {
            write.write(line);
        }

        write.close();
    }

}
