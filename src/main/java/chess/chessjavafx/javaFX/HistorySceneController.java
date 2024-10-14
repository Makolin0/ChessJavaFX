package chess.chessjavafx.javaFX;

import chess.chessjavafx.game.GameMoves;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class HistorySceneController {
    private Scene scene;
    private VBox root;
    private Stage stage;

    private TableView<GameMoves> table;
    private ObservableList<GameMoves> tableData;

    public HistorySceneController(Stage stage, Scene prevScene){
        this.root = new VBox(10);
        this.scene = new Scene(root);
        this.stage = stage;

        Button button = new Button("Wróć");
        button.setOnAction(event -> {
            stage.setScene(prevScene);
        });
        root.getChildren().add(button);

        this.table = new TableView<>();
        this.tableData = FXCollections.observableArrayList();
        table.setItems(tableData);
        generateColumns();

        root.setMinWidth(1000);
        root.setMinHeight(1000);
        root.setAlignment(Pos.CENTER);

        table.setItems(tableData);
        root.getChildren().add(table);
    }

    private void generateColumns(){
        TableColumn<GameMoves, String> dateCol = new TableColumn<>("Data");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        table.getColumns().add(dateCol);

        TableColumn<GameMoves, String> lengthCol = new TableColumn<>("Długość");
        lengthCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        table.getColumns().add(lengthCol);

        TableColumn<GameMoves, String> winnerCol = new TableColumn<>("Zwycięzca");
        winnerCol.setCellValueFactory(new PropertyValueFactory<>("winner"));
        table.getColumns().add(winnerCol);
    }

    private void generateRowClick(){
        table.setRowFactory(tv -> {
            TableRow<GameMoves> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if(!row.isEmpty() && event.getClickCount() >= 1){
                    GameMoves gameMoves = row.getItem();
                    HistoryGameSceneController historyGameSceneController = new HistoryGameSceneController(gameMoves.getMoves(), stage, scene);

                    stage.setScene(historyGameSceneController.getScene());
                }
            });
            return row;
        });
    }

    public Scene getScene(){
        return scene;
    }

    public void loadGames(List<GameMoves> gameMovesList){
        tableData.addAll(gameMovesList);
        generateRowClick();
    }


}
