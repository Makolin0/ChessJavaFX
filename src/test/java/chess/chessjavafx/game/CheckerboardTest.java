package chess.chessjavafx.game;

import chess.chessjavafx.packages.Moveset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckerboardTest {
    Checkerboard checkerboard;

    @BeforeEach
    void setUp() {
        checkerboard = new Checkerboard();
    }

    @Test
    void newGame() {
        checkerboard
    }

    @Test
    void possibleMoves() {
        Moveset moveset = new Moveset();

        //
        List<Position> movableList = new ArrayList<>();
        movableList.add(new Position())
        moveset.setBeatableList(new ArrayList<>());
        moveset.setMovableList();
    }

    @Test
    void move() {
    }
}