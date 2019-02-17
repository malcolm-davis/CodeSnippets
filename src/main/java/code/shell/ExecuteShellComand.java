package code.shell;


import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ExecuteShellComand {

    public static void main(String[] args) {

        ExecuteShellComand obj = new ExecuteShellComand();

        String domainName = "google.com";

        //in mac oxs
        // String command = "ping -c 3 " + domainName;

        //in windows
        String command = "ping -n 3 " + domainName;

        ExecuteShellComand.executeCommand(command);
    }

    private static void executeCommand(String command) {

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
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