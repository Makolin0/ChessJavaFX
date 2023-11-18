package chess.chessjavafx;

import chess.chessjavafx.Pieces.Pawn;
import chess.chessjavafx.Pieces.Piece;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BoardController {
    private Group root;
    private List<Rectangle> board;
    private Integer[] selected;
    private List<Integer[]> piecePreview;


    public BoardController(Group root){
        this.root = root;
        this.board = new ArrayList<>();
        this.selected = new Integer[2];
        this.piecePreview = new ArrayList<>();
    }


    public void setBoard(){
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
        root.getChildren().addAll(board);
    }

    public List<Piece> setPieces(){
        List<Piece> pieces = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            Piece piece = new Pawn(Piece.Team.WHITE, Piece.Type.PAWN, i, 1);
            pieces.add(piece);
            root.getChildren().add(piece.getImg());
        }

        return pieces;
    }


    public void showMovable(Piece piece){
        piecePreview = piece.movableList();

        update();
    }
    public void clearMovable(){
        piecePreview = null;
        update();
    }

    public void showSelected(int x, int y){
        selected = new Integer[]{x, y};
        update();
    }
    
    public void resetBoard(){
        for (Rectangle r :
                board) {
            r.setOpacity(1);
        }
    }

    public void update(){
        resetBoard();

        if(!Objects.isNull(piecePreview))
            for (Integer[] r : piecePreview) {
                board.get(r[0]*8 + r[1]).setOpacity(0.8);
            }

        board.get(selected[0] * 8 + selected[1]).setOpacity(0.6);
    }

}
