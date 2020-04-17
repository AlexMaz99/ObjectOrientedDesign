package pl.agh.edu.dp.labirynth.Factory;

import pl.agh.edu.dp.labirynth.MazeElements.Doors.Door;
import pl.agh.edu.dp.labirynth.MazeElements.Rooms.Room;
import pl.agh.edu.dp.labirynth.MazeElements.Walls.Wall;
import pl.agh.edu.dp.labirynth.Utils.Vector2d;

public class MazeFactory {

    private static MazeFactory instance;

    public static MazeFactory getInstance(){
        if( instance == null){
            instance = new MazeFactory();
        }
        return instance;
    }

    public Door createDoor(Room r1, Room r2) {
        return new Door(r1, r2);
    }

    public Room createRoom(Vector2d pos) {
        return new Room(pos);
    }

    public Wall createWall() {
        return new Wall();
    }
}
