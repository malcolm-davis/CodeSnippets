package code.directory;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

public class ScanFilesIndirectory {

    public static void main(String[] args) throws IOException {
        System.out.println("Start: ScanFilesIndirectory ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        ScanFilesIndirectory test = new ScanFilesIndirectory();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() throws IOException {

//        File directory = new File("C:\\temp\\_config\\config\\sys");
//        final String[] files = directory.list();
//        for(final String fileName : files) {
//            System.out.println(fileName);
//        }
//
//        File dir = new File("C:\\temp\\_config");
//        System.out.println("Getting all files in " + dir.getCanonicalPath() + " including those in subdirectories");
//        List<File> files = (List<File>) FileUtils.listFiles(dir, new IOFileFilter() {
//            @Override
//            public boolean accept(File file) {
//                return file.getName().endsWith(".java");
//            }
//            @Override
//            public boolean accept(File dir, String name) {
//                return false;
//            }
//
//        }, TrueFileFilter.INSTANCE);
//        for (File file : files) {
//            // System.out.println("file: " + file.getCanonicalPath());
//            System.out.println("file: " + file.getName());
//        }

        File dir = new File("C:\\temp\\_config\\classes\\");
        System.out.println("Getting all files in " + dir.getCanonicalPath() + " including those in subdirectories");
        List<File> files = (List<File>) FileUtils.listFiles(dir, new IOFileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".xml");
            }
            @Override
            public boolean accept(File dir, String name) {
                return false;
            }

        }, TrueFileFilter.INSTANCE);
        for (File file : files) {
            // System.out.println("file: " + file.getCanonicalPath());
            System.out.println("file: " + file.getName());
        }

    }

}
