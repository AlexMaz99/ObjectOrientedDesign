package org.example.ShapeAdapter;

public class SquarePegAdapter extends RoundPeg {

    private SquarePeg peg;

    public SquarePegAdapter( SquarePeg peg) {
        super(peg.getWidth());
        this.peg = peg;
    }

    public int getRadius(){
        return (int) (peg.getWidth()*Math.sqrt(2.0)/2.0);
    }

}
