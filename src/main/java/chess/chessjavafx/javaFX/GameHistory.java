package chess.chessjavafx.javaFX;

import chess.chessjavafx.game.Move;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class GameHistory implements Initializable {
    @FXML
    public TableView<MoveRow> tableView;
    @FXML
    public TableColumn<MoveRow, String> whiteCol;
    @FXML
    public TableColumn<MoveRow, String> blackCol;

    List<MoveRow> moveRows;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        whiteCol.setCellValueFactory(new PropertyValueFactory<>("moveWhite"));
        blackCol.setCellValueFactory(new PropertyValueFactory<>("moveBlack"));

    }

    public void gameData(List<Move> moves){
        moveRows = new ArrayList<>();
        for(int i = 0; i < moves.size(); i += 2){
            String whiteMove = moves.get(i).toString();
            String blackMove = "-";
            try{
                blackMove = moves.get(i+1).toString();
            } catch (Exception ignored){
            }

            MoveRow moveRow = new MoveRow(whiteMove, blackMove);
            moveRows.add(moveRow);
        }

        tableView.getItems().setAll(moveRows);
    }

    public void goBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/history.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.show();
    }
}
