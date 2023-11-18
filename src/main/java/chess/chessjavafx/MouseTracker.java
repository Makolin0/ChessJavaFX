package chess.chessjavafx;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.Objects;

public class MouseTracker implements EventHandler<MouseEvent>{
    List<Rectangle> board;
    int mouseX;
    int mouseY;
    int x;
    int y;
    int xPrev;
    int yPrev;

    public MouseTracker(List<Rectangle> board){
        this.board = board;
    }

    @Override
    public void handle(MouseEvent event) {
        mouseX = (int)event.getSceneX();
        mouseY = (int)event.getSceneY();
        System.out.printf("X: %f Y: %f%n", mouseX, mouseY);
/*




        x = mouseX / 100;
        y = mouseY / 100;



        if ((x != xPrev || y != yPrev) && x < 8 && y < 8) {
            boardController.showSelected(x, y);


            xPrev = x;
            yPrev = y;
        }

        if(!Objects.isNull(holdingPiece)){
            //System.out.println("holding piece");
            holdingPiece.moveFree(mouseX, mouseY);
        }


        //System.out.printf("X: %d Y: %d   block: %d x %d%n", mouseX, mouseY, x, y);
        //System.out.println(xPrev + "  " + yPrev);


*/

    }
}
