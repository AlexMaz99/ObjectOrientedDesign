package pl.edu.agh.internetshop.OrderHistory;

import org.junit.jupiter.api.Test;
import pl.edu.agh.internetshop.Order;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class OrderHistoryTest {

    @Test
    void addAndGetOrderTest() {
        // given
        Order expectedOrder = mock(Order.class);
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.addOrder(expectedOrder);

        // when
        Order actualOrder = orderHistory.getOrders().get(0);

        // then
        assertEquals(expectedOrder, actualOrder);
    }
}