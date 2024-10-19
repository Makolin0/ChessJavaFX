package chess.chessjavafx.pieces;

import chess.chessjavafx.game.Position;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Pawn implements Piece{
    private Team team;
    private ImageView img;

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
    public List<Position> getMovableList(Map<Integer, Piece> allPieces, Position currentPosition) {
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
            }
            case BLACK -> {
                if((pos = Checks.legalMove(currentPosition.getX(), currentPosition.getY() - 1, allPieces)) != null)
                    movableSquares.add(pos);

                if(currentPosition.getY()==6) {
                    if((pos = Checks.legalMove(currentPosition.getX(), currentPosition.getY() - 2, allPieces)) != null)
                        movableSquares.add(pos);
                }
            }
        }
        return movableSquares;
    }

    @Override
    public List<Position> getBeatableList(Map<Integer, Piece> allPieces, Position currentPosition) {
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
