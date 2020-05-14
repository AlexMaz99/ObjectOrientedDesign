package pl.edu.agh.to.lab4.searching;

import pl.edu.agh.to.lab4.suspects.Suspect;

public class AgeSearchStrategy implements SearchStrategy {
    private int age;

    public AgeSearchStrategy(int age) {
        this.age = age;
    }

    @Override
    public boolean filter(Suspect suspect) {
        return suspect.getAge() > age;
    }
}
