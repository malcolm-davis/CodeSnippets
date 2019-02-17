package code.json;

import org.apache.commons.lang3.StringUtils;

public class SimpleParserFindDemo {

    public static void main(String[] args)  {
        System.out.println("Start: SimpleParserDemo ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        SimpleParserFindDemo test = new SimpleParserFindDemo();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run()  {
        // this runs 2x faster that the Jackson approach
        long start = System.currentTimeMillis();

        int begin = CONSOLE.indexOf("\"uuid\":");
        if(begin>0) {
            String json = CONSOLE.substring(begin);
            if( exist(json, "userId", "mdavis") ) {
                System.out.println(find(json, "uuid"));
                System.out.println(find(json, "displayId"));
                System.out.println(find(json, "status"));
                System.out.println(find(json, "callback"));
            }
        }
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
    }

    private boolean exist(String json, String field, String key) {
        String value = find(json, field);
        if( StringUtils.isBlank(value) ) {
            return false;
        }
        String keyLookup = "\""+key+"\"";
        return keyLookup.equalsIgnoreCase(value);
    }

    private String find(String json, String field)  {
        String fieldLookup = "\""+field+"\":";
        int index = json.indexOf(fieldLookup);
        if( index<0  ) {
            return "";
        }
        int end = json.indexOf(",",index);
        if( end<0 || (end<index) ) {
            return "";
        }
        return StringUtils.defaultIfBlank(json.substring(index+fieldLookup.length(), end), "");

    }

    static final String CONSOLE = "<< MESSAGE\r\n" +
            "destination:/user/topic/v1/call/ad420e6e-67bd-46ca-8134-9e35c0017aaf/edit\r\n" +
            "content-type:application/json;charset=UTF-8\r\n" +
            "subscription:sub-4\r\n" +
            "message-id:eorzdugb-60830\r\n" +
            "content-length:740\r\n"
            + "{\"uuid\":\"be9ede9d-b33f-4e3a-9bde-26255506b0e4\",\r\n" +
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
