package chess.chessjavafx;

import chess.chessjavafx.game.GameController;
import chess.chessjavafx.history.HistoryController;
import chess.chessjavafx.javaFX.GameSceneController;
import chess.chessjavafx.javaFX.HistorySceneController;
import chess.chessjavafx.javaFX.MainMenuSceneController;
import javafx.application.Application;
import javafx.scene.image.Image;
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

        HistoryController historyController = new HistoryController(stage);
        HistorySceneController historySceneController = historyController.getHistorySceneController();

        MainMenuSceneController mainMenuSceneController = new MainMenuSceneController(stage, gameSceneController.getScene(), historySceneController.getScene());
        stage.setScene(mainMenuSceneController.getScene());

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
