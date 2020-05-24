```java
package pl.edu.agh.internetshop;

import pl.edu.agh.internetshop.MoneyTransfer.MoneyTransfer;
import pl.edu.agh.internetshop.Shipment.Shipment;
import pl.edu.agh.internetshop.Shipment.ShipmentMethod;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


public class Order {
    private static final BigDecimal TAX_VALUE = BigDecimal.valueOf(1.23);
	private final UUID id;
    private final List<Product> products;
    private boolean paid;
    private Shipment shipment;
    private ShipmentMethod shipmentMethod;
    private PaymentMethod paymentMethod;

    public Order(List<Product> products) {
        this.products = products;
        id = UUID.randomUUID();
        paid = false;
    }

    public UUID getId() {
        return id;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public boolean isSent() {
        return shipment != null && shipment.isShipped();
    }

    public boolean isPaid() { return paid; }

    public Shipment getShipment() {
        return shipment;
    }

    public BigDecimal getPrice() {

        return null;
    }

    public BigDecimal getPriceWithTaxes() {
        return getPrice().multiply(TAX_VALUE).setScale(Product.PRICE_PRECISION, Product.ROUND_STRATEGY);
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public ShipmentMethod getShipmentMethod() {
        return shipmentMethod;
    }

    public void setShipmentMethod(ShipmentMethod shipmentMethod) {
        this.shipmentMethod = shipmentMethod;
    }

    public void send() {
        boolean sentSuccesful = getShipmentMethod().send(shipment, shipment.getSenderAddress(), shipment.getRecipientAddress());
        shipment.setShipped(sentSuccesful);
    }

    public void pay(MoneyTransfer moneyTransfer) {
        moneyTransfer.setCommitted(getPaymentMethod().commit(moneyTransfer));
        paid = moneyTransfer.isCommitted();
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }
}
```





```java
package pl.edu.agh.internetshop;

import pl.edu.agh.internetshop.MoneyTransfer.MoneyTransfer;
import pl.edu.agh.internetshop.Shipment.Shipment;
import pl.edu.agh.internetshop.Shipment.ShipmentMethod;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public class Order {
    private static final BigDecimal TAX_VALUE = BigDecimal.valueOf(1.23);
    private final UUID id;
    private final List<Product> products;
    private boolean paid;
    private Shipment shipment;
    private ShipmentMethod shipmentMethod;
    private PaymentMethod paymentMethod;

    public Order(List<Product> products) {
        this.products = products;
        id = UUID.randomUUID();
        paid = false;
    }

    public UUID getId() {
        return id;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public boolean isSent() {
        return shipment != null && shipment.isShipped();
    }

    public boolean isPaid() {
        return paid;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public BigDecimal getPrice() {
        return products
                .stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
    }

    public BigDecimal getPriceWithTaxes() {
        return getPrice().multiply(TAX_VALUE).setScale(Product.PRICE_PRECISION, Product.ROUND_STRATEGY);
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public ShipmentMethod getShipmentMethod() {
        return shipmentMethod;
    }

    public void setShipmentMethod(ShipmentMethod shipmentMethod) {
        this.shipmentMethod = shipmentMethod;
    }

    public void send() {
        boolean sentSuccesful = getShipmentMethod().send(shipment, shipment.getSenderAddress(), shipment.getRecipientAddress());
        shipment.setShipped(sentSuccesful);
    }

    public void pay(MoneyTransfer moneyTransfer) {
        moneyTransfer.setCommitted(getPaymentMethod().commit(moneyTransfer));
        paid = moneyTransfer.isCommitted();
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }
}
```




