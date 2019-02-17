package code.gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class CustomSerializer  implements JsonSerializer<ArrayList<Animal>> {

    private static Map<String, Class> map = new TreeMap<String, Class>();

    static {
        map.put("Animal", Animal.class);
        map.put("Dog", Dog.class);
        map.put("Cat", Cat.class);
    }

    @Override
    public JsonElement serialize(ArrayList<Animal> src, Type typeOfSrc,
            JsonSerializationContext context) {
        if (src == null) {
            return null;
        }
        JsonArray ja = new JsonArray();

        for (Animal bc : src) {
            if (bc instanceof Dog) {
                System.out.println("dog=" + bc.toString());
            }

            Class c = map.get(bc.isA);
            if (c == null) {
                throw new RuntimeException("Unknow class: " + bc.isA);
            }
            ja.add(context.serialize(bc, c));

        }
        return ja;
    }
}
