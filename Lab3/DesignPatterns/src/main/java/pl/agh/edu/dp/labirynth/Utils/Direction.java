package pl.agh.edu.dp.labirynth.Utils;

public enum Direction {
    North, East, South, West;

    public Direction getOppositeSide() {
        int enumI = (this.ordinal() + 2) % Direction.values().length;
        return Direction.values()[enumI];
    }
}