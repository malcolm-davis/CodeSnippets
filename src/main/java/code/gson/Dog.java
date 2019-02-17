package code.gson;


public class Dog extends Animal {

    public Dog(String name, boolean playsCatch) {
        super(name, "dog");
        this.playsCatch = playsCatch;
        isA = "Dog";

    }

    @Override
    public String toString() {
        return "Dog [name=" + name + ", type=" + type + ", playsCatch=" + playsCatch + "]";
    }

    private final boolean playsCatch;
}
