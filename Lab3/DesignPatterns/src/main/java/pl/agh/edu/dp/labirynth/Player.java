package pl.agh.edu.dp.labirynth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.agh.edu.dp.labirynth.MazeElements.Rooms.Room;
import pl.agh.edu.dp.labirynth.Utils.Vector2d;

@AllArgsConstructor
@Setter
@Getter
public class Player {

    Room currentRoom;
    Vector2d pos;
}
