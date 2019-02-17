package code.higharch;


public class Animal {
    public Animal() {
        System.out.println("Animal");
    }

    @Override
    public String toString() {
        return "Animal [name=" + name + ", type=" + type + "]";
    }

    protected String name;

    protected String type;
}