```java
package pl.edu.agh.internetshop;

import org.junit.jupiter.api.Test;
import pl.edu.agh.internetshop.MoneyTransfer.MoneyTransfer;
import pl.edu.agh.internetshop.MoneyTransfer.MoneyTransferPaymentTransaction;
import pl.edu.agh.internetshop.Shipment.Shipment;
import pl.edu.agh.internetshop.Shipment.ShipmentMethod;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static pl.edu.agh.internetshop.util.CustomAssertions.assertBigDecimalCompareValue;

public class OrderTest {

    private Order getOrderWithMockedProduct() {
        Product product = mock(Product.class);
        return new Order(Collections.singletonList(product));
    }

    @Test
    public void testGetProductThroughOrder() {
        // given
        Product expectedProduct = mock(Product.class);
        Order order = new Order(Collections.singletonList(expectedProduct));

        // when
        List<Product> actualProducts = order.getProducts();

        // then
        assertSame(expectedProduct, actualProducts.get(0));
    }

    @Test
    public void testSetShipment() throws Exception {
        // given
        Order order = getOrderWithMockedProduct();
        Shipment expectedShipment = mock(Shipment.class);

        // when
        order.setShipment(expectedShipment);

        // then
        assertSame(expectedShipment, order.getShipment());
    }

    @Test
    public void testShipmentWithoutSetting() throws Exception {
        // given
        Order order = getOrderWithMockedProduct();

        // when

        // then
        assertNull(order.getShipment());
    }

    @Test
    public void testGetPrice() throws Exception {
        // given
        BigDecimal expectedProductPrice = BigDecimal.valueOf(1000);
        Product product = mock(Product.class);
        given(product.getPrice()).willReturn(expectedProductPrice);
        Order order = new Order(Collections.singletonList(product));

        // when
        BigDecimal actualProductPrice = order.getPrice();

        // then
        assertBigDecimalCompareValue(expectedProductPrice, actualProductPrice);
    }


    private Order getOrderWithCertainProductPrice(double productPriceValue) {
        BigDecimal productPrice = BigDecimal.valueOf(productPriceValue);
        Product product = mock(Product.class);
        given(product.getPrice()).willReturn(productPrice);
        return new Order(Collections.singletonList(product));
    }

    private Order getOrderWithCertainProductPrices(List<Double> productPriceValues) {

        List<Product> products = productPriceValues.stream()
                .map(BigDecimal::valueOf)
                .map(val -> {
                    Product product = mock(Product.class);
                    given(product.getPrice()).willReturn(val);
                    return product;
                })
                .collect(Collectors.toList());

        return new Order(products);
    }

    @Test
    public void testGetPriceWhenMoreItems() throws Exception {
        // given
        List<Double> prices = new ArrayList<>(Arrays.asList(0.2, 0.5, 2.5));
        Order order = getOrderWithCertainProductPrices(prices);

        // when
        BigDecimal actualProductPrice = order.getPrice();
        BigDecimal expectedProductPrice = prices.stream()
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // then
        assertBigDecimalCompareValue(expectedProductPrice, actualProductPrice);
    }

    @Test
    public void testPriceWithTaxesWithoutRoundUp() {
        // given

        // when
        Order order = getOrderWithCertainProductPrice(2); // 2 PLN

        // then
        assertBigDecimalCompareValue(order.getPriceWithTaxes(), BigDecimal.valueOf(2.46)); // 2.46 PLN
    }

    @Test
    public void testPriceWithTaxesWithRoundDown() {
        // given

        // when
        Order order = getOrderWithCertainProductPrice(0.01); // 0.01 PLN

        // then
        assertBigDecimalCompareValue(order.getPriceWithTaxes(), BigDecimal.valueOf(0.01)); // 0.01 PLN

    }

    @Test
    public void testPriceWithTaxesWithRoundUp() {
        // given

        // when
        Order order = getOrderWithCertainProductPrice(0.03); // 0.03 PLN

        // then
        assertBigDecimalCompareValue(order.getPriceWithTaxes(), BigDecimal.valueOf(0.04)); // 0.04 PLN

    }

    @Test
    public void testSetShipmentMethod() {
        // given
        Order order = getOrderWithMockedProduct();
        ShipmentMethod surface = mock(SurfaceMailBus.class);

        // when
        order.setShipmentMethod(surface);

        // then
        assertSame(surface, order.getShipmentMethod());
    }

    @Test
    public void testSending() {
        // given
        Order order = getOrderWithMockedProduct();
        SurfaceMailBus surface = mock(SurfaceMailBus.class);
        Shipment shipment = mock(Shipment.class);
        given(shipment.isShipped()).willReturn(true);

        // when
        order.setShipmentMethod(surface);
        order.setShipment(shipment);
        order.send();

        // then
        assertTrue(order.isSent());
    }

    @Test
    public void testIsSentWithoutSending() {
        // given
        Order order = getOrderWithMockedProduct();
        Shipment shipment = mock(Shipment.class);
        given(shipment.isShipped()).willReturn(true);

        // when

        // then
        assertFalse(order.isSent());
    }

    @Test
    public void testWhetherIdExists() throws Exception {
        // given
        Order order = getOrderWithMockedProduct();

        // when

        // then
        assertNotNull(order.getId());
    }

    @Test
    public void testSetPaymentMethod() throws Exception {
        // given
        Order order = getOrderWithMockedProduct();
        PaymentMethod paymentMethod = mock(MoneyTransferPaymentTransaction.class);

        // when
        order.setPaymentMethod(paymentMethod);

        // then
        assertSame(paymentMethod, order.getPaymentMethod());
    }

    @Test
    public void testPaying() throws Exception {
        // given
        Order order = getOrderWithMockedProduct();
        PaymentMethod paymentMethod = mock(MoneyTransferPaymentTransaction.class);
        given(paymentMethod.commit(any(MoneyTransfer.class))).willReturn(true);
        MoneyTransfer moneyTransfer = mock(MoneyTransfer.class);
        given(moneyTransfer.isCommitted()).willReturn(true);

        // when
        order.setPaymentMethod(paymentMethod);
        order.pay(moneyTransfer);

        // then
        assertTrue(order.isPaid());
    }

    @Test
    public void testIsPaidWithoutPaying() throws Exception {
        // given
        Order order = getOrderWithMockedProduct();

        // when

        // then
        assertFalse(order.isPaid());
    }
}

```

