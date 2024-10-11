package chess.chessjavafx.game;

import javafx.geometry.Pos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {
    Move m1;
    Move m2;

    @BeforeEach
    void setUp() {
        Position c3 = new Position(2, 2);
        Position d1 = new Position(3, 0);
        m1 = new Move();
        m2 = new Move(c3, d1);
    }

    @Test
    void testToString() {
        assertEquals("c3 -> d1", m2.toString());
        assertEquals("? -> ?", m1.toString());
    }
}