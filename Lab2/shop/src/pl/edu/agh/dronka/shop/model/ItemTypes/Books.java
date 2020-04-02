package pl.edu.agh.dronka.shop.model.ItemTypes;

import pl.edu.agh.dronka.shop.model.Category;

public class Books extends Item {
    private int nrOfPages;
    private boolean isHard;

    public Books(String name, Category category, int price, int quantity, int nrOfPages, boolean isHard){
        super(name, category, price, quantity);
        this.nrOfPages=nrOfPages;
        this.isHard=isHard;
    }
    public Books(){}

    public int getNrOfPages() {
        return nrOfPages;
    }

    public void setNrOfPages(int nrOfPages) {
        this.nrOfPages = nrOfPages;
    }

    public boolean isHard() {
        return isHard;
    }

    public void setHard(boolean hard) {
        isHard = hard;
    }
}
