package chess.chessjavafx.Pieces;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public interface Piece {


    enum Team {
        WHITE, BLACK;
    }

    enum Type {
        PAWN, ROOK, KNIGHT, BISHOP, QUEEN, KING;
    }

    public Type getType();
    public Team getTeam();
    public ImageView getImg();
    public boolean checkPlace(int x, int y);
    public List<Integer[]> movableList();
    public void moveFree(int x, int y);
    public void moveBack();
    public void setPlace(int x, int y);
}
