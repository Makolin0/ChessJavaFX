package chess.chessjavafx.pieces;

import chess.chessjavafx.game.Position;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Bishop implements Piece{
    private Team team;
    //    private Type type;
    private Position currentPosition;
    private ImageView img;

    public Bishop(Team team, Position currentPosition, Group root) {
        this.team = team;
        this.img = new ImageView(new Image("file:src/imgs/bishop" + (team == Team.WHITE ? "W" : "B") + ".png"));
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
        // ruchy w lewo-gora
        int x = currentPosition.getX();
        int y = currentPosition.getY();
        while(x > 0 && y > 0){
            x--;
            y--;
            if(allPieces.containsKey(x + y*8)){
                break;
            } else {
                movableSquares.add(new Position(x, y));
            }
        }
        // ruchy w lewo-dol
        x = currentPosition.getX();
        y = currentPosition.getY();
        while(x > 0 && y < 7){
            x--;
            y++;
            if(allPieces.containsKey(x + y*8)){
                break;
            } else {
                movableSquares.add(new Position(x, y));
            }
        }
        // ruchy w prawo-gora
        x = currentPosition.getX();
        y = currentPosition.getY();
        while(x < 7 && y > 0){
            x++;
            y--;
            if(allPieces.containsKey(x + y*8)){
                break;
            } else {
                movableSquares.add(new Position(x, y));
            }
        }
        // ruchy w prawo-dol
        x = currentPosition.getX();
        y = currentPosition.getY();
        while(x < 7 && y < 7){
            x++;
            y++;
            if(allPieces.containsKey(x + y*8)){
                break;
            } else {
                movableSquares.add(new Position(x, y));
            }
        }
        return movableSquares;
    }

    @Override
    public List<Position> getBeatableList(Map<Integer, Piece> allPieces, Position currentPosition) {
        List<Position> beatableSquares = new ArrayList<>();
        // ruchy w lewo-gora
        int x = currentPosition.getX();
        int y = currentPosition.getY();
        while(x >= 0 && y >= 0){
            x--;
            y--;
            if(allPieces.containsKey(x + y*8)){
                if(allPieces.get(x + y*8).getTeam() != team){
                    beatableSquares.add(new Position(x, y));
                }
                break;
            }
        }
        // ruchy w lewo-dol
        x = currentPosition.getX();
        y = currentPosition.getY();
        while(x >= 0 && y < 8){
            x--;
            y++;
            if(allPieces.containsKey(x + y*8)){
                if(allPieces.get(x + y*8).getTeam() != team){
                    beatableSquares.add(new Position(x, y));
                }
                break;
            }
        }
        // ruchy w prawo-gora
        x = currentPosition.getX();
        y = currentPosition.getY();
        while(x < 8 && y >= 0){
            x++;
            y--;
            if(allPieces.containsKey(x + y*8)){
                if(allPieces.get(x + y*8).getTeam() != team){
                    beatableSquares.add(new Position(x, y));
                }
                break;
            }
        }
        // ruchy w prawo-dol
        x = currentPosition.getX();
        y = currentPosition.getY();
        while(x < 8 && y < 8){
            x++;
            y++;
            if(allPieces.containsKey(x + y*8)){
                if(allPieces.get(x + y*8).getTeam() != team){
                    beatableSquares.add(new Position(x, y));
                }
                break;
            }
        }

        return beatableSquares;
    }
}
