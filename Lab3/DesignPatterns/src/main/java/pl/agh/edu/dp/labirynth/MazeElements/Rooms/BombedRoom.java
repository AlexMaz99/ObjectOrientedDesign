package pl.agh.edu.dp.labirynth.MazeElements.Rooms;

import pl.agh.edu.dp.labirynth.Utils.Vector2d;

public class BombedRoom extends Room {

    public BombedRoom(Vector2d position) {
        super(position);
    }

    @Override
    public void Enter(){
        System.out.println("You entered bombed room");
    }
}
