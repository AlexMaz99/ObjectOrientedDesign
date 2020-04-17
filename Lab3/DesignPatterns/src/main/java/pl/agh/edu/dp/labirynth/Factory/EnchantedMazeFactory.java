package pl.agh.edu.dp.labirynth.Factory;

import pl.agh.edu.dp.labirynth.Factory.MazeFactory;
import pl.agh.edu.dp.labirynth.MazeElements.Doors.Door;
import pl.agh.edu.dp.labirynth.MazeElements.Doors.EnchantedDoor;
import pl.agh.edu.dp.labirynth.MazeElements.Rooms.EnchantedRoom;
import pl.agh.edu.dp.labirynth.MazeElements.Rooms.Room;
import pl.agh.edu.dp.labirynth.MazeElements.Walls.EnchantedWall;
import pl.agh.edu.dp.labirynth.MazeElements.Walls.Wall;
import pl.agh.edu.dp.labirynth.Utils.Vector2d;

public class EnchantedMazeFactory extends MazeFactory {

    private static EnchantedMazeFactory instance;

    public static EnchantedMazeFactory getInstance(){
        if( instance == null){
            instance = new EnchantedMazeFactory();
        }
        return instance;
    }

    @Override
    public Door createDoor(Room r1, Room r2) {
        return new EnchantedDoor(r1, r2);
    }

    @Override
    public Room createRoom(Vector2d pos) {
        return new EnchantedRoom(pos);
    }

    @Override
    public Wall createWall() {
        return new EnchantedWall();
    }


}
