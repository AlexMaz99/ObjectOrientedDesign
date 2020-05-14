package pl.edu.agh.to.lab4.searching;

import pl.edu.agh.to.lab4.suspects.Suspect;

public class NameSearchStrategy implements SearchStrategy{
    private String name;

    public NameSearchStrategy(String name) {
        this.name = name;
    }

    @Override
    public boolean filter(Suspect suspect) {
        return suspect.getName().equals(this.name);
    }
}
