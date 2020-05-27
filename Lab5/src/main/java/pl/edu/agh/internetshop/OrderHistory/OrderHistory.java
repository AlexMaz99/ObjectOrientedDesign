package pl.edu.agh.internetshop.OrderHistory;

import pl.edu.agh.internetshop.Order;
import pl.edu.agh.internetshop.SearchStrategy.SearchStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderHistory implements OrderHistoryInterface {

    private static OrderHistory orderHistory = null;
    private List<Order> orders;

    public OrderHistory(){
        this.orders = new ArrayList<>();
    }

    public static OrderHistory getInstance(){
        if(OrderHistory.orderHistory == null){
            OrderHistory.orderHistory = new OrderHistory();
        }
        return OrderHistory.orderHistory;
    }


    @Override
    public void addOrder(Order order) {
        orders.add(order);
    }

    @Override
    public List<Order> findOrderByStrategy(SearchStrategy strategy) {

        return this.orders.stream()
                .filter(strategy::filter)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> getOrders() {
        return this.orders;
    }

}
