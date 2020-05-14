package pl.edu.agh.to.lab4.suspects;

public class Student extends Suspect {
    private final String index;

    public Student(String name, String surname, int age, String index) {
        super(name, surname, age);
        this.index = index;
    }

    @Override
    public boolean canBeAccused() {
        return true;
    }

    public String getIndex() {
        return index;
    }
}
