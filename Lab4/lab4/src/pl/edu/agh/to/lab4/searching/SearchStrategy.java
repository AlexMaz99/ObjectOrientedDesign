package pl.edu.agh.to.lab4.searching;

import pl.edu.agh.to.lab4.suspects.Suspect;

public interface SearchStrategy {
    boolean filter(Suspect suspect);
}
