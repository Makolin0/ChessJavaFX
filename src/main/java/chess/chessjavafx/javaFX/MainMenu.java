package chess.chessjavafx.javaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainMenu {
    public Button history;
    public Button game;
    public Button exit;

    @FXML
    private void printHelloWorld(ActionEvent event) {
        event.consume();
        System.out.println("Hello, World!");
    }
}
