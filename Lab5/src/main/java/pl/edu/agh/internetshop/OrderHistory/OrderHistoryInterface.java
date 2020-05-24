package pl.edu.agh.internetshop.OrderHistory;

import pl.edu.agh.internetshop.Order;
import pl.edu.agh.internetshop.SearchStrategy.SearchStrategy;

import java.math.BigDecimal;
import java.util.List;

public interface OrderHistoryInterface {

    public void addOrder(Order order);
    public List<Order> findOrderByStrategy(SearchStrategy strategy);
    public List<Order> getOrders();

}
