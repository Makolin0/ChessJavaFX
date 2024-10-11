package chess.chessjavafx.javaFX;

import chess.chessjavafx.game.Checkerboard;
import chess.chessjavafx.game.Move;
import chess.chessjavafx.game.Position;
import chess.chessjavafx.packages.Moveset;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameSceneController {
    private Scene scene;
    private Group group;
    private List<Rectangle> board;
    private List<Text> positionText;
    private Map<Integer, ImageView> pieceImgs;

    public GameSceneController(Group group) {
        this.scene = new Scene(group, Color.BLUE);
        this.group = group;
        this.board = new ArrayList<>();
        this.positionText = new ArrayList<>();
        this.pieceImgs = new HashMap<>();

        generateBoard();
    }

    public Scene getScene(){
        return scene;
    }

//    private Integer toScenePos(Integer pos){
//        int x = pos%8;
//        int y = pos/8;
//        y = 7-y;
//        return new Position(x, y).getInt();
//    }

    private void generateBoard(){
        for (int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++){
                Rectangle rectangle = new Rectangle();
                rectangle.setFill((y+x)%2==0? Color.DARKGRAY:Color.WHITE);
                rectangle.setX(x*100);
                rectangle.setY(7*100 - y*100);
                rectangle.setWidth(100);
                rectangle.setHeight(100);

                board.add(rectangle);
            }
        }
        // numbers
        for(int i = 0; i < 8; i++){
            Text text = new Text();
            text.setText(Integer.toString(i+1));
            text.setX(5);
            text.setY((8-i)*100 - 85);
            positionText.add(text);
        }
        // alphabet
        for(int i = 0; i < 8; i++){
            Text text = new Text();
//            System.out.println();
            text.setText((char)(i+'a') + "");
            text.setX(i*100 + 85);
            text.setY(8*100 - 5);
            positionText.add(text);
        }

        // TEST - do usunięcia
        Text Xtext = new Text("X");
        Xtext.setX(85);
        Xtext.setY(780);
        positionText.add(Xtext);
        Text Ytext = new Text("Y");
        Ytext.setX(20);
        Ytext.setY(715);
        positionText.add(Ytext);

        group.getChildren().addAll(board);
        group.getChildren().addAll(positionText);

        // TEST - do usunięcia
//        board.get(new Position(2, 4).getInt()).setFill(Color.GREEN);
//        board.get(new Position(6, 6).getInt()).setFill(Color.GREEN);
//        board.get(new Position(0, 0).getInt()).setFill(Color.GREEN);
    }

    public void showMoveset(Moveset moveset){
//        rectangle.setFill((i+j)%2==0? Color.DARKGRAY:Color.WHITE);
        // current position
        board.get(moveset.getCurrentPosition().getInt()).setFill(Color.BLUE);
        // movable
        moveset.getMovableList().forEach((pos)->{
            board.get(pos.getInt()).setFill(Color.GREEN);
        });
        // beatable
        moveset.getBeatableList().forEach((pos)->{
            board.get(pos.getInt()).setFill(Color.RED);
        });
    }

    public void clearBoard(){
        for (int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++){
                board.get(y*8+x).setFill((y+x)%2==0? Color.DARKGRAY:Color.WHITE);
            }
        }
    }

    public void updateAllPieces(Checkerboard checkerboard){
        Map<Integer, ImageView> newPieceImgs = checkerboard.getImages();
        newPieceImgs.forEach((key, img)->{
            Position pos = new Position(key);
            img.setX(pos.getX() * 100);
            img.setY((7 - pos.getY()) * 100);
        });
        group.getChildren().removeAll(new ArrayList<>(pieceImgs.values()));
        pieceImgs.clear();
        pieceImgs.putAll(newPieceImgs);
        group.getChildren().addAll(new ArrayList<>(pieceImgs.values()));
    }

    public void movePiece(Move move){
        ImageView pieceImg = pieceImgs.get(move.getStartPosition().getInt());
        pieceImg.setX(move.getEndPosition().getX() * 100);
        pieceImg.setY((7 - move.getEndPosition().getY()) * 100);
        pieceImgs.remove(move.getStartPosition().getInt());
        group.getChildren().remove(pieceImgs.get(move.getEndPosition().getInt()));

        pieceImgs.replace(move.getEndPosition().getInt(), pieceImg);
    }


}
