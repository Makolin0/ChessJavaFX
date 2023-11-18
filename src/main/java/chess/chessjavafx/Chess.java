package chess.chessjavafx;

import chess.chessjavafx.Pieces.Pawn;
import chess.chessjavafx.Pieces.Piece;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Chess extends Application {
    private int mouseX;
    private int mouseY;
    int xPrev = 0;
    int yPrev = 0;
    int x;
    int y;
    Piece holdingPiece;


    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, Color.LIME);
        stage.setScene(scene);
        stage.setWidth(100*8);
        stage.setHeight(100*9);
        stage.setResizable(false);

        Image icon = new Image("file:src/imgs/pawnW.png");
        stage.getIcons().add(icon);

        BoardController boardController = new BoardController(root);

        boardController.setBoard();


        PiecesController piecesController = new PiecesController(root);





        //Testy pionkow
        //Piece piece = new Pawn(Piece.Team.WHITE, Piece.Type.PAWN, 0, 1);
        //root.getChildren().add(piece.getImg());


        //List<Piece> pieces = boardController.setPieces();






        scene.setOnMouseMoved((MouseEvent event) -> {
            mouseX = (int)event.getSceneX();
            mouseY = (int)event.getSceneY();

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
        });

        scene.setOnMousePressed((MouseEvent event) -> {
            //System.out.println("press entered");

            if(!Objects.isNull(holdingPiece)){
                //System.out.println("placing piece");
                //System.out.println("x" + x + "  y " + y);
                for (Integer[] a : holdingPiece.movableList()) {
                    if(a[0] == x && a[1] == y){
                        //System.out.println("placed piece");
                        holdingPiece.setPlace(x, y);
                        holdingPiece = null;
                        boardController.clearMovable();
                        break;
                    }
                }
                return;
            }



            holdingPiece = piecesController.checkForPiece(x, y);
            if(!Objects.isNull(holdingPiece)){
                //System.out.println("found piece");
                boardController.showMovable(holdingPiece);
            }
        });

        stage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}
