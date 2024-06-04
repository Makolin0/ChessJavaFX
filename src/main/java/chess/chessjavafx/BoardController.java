package chess.chessjavafx;

import chess.chessjavafx.Pieces.*;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.*;

public class BoardController {
    private Group root;
    private List<Rectangle> board;
    private Position selected;
    private List<Position> piecePreview;
    private Map<Integer, Piece> pieceMap;


    public BoardController(Group root){
        this.root = root;
        this.board = new ArrayList<>();
//        this.selected = new Position();
        this.piecePreview = new ArrayList<>();
        this.pieceMap = new HashMap<>();
    }

    public void moveFree(Position position, int x, int y) {
        pieceMap.get(position.getInt()).moveFree(x, y);
    }

    public Position checkForPiece(int x, int y){
        Piece piece = pieceMap.get(y*8 + x);
        if(Objects.nonNull(piece)){
            return piece.getPosition();
        } else {
            return null;
        }
    }

    public void setBoard(){
        for (int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++){
                //System.out.printf("i: %d j: %d kolor: %s%n", i, j, (i+j)%2==0?"white":"gray");

                Rectangle rectangle = new Rectangle();
                rectangle.setFill((i+j)%2==0? Color.WHITE:Color.DARKGRAY);
                rectangle.setX(i*100);
                rectangle.setY(j*100);
                rectangle.setWidth(100);
                rectangle.setHeight(100);

                board.add(rectangle);
            }
        }
        root.getChildren().addAll(board);
    }

    public void setNewGame(){
        pieceMap.clear();
        for(int x = 0; x < 8; x++){
            Piece pieceW = new Pawn(Piece.Team.WHITE,new Position(x, 1));
            Piece pieceB = new Pawn(Piece.Team.BLACK,new Position(x, 6));
            pieceMap.put(pieceW.getPosition().getInt(), pieceW);
            root.getChildren().add(pieceW.getImg());
            pieceMap.put(pieceB.getPosition().getInt(), pieceB);
            root.getChildren().add(pieceB.getImg());
        }
        Piece pieceW = new Pawn(Piece.Team.WHITE,new Position(4, 4));
        pieceMap.put(pieceW.getPosition().getInt(), pieceW);
        root.getChildren().add(pieceW.getImg());
    }

    public void movePiece(Position oldPosition, Position newPosition){
        Piece movingPiece = pieceMap.get(oldPosition.getInt());
        if(Objects.nonNull(movingPiece)){
            System.out.println("znaleziono pion");
            List<Position> movable = movingPiece.getMovableList(pieceMap);
            List<Position> beatable = movingPiece.getBeatableList(pieceMap);
            System.out.println("move " + movable);
            System.out.println("beat " + beatable);
            System.out.println("ruch do: " + newPosition);
            if(beatable.contains(newPosition)){
                System.out.println("można zbić");
                beatPiece(oldPosition, newPosition);
            }
            else if(movable.contains(newPosition)){
                System.out.println("mozna sie ruszyc");
                movingPiece.setPosition(newPosition);
                pieceMap.put(newPosition.getInt(), movingPiece);
                pieceMap.remove(oldPosition.getInt());
            } else {
                movingPiece.moveBack();
            }
        }
    }

    public void beatPiece(Position myPosition, Position beatPosition){
        Piece movingPiece = pieceMap.get(myPosition.getInt());
        Piece beatingPiece = pieceMap.get(beatPosition.getInt());
        if(Objects.nonNull(movingPiece) && Objects.nonNull(beatingPiece)){
            pieceMap.replace(beatingPiece.getPosition().getInt(), movingPiece);
            pieceMap.remove(movingPiece.getPosition().getInt());
            movingPiece.setPosition(beatPosition);
        }
    }


    public void lightMovableSquares(Position position){
        piecePreview = pieceMap.get(position.getInt()).getMovableList(pieceMap);

        update();
    }
    public void clearMovable(){
        piecePreview = null;
        update();
    }

    public void showSelected(int x, int y){
        selected = new Position(x, y);
        update();
    }
    
    public void resetBoard(){
        for (Rectangle r :
                board) {
            r.setOpacity(1);
        }
    }

    public void update(){
        resetBoard();

        if(!Objects.isNull(piecePreview))
            for (Position r : piecePreview) {
                board.get(r.getX()*8 + r.getY()).setOpacity(0.8);
            }

        board.get(selected.getX() * 8 + selected.getY()).setOpacity(0.6);
    }

    @Override
    public String toString() {
        String board = "";

        for(int y = 0; y < 8; y++){
            for(int x = 0; x < 8; x++){
                Piece piece = pieceMap.get(y*8+x);
                board = board + (Objects.isNull(piece) ? " __ " : (piece.getTeam() == Piece.Team.WHITE ? " wp " : " bp "));
            }
            board = board + "\n";
        }
        return board;
    }
}