```java
public Order(@NonNull List<Product> products) {
        if(products.isEmpty())
            throw new IllegalArgumentException("Product list can't be empty");
        this.products = products;
        id = UUID.randomUUID();
        paid = false;
    }
}
```

```java
 @Test
    public void testNullProductListOrder(){
        // when then
        assertThrows(NullPointerException.class, ()->{
            Order order = new Order(null);
        });
    }

    @Test
    public void testEmptyProductListOrder(){
        // given
        List<Product> products= new ArrayList<>();

        // when then
        assertThrows(IllegalArgumentException.class, ()->{
            Order order = new Order(products);
        });
    }

```


## kolejny punkt

```java
public void setOrderDiscount(BigDecimal discount){

}

public BigDecimal getOrderDiscount(){
    return null;
}
```

```java
public Product(String name, BigDecimal price, BigDecimal discount) {
    this(name, price);
}

public void setProductDiscount(BigDecimal discount) {

}

public BigDecimal getProductDiscount() {
    return null;
}
```

```java


    private static final BigDecimal DISCOUNT = BigDecimal.valueOf(0.76);
 
    @Test
    public void testProductPriceWithoutDiscount() throws Exception{
        //given
    	
        // when
        Product product = new Product(NAME, PRICE);
        
        // then
        assertBigDecimalCompareValue(product.getPrice(), PRICE);
    }

    @Test
    public void testProductPriceWithDiscount() throws Exception{
        //given

        // when
        Product product = new Product(NAME, PRICE, DISCOUNT);

        // then
        assertBigDecimalCompareValue(product.getPrice(), PRICE.multiply(DISCOUNT));
    }

    @Test
    public void testProductDiscount() throws Exception{
        //given

        // when
        Product product = new Product(NAME, PRICE, DISCOUNT);

        // then
        assertEquals(product.getProductDiscount(), DISCOUNT);
    }
```


```java

    @Test
    public void testGetOrderPriceWithOnlyOrderDiscount() throws Exception {
        // given
        List<Double> prices = new ArrayList<>(Arrays.asList(0.2, 0.5, 2.5));
        Order order = getOrderWithCertainProductPrices(prices);
        BigDecimal discount = BigDecimal.valueOf(0.76);
        order.setOrderDiscount(discount);

        // when
        BigDecimal actualProductPrice = order.getPrice();
        BigDecimal expectedProductPrice = prices.stream()
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .multiply(discount);

        // then
        assertBigDecimalCompareValue(expectedProductPrice, actualProductPrice);
    }

    @Test
    public void testGetOrderPriceWithOnlyProductDiscount() throws Exception {
        // given
        List<Double> prices = new ArrayList<>(Arrays.asList(0.2, 0.5, 2.5));
        Order order = getOrderWithCertainProductPrices(prices);
        BigDecimal discount = BigDecimal.valueOf(0.76);
        order.getProducts().forEach(product -> product.setProductDiscount(discount));

        // when
        BigDecimal actualProductPrice = order.getPrice();
        BigDecimal expectedProductPrice = prices.stream()
                .map(BigDecimal::valueOf)
                .map(val -> val.multiply(discount))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // then
        assertBigDecimalCompareValue(expectedProductPrice, actualProductPrice);
    }

    @Test
    public void testGetOrderPriceWithProductAndOrderDiscount() throws Exception {
        // given
        List<Double> prices = new ArrayList<>(Arrays.asList(0.2, 0.5, 2.5));
        Order order = getOrderWithCertainProductPrices(prices);
        BigDecimal productDiscount = BigDecimal.valueOf(0.76);
        BigDecimal orderDiscount = BigDecimal.valueOf(0.9);
        order.getProducts().forEach(product -> product.setProductDiscount(productDiscount));
        order.setOrderDiscount(orderDiscount);

        // when
        BigDecimal actualProductPrice = order.getPrice();
        BigDecimal expectedProductPrice = prices.stream()
                .map(BigDecimal::valueOf)
                .map(val -> val.multiply(productDiscount))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .multiply(orderDiscount);

        // then
        assertBigDecimalCompareValue(expectedProductPrice, actualProductPrice);
    }
```

