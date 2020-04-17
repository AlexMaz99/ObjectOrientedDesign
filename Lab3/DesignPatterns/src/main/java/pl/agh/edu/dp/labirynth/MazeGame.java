package pl.agh.edu.dp.labirynth;

import lombok.Getter;
import lombok.Setter;
import pl.agh.edu.dp.labirynth.Builder.CountingMazeBuilder;
import pl.agh.edu.dp.labirynth.Factory.BombedMazeFactory;
import pl.agh.edu.dp.labirynth.MazeElements.Doors.Door;
import pl.agh.edu.dp.labirynth.MazeElements.MapSite;
import pl.agh.edu.dp.labirynth.MazeElements.Walls.Wall;
import pl.agh.edu.dp.labirynth.Utils.*;
import pl.agh.edu.dp.labirynth.Builder.StandardMazeBuilder;
import pl.agh.edu.dp.labirynth.Factory.EnchantedMazeFactory;
import pl.agh.edu.dp.labirynth.Factory.MazeFactory;
import pl.agh.edu.dp.labirynth.MazeElements.Rooms.Room;

import static pl.agh.edu.dp.labirynth.Utils.Direction.*;

@Getter
@Setter
public class MazeGame {

    private Maze maze;
    private Player player;
    private Vector2d rightLowerPos;

    private static MazeGame instance;

    public static MazeGame getInstance() {
        if( instance == null){
            instance = new MazeGame();
        }
        return instance;
    }


    public void createMaze(CountingMazeBuilder builder, MazeFactory factory) throws Exception {
        buildSampleMaze(builder, factory);
        this.rightLowerPos = builder.getRightLowerPos();
        this.maze= builder.getCurrentMaze();
    }

    public void makeMove(Direction dir) throws Exception {
        MapSite side = player.currentRoom.getSide(dir);
        side.Enter();

    }

    public void movePlayerTo(Room room){
        player.setCurrentRoom(room);
        player.setPos(room.getPosition());
    }

    public void createPlayerAt(Vector2d playerPos) throws Exception {
        this.player = new Player(maze.findRoom(playerPos), playerPos);
    }


    /* below sample 5 by 5 maze hardcoded for simplicity */
    private void buildSampleMaze(CountingMazeBuilder builder, MazeFactory factory) throws Exception {

        /* Add every room and make common walls with neighbours */
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Room currRoom = factory.createRoom(new Vector2d(i, j));
                builder.addRoom(currRoom);

                if (j > 0)
                    builder.createCommonWall(North, currRoom, builder.findRoom(new Vector2d(i, j - 1)));
                if (i > 0)
                    builder.createCommonWall(West, currRoom, builder.findRoom(new Vector2d(i - 1, j)));
            }
        }

        /* long code ahead, nothing else at bottom ;) */
        builder.createDoorBetweenRooms(
                builder.findRoom(new Vector2d(0, 0)),
                builder.findRoom(new Vector2d(1, 0))
        );

        builder.createDoorBetweenRooms(
                builder.findRoom(new Vector2d(0, 0)),
                builder.findRoom(new Vector2d(0, 1))
        );

        builder.createDoorBetweenRooms(
                builder.findRoom(new Vector2d(0, 1)),
                builder.findRoom(new Vector2d(0, 2))
        );

        builder.createDoorBetweenRooms(
                builder.findRoom(new Vector2d(1, 0)),
                builder.findRoom(new Vector2d(1, 1))
        );
        builder.createDoorBetweenRooms(
                builder.findRoom(new Vector2d(0, 2)),
                builder.findRoom(new Vector2d(1, 2))
        );

        builder.createDoorBetweenRooms(
                builder.findRoom(new Vector2d(0, 2)),
                builder.findRoom(new Vector2d(0, 3))
        );

        builder.createDoorBetweenRooms(
                builder.findRoom(new Vector2d(1, 3)),
                builder.findRoom(new Vector2d(0, 3))
        );

        builder.createDoorBetweenRooms(
                builder.findRoom(new Vector2d(1, 3)),
                builder.findRoom(new Vector2d(1, 4))
        );

        builder.createDoorBetweenRooms(
                builder.findRoom(new Vector2d(0, 4)),
                builder.findRoom(new Vector2d(1, 4))
        );
        builder.createDoorBetweenRooms(
                builder.findRoom(new Vector2d(2, 0)),
                builder.findRoom(new Vector2d(3, 0))
        );
        builder.createDoorBetweenRooms(
                builder.findRoom(new Vector2d(4, 0)),
                builder.findRoom(new Vector2d(3, 0))
        );
        builder.createDoorBetweenRooms(
                builder.findRoom(new Vector2d(4, 0)),
                builder.findRoom(new Vector2d(4, 1))
        );
        builder.createDoorBetweenRooms(
                builder.findRoom(new Vector2d(4, 0)),
                builder.findRoom(new Vector2d(4, 1))
        );
        builder.createDoorBetweenRooms(
                builder.findRoom(new Vector2d(3, 1)),
                builder.findRoom(new Vector2d(4, 1))
        );
        builder.createDoorBetweenRooms(
                builder.findRoom(new Vector2d(3, 1)),
                builder.findRoom(new Vector2d(2, 1))
        );
        builder.createDoorBetweenRooms(
                builder.findRoom(new Vector2d(2, 2)),
                builder.findRoom(new Vector2d(2, 1))
        );
        builder.createDoorBetweenRooms(
                builder.findRoom(new Vector2d(2, 2)),
                builder.findRoom(new Vector2d(1, 2))
        );
        builder.createDoorBetweenRooms(
                builder.findRoom(new Vector2d(3, 1)),
                builder.findRoom(new Vector2d(3, 2))
        );
        builder.createDoorBetweenRooms(
                builder.findRoom(new Vector2d(4, 2)),
                builder.findRoom(new Vector2d(3, 2))
        );
        builder.createDoorBetweenRooms(
                builder.findRoom(new Vector2d(4, 2)),
                builder.findRoom(new Vector2d(4, 3))
        );
        builder.createDoorBetweenRooms(
                builder.findRoom(new Vector2d(3, 3)),
                builder.findRoom(new Vector2d(4, 3))
        );
        builder.createDoorBetweenRooms(
                builder.findRoom(new Vector2d(3, 3)),
                builder.findRoom(new Vector2d(3, 4))
        );
        builder.createDoorBetweenRooms(
                builder.findRoom(new Vector2d(4, 4)),
                builder.findRoom(new Vector2d(3, 4))
        );
        builder.createDoorBetweenRooms(
                builder.findRoom(new Vector2d(3, 3)),
                builder.findRoom(new Vector2d(2, 3))
        );
        builder.createDoorBetweenRooms(
                builder.findRoom(new Vector2d(2, 4)),
                builder.findRoom(new Vector2d(2, 3))
        );
    }

}
