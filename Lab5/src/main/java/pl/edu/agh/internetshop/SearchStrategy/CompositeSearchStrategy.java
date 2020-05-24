package pl.edu.agh.internetshop.SearchStrategy;

import pl.edu.agh.internetshop.Order;

import java.util.ArrayList;
import java.util.Collection;

public class CompositeSearchStrategy implements SearchStrategy {
    Collection<SearchStrategy> searchStrategies = new ArrayList<>();

    public void addStrategy(SearchStrategy searchStrategy) {
        searchStrategies.add(searchStrategy);
    }

    @Override
    public boolean filter(Order order) {
        return this.searchStrategies
                .stream()
                .allMatch(searchStrategy -> searchStrategy.filter(order));
    }
}