```java
 @Test
    public void testGetOrderPriceWithOnlyOrderDiscount() throws Exception {
        // given
        List<Double> prices = new ArrayList<>(Arrays.asList(0.2, 0.5, 2.5));
        Order order = getOrderWithCertainProductPrices(prices);
        BigDecimal discount = BigDecimal.valueOf(0.76);
        order.setOrderDiscount(discount);

        // when
        BigDecimal actualProductPrice = order.getPrice();
        BigDecimal expectedProductPrice = prices.stream()
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .multiply(discount);

        // then
        assertBigDecimalCompareValue(expectedProductPrice, actualProductPrice);
    }

    @Test
    public void testGetOrderPriceWithOnlyProductDiscount() throws Exception {
        // given
        List<Double> prices = new ArrayList<>(Arrays.asList(0.2, 0.5, 2.5));
        BigDecimal discount = BigDecimal.valueOf(0.76);
        Order order = getOrderWithCertainProductPricesAndDiscount(prices, discount);

        // when
        BigDecimal actualProductPrice = order.getPrice();
        BigDecimal expectedProductPrice = prices.stream()
                .map(BigDecimal::valueOf)
                .map(val -> val.multiply(discount))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // then
        assertBigDecimalCompareValue(expectedProductPrice, actualProductPrice);
    }

    @Test
    public void testGetOrderPriceWithProductAndOrderDiscount() throws Exception {
        // given
        List<Double> prices = new ArrayList<>(Arrays.asList(0.2, 0.5, 2.5));
        BigDecimal productDiscount = BigDecimal.valueOf(0.76);
        BigDecimal orderDiscount = BigDecimal.valueOf(0.9);
        Order order =  getOrderWithCertainProductPricesAndDiscount(prices,  productDiscount);
        order.setOrderDiscount(orderDiscount);

        // when
        BigDecimal actualProductPrice = order.getPrice();
        BigDecimal expectedProductPrice = prices.stream()
                .map(BigDecimal::valueOf)
                .map(val -> val.multiply(productDiscount))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .multiply(orderDiscount);

        // then
        assertBigDecimalCompareValue(expectedProductPrice, actualProductPrice);
    }
```

## 4

```java
package pl.edu.agh.internetshop.SearchStrategy;

import pl.edu.agh.internetshop.Order;

public class ClientNameSearchStrategy implements SearchStrategy {

    private String clientName;

    public ClientNameSearchStrategy(String clientName) {
        
    }

    @Override
    public boolean filter(Order order) {
        return true;
    }
}

```

```java
package pl.edu.agh.internetshop.SearchStrategy;

import pl.edu.agh.internetshop.Order;

import java.util.ArrayList;
import java.util.Collection;

public class CompositeSearchStrategy implements SearchStrategy{

    public void addStrategy(SearchStrategy searchStrategy){
    }

    @Override
    public boolean filter(Order order) {
        return false;
    }
}
```


```java
package pl.edu.agh.internetshop.SearchStrategy;

import pl.edu.agh.internetshop.Order;

import java.math.BigDecimal;

public class PriceSearchStrategy implements SearchStrategy {
    
    public PriceSearchStrategy(BigDecimal price) {
        
    }

    @Override
    public boolean filter(Order order) {
        return false;
    }
}

```

