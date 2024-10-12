package chess.chessjavafx.javaFX;

import chess.chessjavafx.game.GameMoves;
import chess.chessjavafx.game.Move;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class HistoryGameSceneController {
    private Scene scene;
    private VBox root;

    public HistoryGameSceneController(List<Move> moves){
        this.root = new VBox(10);
        this.scene = new Scene(root);

        root.setMinWidth(1000);
        root.setMinHeight(1000);
        root.setAlignment(Pos.CENTER);

        loadMoves(moves);
    }

    public void loadMoves(List<Move> moves){
        for(Move move : moves){
            Text text = new Text(move.toString());
            root.getChildren().add(text);
        }
    }

    public Scene getScene() {
        return scene;
    }
}