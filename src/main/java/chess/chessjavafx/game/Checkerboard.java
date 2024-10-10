package chess.chessjavafx.game;

import chess.chessjavafx.pieces.Piece;
import chess.chessjavafx.packages.Moveset;

import java.util.HashMap;
import java.util.Map;

public class Checkerboard {
    private Map<Integer, Piece> board;

    public Checkerboard(){
        this.board = new HashMap<>();
    }

    public Moveset possibleMoves(Position position) throws NullPointerException{
        Piece piece = board.get(position.getInt());
        if(piece == null){
            throw new NullPointerException("Nie odnaleziono takiego piona");
        }

        Moveset moveset = new Moveset();
        moveset.setCurrentPosition(position);
        moveset.setMovableList(piece.getMovableList(board, position));
        moveset.setBeatableList(piece.getBeatableList(board, position));

        return moveset;
    }

}
