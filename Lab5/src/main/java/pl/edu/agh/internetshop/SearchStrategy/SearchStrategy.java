package pl.edu.agh.internetshop.SearchStrategy;

import pl.edu.agh.internetshop.Order;

public interface SearchStrategy {
    boolean filter(Order order);
}