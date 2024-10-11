package chess.chessjavafx.game;

import chess.chessjavafx.pieces.*;
import chess.chessjavafx.packages.Moveset;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

public class Checkerboard {
    private Map<Integer, Piece> board;

    public Checkerboard(){
        this.board = new HashMap<>();
        newGame();
    }

    public Map<Integer, ImageView> getImages(){
        Map<Integer, ImageView> images = new HashMap<>();
        board.forEach((key, piece) -> {
            images.put(key, piece.getImg());
        });
        return images;
    }

    public void newGame(){
        board.clear();

        for(int x = 0; x < 8; x++){
            Piece pieceW = new Pawn(Piece.Team.WHITE);
            Piece pieceB = new Pawn(Piece.Team.BLACK);
            board.put(new Position(x, 1).getInt(), pieceW);
            board.put(new Position(x, 6).getInt(), pieceB);
        }

        board.put(0, new Rook(Piece.Team.WHITE));
        board.put(7, new Rook(Piece.Team.WHITE));
        board.put(56, new Rook(Piece.Team.BLACK));
        board.put(63, new Rook(Piece.Team.BLACK));

        board.put(2, new Bishop(Piece.Team.WHITE));
        board.put(5, new Bishop(Piece.Team.WHITE));
        board.put(58, new Bishop(Piece.Team.BLACK));
        board.put(61, new Bishop(Piece.Team.BLACK));

        board.put(1, new Knight(Piece.Team.WHITE));
        board.put(6, new Knight(Piece.Team.WHITE));
        board.put(57, new Knight(Piece.Team.BLACK));
        board.put(62, new Knight(Piece.Team.BLACK));

        board.put(3, new Queen(Piece.Team.WHITE));
        board.put(59, new Queen(Piece.Team.BLACK));

        board.put(4, new King(Piece.Team.WHITE));
        board.put(60, new King(Piece.Team.BLACK));
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

    public void move(Move move) throws NullPointerException, IllegalArgumentException{
        Piece piece = board.get(move.getStartPosition().getInt());
        if(piece == null){
            throw new NullPointerException("Nie odnaleziono takiego piona");
        }
        if(piece.getMovableList(board, move.getStartPosition()).contains(move.getEndPosition())){
            // ruch
            board.remove(move.getStartPosition().getInt());
            board.put(move.getEndPosition().getInt(), piece);
        } else if (piece.getBeatableList(board, move.getStartPosition()).contains(move.getEndPosition())) {
            //bicie
            board.remove(move.getStartPosition().getInt());
            board.replace(move.getEndPosition().getInt(), piece);
        } else {
            throw new IllegalArgumentException("Pion nie może wykonać tego ruchu");
        }
    }


}
