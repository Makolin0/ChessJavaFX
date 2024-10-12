package chess.chessjavafx.history;

import chess.chessjavafx.game.GameMoves;
import chess.chessjavafx.javaFX.HistorySceneController;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HistoryController {
    private List<GameMoves> gameList;
    private HistorySceneController historySceneController;

    public HistoryController(Stage stage){
        this.gameList = new ArrayList<>();
        this.historySceneController = new HistorySceneController(stage);

        loadGames();
        historySceneController.loadGames(gameList);
    }

    public HistorySceneController getHistorySceneController() {
        return historySceneController;
    }

    public void loadGames(){
        gameList.clear();
        Path dir = Paths.get("data");

        try(DirectoryStream<Path> ds = Files.newDirectoryStream(dir)){
            for(Path file : ds){
                System.out.println(file.getFileName());
                gameList.add(new GameMoves(file));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
