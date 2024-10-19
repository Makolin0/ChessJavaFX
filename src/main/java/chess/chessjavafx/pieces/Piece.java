package chess.chessjavafx.pieces;

import chess.chessjavafx.game.Position;
import javafx.scene.image.ImageView;
import java.util.List;
import java.util.Map;

public interface Piece {

    enum Team {
        WHITE, BLACK
    }

    ImageView getImg();
    Team getTeam();
    List<Position> getMovableList(Map<Integer, Piece> allPieces, Position currentPosition);
    List<Position> getBeatableList(Map<Integer, Piece> allPieces, Position currentPosition);
}
