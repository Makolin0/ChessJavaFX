package chess.chessjavafx.Pieces;

import chess.chessjavafx.Position;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class King implements Piece{
    private Team team;
    //    private Type type;
    private Position position;
    private ImageView img;

    //    public Pawn(Team team, Type type, Position position) {
    public King(Team team, Position position, Group root) {
        this.team = team;
//        this.type = type;
        this.img = new ImageView(new Image("file:src/imgs/king" + (team == Team.WHITE ? "W" : "B") + ".png"));
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

        int xLeft = position.getX() - 1;
        int xRight = position.getX() + 1;
        int yDown = position.getY() - 1;
        int yUp = position.getY() + 1;
        if(xLeft >= 0){
            if(!allPieces.containsKey(position.getY() * 8 + xLeft)){
                movableSquares.add(new Position(xLeft, position.getY()));
            }
            if(yDown >= 0){
                if(!allPieces.containsKey(yDown * 8 + xLeft)){
                    movableSquares.add(new Position(xLeft, yDown));
                }
            }
            if(yUp < 8){
                if(!allPieces.containsKey(yUp * 8 + xLeft)){
                    movableSquares.add(new Position(xLeft, yUp));
                }
            }
        }
        if(xRight < 8){
            if(!allPieces.containsKey(position.getY() * 8 + xRight)){
                movableSquares.add(new Position(xRight, position.getY()));
            }
            if(yDown >= 0){
                if(!allPieces.containsKey(yDown * 8 + xRight)){
                    movableSquares.add(new Position(xRight, yDown));
                }
            }
            if(yUp < 8){
                if(!allPieces.containsKey(yUp * 8 + xRight)){
                    movableSquares.add(new Position(xRight, yUp));
                }
            }
        }
        if(yDown >= 0){
            if(!allPieces.containsKey(yDown * 8 + position.getX())){
                movableSquares.add(new Position(position.getX(), yDown));
            }
        }
        if(yUp < 8){
            if(!allPieces.containsKey(yUp * 8 + position.getX())){
                movableSquares.add(new Position(position.getX(), yUp));
            }
        }

        return movableSquares;
    }

    @Override
    public List<Position> getBeatableList(Map<Integer, Piece> allPieces) {
        List<Position> beatableSquares = new ArrayList<>();


        int xLeft = position.getX() - 1;
        int xRight = position.getX() + 1;
        int yDown = position.getY() - 1;
        int yUp = position.getY() + 1;
        if(xLeft >= 0){
            if(allPieces.containsKey(position.getY() * 8 + xLeft)){
                if(allPieces.get(position.getY() * 8 + xLeft).getTeam() != team)
                    beatableSquares.add(new Position(xLeft, position.getY()));
            }
            if(yDown >= 0){
                if(allPieces.containsKey(yDown * 8 + xLeft)){
                    if(allPieces.get(yDown * 8 + xLeft).getTeam() != team)
                        beatableSquares.add(new Position(xLeft, yDown));
                }
            }
            if(yUp < 8){
                if(allPieces.containsKey(yUp * 8 + xLeft)){
                    if(allPieces.get(yUp * 8 + xLeft).getTeam() != team)
                        beatableSquares.add(new Position(xLeft, yUp));
                }
            }
        }
        if(xRight < 8){
            if(allPieces.containsKey(position.getY() * 8 + xRight)){
                if(allPieces.get(position.getY() * 8 + xRight).getTeam() != team)
                    beatableSquares.add(new Position(xRight, position.getY()));
            }
            if(yDown >= 0){
                if(allPieces.containsKey(yDown * 8 + xRight)){
                    if(allPieces.get(yDown * 8 + xRight).getTeam() != team)
                        beatableSquares.add(new Position(xRight, yDown));
                }
            }
            if(yUp < 8){
                if(allPieces.containsKey(yUp * 8 + xRight)){
                    if(allPieces.get(yUp * 8 + xRight).getTeam() != team)
                        beatableSquares.add(new Position(xRight, yUp));
                }
            }
        }
        if(yDown >= 0){
            if(allPieces.containsKey(yDown * 8 + position.getX())){
                if(allPieces.get(yDown * 8 + position.getX()).getTeam() != team)
                    beatableSquares.add(new Position(position.getX(), yDown));
            }
        }
        if(yUp < 8){
            if(allPieces.containsKey(yUp * 8 + position.getX())){
                if(allPieces.get(yUp * 8 + position.getX()).getTeam() != team)
                    beatableSquares.add(new Position(position.getX(), yUp));
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
