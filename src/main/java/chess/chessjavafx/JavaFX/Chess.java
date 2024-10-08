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
//        Scene scene = new Scene(root, Color.LIME);
//        stage.setScene(scene);
//        stage.setWidth(100*8 + 100);
//        stage.setHeight(100*8 + 20);
        stage.setResizable(false);

        Image icon = new Image("file:src/imgs/pawnW.png");
        stage.getIcons().add(icon);



//        InfoPanel infoPanel = new InfoPanel(root);
//        boardController.setInfoPanel(infoPanel);


        // obsługa komunikacji z arduino
//        SerialTest serialTest = new SerialTest(boardController);
        // serialTest.initiate();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
