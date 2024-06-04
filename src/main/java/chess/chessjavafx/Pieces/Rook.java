package chess.chessjavafx.Pieces;

import chess.chessjavafx.Position;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Rook implements Piece{
    private Team team;
    //    private Type type;
    private Position position;
    private ImageView img;

    //    public Pawn(Team team, Type type, Position position) {
    public Rook(Team team, Position position, Group root) {
        this.team = team;
//        this.type = type;
        this.img = new ImageView(new Image("file:src/imgs/rook" + (team == Team.WHITE ? "W" : "B") + ".png"));
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
        // ruchy w lewo
        for(int y = position.getY() - 1; y >= 0; y--){
            if(allPieces.containsKey(position.getX() + y * 8)){
                break;
            } else {
                movableSquares.add(new Position(position.getX(), y));
            }
        }
        // ruchy w prawo
        for(int y = position.getY() + 1; y < 8; y++){
            if(allPieces.containsKey(position.getX() + y * 8)){
                break;
            } else {
                movableSquares.add(new Position(position.getX(), y));
            }
        }
        // ruchy w gore
        for(int x = position.getX() - 1; x >= 0; x--){
            if(allPieces.containsKey(position.getY() * 8 + x)){
                break;
            } else {
                movableSquares.add(new Position(x, position.getY()));
            }
        }
        // ruchy w dol
        for(int x = position.getX() + 1; x < 8; x++){
            if(allPieces.containsKey(position.getY() * 8 + x)){
                break;
            } else {
                movableSquares.add(new Position(x, position.getY()));
            }
        }
        return movableSquares;
    }

    @Override
    public List<Position> getBeatableList(Map<Integer, Piece> allPieces) {
        List<Position> beatableSquares = new ArrayList<>();

        // ruchy w lewo
        for(int y = position.getY() - 1; y >= 0; y--){
            if(allPieces.containsKey(position.getX() + y * 8)){
                if(allPieces.get(position.getX() + y * 8).getTeam() != team){
                    beatableSquares.add(new Position(position.getX(),y));
                }
                break;
            }
        }
        // ruchy w prawo
        for(int y = position.getY() + 1; y < 8; y++){
            if(allPieces.containsKey(position.getX() + y * 8)){
                if(allPieces.get(position.getX() + y * 8).getTeam() != team){
                    beatableSquares.add(new Position(position.getX(),y));
                }
                break;
            }
        }
        // ruchy w gore
        for(int x = position.getX() - 1; x >= 0; x--){
            if(allPieces.containsKey(position.getY() * 8 + x)){
                if(allPieces.get(position.getY() * 8 + x).getTeam() != team){
                    beatableSquares.add(new Position(x, position.getY()));
                }
                break;
            }
        }
        // ruchy w dol
        for(int x = position.getX() + 1; x < 8; x++){
            if(allPieces.containsKey(position.getY() * 8 + x)){
                if(allPieces.get(position.getY() * 8 + x).getTeam() != team){
                    beatableSquares.add(new Position(x, position.getY()));
                }
                break;
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
