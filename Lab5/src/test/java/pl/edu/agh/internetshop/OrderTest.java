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
    private Order getOrderWithCertainProductPricesAndDiscount(List<Double> productPriceValues, BigDecimal discount) {

        List<Product> products = productPriceValues.stream()
                .map(BigDecimal::valueOf)
                .map(val -> {
                    Product product = mock(Product.class);
                    given(product.getPrice()).willReturn(val.multiply(discount));
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
