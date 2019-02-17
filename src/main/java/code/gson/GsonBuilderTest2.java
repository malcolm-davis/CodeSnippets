package code.gson;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonBuilderTest2 {

    public static void main(String[] args) {
        System.out.println("Start: GsonBuilderTest ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        GsonBuilderTest2 test = new GsonBuilderTest2();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    // https://futurestud.io/tutorials/how-to-deserialize-a-list-of-polymorphic-objects-with-gson
    private void run() {
        List<Animal> animals = new ArrayList<>();
        animals.add(new Dog("dog1", true));
        animals.add(new Dog("dog2", false));
        animals.add(new Cat("cat1", false));

        // adding all different container classes with their flag
        final RuntimeTypeAdapterFactory<Animal> typeFactory = RuntimeTypeAdapterFactory
                .of(Animal.class, "type") // Here you specify which is the parent class and what field particularizes the child class.
                .registerSubtype(Dog.class, "dog") // if the flag equals the class name, you can skip the second parameter. This is only necessary, when the "type" field does not equal the class name.
                .registerSubtype(Cat.class, "cat");

        Gson gson = new GsonBuilder().registerTypeAdapterFactory(typeFactory).setPrettyPrinting().create();


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
