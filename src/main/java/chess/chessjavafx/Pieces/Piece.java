package chess.chessjavafx.Pieces;

import chess.chessjavafx.Position;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.Map;

public interface Piece {


    enum Team {
        WHITE, BLACK;
    }

    enum Type {
        PAWN, ROOK, KNIGHT, BISHOP, QUEEN, KING;
    }

//    public Type getType();
    public Team getTeam();
    public ImageView getImg();
    public List<Position> getMovableList(Map<Integer, Piece> allPieces);
    public List<Position> getBeatableList(Map<Integer, Piece> allPieces);
    public void moveFree(int x, int y);
    public void moveBack();
    public void setPosition(Position position);
    public Position getPosition();
}
