package chess.chessjavafx;

import chess.chessjavafx.game.Checkerboard;
import chess.chessjavafx.game.Position;

public class Test {
    public static void main(String[] args) {
        Checkerboard checkerboard = new Checkerboard();

//        checkerboard.possibleMoves(new Position(4));
        Position position = new Position(0, 0);
        System.out.println(position);
    }

}
