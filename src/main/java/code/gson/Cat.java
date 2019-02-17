package code.gson;


public class Cat extends Animal {

    public Cat(String name, boolean chasesLaser) {
        super(name, "cat");
        this.chasesLaser = chasesLaser;
        isA = "Cat";
    }

    @Override
    public String toString() {
        return "Cat [name=" + name + ", type=" + type + ", chasesLaser=" + chasesLaser + "]";
    }

    private final boolean chasesLaser;
}
