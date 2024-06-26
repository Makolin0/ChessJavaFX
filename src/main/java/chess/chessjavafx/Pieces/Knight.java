package chess.chessjavafx.Pieces;

import chess.chessjavafx.Position;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Knight implements Piece{
    private Team team;
    //    private Type type;
    private Position position;
    private ImageView img;

    //    public Pawn(Team team, Type type, Position position) {
    public Knight(Team team, Position position, Group root) {
        this.team = team;
//        this.type = type;
        this.img = new ImageView(new Image("file:src/imgs/knight" + (team == Team.WHITE ? "W" : "B") + ".png"));
        setPosition(position);
        root.getChildren().add(this.img);
    }

//    @Override
//    public Type getType() {
//        return type;
//    }

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

        if(position.getX() < 6 && position.getY() >= 1) {
            if (!allPieces.containsKey(position.getInt() - 6)) {
                movableSquares.add(new Position(position.getInt() - 6));

            }
        }
        if(position.getX() >= 2 && position.getY() >= 1) {
            if (!allPieces.containsKey(position.getInt() - 10)) {
                movableSquares.add(new Position(position.getInt() - 10));

            }
        }
        if(position.getX() < 7 && position.getY() >= 2) {
            if (!allPieces.containsKey(position.getInt() - 15)) {
                movableSquares.add(new Position(position.getInt() - 15));

            }
        }
        if(position.getX() >= 1 && position.getY() >= 2){
            if(!allPieces.containsKey(position.getInt() - 17)){
                movableSquares.add(new Position(position.getInt() - 17));

            }
        }
        if(position.getX() >= 2 && position.getY() <= 6) {
            if (!allPieces.containsKey(position.getInt() + 6)) {
                movableSquares.add(new Position(position.getInt() + 6));

            }
        }
        if(position.getX() <= 5 && position.getY() <= 6) {
            if (!allPieces.containsKey(position.getInt() + 10)) {
                movableSquares.add(new Position(position.getInt() + 10));

            }
        }
        if(position.getX() >= 1 && position.getY() <= 5) {
            if (!allPieces.containsKey(position.getInt() + 15)) {
                movableSquares.add(new Position(position.getInt() + 15));

            }
        }
        if(position.getX() <= 6 && position.getY() <= 5){
            if(!allPieces.containsKey(position.getInt() + 17)) {
                movableSquares.add(new Position(position.getInt() + 17));

            }
        }

        return movableSquares;
    }

    @Override
    public List<Position> getBeatableList(Map<Integer, Piece> allPieces) {
        List<Position> beatableSquares = new ArrayList<>();

        if(position.getX() < 6 && position.getY() >= 1) {
            if (allPieces.containsKey(position.getInt() - 6)) {
                if (allPieces.get(position.getInt() - 6).getTeam() != team) {
                    beatableSquares.add(new Position(position.getInt() - 6));
                }
            }
        }
        if(position.getX() >= 2 && position.getY() >= 1) {
            if (allPieces.containsKey(position.getInt() - 10)) {
                if (allPieces.get(position.getInt() - 10).getTeam() != team) {
                    beatableSquares.add(new Position(position.getInt() - 10));
                }
            }
        }
        if(position.getX() < 7 && position.getY() >= 2) {
            if (allPieces.containsKey(position.getInt() - 15)) {
                if (allPieces.get(position.getInt() - 15).getTeam() != team) {
                    beatableSquares.add(new Position(position.getInt() - 15));
                }
            }
        }
        if(position.getX() >= 1 && position.getY() >= 2){
            if(allPieces.containsKey(position.getInt() - 17)){
                if(allPieces.get(position.getInt() - 17).getTeam() != team) {
                    beatableSquares.add(new Position(position.getInt() - 17));
                }
            }
        }
        if(position.getX() >= 2 && position.getY() <= 6) {
            if (allPieces.containsKey(position.getInt() + 6)) {
                if (allPieces.get(position.getInt() + 6).getTeam() != team) {
                    beatableSquares.add(new Position(position.getInt() + 6));
                }
            }
        }

        // POZAMIENIAC SRODKOWY IF

        if(position.getX() <= 5 && position.getY() <= 6) {
            if (allPieces.containsKey(position.getInt() + 10)) {
                if (allPieces.get(position.getInt() + 10).getTeam() != team) {
                    beatableSquares.add(new Position(position.getInt() + 10));
                }
            }
        }
        if(position.getX() >= 1 && position.getY() <= 5) {
            if (allPieces.containsKey(position.getInt() + 15)) {
                if (allPieces.get(position.getInt() + 15).getTeam() != team) {
                    beatableSquares.add(new Position(position.getInt() + 15));
                }
            }
        }
        if(position.getX() <= 6 && position.getY() <= 5){
            if(allPieces.containsKey(position.getInt() + 17)) {
                if (allPieces.get(position.getInt() + 17).getTeam() != team) {
                    beatableSquares.add(new Position(position.getInt() + 17));
                }
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
