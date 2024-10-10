package chess.chessjavafx.pieces;

import chess.chessjavafx.game.Position;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Knight implements Piece{
    private Team team;
    private Position currentPosition;
    private ImageView img;

    public Knight(Team team, Position currentPosition, Group root) {
        this.team = team;
        this.img = new ImageView(new Image("file:src/imgs/knight" + (team == Team.WHITE ? "W" : "B") + ".png"));
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
    public List<Position> getMovableList(Map<Integer, Piece> allPieces, Position currentPosition) {
        List<Position> movableSquares = new ArrayList<>();

        if(this.currentPosition.getX() < 6 && this.currentPosition.getY() >= 1) {
            if (!allPieces.containsKey(this.currentPosition.getInt() - 6)) {
                movableSquares.add(new Position(this.currentPosition.getInt() - 6));

            }
        }
        if(this.currentPosition.getX() >= 2 && this.currentPosition.getY() >= 1) {
            if (!allPieces.containsKey(this.currentPosition.getInt() - 10)) {
                movableSquares.add(new Position(this.currentPosition.getInt() - 10));

            }
        }
        if(this.currentPosition.getX() < 7 && this.currentPosition.getY() >= 2) {
            if (!allPieces.containsKey(this.currentPosition.getInt() - 15)) {
                movableSquares.add(new Position(this.currentPosition.getInt() - 15));

            }
        }
        if(this.currentPosition.getX() >= 1 && this.currentPosition.getY() >= 2){
            if(!allPieces.containsKey(this.currentPosition.getInt() - 17)){
                movableSquares.add(new Position(this.currentPosition.getInt() - 17));

            }
        }
        if(this.currentPosition.getX() >= 2 && this.currentPosition.getY() <= 6) {
            if (!allPieces.containsKey(this.currentPosition.getInt() + 6)) {
                movableSquares.add(new Position(this.currentPosition.getInt() + 6));

            }
        }
        if(this.currentPosition.getX() <= 5 && this.currentPosition.getY() <= 6) {
            if (!allPieces.containsKey(this.currentPosition.getInt() + 10)) {
                movableSquares.add(new Position(this.currentPosition.getInt() + 10));

            }
        }
        if(this.currentPosition.getX() >= 1 && this.currentPosition.getY() <= 5) {
            if (!allPieces.containsKey(this.currentPosition.getInt() + 15)) {
                movableSquares.add(new Position(this.currentPosition.getInt() + 15));

            }
        }
        if(this.currentPosition.getX() <= 6 && this.currentPosition.getY() <= 5){
            if(!allPieces.containsKey(this.currentPosition.getInt() + 17)) {
                movableSquares.add(new Position(this.currentPosition.getInt() + 17));

            }
        }

        return movableSquares;
    }

    @Override
    public List<Position> getBeatableList(Map<Integer, Piece> allPieces, Position currentPosition) {
        List<Position> beatableSquares = new ArrayList<>();

        if(this.currentPosition.getX() < 6 && this.currentPosition.getY() >= 1) {
            if (allPieces.containsKey(this.currentPosition.getInt() - 6)) {
                if (allPieces.get(this.currentPosition.getInt() - 6).getTeam() != team) {
                    beatableSquares.add(new Position(this.currentPosition.getInt() - 6));
                }
            }
        }
        if(this.currentPosition.getX() >= 2 && this.currentPosition.getY() >= 1) {
            if (allPieces.containsKey(this.currentPosition.getInt() - 10)) {
                if (allPieces.get(this.currentPosition.getInt() - 10).getTeam() != team) {
                    beatableSquares.add(new Position(this.currentPosition.getInt() - 10));
                }
            }
        }
        if(this.currentPosition.getX() < 7 && this.currentPosition.getY() >= 2) {
            if (allPieces.containsKey(this.currentPosition.getInt() - 15)) {
                if (allPieces.get(this.currentPosition.getInt() - 15).getTeam() != team) {
                    beatableSquares.add(new Position(this.currentPosition.getInt() - 15));
                }
            }
        }
        if(this.currentPosition.getX() >= 1 && this.currentPosition.getY() >= 2){
            if(allPieces.containsKey(this.currentPosition.getInt() - 17)){
                if(allPieces.get(this.currentPosition.getInt() - 17).getTeam() != team) {
                    beatableSquares.add(new Position(this.currentPosition.getInt() - 17));
                }
            }
        }
        if(this.currentPosition.getX() >= 2 && this.currentPosition.getY() <= 6) {
            if (allPieces.containsKey(this.currentPosition.getInt() + 6)) {
                if (allPieces.get(this.currentPosition.getInt() + 6).getTeam() != team) {
                    beatableSquares.add(new Position(this.currentPosition.getInt() + 6));
                }
            }
        }

        // POZAMIENIAC SRODKOWY IF

        if(this.currentPosition.getX() <= 5 && this.currentPosition.getY() <= 6) {
            if (allPieces.containsKey(this.currentPosition.getInt() + 10)) {
                if (allPieces.get(this.currentPosition.getInt() + 10).getTeam() != team) {
                    beatableSquares.add(new Position(this.currentPosition.getInt() + 10));
                }
            }
        }
        if(this.currentPosition.getX() >= 1 && this.currentPosition.getY() <= 5) {
            if (allPieces.containsKey(this.currentPosition.getInt() + 15)) {
                if (allPieces.get(this.currentPosition.getInt() + 15).getTeam() != team) {
                    beatableSquares.add(new Position(this.currentPosition.getInt() + 15));
                }
            }
        }
        if(this.currentPosition.getX() <= 6 && this.currentPosition.getY() <= 5){
            if(allPieces.containsKey(this.currentPosition.getInt() + 17)) {
                if (allPieces.get(this.currentPosition.getInt() + 17).getTeam() != team) {
                    beatableSquares.add(new Position(this.currentPosition.getInt() + 17));
                }
            }
        }


        return beatableSquares;
    }
}
