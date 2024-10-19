package chess.chessjavafx.javaFX;

import chess.chessjavafx.game.GameMoves;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class History implements Initializable {
    @FXML
    public TableColumn<GameMoves, String> dateCol;
    @FXML
    public TableColumn<GameMoves, String> lengthCol;
    @FXML
    public TableColumn<GameMoves, String> winnerCol;
    @FXML
    public TableView<GameMoves> tableView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        lengthCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        winnerCol.setCellValueFactory(new PropertyValueFactory<>("winner"));

        tableView.getItems().setAll(loadGames());
        generateRowClick();
    }

    private List<GameMoves> loadGames() {
        List<GameMoves> games = new ArrayList<>();
        Path dir = Paths.get("data");
        try(DirectoryStream<Path> ds = Files.newDirectoryStream(dir)){
            for(Path file : ds){
                games.add(new GameMoves(file));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return games;
    }

    private void generateRowClick(){
        tableView.setRowFactory(tv -> {
            TableRow<GameMoves> row = new TableRow<>();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/game-history.fxml"));
                Parent root = loader.load();
                GameHistory gameHistory = loader.getController();
                row.setOnMouseClicked(event -> {
                    if(!row.isEmpty() && event.getClickCount() >= 1){
                        GameMoves gameMoves = row.getItem();
                        gameHistory.gameData(gameMoves.getMoves());

                        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        stage.getScene().setRoot(root);
                        stage.show();
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return row;
        });
    }

    public void goBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/main-menu.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.show();
    }
}
