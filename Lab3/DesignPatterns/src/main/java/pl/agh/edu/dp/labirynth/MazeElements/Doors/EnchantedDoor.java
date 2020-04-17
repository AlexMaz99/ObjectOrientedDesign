package pl.agh.edu.dp.labirynth.MazeElements.Doors;

import pl.agh.edu.dp.labirynth.MazeElements.Rooms.Room;

public class EnchantedDoor extends Door {
    public EnchantedDoor(Room r1, Room r2) {
        super(r1, r2);
    }

    @Override
    public void Enter() throws Exception {
        System.out.println("You opened enchanted door");
        movePlayerToOtherSide();
    }
}
