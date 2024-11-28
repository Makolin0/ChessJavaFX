package chess.chessjavafx.pieces;

import chess.chessjavafx.game.Checkerboard;
import chess.chessjavafx.game.Move;
import chess.chessjavafx.game.Position;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

public class King implements Piece{
    private final Team team;
    private final ImageView img;
    private final boolean moved;

    public King(Team team) {
        this.team = team;
        this.img = new ImageView(new Image("file:src/imgs/king" + (team == Team.WHITE ? "W" : "B") + ".png"));
        this.moved = false;
    }


    @Override
    public Team getTeam() {
        return team;
    }

    @Override
    public ImageView getImg() {
        return img;
    }

    @Override
    public List<Position> getMovableList(Checkerboard checkerboard, Position currentPosition) {
        Map<Integer, Piece> allPieces = checkerboard.getBoard();
        List<Position> movableSquares = new ArrayList<>();
        Position pos;

        if((pos = Checks.legalMove(currentPosition.getX()+1, currentPosition.getY()+1, allPieces)) != null)
            movableSquares.add(pos);
        if((pos = Checks.legalMove(currentPosition.getX()+1, currentPosition.getY(), allPieces)) != null)
            movableSquares.add(pos);
        if((pos = Checks.legalMove(currentPosition.getX()+1, currentPosition.getY()-1, allPieces)) != null)
            movableSquares.add(pos);
        if((pos = Checks.legalMove(currentPosition.getX()-1, currentPosition.getY()+1, allPieces)) != null)
            movableSquares.add(pos);
        if((pos = Checks.legalMove(currentPosition.getX()-1, currentPosition.getY(), allPieces)) != null)
            movableSquares.add(pos);
        if((pos = Checks.legalMove(currentPosition.getX()-1, currentPosition.getY()-1, allPieces)) != null)
            movableSquares.add(pos);
        if((pos = Checks.legalMove(currentPosition.getX(), currentPosition.getY()+1, allPieces)) != null)
            movableSquares.add(pos);
        if((pos = Checks.legalMove(currentPosition.getX(), currentPosition.getY()-1, allPieces)) != null)
            movableSquares.add(pos);


        // castling
        // must be king's first move
        if(moved)
            return movableSquares;


        Piece king = allPieces.get(currentPosition);
        if (!(king instanceof King)) {
            throw new IllegalArgumentException("it's not a king");
        }

        // can't be in check
        if(checkerboard.lookForCheck() == king.getTeam()){
            return movableSquares;
        }

        Checkerboard testCheckerboard = null;
        try {
            testCheckerboard = (Checkerboard) checkerboard.clone();
        } catch (CloneNotSupportedException e){
            System.out.println("Clone not supported");
            System.exit(1);
        }


        int y = currentPosition.getY();
        Position rookLPosition = new Position(0, y);
        Position rookRPosition = new Position(7, y);
        Piece rookL = allPieces.get(rookLPosition);
        Piece rookR = allPieces.get(rookRPosition);

        // must be rook's first move
        if(rookL instanceof Rook) {
            if(((Rook) rookL).getMoved()){
                rookL = null;
            }
        } else {
            rookL = null;
        }
        if(rookR instanceof Rook) {
            if(((Rook) rookR).getMoved()){
                rookR = null;
            }
        } else {
            rookR = null;
        }

        // no pieces between rook and king
        // no check at path for king
        // Rook on left
        for(int posInt : IntStream.range(currentPosition.getInt() - 1, rookLPosition.getInt() + 1).toArray()){
            if (allPieces.containsKey(posInt)) {
                rookL = null;
                break;
            }
            testCheckerboard.move(new Move(new Position(posInt), new Position(posInt-1)));
            if(testCheckerboard.lookForCheck() == king.getTeam()){
                rookL = null;
                break;
            }
        }

        try {
            testCheckerboard = (Checkerboard) checkerboard.clone();
        } catch (CloneNotSupportedException e){
            System.out.println("Clone not supported");
            System.exit(1);
        }

        // Rook on right
        for(int posInt : IntStream.range(currentPosition.getInt() + 1, rookRPosition.getInt() - 1).toArray()){
            if (allPieces.containsKey(posInt)) {
                rookR = null;
                break;
            }
            testCheckerboard.move(new Move(new Position(posInt), new Position(posInt+1)));
            if(testCheckerboard.lookForCheck() == king.getTeam()){
                rookR = null;
                break;
            }
        }

        if(rookL != null){
            movableSquares.add(new Position(currentPosition.getInt() - 2));
        }
        if(rookR != null){
            movableSquares.add(new Position(currentPosition.getInt() + 2));
        }

        return movableSquares;
    }

    @Override
    public List<Position> getBeatableList(Checkerboard checkerboard, Position currentPosition) {
        Map<Integer, Piece> allPieces = checkerboard.getBoard();
        List<Position> beatableSquares = new ArrayList<>();
        Position pos;

        if((pos = Checks.legalBeat(currentPosition.getX()+1, currentPosition.getY()+1, team, allPieces)) != null)
            beatableSquares.add(pos);
        if((pos = Checks.legalBeat(currentPosition.getX()+1, currentPosition.getY(), team, allPieces)) != null)
            beatableSquares.add(pos);
        if((pos = Checks.legalBeat(currentPosition.getX()+1, currentPosition.getY()-1, team, allPieces)) != null)
            beatableSquares.add(pos);
        if((pos = Checks.legalBeat(currentPosition.getX()-1, currentPosition.getY()+1, team, allPieces)) != null)
            beatableSquares.add(pos);
        if((pos = Checks.legalBeat(currentPosition.getX()-1, currentPosition.getY(), team, allPieces)) != null)
            beatableSquares.add(pos);
        if((pos = Checks.legalBeat(currentPosition.getX()-1, currentPosition.getY()-1, team, allPieces)) != null)
            beatableSquares.add(pos);
        if((pos = Checks.legalBeat(currentPosition.getX(), currentPosition.getY()+1, team, allPieces)) != null)
            beatableSquares.add(pos);
        if((pos = Checks.legalBeat(currentPosition.getX(), currentPosition.getY()-1, team, allPieces)) != null)
            beatableSquares.add(pos);

        return beatableSquares;
    }
}
