package chess.chessjavafx.Pieces;

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
    public List<Integer[]> movableList();
    public void move(int x, int y);
}
