package pl.agh.edu.dp.gui;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import pl.agh.edu.dp.labirynth.MazeElements.Rooms.Room;
import pl.agh.edu.dp.labirynth.MazeElements.Walls.Wall;
import pl.agh.edu.dp.labirynth.Utils.Vector2d;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

import java.util.Vector;

import static pl.agh.edu.dp.gui.GameFrame.PIXEL_SIZE;
import static pl.agh.edu.dp.gui.GameFrame.WALL_THICKNESS;
import static pl.agh.edu.dp.labirynth.Utils.Direction.*;

class GameScene extends Scene {

    private Group group;

    GameScene(Pane rootPane, int width, int height) {
        super(rootPane, width, height);
        group = new Group();
        rootPane.getChildren().add(group);
    }

    void genTiles(Vector<Room> rooms, Vector2d playerPos, Vector2d lowerRight) {
        group.getChildren().clear();

        /* Generates pixels at corners, so there isn't weird space */
        Group cornerGroup = new Group();
        for (int i = 0; i <= lowerRight.x + 1; i++) {
            for (int j = 0; j <= lowerRight.y + 1; j++) {
                Vector2d posOnCanvas = getRoomPosOnCanvas(new Vector2d(i, j));
                addRectangle(WALL_THICKNESS, WALL_THICKNESS, posOnCanvas.x, posOnCanvas.y, cornerGroup, Color.BLACK);
            }
        }
        group.getChildren().add(cornerGroup);

        rooms.forEach(room -> {

            Vector2d posOnCanvas = getRoomPosOnCanvas(room.getPosition());
            Group tileGroup = new Group();

            if (room.getSide(North) instanceof Wall)
                addRectangle(PIXEL_SIZE, WALL_THICKNESS, posOnCanvas.x + WALL_THICKNESS, posOnCanvas.y, tileGroup, Color.BLACK);

            if (room.getSide(East) instanceof Wall)
                addRectangle(WALL_THICKNESS, PIXEL_SIZE, posOnCanvas.x + WALL_THICKNESS + PIXEL_SIZE, posOnCanvas.y + WALL_THICKNESS, tileGroup, Color.BLACK);

            if (room.getSide(South) instanceof Wall)
                addRectangle(PIXEL_SIZE, WALL_THICKNESS, posOnCanvas.x + WALL_THICKNESS, posOnCanvas.y + WALL_THICKNESS + PIXEL_SIZE, tileGroup, Color.BLACK);

            if (room.getSide(West) instanceof Wall)
                addRectangle(WALL_THICKNESS, PIXEL_SIZE, posOnCanvas.x, posOnCanvas.y + WALL_THICKNESS, tileGroup, Color.BLACK);

            group.getChildren().add(tileGroup);
        });

        /* Player rectangle */
        Vector2d posOnCanvas = getRoomPosOnCanvas(playerPos);
        addRectangle(
                PIXEL_SIZE,
                PIXEL_SIZE,
                posOnCanvas.x + WALL_THICKNESS,
                posOnCanvas.y + WALL_THICKNESS,
                group,
                Color.GREEN
        );
    }

    private void addRectangle(int width, int height, int x, int y, Group tileGroup, Color color) {
        Rectangle rectangle = new Rectangle(width, height);
        rectangle.setFill(color);
        rectangle.setX(x);
        rectangle.setY(y);
        tileGroup.getChildren().add(rectangle);
    }


    private Vector2d getRoomPosOnCanvas(Vector2d relativeRoomPos) {
        return new Vector2d(
                relativeRoomPos.x * (PIXEL_SIZE + WALL_THICKNESS),
                relativeRoomPos.y * (PIXEL_SIZE + WALL_THICKNESS)
        );
    }
}
