package code.json;

import java.util.List;

import com.jayway.jsonpath.JsonPath;

public class JsonQueryExample {

    public static void main(String[] args) {
        System.out.println("Start: JsonQueryExample ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        JsonQueryExample test = new JsonQueryExample();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis() - start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        String jsonDispatchGroups = "{" +
                "records: [" +
                "    {" +
                "    id: \"recwhtH2PsZ19i2dU\"," +
                "    fields: {" +
                "    Dispatch Group: \"enid\"" +
                "    }," +
                "    createdTime: \"2018-08-02T21:08:07.000Z\"" +
                "    }" +
                "]" +
                "}" +
                "";

        List<String> groups = JsonPath.read(jsonDispatchGroups, "$..fields[*]");
        for(String group : groups) {
            System.out.println(group);
        }
    }

}
