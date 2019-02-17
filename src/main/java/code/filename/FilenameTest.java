package code.filename;

import java.io.File;

import org.apache.commons.io.FilenameUtils;

public class FilenameTest {

    public static void main(String[] args) {
        System.out.println("Start: FilenameTest ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        FilenameTest test = new FilenameTest();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        String name = "C:\\dev\\projects\\_code\\_regressionTesting\\_testing\\logs\\localhost.log";
        File filename = new File(name);
        System.out.println(filename.getAbsolutePath());
        System.out.println(filename.getName());
        System.out.println(FilenameUtils.getBaseName(name));
        System.out.println(FilenameUtils.getName(name));
    }

}
