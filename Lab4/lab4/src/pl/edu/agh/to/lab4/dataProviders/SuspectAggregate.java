package pl.edu.agh.to.lab4.dataProviders;

import pl.edu.agh.to.lab4.suspects.Suspect;

import java.util.Iterator;

public interface SuspectAggregate {
    Iterator<? extends Suspect> iterator();
}
