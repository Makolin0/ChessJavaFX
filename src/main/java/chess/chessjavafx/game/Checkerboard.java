package chess.chessjavafx.game;

import chess.chessjavafx.pieces.*;
import chess.chessjavafx.packages.Moveset;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static chess.chessjavafx.pieces.Piece.Team.WHITE;

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

    public Piece.Team getPieceTeam(Position position){
        return board.get(position.getInt()).getTeam();
    }

    public void newGame(){
        board.clear();

        for(int x = 0; x < 8; x++){
            Piece pieceW = new Pawn(WHITE);
            Piece pieceB = new Pawn(Piece.Team.BLACK);
            board.put(new Position(x, 1).getInt(), pieceW);
            board.put(new Position(x, 6).getInt(), pieceB);
        }

        board.put(0, new Rook(WHITE));
        board.put(7, new Rook(WHITE));
        board.put(56, new Rook(Piece.Team.BLACK));
        board.put(63, new Rook(Piece.Team.BLACK));

        board.put(2, new Bishop(WHITE));
        board.put(5, new Bishop(WHITE));
        board.put(58, new Bishop(Piece.Team.BLACK));
        board.put(61, new Bishop(Piece.Team.BLACK));

        board.put(1, new Knight(WHITE));
        board.put(6, new Knight(WHITE));
        board.put(57, new Knight(Piece.Team.BLACK));
        board.put(62, new Knight(Piece.Team.BLACK));

        board.put(3, new Queen(WHITE));
        board.put(59, new Queen(Piece.Team.BLACK));

        board.put(4, new King(WHITE));
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

    public Piece.Team lookForCheck(){
        System.out.println("loook for check");
        for(Map.Entry<Integer, Piece> entry : board.entrySet()) {
            Piece piece = entry.getValue();
            Piece.Team team = piece.getTeam();
            Position currentPos = new Position(entry.getKey());

            piece.getBeatableList(board, currentPos);


            for(Position pos : piece.getBeatableList(board, currentPos)){
                System.out.println(board.get(pos.getInt()).getClass().getSimpleName());
                if("King".equals(board.get(pos.getInt()).getClass().getSimpleName())){
                    System.out.println("CHECK!!!!!!!!!!!!!!!");
                    return board.get(pos.getInt()).getTeam();
                }
            }
        }

        return null;
    }

    public boolean lookForCheckmate(Piece.Team team) {
        Checkerboard mockCheckerboard = null;
        try {
            mockCheckerboard = (Checkerboard) this.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("CloneNotSupportedException");
        }
        assert mockCheckerboard != null;

        for(Map.Entry<Integer, Piece> entry : this.board.entrySet()){
            Piece piece = entry.getValue();
            Position startPos = new Position(entry.getKey());

            if(piece.getTeam() != team)
                continue;

            Moveset moveset = this.possibleMoves(startPos);

            List<Position> allMoves = moveset.getMovableList();
            allMoves.addAll(moveset.getBeatableList());

            for(Position endPos : allMoves){
                mockCheckerboard.move(new Move(startPos, endPos));
                if(mockCheckerboard.lookForCheck() == null)
                    return false;
                try {
                    mockCheckerboard = (Checkerboard) this.clone();
                } catch (CloneNotSupportedException e) {
                    System.out.println("CloneNotSupportedException");
                }
            }
        }
        System.out.println("SZACH MAT!!!!!!!!!!SZACH MAT!!!!!!!!!!SZACH MAT!!!!!!!!!!SZACH MAT!!!!!!!!!!SZACH MAT!!!!!!!!!!");
        return true;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Checkerboard copy = new Checkerboard();
        copy.board.clear();
        copy.board.putAll(this.board);
        return copy;
    }
}
