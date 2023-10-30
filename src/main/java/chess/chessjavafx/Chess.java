package chess.chessjavafx;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Chess extends Application {
    private int mouseX;
    private int mouseY;
    int xPrev = 0;
    int yPrev = 0;


    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, Color.LIME);
        stage.setScene(scene);
        stage.setWidth(100*8);
        stage.setHeight(100*9);
        stage.setResizable(false);

        List<Rectangle> board = setBoard(root);



        scene.setOnMouseMoved((MouseEvent event) -> {
            mouseX = (int)event.getSceneX();
            mouseY = (int)event.getSceneY();

            int x = mouseX / 100;
            int y = mouseY / 100;



            if ((x != xPrev || y != yPrev) && x < 8 && y < 8) {
                board.get(x*8+y).setOpacity(0.6);
                board.get(xPrev*8+yPrev).setOpacity(1);
                xPrev = x;
                yPrev = y;
            }



            System.out.printf("X: %d Y: %d   block: %d x %d%n", mouseX, mouseY, x, y);
            //System.out.println(xPrev + "  " + yPrev);
        });





        stage.show();
    }

    private List<Rectangle> setBoard(Group root){
        List<Rectangle> board = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++){
                //System.out.printf("i: %d j: %d kolor: %s%n", i, j, (i+j)%2==0?"white":"gray");

                Rectangle rectangle = new Rectangle();
                rectangle.setFill((i+j)%2==0?Color.WHITE:Color.DARKGRAY);
                rectangle.setX(i*100);
                rectangle.setY(j*100);
                rectangle.setWidth(100);
                rectangle.setHeight(100);

                board.add(rectangle);
            }
        }
        root.getChildren().addAll(board);

        return board;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
