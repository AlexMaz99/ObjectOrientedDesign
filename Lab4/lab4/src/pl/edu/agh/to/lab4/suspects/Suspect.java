package pl.edu.agh.to.lab4.suspects;

public abstract class Suspect {
    private final String name;
    private final String surname;
    private int age;

    public Suspect(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public String display() {
        return name + " " + surname;
    }

    public abstract boolean canBeAccused();
}
