package chess.chessjavafx;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class MouseTracker implements EventHandler<MouseEvent> {
    List<Rectangle> board;
    double mouseX;
    double mouseY;

    public MouseTracker(List<Rectangle> board){
        this.board = board;
    }

    @Override
    public void handle(MouseEvent event) {
        mouseX = event.getSceneX();
        mouseY = event.getSceneY();
        System.out.printf("X: %f Y: %f%n", mouseX, mouseY);

    }
}
