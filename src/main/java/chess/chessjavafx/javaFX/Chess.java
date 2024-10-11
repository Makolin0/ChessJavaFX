package chess.chessjavafx.javaFX;

import chess.chessjavafx.game.GameController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Chess extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // set scene
        Group root = new Group();
        GameController gameController = new GameController(root);
        GameSceneController gameSceneController = gameController.getGameSceneController();
        stage.setScene(gameSceneController.getScene());
        stage.setResizable(false);

        Image icon = new Image("file:src/imgs/pawnW.png");
        stage.getIcons().add(icon);

        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
