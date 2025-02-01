package chess.chessjavafx.javaFX;

import chess.chessjavafx.game.GameController;
import chess.chessjavafx.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import jssc.SerialPortList;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {
    @FXML
    public Label arduinoFound;
    @FXML
    public Button arduinoReload;

    String portName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        findArduino();
        arduinoReload.setOnAction((ActionEvent event) -> findArduino());
    }

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

    private void findArduino() {
        for (String port : SerialPortList.getPortNames()) {
            String lowerPort = port.toLowerCase();
            if (lowerPort.contains("usb") || lowerPort.contains("arduino") ||
                    lowerPort.contains("ttyacm") || lowerPort.contains("ttyusb")) {
                portName = port;
                arduinoFound.setText("Port Arduino: " + portName);
                return;
            }
        }
        arduinoFound.setText("Nie znalezono Arduino");
        portName = null;
    }
}
