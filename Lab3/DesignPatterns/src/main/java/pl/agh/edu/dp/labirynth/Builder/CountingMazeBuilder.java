package pl.agh.edu.dp.labirynth.Builder;

import lombok.Getter;
import pl.agh.edu.dp.labirynth.Factory.MazeFactory;
import pl.agh.edu.dp.labirynth.Utils.Direction;
import pl.agh.edu.dp.labirynth.MazeElements.Rooms.Room;
import pl.agh.edu.dp.labirynth.Utils.Vector2d;

@Getter
public class CountingMazeBuilder extends StandardMazeBuilder {

    private int roomsNr;
    private int doorsNr;
    private int wallNr;
    private Vector2d RightLowerPos;

    public CountingMazeBuilder(MazeFactory factory) {
        super(factory);
        this.roomsNr = 0;
        this.doorsNr = 0;
        this.wallNr = 0;
        RightLowerPos = new Vector2d(0, 0);
    }

    @Override
    public void addRoom(Room room) {
        super.addRoom(room);
        roomsNr++;
        wallNr += 4;

        RightLowerPos = RightLowerPos.lowerRight(room.getPosition());
    }

    @Override
    public void createDoorBetweenRooms(Room r1, Room r2) throws Exception {
        super.createDoorBetweenRooms(r1,r2);
        wallNr--;
        doorsNr++;
    }

    @Override
    public void createCommonWall(Direction firstRoomDir, Room r1, Room r2) throws Exception {
        super.createCommonWall(firstRoomDir, r1, r2);
        wallNr--;
    }

    public int getCounts() {
        return roomsNr + wallNr + doorsNr;
    }




}
