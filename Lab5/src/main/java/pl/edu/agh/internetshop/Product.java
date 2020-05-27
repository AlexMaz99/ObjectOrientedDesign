package pl.edu.agh.internetshop;

import java.math.BigDecimal;

public class Product {

    public static final int PRICE_PRECISION = 2;
    public static final int ROUND_STRATEGY = BigDecimal.ROUND_HALF_UP;
    private BigDecimal discount;

    private final String name;
    private final BigDecimal price;

    public Product(String name, BigDecimal price) {
       this(name, price, BigDecimal.valueOf(1));
    }

    public Product(String name, BigDecimal price, BigDecimal discount) {
        this.name = name;
        this.price = price;
        this.price.setScale(PRICE_PRECISION, ROUND_STRATEGY);
        this.discount = discount;
    }

    public void setProductDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getProductDiscount() {
        return this.discount;
    }


    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price.multiply(discount);
    }
}
