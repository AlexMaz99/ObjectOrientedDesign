package pl.edu.agh.dronka.shop.model.ItemTypes;

import pl.edu.agh.dronka.shop.model.Category;

import java.util.Date;

public class Food extends Item {

    private Date expirationDate;

    public Food(String name, Category category, int price, int quantity,  Date expirationDate) {
        super(name, category, price, quantity);
        this.expirationDate=expirationDate;
    }

    public Food(){}

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
