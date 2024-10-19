package chess.chessjavafx;

import chess.chessjavafx.game.GameController;
import chess.chessjavafx.game.Move;
import chess.chessjavafx.game.Position;
import chess.chessjavafx.history.HistoryController;
import chess.chessjavafx.javaFX.GameSceneController;
import chess.chessjavafx.javaFX.HistorySceneController;
import chess.chessjavafx.javaFX.MainMenu;
import chess.chessjavafx.javaFX.MainMenuSceneController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Scanner;

public class Chess extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Image icon = new Image("file:src/imgs/pawnW.png");
        stage.getIcons().add(icon);
        stage.setTitle("Szachy - Arduino");
        stage.setFullScreen(true);

//        MainMenuSceneController mainMenuSceneController = new MainMenuSceneController(stage);
//        Scene MainMenu = mainMenuSceneController.getScene();
//        stage.setScene(MainMenu);
//
//        GameController gameController = new GameController();
//        GameSceneController gameSceneController = gameController.getGameSceneController();
//        mainMenuSceneController.setGameButton(gameSceneController.getScene());
//
//        HistoryController historyController = new HistoryController(stage, MainMenu);
//        HistorySceneController historySceneController = historyController.getHistorySceneController();
//        mainMenuSceneController.setHistoryButton(historySceneController.getScene());
//
//
//        Thread thread = new Thread(() -> {
//            terminalController(gameController);
//        });
//        thread.start();

        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/main-menu.fxml")));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

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
