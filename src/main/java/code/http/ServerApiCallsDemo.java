package code.http;

import java.io.File;

import org.apache.http.message.BasicNameValuePair;
import org.slf4j.LoggerFactory;


public class ServerApiCallsDemo {
    public static void main(String[] args) {
        System.out.println("Start: ServerApiCallsDemo ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        ServerApiCallsDemo test = new ServerApiCallsDemo();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        ConnectionConfiguration connection =
                ConnectionConfiguration.newConfiguration(
                    "http", "localhost", 8080, "dempApp", null, null);
        ServerApiCalls.INSTANCE.setConnectionConfig(connection);


        File file = new File("C:\\temp\\_cdr\\CallDetailRecord.xsd");
        System.out.println(file.getName());

        System.out.println("File upload="+ServerApiCalls.INSTANCE.postFile(
            "FileUploadServlet", "PostFile", file,
            new BasicNameValuePair("filename", file.getName()),
            new BasicNameValuePair("description", "file test description")));

        System.out.println("Ping="+ServerApiCalls.INSTANCE.ping());
        System.out.println("getCity(\"35444\")="+ServerApiCalls.INSTANCE.getCity("35444"));
        System.out.println("getConnectionStatus()="+ServerApiCalls.INSTANCE.getConnectionStatus());
        System.out.println("echo="+ServerApiCalls.INSTANCE.methodCallGet("echo/", new BasicNameValuePair("echo", "response")));
        System.out.println("kill="+ServerApiCalls.INSTANCE.methodCallGet("kill/", new BasicNameValuePair("kill", "true")));
    }

    private static final org.slf4j.Logger LOG =
            LoggerFactory.getLogger(ServerApiCallsDemo.class.getName());
}
