package pl.edu.agh.to.lab4.dataProviders;

import pl.edu.agh.to.lab4.dataProviders.SuspectAggregate;
import pl.edu.agh.to.lab4.suspects.Suspect;

import java.util.Iterator;

public class CompositeAggregate implements SuspectAggregate {
    private final Iterator<?extends Suspect> iterators[];
    private  int currentIterator;

    public CompositeAggregate(Iterator<? extends Suspect> ... iterators){
        this.iterators = iterators;
        this.currentIterator = 0;
    }

    public boolean hasNext(){
        while(currentIterator < iterators.length && !iterators[currentIterator].hasNext()){
            currentIterator ++;
        }
        return currentIterator < iterators.length;
    }

    @Override
    public Iterator<? extends Suspect> iterator() {
        while(currentIterator < iterators.length && !iterators[currentIterator].hasNext()){
            currentIterator ++;
        }
        return iterators[currentIterator];
    }
}
