package pl.edu.agh.to.lab4;

import pl.edu.agh.to.lab4.dataProviders.CompositeAggregate;
import pl.edu.agh.to.lab4.searching.SearchStrategy;
import pl.edu.agh.to.lab4.suspects.Suspect;

import java.util.ArrayList;
import java.util.Iterator;

public class Finder {
    private final CompositeAggregate compositeAggregate;

    public Finder(Iterator<? extends Suspect> ... iterators) {
        this.compositeAggregate = new CompositeAggregate(iterators);
    }

    public void display(SearchStrategy searchStrategy) {
        ArrayList<Suspect> suspects = new ArrayList<Suspect>();

        while(compositeAggregate.hasNext()){
            Iterator<?extends Suspect> iterator = compositeAggregate.iterator();
            while(iterator.hasNext()){
                Suspect suspect = iterator.next();
                if (suspect.canBeAccused() && searchStrategy.filter(suspect)){
                    suspects.add(suspect);
                }
                if (suspects.size() >= 10) break;
            }
            if (suspects.size() >= 10) break;
        }

        System.out.println("Znalazlem " + suspects.size() + " pasujacych podejrzanych!");

        for(Suspect suspect: suspects){
            System.out.println(suspect.display());
        }
    }
}
