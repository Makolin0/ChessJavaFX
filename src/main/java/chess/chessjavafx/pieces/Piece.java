package chess.chessjavafx.pieces;

import chess.chessjavafx.Team;
import chess.chessjavafx.game.Checkerboard;
import chess.chessjavafx.game.Position;
import javafx.scene.image.ImageView;
import java.util.List;

public interface Piece {
    ImageView getImg();
    Team getTeam();
    List<Position> getMovableList(Checkerboard checkerboard, Position currentPosition);
    List<Position> getBeatableList(Checkerboard checkerboard, Position currentPosition);
}
