package chess.chessjavafx.pieces;

import chess.chessjavafx.game.Position;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.Map;

public interface Piece {

    enum Team {
        WHITE, BLACK;
    }

    public ImageView getImg();
    public Team getTeam();
    public List<Position> getMovableList(Map<Integer, Piece> allPieces, Position currentPosition);
    public List<Position> getBeatableList(Map<Integer, Piece> allPieces, Position currentPosition);
}
