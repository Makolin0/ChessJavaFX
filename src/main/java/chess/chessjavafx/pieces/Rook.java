package chess.chessjavafx.pieces;

import chess.chessjavafx.game.Position;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Rook implements Piece{
    private Team team;
    private ImageView img;

    public Rook(Team team) {
        this.team = team;
        this.img = new ImageView(new Image("file:src/imgs/rook" + (team == Team.WHITE ? "W" : "B") + ".png"));
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
        // ruchy w lewo
        for(int y = currentPosition.getY() - 1; y >= 0; y--){
            if(allPieces.containsKey(currentPosition.getX() + y * 8)){
                break;
            } else {
                movableSquares.add(new Position(currentPosition.getX(), y));
            }
        }
        // ruchy w prawo
        for(int y = currentPosition.getY() + 1; y < 8; y++){
            if(allPieces.containsKey(currentPosition.getX() + y * 8)){
                break;
            } else {
                movableSquares.add(new Position(currentPosition.getX(), y));
            }
        }
        // ruchy w gore
        for(int x = currentPosition.getX() - 1; x >= 0; x--){
            if(allPieces.containsKey(currentPosition.getY() * 8 + x)){
                break;
            } else {
                movableSquares.add(new Position(x, currentPosition.getY()));
            }
        }
        // ruchy w dol
        for(int x = currentPosition.getX() + 1; x < 8; x++){
            if(allPieces.containsKey(currentPosition.getY() * 8 + x)){
                break;
            } else {
                movableSquares.add(new Position(x, currentPosition.getY()));
            }
        }
        return movableSquares;
    }

    @Override
    public List<Position> getBeatableList(Map<Integer, Piece> allPieces, Position currentPosition) {
        List<Position> beatableSquares = new ArrayList<>();

        // ruchy w lewo
        for(int y = currentPosition.getY() - 1; y >= 0; y--){
            if(allPieces.containsKey(currentPosition.getX() + y * 8)){
                if(allPieces.get(currentPosition.getX() + y * 8).getTeam() != team){
                    beatableSquares.add(new Position(currentPosition.getX(),y));
                }
                break;
            }
        }
        // ruchy w prawo
        for(int y = currentPosition.getY() + 1; y < 8; y++){
            if(allPieces.containsKey(currentPosition.getX() + y * 8)){
                if(allPieces.get(currentPosition.getX() + y * 8).getTeam() != team){
                    beatableSquares.add(new Position(currentPosition.getX(),y));
                }
                break;
            }
        }
        // ruchy w gore
        for(int x = currentPosition.getX() - 1; x >= 0; x--){
            if(allPieces.containsKey(currentPosition.getY() * 8 + x)){
                if(allPieces.get(currentPosition.getY() * 8 + x).getTeam() != team){
                    beatableSquares.add(new Position(x, currentPosition.getY()));
                }
                break;
            }
        }
        // ruchy w dol
        for(int x = currentPosition.getX() + 1; x < 8; x++){
            if(allPieces.containsKey(currentPosition.getY() * 8 + x)){
                if(allPieces.get(currentPosition.getY() * 8 + x).getTeam() != team){
                    beatableSquares.add(new Position(x, currentPosition.getY()));
                }
                break;
            }
        }

        return beatableSquares;
    }
}
