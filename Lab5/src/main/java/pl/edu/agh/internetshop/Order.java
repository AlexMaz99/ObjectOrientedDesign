package pl.edu.agh.internetshop;

import lombok.NonNull;
import pl.edu.agh.internetshop.MoneyTransfer.MoneyTransfer;
import pl.edu.agh.internetshop.OrderHistory.OrderHistory;
import pl.edu.agh.internetshop.Shipment.Shipment;
import pl.edu.agh.internetshop.Shipment.ShipmentMethod;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


public class Order {
    private static final BigDecimal TAX_VALUE = BigDecimal.valueOf(1.23);
    private BigDecimal discount = BigDecimal.valueOf(1);
    private final UUID id;
    private final List<Product> products;
    private boolean paid;
    private Shipment shipment;
    private ShipmentMethod shipmentMethod;
    private PaymentMethod paymentMethod;


    public Order(@NonNull List<Product> products) {
        if(products.isEmpty())
            throw new IllegalArgumentException("Product list can't be empty");
        this.products = products;
        id = UUID.randomUUID();
        paid = false;
        OrderHistory.getInstance().addOrder(this);
    }

    public void setOrderDiscount(BigDecimal discount){
        this.discount = discount;
    }

    public BigDecimal getOrderDiscount(){
        return discount;
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
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .multiply(discount);

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
