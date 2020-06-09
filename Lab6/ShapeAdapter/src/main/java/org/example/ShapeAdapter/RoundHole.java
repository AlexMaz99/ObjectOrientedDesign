package org.example.ShapeAdapter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RoundHole {

    private int radius;

    public boolean fits(RoundPeg peg){
        return this.getRadius()>=peg.getRadius();
    }

}
