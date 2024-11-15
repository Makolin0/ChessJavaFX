package chess.chessjavafx.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

class GameMovesTest {
    GameMoves gm;

    @BeforeEach
    void setUp() {
        gm = new GameMoves();
    }

    @Test
    void save() {
        gm.addMove(new Move(new Position(1, 1), new Position(2, 2)));
        gm.addMove(new Move(new Position(1, 1), new Position(2, 2)));
        gm.addMove(new Move(new Position(1, 1), new Position(2, 2)));
        gm.addMove(new Move(new Position(1, 1), new Position(2, 2)));
        gm.addMove(new Move(new Position(1, 2), new Position(1, 2)));
        gm.addMove(new Move(new Position(1, 2), new Position(1, 2)));
        gm.addMove(new Move(new Position(1, 2), new Position(1, 2)));
        gm.addMove(new Move(new Position(4, 1), new Position(2, 6)));
        gm.addMove(new Move(new Position(4, 1), new Position(2, 6)));
        gm.addMove(new Move(new Position(4, 1), new Position(2, 6)));
        gm.addMove(new Move(new Position(4, 1), new Position(2, 6)));
        gm.addMove(new Move(new Position(4, 1), new Position(2, 6)));
        gm.addMove(new Move(new Position(4, 1), new Position(2, 6)));
        gm.addMove(new Move(new Position(4, 1), new Position(2, 6)));
        gm.addMove(new Move(new Position(4, 1), new Position(2, 6)));

//        gm.setWinner(Winner.WHITE);
        gm.setDuration(Duration.ofHours(2));

        gm.save();
    }

    @Test
    void load() {
//        gm.load("2024-10-12 16:04:37.txt");
//
//        System.out.println("duration" + gm.getDuration().getSeconds());
//        System.out.println("winner" + gm.getWinner());
//        System.out.println("start" + gm.getStartTime());
//
//        for(Move move : gm.getMoves()){
//            System.out.println(move);
//        }
    }
}