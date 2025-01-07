package chess.chessjavafx.javaFX;

import chess.chessjavafx.game.GameController;
import chess.chessjavafx.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainMenu {
    public void enterHistory(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/history.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.show();
    }
    public void newGame(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/select-timer.fxml")));
        Parent root = loader.load();
        SelectTimer controller = loader.getController();
        controller.setData(null, null);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.show();
    }
    public void newGameAI(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/select-difficulty.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.show();
    }
    public void closeApp() {
        System.exit(0);
    }

    public void enterLoadGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/load-game.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.show();
    }
}
