package pl.edu.agh.internetshop.SearchStrategy;

import pl.edu.agh.internetshop.Order;

import java.math.BigDecimal;

public class PriceSearchStrategy implements SearchStrategy {

    private final BigDecimal price;

    public PriceSearchStrategy(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean filter(Order order) {

        return order.getPrice().equals(price);

    }
}
