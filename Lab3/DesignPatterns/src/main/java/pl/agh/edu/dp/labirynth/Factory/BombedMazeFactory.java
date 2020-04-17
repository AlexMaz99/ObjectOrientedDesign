package pl.agh.edu.dp.labirynth.Factory;


import pl.agh.edu.dp.labirynth.MazeElements.Doors.BombedDoor;
import pl.agh.edu.dp.labirynth.MazeElements.Doors.Door;
import pl.agh.edu.dp.labirynth.MazeElements.Rooms.BombedRoom;
import pl.agh.edu.dp.labirynth.MazeElements.Rooms.Room;
import pl.agh.edu.dp.labirynth.MazeElements.Walls.BombedWall;
import pl.agh.edu.dp.labirynth.MazeElements.Walls.Wall;
import pl.agh.edu.dp.labirynth.Utils.Vector2d;

public class BombedMazeFactory extends MazeFactory{

    private static BombedMazeFactory instance;

    public static BombedMazeFactory getInstance(){
        if( instance == null){
            instance = new BombedMazeFactory();
        }
        return instance;
    }

    @Override
    public Door createDoor(Room r1, Room r2) {
        return new BombedDoor(r1, r2);
    }

    @Override
    public Room createRoom(Vector2d pos) {
        return new BombedRoom(pos);
    }

    @Override
    public Wall createWall() {
        return new BombedWall();
    }
}
