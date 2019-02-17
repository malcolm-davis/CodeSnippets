package code.json;

import org.apache.commons.beanutils.PropertyUtils;
import org.codehaus.jackson.map.ObjectMapper;

public class JacksonObjectPathDemo {

    public static void main(String[] args) throws Exception {
        System.out.println("Start: JacksonObjectPathDemo ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        JacksonObjectPathDemo test = new JacksonObjectPathDemo();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() throws Exception {
        // this runs half as fast as the json approach

        //actually it is a Map instance with maps-fields within
        ObjectMapper objectMapper = new ObjectMapper();

        long start = System.currentTimeMillis();
        Object jsonObj = objectMapper.readValue(JSON, Object.class);
        SysOut(jsonObj, "displayId");
        SysOut(jsonObj, "userId");
        SysOut(jsonObj, "status");
        SysOut(jsonObj, "callback");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
    }

    private void SysOut(Object jsonObj, String field) throws Exception {
        Object output = PropertyUtils.getProperty(jsonObj, field);
        System.out.println(field + "=" + ((output==null) ? "" : output.toString()));

    }

    static final String JSON = "{\r\n" +
            "\"uuid\":\"be9ede9d-b33f-4e3a-9bde-26255506b0e4\",\r\n" +
            "\"displayId\":22611,\r\n" +
            "\"userId\":\"mdavis\",\r\n" +
            "\"status\":\"CONNECTED\",\r\n" +
            "\"createdTimestamp\":1520007487085,\r\n" +
            "\"updatedTimestamp\":1520007511342,\r\n" +
            "\"trunkType\":\"E911\",\r\n" +
            "\"type\":\"INBOUND\",\r\n" +
            "\"typeDisplay\":\"e911\",\r\n" +
            "\"name\":\"WAGGONER, MINNIE\",\r\n" +
            "\"callback\":\"759-5599\",\r\n" +
            "\"preAli\":\"1998 BOONEVILLE HWY\",\r\n" +
            "\"acdAssignedAgent\":null,\r\n" +
            "\"clusterId\":\"00000000-0000-0000-0000-000000000001\",\r\n" +
            "\"participants\":{\"mdavis\":{\"joinedOn\":1520007511342,\r\n" +
            "\"leftOn\":null,\r\n" +
            "\"updatedOn\":1520007511342,\r\n" +
            "\"status\":\"CONNECTED\",\r\n" +
            "\"callback\":\"759-5599\",\r\n" +
            "\"typeDisplay\":\"e911\",\r\n" +
            "\"channelSuffix\":\"0000010b\",\r\n" +
            "\"muted\":false,\r\n" +
            "\"silentMonitor\":false}},\r\n" +
            "\"dispatchGroups\":[\"dispatchGroup1\"],\r\n" +
            "\"ringingUsers\":[],\r\n" +
            "\"ringingQueuesUsers\":[],\r\n" +
            "\"deafened\":false,\r\n" +
            "\"externalTransfer\":false,\r\n" +
            "\"onNetTransfer\":false\r\n" +
            "}\r\n";


}
