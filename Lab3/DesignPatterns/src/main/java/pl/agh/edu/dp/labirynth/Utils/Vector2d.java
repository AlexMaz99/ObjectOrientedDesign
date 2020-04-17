package pl.agh.edu.dp.labirynth.Utils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Vector2d {
    final public int x;
    final public int y;

    public Vector2d nextPos(Direction dir) {
        switch (dir) {
            case South:
                return new Vector2d(this.x, this.y - 1);
            case East:
                return new Vector2d(this.x + 1, this.y);
            case West:
                return new Vector2d(this.x - 1, this.y);
            case North:
                return new Vector2d(this.x, this.y + 1);
        }
        throw new NullPointerException("Pos doesnt exist");
    }


    public Vector2d lowerRight(Vector2d other) {
        return new Vector2d(Math.max(this.x, other.x), Math.max(this.y, other.y));
    }
}
