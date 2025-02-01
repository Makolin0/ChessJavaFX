package chess.chessjavafx.javaFX;

import chess.chessjavafx.game.Checkerboard;
import chess.chessjavafx.game.GameController;
import chess.chessjavafx.game.Move;
import chess.chessjavafx.game.Position;
import chess.chessjavafx.packages.Moveset;
import chess.chessjavafx.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jssc.SerialPortList;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.*;

public class Game implements Initializable {

    private static final int SQUARE_SIZE = 80;
    private static final int BORDER_SHIFT = 15;
    private static final Color LIGHT_COLOR = Color.WHITE;
    private static final Color DARK_COLOR = Color.DARKGRAY;

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
    @FXML
    public Text alarm;

    @FXML
    public TextField positionField;

    @FXML
    public Text timerText;
    @FXML
    public Text timerWhiteText;
    @FXML
    public Text timerBlackText;
    @FXML
    public Text comTeamText;
    @FXML
    public Text comDifficultyText;
    @FXML
    public FlowPane timerWhiteBg;
    @FXML
    public FlowPane timerBlackBg;
    @FXML
    public FlowPane checkBg;
    @FXML
    public FlowPane alarmBg;

    private List<Rectangle> squares;
    private Map<Integer, ImageView> pieceImgs;
    private List<Text> positionText;

    private GameController gameController;

    // temporary for testing without arduino
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.whiteCol.setCellValueFactory(new PropertyValueFactory<>("moveWhite"));
        this.blackCol.setCellValueFactory(new PropertyValueFactory<>("moveBlack"));

        this.squares = new ArrayList<>();
        this.positionText = new ArrayList<>();
        this.pieceImgs = new HashMap<>();

        setPlayer(Team.WHITE);

        this.alarmBg.setVisible(false);
        this.checkBg.setVisible(false);

        this.tableView.getItems().add(0, new MoveRow());

