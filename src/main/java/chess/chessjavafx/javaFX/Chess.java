package chess.chessjavafx.javaFX;

import chess.chessjavafx.game.GameController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Chess extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Image icon = new Image("file:src/imgs/pawnW.png");
        stage.getIcons().add(icon);
        stage.setTitle("Szachy - Arduino");
        stage.setWidth(1000);
        stage.setWidth(1000);
        stage.setResizable(false);

        GameController gameController = new GameController();
        GameSceneController gameSceneController = gameController.getGameSceneController();

        MainMenuSceneController mainMenuSceneController = new MainMenuSceneController(stage, gameSceneController.getScene());
        stage.setScene(mainMenuSceneController.getScene());

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
