package pl.edu.agh.to.lab4.searching;

import pl.edu.agh.to.lab4.suspects.Suspect;

import java.util.ArrayList;
import java.util.Collection;

public class CompositeSearchStrategy implements SearchStrategy{
    Collection<SearchStrategy> searchStrategies = new ArrayList<>();
    public void addStrategy(SearchStrategy searchStrategy){
        searchStrategies.add(searchStrategy);
    }

    @Override
    public boolean filter(Suspect suspect) {
        for(SearchStrategy searchStrategy: searchStrategies)
            if(!searchStrategy.filter(suspect))
                return false;
        return true;
    }
}