        generateBoard();
    }

    public void goBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/main-menu.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.show();
    }


    private void generateBoard() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Rectangle rectangle = new Rectangle();
                rectangle.setFill((y + x) % 2 == 0 ? DARK_COLOR : LIGHT_COLOR);
                rectangle.setX(x * SQUARE_SIZE + BORDER_SHIFT);
                rectangle.setY(7 * SQUARE_SIZE - y * SQUARE_SIZE + BORDER_SHIFT);
                rectangle.setWidth(SQUARE_SIZE);
                rectangle.setHeight(SQUARE_SIZE);
                squares.add(rectangle);
            }
        }
        // numbers
        for (int i = 0; i < 8; i++) {
            Text text = new Text();
            text.setText(Integer.toString(i + 1));
            text.setX(5 + BORDER_SHIFT);
            text.setY((8 - i) * SQUARE_SIZE + BORDER_SHIFT - ((double) (SQUARE_SIZE * 5) / 6));
            positionText.add(text);
        }
        // alphabet
        for (int i = 0; i < 8; i++) {
            Text text = new Text();
            text.setText((char) (i + 'a') + "");
            text.setX(i * SQUARE_SIZE + BORDER_SHIFT + ((double) (SQUARE_SIZE * 5) / 6));
            text.setY(8 * SQUARE_SIZE - 5 + BORDER_SHIFT);
            positionText.add(text);
        }

        board.getChildren().addAll(squares);
        board.getChildren().addAll(positionText);
    }


    public void setPlayer(Team currentPlayer) {
        if (currentPlayer == Team.WHITE) {
            timerWhiteBg.getStyleClass().add("timer-current");
            timerBlackBg.getStyleClass().remove("timer-current");
            currentPlayerText.setText("Biały");
        } else {
            timerBlackBg.getStyleClass().add("timer-current");
            timerWhiteBg.getStyleClass().remove("timer-current");
            currentPlayerText.setText("Czarny");
        }
    }

    private void colorCurrentPos(Position pos) {
        squares.get(pos.getInt()).setFill((pos.getX() + pos.getY()) % 2 == 0 ? Color.BLUE : Color.LIGHTBLUE);
    }

    private void colorMovePos(Position pos) {
        squares.get(pos.getInt()).setFill((pos.getX() + pos.getY()) % 2 == 0 ? Color.GREEN : Color.LIGHTGREEN);
    }

    private void colorBeatPos(Position pos) {
        squares.get(pos.getInt()).setFill((pos.getX() + pos.getY()) % 2 == 0 ? Color.DARKRED : Color.RED);
    }

    public void showMoveset(Moveset moveset) {
        colorCurrentPos(moveset.getCurrentPosition());
        moveset.getMovableList().forEach(this::colorMovePos);
        moveset.getBeatableList().forEach(this::colorBeatPos);
    }

    public void clearBoard() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                squares.get(y * 8 + x).setFill((y + x) % 2 == 0 ? Color.DARKGRAY : Color.WHITE);
            }
        }
    }

    public void updateAllPieces(Checkerboard checkerboard) {
        Map<Integer, ImageView> newPieceImgs = checkerboard.getImages();
        newPieceImgs.forEach((key, img) -> {
            Position pos = new Position(key);
            img.setX(pos.getX() * SQUARE_SIZE + BORDER_SHIFT);
            img.setY((7 - pos.getY()) * SQUARE_SIZE + BORDER_SHIFT);
            img.setFitHeight(SQUARE_SIZE);
            img.setFitWidth(SQUARE_SIZE);
        });
        board.getChildren().removeAll(new ArrayList<>(pieceImgs.values()));
        pieceImgs.clear();
        pieceImgs.putAll(newPieceImgs);
        board.getChildren().addAll(new ArrayList<>(pieceImgs.values()));
    }

    public void saveMove(Move move) {
        switch (tableView.getItems().get(0).getState()) {
            case 0:
                tableView.getItems().get(0).setMoveWhite(move.toString());
                break;
            case 1:
                tableView.getItems().get(0).setMoveBlack(move.toString());
                tableView.getItems().add(0, new MoveRow());
                break;
            case 2:
                break;
        }
        this.tableView.refresh();
    }

    public void movePiece(Move move) {
        ImageView pieceImg = pieceImgs.get(move.getStartPosition().getInt());
        pieceImg.setX(move.getEndPosition().getX() * SQUARE_SIZE + BORDER_SHIFT);
        pieceImg.setY((7 - move.getEndPosition().getY()) * SQUARE_SIZE + BORDER_SHIFT);
        pieceImgs.remove(move.getStartPosition().getInt());
        board.getChildren().remove(pieceImgs.get(move.getEndPosition().getInt()));

        pieceImgs.replace(move.getEndPosition().getInt(), pieceImg);

        saveMove(move);
        clearBoard();
    }

    public void modifyCheck(Team team) {
        if (team == null) {
            checkText.setText("");
            checkBg.setVisible(false);
        } else {
            checkBg.setVisible(true);
            checkText.setText("Szach dla: " + team);
        }
    }


    public void colorIllegalPlace(List<Position> illegalPlace) {
        for (Position position : illegalPlace) {
            colorBeatPos(position);
        }
    }

    public void colorIllegalPickUp(List<Position> illegalPickUp) {
        for (Position position : illegalPickUp) {
            colorCurrentPos(position);
        }
    }

    public void sendPickUp() {
        Position position = new Position(positionField.getText());
        positionField.clear();

        gameController.pickUp(position);
    }

    public void sendPlace() {
        Position position = new Position(positionField.getText());
        positionField.clear();

        gameController.place(position);
    }

    public void setAlarmVisibility(Boolean isVisible) {
        alarm.setVisible(isVisible);
        alarmBg.setVisible(isVisible);
    }

    public void setInfo(Team vsAI, Integer difficulty, Integer timer) {
        comTeamText.setText(vsAI == null ? "" : "Kolor komputera: " + vsAI);
        comDifficultyText.setText(difficulty == null ? "" : "Poziom trudności: " + difficulty);
        timerText.setText(timer == null ? "Czas: nieskończoność" : "Czas: " + timer + " minut");
    }

    public void updateTimer(Team team, String timeLeft) {
        if (team == Team.WHITE)
            timerWhiteText.setText(timeLeft);
        if (team == Team.BLACK)
            timerBlackText.setText(timeLeft);
    }


}
