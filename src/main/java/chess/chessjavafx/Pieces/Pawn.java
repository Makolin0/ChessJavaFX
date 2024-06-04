package chess.chessjavafx.Pieces;

import chess.chessjavafx.Position;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Pawn implements Piece{
    private Team team;
    private Position position;
    private ImageView img;

    public Pawn(Team team, Position position, Group root) {
        this.team = team;
        this.img = new ImageView(new Image("file:src/imgs/pawn" + (team == Team.WHITE ? "W" : "B") + ".png"));
        setPosition(position);
        root.getChildren().add(this.img);
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
    public void setPosition(Position position) {
        this.position = position;
        img.setX(position.getX()*100);
        img.setY(position.getY()*100);
    }
    @Override
    public Position getPosition(){
        return position;
    }

    @Override
    public List<Position> getMovableList(Map<Integer, Piece> allPieces) {
        List<Position> movableSquares = new ArrayList<>();
        switch (team){
            case WHITE -> {
                if(position.getY() > 6)
                    return movableSquares;

                if(Objects.isNull(allPieces.get(position.getInt()+8))) {
                    movableSquares.add(new Position(position.getX(), position.getY() + 1));
                } else {return movableSquares;}
                if(position.getY()==1)
                    if(Objects.isNull(allPieces.get(position.getInt()+16)))
                        movableSquares.add(new Position(position.getX(), 3));
            }
            case BLACK -> {
                if(position.getY() < 1)
                    return movableSquares;

                if(Objects.isNull(allPieces.get(position.getInt()-8))) {
                    movableSquares.add(new Position(position.getX(), position.getY() - 1));
                } else {return movableSquares;}
                if(position.getY()==6)
                    if(Objects.isNull(allPieces.get(position.getInt()-16)))
                        movableSquares.add(new Position(position.getX(), 4));
            }
        }
        return movableSquares;
    }

    @Override
    public List<Position> getBeatableList(Map<Integer, Piece> allPieces) {
        List<Position> beatableSquares = new ArrayList<>();
        switch (team){
            case WHITE -> {
                Piece checkLeft = allPieces.get(position.getInt()+7);
                Piece checkRight = allPieces.get(position.getInt()+9);
                if(!Objects.isNull(checkLeft) && checkLeft.getTeam()==Team.BLACK)
                    beatableSquares.add(new Position(position.getInt()+7));
                if(!Objects.isNull(checkRight) && checkRight.getTeam()==Team.BLACK)
                    beatableSquares.add(new Position(position.getInt()+9));
            }
            case BLACK -> {
                Piece checkLeft = allPieces.get(position.getInt()-9);
                Piece checkRight = allPieces.get(position.getInt()-7);
                if(!Objects.isNull(checkLeft) && checkLeft.getTeam()==Team.WHITE)
                    beatableSquares.add(new Position(position.getInt()-9));
                if(!Objects.isNull(checkRight) && checkRight.getTeam()==Team.WHITE)
                    beatableSquares.add(new Position(position.getInt()-7));
            }
        }
        return beatableSquares;
    }

    @Override
    public void moveFree(int x, int y) {
        img.setX(x - 50);
        img.setY(y - 50);
    }

    @Override
    public void moveBack() {
        img.setX(position.getX()*100);
        img.setY(position.getY()*100);
    }

}
