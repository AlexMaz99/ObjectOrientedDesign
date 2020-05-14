package pl.edu.agh.to.lab4.dataProviders;

import pl.edu.agh.to.lab4.suspects.Prisoner;
import pl.edu.agh.to.lab4.suspects.Suspect;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class FlatIterator implements Iterator<Suspect> {
    protected final Map<String, Collection<Prisoner>> prisoners;
    private Prisoner currentPrisoner = null;
    protected int numberOfPrisoners = 0;

    public FlatIterator(Map<String, Collection<Prisoner>> prisoners){
        this.prisoners = prisoners;
    }

    @Override
    public boolean hasNext() {
        return this.numberOfPrisoners != 0;
    }

    @Override
    public Suspect next() {
        this.numberOfPrisoners --;
        for (String key: this.prisoners.keySet()){
            for (Prisoner prisoner: this.prisoners.get(key)){
                if (this.currentPrisoner == null){
                    this.currentPrisoner = prisoner;
                    return prisoner;
                }
                else if (this.currentPrisoner == prisoner){
                    this.currentPrisoner = null;
                }
            }
        }
        return null;
    }
}