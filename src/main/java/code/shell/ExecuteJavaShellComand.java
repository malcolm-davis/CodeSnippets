package code.shell;


import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class ExecuteJavaShellComand {

    public static void main(String[] args) {
        ExecuteJavaShellComand.executeCommand("com.elasolutions.shell.TestClass");
    }

    private static void executeCommand(String command) {
        // Setup for the java call

        // build Java location
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome +
                File.separator + "bin" +
                File.separator + "java";

        // classpath is used for situations where there is not a single jar situation
        String classpath = System.getProperty("java.class.path");

        // Another way to get classpath?
        // URL[]urls = ((URLClassLoader)Thread.currentThread().getContextClassLoader()).getURLs();
        // Process.exec(javaBin, "-classpath", urls.join(":"), CLASS_TO_BE_EXECUTED)
        // reconstruct the classpath of current application.
// Sample output of classloader
//        URL[]urls = ((URLClassLoader)Thread.currentThread().getContextClassLoader()).getURLs();
//        for (URL url : urls) {
//            System.out.println(url.toString());
//            System.out.println("\t"+url.getFile());
//        }

        // Note: This assumes that all the dependent jars are in CodeSnippets
        String jar = "C:\\dev\\projects\\_code\\_general\\CodeSnippets\\target\\CodeSnippets.jar";

        // Note: This only works when decomposed into separate components and not a single line
        // ProcessBuilder builder = new ProcessBuilder( javaBin, "-cp", classpath, fullCmd);
        ProcessBuilder builder = new ProcessBuilder( javaBin, "-jar", jar, command);

        for( String cmdLine : builder.command() ) {
            System.out.println(cmdLine);
        }

        // Display the error that occurred during execution of the process
        builder.redirectErrorStream(true);

        try {
            Process p  = builder.start();
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                System.out.println(line);
                Thread.sleep(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}