package chess.chessjavafx.javaFX;

import chess.chessjavafx.game.Position;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class GameSceneController {
    private Scene scene;
    private Group group;
    private List<Rectangle> board = new ArrayList<>();

    public GameSceneController(Group group) {
        this.scene = new Scene(group, Color.BLUE);
        this.group = group;

        generateBoard();
    }

    public Scene getScene(){
        return scene;
    }

    private void generateBoard(){
        for (int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++){
                Rectangle rectangle = new Rectangle();
                rectangle.setFill((i+j)%2==0? Color.WHITE:Color.DARKGRAY);
                rectangle.setX(i*100);
                rectangle.setY(j*100);
                rectangle.setWidth(100);
                rectangle.setHeight(100);

                board.add(rectangle);
            }
        }
        group.getChildren().addAll(board);

        board.get(new Position(2, 4).getInt()).setFill(Color.GREEN);
    }
}
