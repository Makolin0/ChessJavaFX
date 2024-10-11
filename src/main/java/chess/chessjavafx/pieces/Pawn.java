package chess.chessjavafx.pieces;

import chess.chessjavafx.game.Position;
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

                if(Objects.isNull(allPieces.get(currentPosition.getInt()+8))) {
                    movableSquares.add(new Position(currentPosition.getX(), currentPosition.getY() + 1));
                } else {return movableSquares;}
                if(currentPosition.getY()==1)
                    if(Objects.isNull(allPieces.get(currentPosition.getInt()+16)))
                        movableSquares.add(new Position(currentPosition.getX(), 3));
            }
            case BLACK -> {
                if(currentPosition.getY() < 1)
                    return movableSquares;

                if(Objects.isNull(allPieces.get(currentPosition.getInt()-8))) {
                    movableSquares.add(new Position(currentPosition.getX(), currentPosition.getY() - 1));
                } else {return movableSquares;}
                if(currentPosition.getY()==6)
                    if(Objects.isNull(allPieces.get(currentPosition.getInt()-16)))
                        movableSquares.add(new Position(currentPosition.getX(), 4));
            }
        }
        return movableSquares;
    }

    @Override
    public List<Position> getBeatableList(Map<Integer, Piece> allPieces, Position currentPosition) {
        List<Position> beatableSquares = new ArrayList<>();
        switch (team){
            case WHITE -> {
                Piece checkLeft = allPieces.get(currentPosition.getInt()+7);
                Piece checkRight = allPieces.get(currentPosition.getInt()+9);
                if(!Objects.isNull(checkLeft) && checkLeft.getTeam()==Team.BLACK)
                    beatableSquares.add(new Position(currentPosition.getInt()+7));
                if(!Objects.isNull(checkRight) && checkRight.getTeam()==Team.BLACK)
                    beatableSquares.add(new Position(currentPosition.getInt()+9));
            }
            case BLACK -> {
                Piece checkLeft = allPieces.get(currentPosition.getInt()-9);
                Piece checkRight = allPieces.get(currentPosition.getInt()-7);
                if(!Objects.isNull(checkLeft) && checkLeft.getTeam()==Team.WHITE)
                    beatableSquares.add(new Position(currentPosition.getInt()-9));
                if(!Objects.isNull(checkRight) && checkRight.getTeam()==Team.WHITE)
                    beatableSquares.add(new Position(currentPosition.getInt()-7));
            }
        }
        return beatableSquares;
    }
}
