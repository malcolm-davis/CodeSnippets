package code.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonParserExample {

    public static void main(String[] args) {
        System.out.println("Start: JsonParserExample ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        JsonParserExample test = new JsonParserExample();
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
                "}";

        String jsonDispatchGroupsMod = jsonDispatchGroups.replace(" Dispatch Group:", " 'Dispatch Group':");
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonTree = jsonParser.parse(jsonDispatchGroupsMod);

        if(jsonTree.isJsonObject()){
            JsonObject jsonObject = jsonTree.getAsJsonObject();

            JsonElement records = jsonObject.get("records");

            if(records!=null && records.isJsonArray()) {
                int size = records.getAsJsonArray().size();
                System.out.println("records array size=" + size);

                for(int index = 0; index < size; index++) {
                    JsonElement record = records.getAsJsonArray().get(index);

                    if(record!=null && record.isJsonObject() ) {
                        dump("record", record);
                        JsonElement id = record.getAsJsonObject().get("id");
                        dump("id", id);

                        JsonElement fields = record.getAsJsonObject().get("fields");
                        if(fields!=null && fields.isJsonObject()) {
                            dump("fields", fields);
                            JsonElement dispatchGroup = fields.getAsJsonObject().get("Dispatch Group");
                            dump("dispatchGroup", dispatchGroup);
                        }

                    }
                }
            }


        }
    }

    private void dump(String elementName, JsonElement field) {
        System.out.println(elementName);
        System.out.println("\t"+field.toString());
        System.out.println("\t"+field.isJsonArray() + ", " + field.isJsonNull() + ", " +  field.isJsonObject() + ", " +  field.isJsonPrimitive());
    }

}
