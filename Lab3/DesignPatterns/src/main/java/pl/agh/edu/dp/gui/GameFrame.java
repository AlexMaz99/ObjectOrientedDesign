package pl.agh.edu.dp.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.agh.edu.dp.labirynth.Builder.CountingMazeBuilder;
import pl.agh.edu.dp.labirynth.Factory.BombedMazeFactory;
import pl.agh.edu.dp.labirynth.Factory.EnchantedMazeFactory;
import pl.agh.edu.dp.labirynth.Factory.MazeFactory;
import pl.agh.edu.dp.labirynth.MazeGame;
import pl.agh.edu.dp.labirynth.Utils.Vector2d;

import static pl.agh.edu.dp.labirynth.Utils.Direction.*;


public class GameFrame extends Application {


    /* ================= Edit here  ================= */
    static final int PIXEL_SIZE = 64;
    static final int WALL_THICKNESS = 2;

    private void SetupMaze(Stage stage) throws Exception {
        stage.setTitle("MazeGame3000");

        /* What factory and builder */
        MazeFactory factory = EnchantedMazeFactory.getInstance();
        CountingMazeBuilder builder = new CountingMazeBuilder(factory);

        mazeGame.createMaze(builder, factory);

        /* Starting pos of player */
        Vector2d playerPos = new Vector2d(4, 0);

        mazeGame.createPlayerAt(playerPos);
    }

    /* ================= GameFrame  ================= */

    private MazeGame mazeGame;

    @Override
    public void start(Stage stage) throws Exception {

        this.mazeGame =  MazeGame.getInstance();
        SetupMaze(stage);
        int mapHeight = (mazeGame.getRightLowerPos().y + 1) * PIXEL_SIZE + (mazeGame.getRightLowerPos().y + 2) * WALL_THICKNESS;
        int mapWidth = (mazeGame.getRightLowerPos().x + 1) * PIXEL_SIZE + (mazeGame.getRightLowerPos().x + 2) * WALL_THICKNESS;

        stage.setResizable(false);
        stage.setOnCloseRequest(event -> Platform.exit());

        Pane rootNode = new Pane();
        GameScene gameScene = new GameScene(rootNode, mapWidth, mapHeight);
        refresh(gameScene);
        gameScene.setOnKeyPressed(event -> {

            try {
                switch (event.getCode()) {
                    case LEFT:
                        mazeGame.makeMove(West);
                        break;
                    case RIGHT:
                        mazeGame.makeMove(East);
                        break;
                    case DOWN:
                        mazeGame.makeMove(South);
                        break;
                    case UP:
                        mazeGame.makeMove(North);
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            refresh(gameScene);

        });
        stage.setScene(gameScene);
        stage.show();

    }

    private void refresh(GameScene gameScene) {
        gameScene.genTiles(
                mazeGame.getMaze().getRooms(),
                mazeGame.getPlayer().getPos(),
                mazeGame.getRightLowerPos()
        );
    }


    public void show(String[] args){
        launch(args);
    }
}
