package pl.edu.agh.internetshop.SearchStrategy;

import pl.edu.agh.internetshop.Order;

public class ClientNameSearchStrategy implements SearchStrategy {

    private final String clientName;

    public ClientNameSearchStrategy(String clientName) {
        this.clientName=clientName;
    }

    @Override
    public boolean filter(Order order) {
        return order.getShipment()
                .getRecipientAddress()
                .getName()
                .equals(clientName);
    }
}
