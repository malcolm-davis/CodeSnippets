package code.unirest;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class UnirestDemo {

    public static void main(String[] args) {
        System.out.println("Start: UnirestDemo ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        UnirestDemo test = new UnirestDemo();
        try {
            test.run();
        } catch (Exception excep) {
            excep.printStackTrace();
        }

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    @SuppressWarnings("static-method")
    private void run() throws UnirestException, IOException {
        // see http://unirest.io/java.html
        HttpResponse<String> stringResponse = Unirest.post("http://localhost:8080/dempApp/Ping")
                .header("accept", "application/json").asString();
        System.out.println(IOUtils.toString(stringResponse.getRawBody(), Charset.defaultCharset()));

        stringResponse = Unirest.post("http://localhost:8080/dempApp/echo")
                .header("accept", "application/json")
                .field("echo", "value response").asString();
        System.out.println(IOUtils.toString(stringResponse.getRawBody(), Charset.defaultCharset()));
    }

}
