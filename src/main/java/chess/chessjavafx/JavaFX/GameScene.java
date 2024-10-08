package chess.chessjavafx.JavaFX;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class GameScene {
    private Parent parent;
    private List<Rectangle> board = new ArrayList<>();

    public GameScene() {

        this.parent = parent;
    }

    private void generateBoard(){
        for (int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++){
                //System.out.printf("i: %d j: %d kolor: %s%n", i, j, (i+j)%2==0?"white":"gray");

                Rectangle rectangle = new Rectangle();
                rectangle.setFill((i+j)%2==0? Color.WHITE:Color.DARKGRAY);
                rectangle.setX(i*100);
                rectangle.setY(j*100);
                rectangle.setWidth(100);
                rectangle.setHeight(100);

                board.add(rectangle);
            }
        }

        this
    }
}
