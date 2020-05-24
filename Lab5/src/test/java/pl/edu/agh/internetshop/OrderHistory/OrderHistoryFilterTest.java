package pl.edu.agh.internetshop.OrderHistory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.edu.agh.internetshop.Address;
import pl.edu.agh.internetshop.Order;
import pl.edu.agh.internetshop.OrderHistory.OrderHistory;
import pl.edu.agh.internetshop.OrderHistory.OrderHistoryInterface;
import pl.edu.agh.internetshop.Product;
import pl.edu.agh.internetshop.SearchStrategy.ClientNameSearchStrategy;
import pl.edu.agh.internetshop.SearchStrategy.CompositeSearchStrategy;
import pl.edu.agh.internetshop.SearchStrategy.PriceSearchStrategy;
import pl.edu.agh.internetshop.SearchStrategy.ProductNameSearchStrategy;
import pl.edu.agh.internetshop.Shipment.Shipment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class OrderHistoryFilterTest {

    private static final String customerName1 = "Adam Smith";
    private static final String customerName2 = "Adam Nowak";

    private static final BigDecimal productPrice1 = BigDecimal.valueOf(1.2);
    private static final BigDecimal productPrice2 = BigDecimal.valueOf(1.9);

    private static final String productName1 = "Milk";
    private static final String productName2 = "Apple";

    @BeforeAll
    static void addMockOrders() {

        Product product1 = new Product(productName1, productPrice1);
        Product product2 = new Product(productName2, productPrice2);

        List<Product> list1 = new ArrayList<Product>(Arrays.asList(product1, product1, product2));
        List<Product> list2 = new ArrayList<Product>(Arrays.asList(product1, product1));


        Order order1 = new Order(list1);
        Order order2 = new Order(list2);

        Address address1 = mock(Address.class);
        given(address1.getName()).willReturn(customerName1);

        Address address2 = mock(Address.class);
        given(address2.getName()).willReturn(customerName2);

        Shipment shipment1 = mock(Shipment.class);
        given(shipment1.getRecipientAddress()).willReturn(address1);

        Shipment shipment2 = mock(Shipment.class);
        given(shipment2.getRecipientAddress()).willReturn(address2);

        order1.setShipment(shipment1);
        order2.setShipment(shipment2);
    }


    @Test
    public void testClientNameSearch() {

        // given
        ClientNameSearchStrategy searchStrategy = new ClientNameSearchStrategy(customerName1);
        OrderHistory orderHistory = OrderHistory.getInstance();

        // when
        List<Order> actualOrders = orderHistory.findOrderByStrategy(searchStrategy);
        List<Order> expectedOrders = orderHistory
                .getOrders()
                .stream()
                .filter(order -> order.getShipment().getRecipientAddress().getName().equals(customerName1))
                .collect(Collectors.toList());


        // then
        assertEquals(actualOrders.size(), expectedOrders.size());
        assertEquals(actualOrders.get(0), expectedOrders.get(0));


    }

    @Test
    public void testProductPriceSearch() {

        // given
        BigDecimal expectedPrice = BigDecimal.valueOf(1.2).add(BigDecimal.valueOf(1.2));
        PriceSearchStrategy searchStrategy = new PriceSearchStrategy(expectedPrice);
        OrderHistory orderHistory = OrderHistory.getInstance();

        // when
        List<Order> actualOrders = orderHistory.findOrderByStrategy(searchStrategy);
        List<Order> expectedOrders = orderHistory
                .getOrders()
                .stream()
                .filter(order -> order.getPrice().equals(expectedPrice))
                .collect(Collectors.toList());


        // then
        assertEquals(actualOrders.size(), expectedOrders.size());
        assertEquals(actualOrders.get(0), expectedOrders.get(0));

    }

    @Test
    public void testProductNameSearch() {

        // given
        ProductNameSearchStrategy searchStrategy = new ProductNameSearchStrategy(productName2);
        OrderHistory orderHistory = OrderHistory.getInstance();

        // when
        List<Order> actualOrders = orderHistory.findOrderByStrategy(searchStrategy);
        List<Order> expectedOrders = orderHistory
                .getOrders()
                .stream()
                .filter(order ->
                        order.getProducts()
                                .stream()
                                .anyMatch(product -> product.getName().equals(productName2))
                )
                .collect(Collectors.toList());


        // then
        assertEquals(actualOrders.size(), expectedOrders.size());
        assertEquals(actualOrders.get(0), expectedOrders.get(0));


    }

    @Test
    public void testCompositeSearch() {

        // given
        ProductNameSearchStrategy productSearchStrategy = new ProductNameSearchStrategy(productName2);
        ClientNameSearchStrategy clientSearchStrategy = new ClientNameSearchStrategy(customerName1);
        CompositeSearchStrategy searchStrategy = new CompositeSearchStrategy();
        searchStrategy.addStrategy(productSearchStrategy);
        searchStrategy.addStrategy(clientSearchStrategy);
        OrderHistory orderHistory = OrderHistory.getInstance();

        // when
        List<Order> actualOrders = orderHistory.findOrderByStrategy(searchStrategy);
        List<Order> expectedOrders = orderHistory
                .getOrders()
                .stream()
                .filter(order -> order.getShipment().getRecipientAddress().getName().equals(customerName1))
                .filter(order ->
                        order.getProducts()
                                .stream()
                                .anyMatch(product -> product.getName().equals(productName2))
                )
                .collect(Collectors.toList());


        // then
        assertEquals(actualOrders.size(), expectedOrders.size());
        assertEquals(actualOrders.get(0), expectedOrders.get(0));


    }





}
