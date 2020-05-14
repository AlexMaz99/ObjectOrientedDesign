package pl.edu.agh.to.lab4.suspects;

import pl.edu.agh.to.lab4.suspects.Suspect;

public class CracowCitizen extends Suspect {

    public CracowCitizen(String name, String surname, int age) {
        super(name, surname, age);
    }

    @Override
    public boolean canBeAccused() {
        return this.getAge() > 18;
    }

}
