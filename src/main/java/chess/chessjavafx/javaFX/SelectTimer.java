package chess.chessjavafx.javaFX;

import chess.chessjavafx.Team;
import chess.chessjavafx.game.GameController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SelectTimer {
    @FXML
    public Button button1;
    @FXML
    public Button button5;
    @FXML
    public Button button10;
    @FXML
    public Button button15;
    @FXML
    public Button button20;
    @FXML
    public Button buttonInfinite;

    public void setData(Team vsAI, Integer difficulty) {
        button1.setOnAction(event -> {enterGame(event, 1, vsAI, difficulty);});
        button5.setOnAction(event -> {enterGame(event, 5, vsAI, difficulty);});
        button10.setOnAction(event -> {enterGame(event, 10, vsAI, difficulty);});
        button15.setOnAction(event -> {enterGame(event, 15, vsAI, difficulty);});
        button20.setOnAction(event -> {enterGame(event, 20, vsAI, difficulty);});
        buttonInfinite.setOnAction(event -> {enterGame(event, null, vsAI, difficulty);});
    }

    public void enterGame(ActionEvent event, Integer timer, Team vsAI, Integer difficulty) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        try {
            new GameController(stage, timer, vsAI, difficulty);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void goBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/main-menu.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.show();
    }
}
