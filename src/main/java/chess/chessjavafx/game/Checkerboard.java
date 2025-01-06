package chess.chessjavafx.game;


import chess.chessjavafx.Team;
import chess.chessjavafx.packages.Moveset;
import chess.chessjavafx.pieces.*;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Checkerboard {
    private final Map<Integer, Piece> board;

    public Checkerboard(){
        this.board = new HashMap<>();
        newGame();
    }

    public Map<Integer, ImageView> getImages(){
        Map<Integer, ImageView> images = new HashMap<>();
        board.forEach((key, piece) -> images.put(key, piece.getImg()));
        return images;
    }

    public Team getPieceTeam(Position position){
        return board.get(position.getInt()).getTeam();
    }

    public Map<Integer, Piece> getBoard(){
        return board;
    }

    public void newGame(){
        board.clear();

        for(int x = 0; x < 8; x++){
            Piece pieceW = new Pawn(Team.WHITE);
            Piece pieceB = new Pawn(Team.BLACK);
            board.put(new Position(x, 1).getInt(), pieceW);
            board.put(new Position(x, 6).getInt(), pieceB);
        }

        board.put(0, new Rook(Team.WHITE));
        board.put(7, new Rook(Team.WHITE));
        board.put(56, new Rook(Team.BLACK));
        board.put(63, new Rook(Team.BLACK));

        board.put(2, new Bishop(Team.WHITE));
        board.put(5, new Bishop(Team.WHITE));
        board.put(58, new Bishop(Team.BLACK));
        board.put(61, new Bishop(Team.BLACK));

        board.put(1, new Knight(Team.WHITE));
        board.put(6, new Knight(Team.WHITE));
        board.put(57, new Knight(Team.BLACK));
        board.put(62, new Knight(Team.BLACK));

        board.put(3, new Queen(Team.WHITE));
        board.put(59, new Queen(Team.BLACK));

        board.put(4, new King(Team.WHITE));
        board.put(60, new King(Team.BLACK));
    }

    public Moveset possibleMoves(Position position) throws NullPointerException{
        Piece piece = board.get(position.getInt());
        if(piece == null){
            throw new NullPointerException("Nie odnaleziono takiego piona");
        }

        Moveset moveset = new Moveset();
        moveset.setCurrentPosition(position);
        moveset.setMovableList(piece.getMovableList(this, position));
        moveset.setBeatableList(piece.getBeatableList(this, position));

        return moveset;
    }


    public void move(Move move) throws NullPointerException, IllegalArgumentException{
        Position piecePosition = move.getStartPosition();
        Position destination = move.getEndPosition();
        Piece piece = board.get(piecePosition.getInt());

        if(piece == null){
            throw new NullPointerException("Nie odnaleziono takiego piona");
        }

        List<Position> movableList = piece.getMovableList(this, piecePosition);
        List<Position> beatableList = piece.getBeatableList(this, piecePosition);

        if(movableList.contains(destination)){
            // ruch
            destination = movableList.get(movableList.indexOf(destination));

            // Oznaczenie że figura została ruszona
            String className = board.get(piecePosition.getInt()).getClass().getSimpleName();
            if("King".equals(className)){
                ((King)board.get(piecePosition.getInt())).setMoved(true);
            } else if ("Rook".equals(className)) {
                ((Rook)board.get(piecePosition.getInt())).setMoved(true);
            }


            // roszada
            if(destination.getCastling() != null){
                System.out.println("castling: " + destination.getCastling());
                Piece castlingRook = board.get(destination.getCastling().getStartPosition().getInt());
                board.remove(destination.getCastling().getStartPosition().getInt());
                board.put(destination.getCastling().getEndPosition().getInt(), castlingRook);
            }
            // en passant
            if(destination.getPassingEnemy() != null){
                board.remove(destination.getPassingEnemy().getInt());
            }

            // wykonanie ruchu
            board.remove(piecePosition.getInt());
            board.put(destination.getInt(), piece);

        } else if (beatableList.contains(destination)) {
            //bicie
            board.remove(piecePosition.getInt());
            board.replace(destination.getInt(), piece);
        } else {
            throw new IllegalArgumentException("Pion nie może wykonać tego ruchu");
        }
    }

    public Team lookForCheck(){
        for(Map.Entry<Integer, Piece> entry : board.entrySet()) {
            Piece piece = entry.getValue();
            Position currentPos = new Position(entry.getKey());

            piece.getBeatableList(this, currentPos);

            for(Position pos : piece.getBeatableList(this, currentPos)){
                if("King".equals(board.get(pos.getInt()).getClass().getSimpleName())){
                    return board.get(pos.getInt()).getTeam();
                }
            }
        }

        return null;
    }

    public boolean lookForCheckmate(Team team) {
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
        System.out.println("SZACH MAT!!");
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Checkerboard copy = new Checkerboard();
        copy.board.clear();
        copy.board.putAll(this.board);
        return copy;
    }

    public Boolean checkIfLegal(List<Integer> physicalBoard) {
        if(physicalBoard.size() == board.size()){
            for (Integer position : physicalBoard) {
                if(!board.containsKey(position)){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
