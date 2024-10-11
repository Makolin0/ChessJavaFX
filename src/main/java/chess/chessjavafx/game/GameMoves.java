package chess.chessjavafx.game;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GameMoves {
    private List<Move> moves;
    private LocalDateTime startTime;
    private Winner winner;

    public GameMoves() {
        moves = new ArrayList<>();
        startTime = LocalDateTime.now();
        winner = Winner.DRAW;
    }

    public void addMove(Move move){
        moves.add(move);
    }

}
