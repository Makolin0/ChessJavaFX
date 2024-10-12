package chess.chessjavafx.javaFX;

import chess.chessjavafx.game.GameMoves;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class HistorySceneController {
    private Scene scene;
    private VBox root;
    private Stage stage;
    private List<Button> gameList;

    public HistorySceneController(Stage stage){
        this.root = new VBox(10);
        this.scene = new Scene(root);
        this.gameList = new ArrayList<>();
        this.stage = stage;

        root.setMinWidth(1000);
        root.setMinHeight(1000);
        root.setAlignment(Pos.CENTER);
    }

    public Scene getScene(){
        return scene;
    }

    public void loadGames(List<GameMoves> gameMovesList){
        for(GameMoves gameMoves : gameMovesList){
            String formattedDuration = gameMoves.getDuration().toHoursPart() + ":"+gameMoves.getDuration().toMinutesPart();
            Button button = new Button(gameMoves.getStartTime() +" "+ gameMoves.getWinner() +" "+ formattedDuration);

            HistoryGameSceneController historyGameSceneController = new HistoryGameSceneController(gameMoves.getMoves());
            button.setOnAction(event -> {
                stage.setScene(historyGameSceneController.getScene());
            });
            gameList.add(button);

            root.getChildren().add(button);
        }
    }


}
