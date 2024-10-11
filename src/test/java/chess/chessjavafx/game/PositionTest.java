package chess.chessjavafx.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class PositionTest {

    Position p1;
    Position p2;
    Position p3;

    @BeforeEach
    void setUp() {
        p1 = new Position(2, 5);
        p2 = new Position(0, 0);
        p3 = new Position(7, 1);
    }

    @Test
    void getX() {
        assertEquals(2, p1.getX());
        assertEquals(0, p2.getX());
        assertEquals(7, p3.getX());
    }

    @Test
    void getY() {
        assertEquals(5, p1.getY());
        assertEquals(0, p2.getY());
        assertEquals(1, p3.getY());
    }

    @Test
    void getInt() {
        assertEquals(8*5+2, p1.getInt());
        assertEquals(0, p2.getInt());
        assertEquals(8*1+7, p3.getInt());
    }

    @Test
    void getArray() {
        assertArrayEquals(new Integer[]{2, 5}, p1.getArray());
        assertArrayEquals(new Integer[]{0, 0}, p2.getArray());
        assertArrayEquals(new Integer[]{7, 1}, p3.getArray());
    }

    @Test
    void setPosition() {
        p1.setPosition(1, 1);
        assertArrayEquals(new Integer[]{1, 1}, p1.getArray());
    }

    @Test
    void setPositionInt() {
        p1.setPosition(9);
        assertArrayEquals(new Integer[]{1, 1}, p1.getArray());
    }

    @Test
    void setPositionArray() {
        p1.setPosition(new Integer[]{1, 1});
        assertArrayEquals(new Integer[]{1, 1}, p1.getArray());
    }

    @Test
    void testToString() {
        assertEquals("c6", p1.toString());
        assertEquals("a1", p2.toString());
        assertEquals("h2", p3.toString());
    }

    @Test
    void testEquals() {
        Position p4 = new Position(7,1);
        assertNotEquals(p1, p2);
        assertNotEquals(p1, p3);
        assertEquals(p3, p4);
    }
}