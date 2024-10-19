package chess.chessjavafx;

import chess.chessjavafx.game.GameController;
import chess.chessjavafx.game.Position;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Scanner;

public class Chess extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Image icon = new Image("file:src/imgs/pawnW.png");
        stage.getIcons().add(icon);
        stage.setTitle("Szachy - Arduino");

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/main-menu.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    private void terminalController(GameController gameController){
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String text = scanner.next();
            System.out.println("napisano " + text);
            Position pos = new Position(text);
            System.out.println("pozycja " + pos);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gameController.sendPosition(pos);
                }
            });
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
