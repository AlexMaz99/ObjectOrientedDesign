package pl.edu.agh.internetshop;

import org.junit.jupiter.api.Test;
import pl.edu.agh.internetshop.SearchStrategy.ClientNameSearchStrategy;
import pl.edu.agh.internetshop.SearchStrategy.CompositeSearchStrategy;
import pl.edu.agh.internetshop.SearchStrategy.PriceSearchStrategy;
import pl.edu.agh.internetshop.SearchStrategy.ProductNameSearchStrategy;
import pl.edu.agh.internetshop.Shipment.Shipment;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class SearchStrategyTest {

    @Test
    public void clientNameSearchStrategyTest() throws Exception{
        // given
        String wantedName = "Name Wanted";

        ClientNameSearchStrategy searchStrategy = new ClientNameSearchStrategy(wantedName);

        Address address1 = mock(Address.class);
        given(address1.getName()).willReturn(wantedName);
        Shipment shipment1 = mock(Shipment.class);
        given(shipment1.getRecipientAddress()).willReturn(address1);
        Order goodOrder = mock(Order.class);
        given(goodOrder.getShipment()).willReturn(shipment1);

        Address address2= mock(Address.class);
        given(address2.getName()).willReturn("wrong name");
        Shipment shipment2 = mock(Shipment.class);
        given(shipment2.getRecipientAddress()).willReturn(address2);
        Order badOrder = mock(Order.class);
        given(badOrder.getShipment()).willReturn(shipment2);


        // when then
        assertTrue(searchStrategy.filter(goodOrder));
        assertFalse(searchStrategy.filter(badOrder));

    }

    @Test
    public void productNameSearchStrategyTest() throws Exception{
        // given
        String wantedName = "Name Wanted";

        ProductNameSearchStrategy searchStrategy = new ProductNameSearchStrategy(wantedName);

        Product product1 = mock(Product.class);
        given(product1.getName()).willReturn(wantedName);
        Order goodOrder = new Order(Collections.singletonList(product1));

        Product product2 = mock(Product.class);
        given(product2.getName()).willReturn("wrong name");
        Order badOrder = new Order(Collections.singletonList(product2));

        // when then
        assertTrue(searchStrategy.filter(goodOrder));
        assertFalse(searchStrategy.filter(badOrder));

    }


    @Test
    public void priceSearchStrategyTest() throws Exception{
        // given
        BigDecimal wantedPrice = BigDecimal.valueOf(2.1);

        PriceSearchStrategy searchStrategy = new PriceSearchStrategy(wantedPrice);

        Product product1 = mock(Product.class);
        given(product1.getPrice()).willReturn(wantedPrice);
        Order goodOrder = new Order(Collections.singletonList(product1));

        Product product2 = mock(Product.class);
        given(product2.getPrice()).willReturn(BigDecimal.valueOf(3.7));
        Order badOrder = new Order(Collections.singletonList(product2));

        // when then
        assertFalse(searchStrategy.filter(badOrder));
        assertTrue(searchStrategy.filter(goodOrder));
    }

    @Test
    public void CompositeSearchTest() {
        // given
        String wantedName = "Name Wanted";
        BigDecimal wantedPrice = BigDecimal.valueOf(2.1);

        PriceSearchStrategy priceSearchStrategy = new PriceSearchStrategy(wantedPrice);
        ProductNameSearchStrategy nameSearchStrategy = new ProductNameSearchStrategy(wantedName);
        CompositeSearchStrategy searchStrategy = new CompositeSearchStrategy();

        Product product1 = mock(Product.class);
        given(product1.getName()).willReturn(wantedName);
        given(product1.getPrice()).willReturn(wantedPrice);
        Order goodOrder = new Order(Collections.singletonList(product1));

        Product product2 = mock(Product.class);
        given(product2.getName()).willReturn("wrong name");
        given(product2.getPrice()).willReturn(wantedPrice);
        Order badOrder1 = new Order(Collections.singletonList(product2));

        Product product3 = mock(Product.class);
        given(product3.getName()).willReturn(wantedName);
        given(product3.getPrice()).willReturn(BigDecimal.valueOf(3.7));
        Order badOrder2 = new Order(Collections.singletonList(product3));

        // when
        searchStrategy.addStrategy(priceSearchStrategy);
        searchStrategy.addStrategy(nameSearchStrategy);

        // then
        assertTrue(searchStrategy.filter(goodOrder));
        assertFalse(searchStrategy.filter(badOrder1));
        assertFalse(searchStrategy.filter(badOrder2));
    }
}
