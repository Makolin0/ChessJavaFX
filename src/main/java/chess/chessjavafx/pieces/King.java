package chess.chessjavafx.pieces;

import chess.chessjavafx.game.Position;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class King implements Piece{
    private Team team;
    //    private Type type;
    private Position currentPosition;
    private ImageView img;

    //    public Pawn(Team team, Type type, Position position) {
    public King(Team team, Position currentPosition, Group root) {
        this.team = team;
//        this.type = type;
        this.img = new ImageView(new Image("file:src/imgs/king" + (team == Team.WHITE ? "W" : "B") + ".png"));
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
    public List<Position> getMovableList(Map<Integer, Piece> allPieces, Position currentPosition) {
        List<Position> movableSquares = new ArrayList<>();

        int xLeft = this.currentPosition.getX() - 1;
        int xRight = this.currentPosition.getX() + 1;
        int yDown = this.currentPosition.getY() - 1;
        int yUp = this.currentPosition.getY() + 1;
        if(xLeft >= 0){
            if(!allPieces.containsKey(this.currentPosition.getY() * 8 + xLeft)){
                movableSquares.add(new Position(xLeft, this.currentPosition.getY()));
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
            if(!allPieces.containsKey(this.currentPosition.getY() * 8 + xRight)){
                movableSquares.add(new Position(xRight, this.currentPosition.getY()));
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
            if(!allPieces.containsKey(yDown * 8 + this.currentPosition.getX())){
                movableSquares.add(new Position(this.currentPosition.getX(), yDown));
            }
        }
        if(yUp < 8){
            if(!allPieces.containsKey(yUp * 8 + this.currentPosition.getX())){
                movableSquares.add(new Position(this.currentPosition.getX(), yUp));
            }
        }

        return movableSquares;
    }

    @Override
    public List<Position> getBeatableList(Map<Integer, Piece> allPieces, Position currentPosition) {
        List<Position> beatableSquares = new ArrayList<>();


        int xLeft = this.currentPosition.getX() - 1;
        int xRight = this.currentPosition.getX() + 1;
        int yDown = this.currentPosition.getY() - 1;
        int yUp = this.currentPosition.getY() + 1;
        if(xLeft >= 0){
            if(allPieces.containsKey(this.currentPosition.getY() * 8 + xLeft)){
                if(allPieces.get(this.currentPosition.getY() * 8 + xLeft).getTeam() != team)
                    beatableSquares.add(new Position(xLeft, this.currentPosition.getY()));
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
            if(allPieces.containsKey(this.currentPosition.getY() * 8 + xRight)){
                if(allPieces.get(this.currentPosition.getY() * 8 + xRight).getTeam() != team)
                    beatableSquares.add(new Position(xRight, this.currentPosition.getY()));
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
            if(allPieces.containsKey(yDown * 8 + this.currentPosition.getX())){
                if(allPieces.get(yDown * 8 + this.currentPosition.getX()).getTeam() != team)
                    beatableSquares.add(new Position(this.currentPosition.getX(), yDown));
            }
        }
        if(yUp < 8){
            if(allPieces.containsKey(yUp * 8 + this.currentPosition.getX())){
                if(allPieces.get(yUp * 8 + this.currentPosition.getX()).getTeam() != team)
                    beatableSquares.add(new Position(this.currentPosition.getX(), yUp));
            }
        }

        return beatableSquares;
    }
}
