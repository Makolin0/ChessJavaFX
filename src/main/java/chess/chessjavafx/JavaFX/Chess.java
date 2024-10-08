package chess.chessjavafx.JavaFX;

import chess.chessjavafx.BoardController;
import chess.chessjavafx.InfoPanel;
import chess.chessjavafx.Position;
import chess.chessjavafx.SerialTest;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Objects;

public class Chess extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // set scene
        Group root = new Group();
        Scene scene = new Scene(root, Color.LIME);
        GameScene gameScene = new GameScene(root);
//        stage.setScene(scene);
        stage.setScene(gameScene);
        stage.setResizable(false);

        Image icon = new Image("file:src/imgs/pawnW.png");
        stage.getIcons().add(icon);




        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
