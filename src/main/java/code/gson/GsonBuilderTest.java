package code.gson;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonBuilderTest {

    public static void main(String[] args) {
        System.out.println("Start: GsonBuilderTest ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        GsonBuilderTest test = new GsonBuilderTest();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        List<Animal> animals = new ArrayList<>();
        animals.add(new Dog("dog1", true));
        animals.add(new Dog("dog2", false));
        animals.add(new Cat("cat1", false));

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(animals.getClass(), new CustomDeserializer())
                .registerTypeAdapter(animals.getClass(), new CustomSerializer())
                .setPrettyPrinting().create();


        String json = gson.toJson(animals);
        System.out.println(json);

//        java.lang.reflect.Type listType = new TypeToken<List<Animal>>(){}.getType();
//        List<Animal> animalsReconstuted = gson.fromJson(json,listType);

        List<Animal> animalsReconstuted = gson.fromJson(json, List.class);

        for (Object animal : animalsReconstuted) {
            if (animal instanceof Dog) {
                Dog dog = (Dog)animal;
                System.out.println("dog=" + dog.toString());
            }
            if (animal instanceof Cat) {
                Cat cat = (Cat)animal;
                System.out.println("Cat=" + cat.toString());
            }
            System.out.println(animal.toString());
        }
    }

}
