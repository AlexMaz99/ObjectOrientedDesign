package pl.agh.edu.dp.labirynth.MazeElements.Doors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.agh.edu.dp.labirynth.MazeElements.MapSite;
import pl.agh.edu.dp.labirynth.MazeElements.Rooms.Room;
import pl.agh.edu.dp.labirynth.MazeGame;

@Getter
@Setter
@AllArgsConstructor
public class Door extends MapSite {
    private Room room1;
    private Room room2;

    public Room getRoomAtOthersSide(Room firstR) {
        return room1 == firstR ? room2 : room1;
    }

    void movePlayerToOtherSide() throws Exception {
        Room newRoom = getRoomAtOthersSide(MazeGame.getInstance().getPlayer().getCurrentRoom());
        newRoom.Enter();
        MazeGame.getInstance().movePlayerTo(newRoom);
    }

    @Override
    public void Enter() throws Exception {
        System.out.println("You opened normal door");
        movePlayerToOtherSide();
    }

}
