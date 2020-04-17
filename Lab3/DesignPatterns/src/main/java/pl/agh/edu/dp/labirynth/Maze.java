package pl.agh.edu.dp.labirynth;

import lombok.Getter;
import pl.agh.edu.dp.labirynth.MazeElements.Rooms.Room;
import pl.agh.edu.dp.labirynth.Utils.Vector2d;

import java.util.Optional;
import java.util.Vector;
@Getter
public class Maze {

    private Vector<Room> rooms;

    public Maze() {
        this.rooms = new Vector<Room>();
    }

    public void addRoom(Room room){
        rooms.add(room);
    }

    public Room findRoom(Vector2d pos) throws Exception {
        Optional<Room> foundRoom =  rooms.stream().filter(room -> room.getPosition().equals(pos)).findAny();
        if(foundRoom.isPresent()) return foundRoom.get();
        throw new Exception("Error: Room with that number doesnt exist");
    }


    public int getRoomNumbers()
    {
        return rooms.size();
    }


}
