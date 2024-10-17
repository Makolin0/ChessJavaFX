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
        switch (team){
            case WHITE -> {
                if(currentPosition.getY() > 6)
                    return movableSquares;

                try{
                    Position newPos = new Position(currentPosition.getX(), currentPosition.getY() + 1);
                    if(Objects.isNull(allPieces.get(newPos.getInt())))
                        movableSquares.add(newPos);
                } catch(Exception ignored){}

                if(currentPosition.getY()==1) {
                    try {
                        Position newPos = new Position(currentPosition.getX(), 3);
                        if (Objects.isNull(allPieces.get(newPos.getInt())))
                            movableSquares.add(newPos);
                    } catch (Exception ignored){}
                }
            }
            case BLACK -> {
                if(currentPosition.getY() < 1)
                    return movableSquares;

                try{
                    Position newPos = new Position(currentPosition.getX(), currentPosition.getY() - 1);
                    if(Objects.isNull(allPieces.get(newPos.getInt())))
                        movableSquares.add(newPos);
                } catch(Exception ignored){}

                if(currentPosition.getY()==1) {
                    try {
                        Position newPos = new Position(currentPosition.getX(), 4);
                        if (Objects.isNull(allPieces.get(newPos.getInt())))
                            movableSquares.add(newPos);
                    } catch (Exception ignored){}
                }
            }
        }
        return movableSquares;
    }

    @Override
    public List<Position> getBeatableList(Map<Integer, Piece> allPieces, Position currentPosition) {
        System.out.println("beat for pawn");
        List<Position> beatableSquares = new ArrayList<>();
        switch (team){
            case WHITE -> {
                try{
                    Position leftPos = new Position(currentPosition.getX()-1, currentPosition.getY()+1);
                    Piece checkLeft = allPieces.get(leftPos.getInt());
                    if(!Objects.isNull(checkLeft) && checkLeft.getTeam()==Team.BLACK)
                        beatableSquares.add(leftPos);
                } catch (IllegalArgumentException ignored){}

                try{
                    Position rightPos = new Position(currentPosition.getX()+1, currentPosition.getY()+1);
                    Piece checkRight = allPieces.get(rightPos.getInt());
                    if(!Objects.isNull(checkRight) && checkRight.getTeam()==Team.BLACK)
                        beatableSquares.add(rightPos);
                } catch (Exception ignored){}
            }
            case BLACK -> {
                try{
                    Position posLeft = new Position(currentPosition.getX()-1, currentPosition.getY()-1);
                    Piece checkLeft = allPieces.get(posLeft.getInt());
                    if(!Objects.isNull(checkLeft) && checkLeft.getTeam()==Team.WHITE)
                        beatableSquares.add(posLeft);
                } catch (Exception ignored) {}

                try{
                    Position posRight = new Position(currentPosition.getX()+1, currentPosition.getY()-1);
                    Piece checkRight = allPieces.get(posRight.getInt());
                    if(!Objects.isNull(checkRight) && checkRight.getTeam()==Team.WHITE)
                        beatableSquares.add(posRight);
                } catch (Exception ignored) {}
            }
        }
        return beatableSquares;
    }
}
