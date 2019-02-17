package code.gson;


public class Animal {
    public Animal(String name, String type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Animal [name=" + name + ", type=" + type + "]";
    }

    protected String name;

    protected String type;

    protected String isA="Animal";
}