```java

package pl.edu.agh.internetshop.SearchStrategy;

import pl.edu.agh.internetshop.Order;

public class ProductNameSearchStrategy implements SearchStrategy {
    public ProductNameSearchStrategy(String productName) {
    }

    @Override
    public boolean filter(Order order) {
        return false;
    }
}

```

```java

public interface OrderHistoryInterface {

    public void addOrder(Order order);
    public List<Order> findOrderByStrategy(SearchStrategy strategy);

}



```

```java
public class OrderHistory implements OrderHistoryInterface {


    public static OrderHistory getInstance(){
        return null;
    }


    @Override
    public void addOrder(Order order) {

    }

    @Override
    public List<Order> findOrderByStrategy(SearchStrategy strategy) {
        return null;
    }

}



```


testy 

```java
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

        String wantedName = "Name Wanted";

        ClientNameSearchStrategy searchStrategy = new ClientNameSearchStrategy(wantedName);

        Address address1 = mock(Address.class);
        given(address1.getName()).willReturn(wantedName);
        Shipment shipment1 = mock(Shipment.class);
        given(shipment1.getRecipientAddress()).willReturn(address1);
        Order goodOrder = mock(Order.class);
        given(goodOrder.getShipment()).willReturn(shipment1);

        Address address2= mock(Address.class);
        given(address1.getName()).willReturn("wrong name");
        Shipment shipment2 = mock(Shipment.class);
        given(shipment1.getRecipientAddress()).willReturn(address2);
        Order badOrder = mock(Order.class);
        given(badOrder.getShipment()).willReturn(shipment2);



        assertTrue(searchStrategy.filter(goodOrder));
        assertFalse(searchStrategy.filter(badOrder));

    }

    @Test
    public void productNameSearchStrategyTest() throws Exception{

        String wantedName = "Name Wanted";

        ProductNameSearchStrategy searchStrategy = new ProductNameSearchStrategy(wantedName);

        Product product1 = mock(Product.class);
        given(product1.getName()).willReturn(wantedName);
        Order goodOrder = new Order(Collections.singletonList(product1));

        Product product2 = mock(Product.class);
        given(product2.getName()).willReturn("wrong name");
        Order badOrder = new Order(Collections.singletonList(product2));



        assertTrue(searchStrategy.filter(goodOrder));
        assertFalse(searchStrategy.filter(badOrder));

    }


    @Test
    public void priceSearchStrategyTest() throws Exception{

        BigDecimal wantedPrice = BigDecimal.valueOf(2.1);

        PriceSearchStrategy searchStrategy = new PriceSearchStrategy(wantedPrice);

        Product product1 = mock(Product.class);
        given(product1.getPrice()).willReturn(wantedPrice);
        Order goodOrder = new Order(Collections.singletonList(product1));

        Product product2 = mock(Product.class);
        given(product2.getPrice()).willReturn(BigDecimal.valueOf(3.7));
        Order badOrder = new Order(Collections.singletonList(product2));

        assertFalse(searchStrategy.filter(badOrder));
        assertTrue(searchStrategy.filter(goodOrder));

    }

    @Test
    public void CompositeSearchTest() {

        String wantedName = "Name Wanted";
        BigDecimal wantedPrice = BigDecimal.valueOf(2.1);

        PriceSearchStrategy priceSearchStrategy = new PriceSearchStrategy(wantedPrice);
        ProductNameSearchStrategy nameSearchStrategy = new ProductNameSearchStrategy(wantedName);
        CompositeSearchStrategy searchStrategy = new CompositeSearchStrategy();
        searchStrategy.addStrategy(priceSearchStrategy);
        searchStrategy.addStrategy(nameSearchStrategy);


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



        assertTrue(searchStrategy.filter(goodOrder));
        assertFalse(searchStrategy.filter(badOrder1));
        assertFalse(searchStrategy.filter(badOrder2));


    }

}
```

```java
package pl.edu.agh.internetshop.OrderHistory;

import org.junit.jupiter.api.Test;
import pl.edu.agh.internetshop.Order;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class OrderHistoryTest {

    @Test
    void addAndGetOrderTest() {
        Order expectedOrder = mock(Order.class);
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.addOrder(expectedOrder);

        Order actualOrder = orderHistory.getOrders().get(0);

        assertSame(expectedOrder, actualOrder);

    }
}
```


```java
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
```








