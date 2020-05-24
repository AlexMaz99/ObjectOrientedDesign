package pl.edu.agh.internetshop.SearchStrategy;

import pl.edu.agh.internetshop.Order;
import pl.edu.agh.internetshop.Product;

public class ProductNameSearchStrategy implements SearchStrategy {

    private final String productName;

    public ProductNameSearchStrategy(String productName) {
        this.productName=productName;
    }

    @Override
    public boolean filter(Order order) {
        return order.getProducts()
                .stream()
                .map(Product::getName)
                .anyMatch(prodName-> prodName.equals(this.productName));
    }
}
