package chess.chessjavafx.javaFX;

import chess.chessjavafx.game.Checkerboard;
import chess.chessjavafx.game.GameController;
import chess.chessjavafx.game.Move;
import chess.chessjavafx.game.Position;
import chess.chessjavafx.packages.Moveset;
import chess.chessjavafx.pieces.Piece;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Game implements Initializable {
    @FXML
    public Pane board;
    @FXML
    public Text checkText;
    @FXML
    public Text currentPlayerText;

    @FXML
    public TableView<MoveRow> tableView;
    @FXML
    public TableColumn<MoveRow, String> whiteCol;
    @FXML
    public TableColumn<MoveRow, String> blackCol;
    private MoveRow currentRow;

    private List<Rectangle> squares;
    private List<Text> positionText;

    private Map<Integer, ImageView> pieceImgs;
    private Piece.Team currentPlayer;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.whiteCol.setCellValueFactory(new PropertyValueFactory<>("moveWhite"));
        this.blackCol.setCellValueFactory(new PropertyValueFactory<>("moveBlack"));

//        tableView.getItems().add(new MoveRow("a", "b"));

        this.squares = new ArrayList<>();
        this.positionText = new ArrayList<>();
        this.pieceImgs = new HashMap<>();
        this.currentPlayer = Piece.Team.WHITE;
        this.currentPlayerText.setText("Aktualny gracz: "+ currentPlayer);

        this.tableView.getItems().add(0, new MoveRow());
//        this.tableView.getItems().get(0).setMoveWhite("init");

        generateBoard();
    }

    public void goBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/main-menu.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.show();
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
                squares.add(rectangle);
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

        board.getChildren().addAll(squares);
        board.getChildren().addAll(positionText);
    }


    public void swapPlayer(){
        currentPlayer = currentPlayer == Piece.Team.WHITE ? Piece.Team.BLACK : Piece.Team.WHITE;
        currentPlayerText.setText("Aktualny gracz: " + currentPlayer);
    }

    public void showMoveset(Moveset moveset){
//        rectangle.setFill((i+j)%2==0? Color.DARKGRAY:Color.WHITE);
        // current position
        squares.get(moveset.getCurrentPosition().getInt()).setFill(Color.BLUE);
        // movable
        moveset.getMovableList().forEach((pos)->{
            squares.get(pos.getInt()).setFill(Color.GREEN);
        });
        // beatable
        moveset.getBeatableList().forEach((pos)->{
            squares.get(pos.getInt()).setFill(Color.RED);
        });
    }

    public void clearBoard(){
        for (int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++){
                squares.get(y*8+x).setFill((y+x)%2==0? Color.DARKGRAY:Color.WHITE);
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
        board.getChildren().removeAll(new ArrayList<>(pieceImgs.values()));
        pieceImgs.clear();
        pieceImgs.putAll(newPieceImgs);
        board.getChildren().addAll(new ArrayList<>(pieceImgs.values()));
    }

    public void saveMove(Move move){
        switch (tableView.getItems().get(0).getState()){
            case 0:
                tableView.getItems().get(0).setMoveWhite(move.toString());
                System.out.println("save move white " + move.toString());
                break;
                case 1:
                    tableView.getItems().get(0).setMoveBlack(move.toString());
                    tableView.getItems().add(0, new MoveRow());
                    System.out.println("save move black");
                    break;
                    case 2:
                        System.out.println("Nigdy nie powinno byc");
                        break;
        }
        this.tableView.refresh();
    }

    public void movePiece(Move move){
        ImageView pieceImg = pieceImgs.get(move.getStartPosition().getInt());
        pieceImg.setX(move.getEndPosition().getX() * 100);
        pieceImg.setY((7 - move.getEndPosition().getY()) * 100);
        pieceImgs.remove(move.getStartPosition().getInt());
        board.getChildren().remove(pieceImgs.get(move.getEndPosition().getInt()));

        pieceImgs.replace(move.getEndPosition().getInt(), pieceImg);

        saveMove(move);
        clearBoard();
    }

    public void modifyCheck(Piece.Team team){
        if(team == null){
            checkText.setText("");
        } else {
            checkText.setText(team.toString());
        }
    }
}
