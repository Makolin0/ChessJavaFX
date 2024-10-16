package chess.chessjavafx.javaFX;

import chess.chessjavafx.game.Checkerboard;
import chess.chessjavafx.game.Move;
import chess.chessjavafx.game.Position;
import chess.chessjavafx.packages.Moveset;
import chess.chessjavafx.pieces.Piece;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.*;

public class GameSceneController {
    private Scene scene;
    private Group root;
    private List<Rectangle> board;
    private List<Text> positionText;
    private Map<Integer, ImageView> pieceImgs;
    private VBox moveList;
    private Text currentPlayerText;
    private Piece.Team currentPlayer;

    public GameSceneController() {
        this.root = new Group();
        this.scene = new Scene(root, Color.LIGHTGRAY);
        this.board = new ArrayList<>();
        this.positionText = new ArrayList<>();
        this.pieceImgs = new HashMap<>();
        this.currentPlayer = Piece.Team.WHITE;
        this.currentPlayerText = new Text(String.valueOf(currentPlayer));

        currentPlayerText.setX(8*100 + 50);
        currentPlayerText.setY(30);
        root.getChildren().add(currentPlayerText);

        this.moveList = new VBox(10);
        root.getChildren().add(moveList);
        moveList.setLayoutX(8*100 + 50);
        moveList.setLayoutY(50);

        generateBoard();

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/fontstyle.css")).toExternalForm());
    }

    public Scene getScene(){
        return scene;
    }

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
            text.setText((char)(i+'a') + "");
            text.setX(i*100 + 85);
            text.setY(8*100 - 5);
            positionText.add(text);
        }

        root.getChildren().addAll(board);
        root.getChildren().addAll(positionText);
    }

    private void swapPlayer(){
        currentPlayer = currentPlayer == Piece.Team.WHITE ? Piece.Team.BLACK : Piece.Team.WHITE;
        currentPlayerText.setText(String.valueOf(currentPlayer));
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
        root.getChildren().removeAll(new ArrayList<>(pieceImgs.values()));
        pieceImgs.clear();
        pieceImgs.putAll(newPieceImgs);
        root.getChildren().addAll(new ArrayList<>(pieceImgs.values()));
    }

    private void saveMove(Move move){
        Text text = new Text(move.toString());
        moveList.getChildren().add(0, text);
    }

    public void movePiece(Move move){
        ImageView pieceImg = pieceImgs.get(move.getStartPosition().getInt());
        pieceImg.setX(move.getEndPosition().getX() * 100);
        pieceImg.setY((7 - move.getEndPosition().getY()) * 100);
        pieceImgs.remove(move.getStartPosition().getInt());
        root.getChildren().remove(pieceImgs.get(move.getEndPosition().getInt()));

        pieceImgs.replace(move.getEndPosition().getInt(), pieceImg);

        saveMove(move);
        swapPlayer();
        clearBoard();
    }
}
