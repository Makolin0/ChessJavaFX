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

public class SelectDifficulty implements Initializable {
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private Button button5;
    @FXML
    private Button button6;
    @FXML
    private Button button7;
    @FXML
    private Button button8;
    @FXML
    private Button button9;
    @FXML
    private Button button10;
    @FXML
    private Button button11;
    @FXML
    private Button button12;
    @FXML
    private Button button13;
    @FXML
    private Button button14;
    @FXML
    private Button button15;
    @FXML
    private Button button16;
    @FXML
    private Button button17;
    @FXML
    private Button button18;
    @FXML
    private Button button19;
    @FXML
    private Button button20;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button1.setOnAction(event -> {enterGameAI(event, 1);});
        button2.setOnAction(event -> {enterGameAI(event, 2);});
        button3.setOnAction(event -> {enterGameAI(event, 3);});
        button4.setOnAction(event -> {enterGameAI(event, 4);});
        button5.setOnAction(event -> {enterGameAI(event, 5);});
        button6.setOnAction(event -> {enterGameAI(event, 6);});
        button7.setOnAction(event -> {enterGameAI(event, 7);});
        button8.setOnAction(event -> {enterGameAI(event, 8);});
        button9.setOnAction(event -> {enterGameAI(event, 9);});
        button10.setOnAction(event -> {enterGameAI(event, 10);});
        button11.setOnAction(event -> {enterGameAI(event, 11);});
        button12.setOnAction(event -> {enterGameAI(event, 12);});
        button13.setOnAction(event -> {enterGameAI(event, 13);});
        button14.setOnAction(event -> {enterGameAI(event, 14);});
        button15.setOnAction(event -> {enterGameAI(event, 15);});
        button16.setOnAction(event -> {enterGameAI(event, 16);});
        button17.setOnAction(event -> {enterGameAI(event, 17);});
        button18.setOnAction(event -> {enterGameAI(event, 18);});
        button19.setOnAction(event -> {enterGameAI(event, 19);});
        button20.setOnAction(event -> {enterGameAI(event, 20);});
    }

    public void goBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/main-menu.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.show();
    }

    public void enterGameAI(ActionEvent event, int difficulty) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        try {
            new GameController(stage, Team.BLACK, difficulty);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
