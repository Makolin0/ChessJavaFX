package chess.chessjavafx.pieces;

import chess.chessjavafx.Team;
import chess.chessjavafx.game.Checkerboard;
import chess.chessjavafx.game.Position;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Pawn implements Piece{
    private final Team team;
    private final ImageView img;

    public Pawn(Team team) {
        this.team = team;
        this.img = new ImageView(new Image("file:src/imgs/pawn" + (team == Team.WHITE ? "W" : "B") + ".png"));
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
        switch (team){
            case WHITE -> {
                if((pos = Checks.legalMove(currentPosition.getX(), currentPosition.getY() + 1, allPieces)) != null)
                    movableSquares.add(pos);

                if(currentPosition.getY()==1) {
                    if((pos = Checks.legalMove(currentPosition.getX(), currentPosition.getY() + 2, allPieces)) != null)
                        movableSquares.add(pos);
                }

                // en passant
                if(currentPosition.getY()==4) {
                    pos = Checks.legalBeat(currentPosition.getX() - 1, currentPosition.getY(), Team.WHITE, allPieces);
                    if(pos != null){
                        Piece leftPawn = allPieces.get(pos.getInt());
                        if(leftPawn.getClass().equals(Pawn.class)){
                            Position p = new Position(currentPosition.getX() - 1, currentPosition.getY() + 1);
                            p.setPassingEnemy(pos);
                            movableSquares.add(p);
                        }
                    }

                    pos = Checks.legalBeat(currentPosition.getX() + 1, currentPosition.getY(), Team.WHITE, allPieces);
                    if(pos != null){
                        Piece leftPawn = allPieces.get(pos.getInt());
                        if(leftPawn.getClass().equals(Pawn.class)){
                            Position p = new Position(currentPosition.getX() + 1, currentPosition.getY() + 1);
                            p.setPassingEnemy(pos);
                            movableSquares.add(p);
                        }
                    }
                }
            }
            case BLACK -> {
                if((pos = Checks.legalMove(currentPosition.getX(), currentPosition.getY() - 1, allPieces)) != null)
                    movableSquares.add(pos);

                if(currentPosition.getY()==6) {
                    if((pos = Checks.legalMove(currentPosition.getX(), currentPosition.getY() - 2, allPieces)) != null)
                        movableSquares.add(pos);
                }

                // en passant
                if(currentPosition.getY()==3) {
                    pos = Checks.legalBeat(currentPosition.getX() - 1, currentPosition.getY(), Team.BLACK, allPieces);
                    if(pos != null){
                        Piece rightPawn = allPieces.get(pos.getInt());
                        if(rightPawn.getClass().equals(Pawn.class)){
                            Position p = new Position(currentPosition.getX() - 1, currentPosition.getY() - 1);
                            p.setPassingEnemy(pos);
                            movableSquares.add(p);
                        }
                    }

                    pos = Checks.legalBeat(currentPosition.getX() + 1, currentPosition.getY(), Team.BLACK, allPieces);
                    if(pos != null){
                        Piece rightPawn = allPieces.get(pos.getInt());
                        if(rightPawn.getClass().equals(Pawn.class)){
                            Position p = new Position(currentPosition.getX() + 1, currentPosition.getY() - 1);
                            p.setPassingEnemy(pos);
                            movableSquares.add(p);
                        }
                    }
                }
            }
        }
        return movableSquares;
    }

    @Override
    public List<Position> getBeatableList(Checkerboard checkerboard, Position currentPosition) {
        Map<Integer, Piece> allPieces = checkerboard.getBoard();
        List<Position> beatableSquares = new ArrayList<>();
        Position pos;
        switch (team){
            case WHITE -> {
                if((pos = Checks.legalBeat(currentPosition.getX()-1, currentPosition.getY()+1, team, allPieces)) != null)
                    beatableSquares.add(pos);
                if((pos = Checks.legalBeat(currentPosition.getX()+1, currentPosition.getY()+1, team, allPieces)) != null)
                    beatableSquares.add(pos);
            }
            case BLACK -> {
                if((pos = Checks.legalBeat(currentPosition.getX()-1, currentPosition.getY()-1, team, allPieces)) != null)
                    beatableSquares.add(pos);
                if((pos = Checks.legalBeat(currentPosition.getX()+1, currentPosition.getY()-1, team, allPieces)) != null)
                    beatableSquares.add(pos);
            }
        }
        return beatableSquares;
    }
}
