package code.gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class CustomDeserializer implements JsonDeserializer<List<Animal>> {

    private static Map<String, Class> map = new TreeMap<String, Class>();

    static {
        map.put("Animal", Animal.class);
        map.put("Dog", Dog.class);
        map.put("Cat", Cat.class);
    }


    @Override
    public List<Animal> deserialize(JsonElement json, Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {

        List<Animal> list = new ArrayList<Animal>();
        JsonArray ja = json.getAsJsonArray();

        for (JsonElement je : ja) {
            String type = je.getAsJsonObject().get("isA").getAsString();
            Class c = map.get(type);
            if (c == null) {
                throw new RuntimeException("Unknown class: " + type);
            }
            list.add(context.deserialize(je, c));
        }
        return list;
    }
}
