package pl.agh.edu.dp.labirynth.Builder;

import lombok.Getter;
import pl.agh.edu.dp.labirynth.*;
import pl.agh.edu.dp.labirynth.Factory.MazeFactory;
import pl.agh.edu.dp.labirynth.MazeElements.Doors.Door;
import pl.agh.edu.dp.labirynth.MazeElements.MapSite;
import pl.agh.edu.dp.labirynth.MazeElements.Rooms.Room;
import pl.agh.edu.dp.labirynth.MazeElements.Walls.Wall;
import pl.agh.edu.dp.labirynth.Utils.Direction;
import pl.agh.edu.dp.labirynth.Utils.Vector2d;

public class StandardMazeBuilder implements MazeBuilder {

    @Getter
    Maze currentMaze;
    private MazeFactory factory;

    public StandardMazeBuilder(MazeFactory factory) {
        this.currentMaze = new Maze();
        this.factory=factory;
    }


    @Override
    public void addRoom(Room room) {
        room.setSide(Direction.North, factory.createWall());
        room.setSide(Direction.East, factory.createWall());
        room.setSide(Direction.South, factory.createWall());
        room.setSide(Direction.West, factory.createWall());
        currentMaze.addRoom(room);
    }

    @Override
    public void createDoorBetweenRooms(Room r1, Room r2) throws Exception {

        Direction firstRoomDir = commonWall(r1, r2);

        Door door = factory.createDoor(r1, r2);

        r1.setSide(firstRoomDir, door);
        r2.setSide(firstRoomDir.getOppositeSide(), door);
    }

    @Override
    public void createCommonWall(Direction firstRoomDir, Room r1, Room r2) throws Exception {
        MapSite side = r1.getSide(firstRoomDir);
        r2.setSide(firstRoomDir.getOppositeSide(), side);

    }

    private Direction commonWall(Room r1, Room r2) throws Exception {
        for (Direction dir1 : Direction.values()) {
            if (r1.getSide(dir1).equals(r2.getSide(dir1.getOppositeSide()))) {
                return dir1;
            }
        }
        throw new Exception("Rooms doesnt have common wall");
    }

    public Room findRoom(Vector2d pos) throws Exception {
        return currentMaze.findRoom(pos);
    }
}
