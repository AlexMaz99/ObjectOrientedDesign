package pl.agh.edu.dp.labirynth.MazeElements.Rooms;

import lombok.Getter;
import pl.agh.edu.dp.labirynth.MazeElements.MapSite;
import pl.agh.edu.dp.labirynth.Utils.Direction;
import pl.agh.edu.dp.labirynth.Utils.Vector2d;

import java.util.EnumMap;
import java.util.Map;

public class Room extends MapSite {
    @Getter
    private Vector2d position;
    @Getter
    private Map<Direction, MapSite> sides;


    public Room(Vector2d position) {
        this.sides = new EnumMap<>(Direction.class);
        this.position = position;
    }

    public MapSite getSide(Direction direction) {
        return this.sides.get(direction);
    }

    public void setSide(Direction direction, MapSite ms) {
        this.sides.put(direction, ms);
    }

    @Override
    public void Enter() {
        System.out.println("You entered normal room");
    }
}
