package pl.agh.edu.dp.labirynth.Builder;

import pl.agh.edu.dp.labirynth.Utils.Direction;
import pl.agh.edu.dp.labirynth.MazeElements.Rooms.Room;
import pl.agh.edu.dp.labirynth.Utils.Vector2d;

public interface MazeBuilder {

    void addRoom(Room room);
    void createDoorBetweenRooms(Room r1, Room r2) throws Exception;
    void createCommonWall(Direction firstRoomDir, Room r1, Room r2) throws Exception;
    Room findRoom(Vector2d pos) throws Exception;
}
