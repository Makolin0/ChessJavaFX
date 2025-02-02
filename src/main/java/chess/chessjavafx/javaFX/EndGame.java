package chess.chessjavafx.javaFX;

import chess.chessjavafx.Team;
import chess.chessjavafx.Winner;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class EndGame {
    public Label winnerText;

    public void setData(Winner winner) {
        winnerText.setText(winner == Winner.WHITE ? "Biały" : winner == Winner.BLACK ? "Czarny" : "Nikt");
    }

    public void goBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/main-menu.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.show();
    }
}
