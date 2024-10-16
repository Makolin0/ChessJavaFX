package chess.chessjavafx.javaFX;

import chess.chessjavafx.game.GameMoves;
import chess.chessjavafx.game.Move;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

public class HistoryGameSceneController {
    private Scene scene;
    private VBox root;

    private TableView<MoveRow> table;
    private ObservableList<MoveRow> tableData;

    public HistoryGameSceneController(List<Move> moves, Stage stage, Scene prevScene){
        this.root = new VBox(10);
        this.scene = new Scene(root);

        this.table = new TableView<>();
        this.tableData = FXCollections.observableArrayList();
        table.setItems(tableData);
        generateColumns();

        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        table.setPrefWidth(150);
        table.setMaxWidth(200);



        Button button = new Button("Wróć");
        button.setOnAction(event -> {
            stage.setScene(prevScene);
        });
        root.getChildren().add(button);

        root.setMinWidth(1000);
        root.setMinHeight(1000);
        root.setAlignment(Pos.CENTER);

        root.getChildren().add(table);

        loadMoves(moves);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/fontstyle.css")).toExternalForm());
    }

    private void generateColumns() {
        TableColumn<MoveRow, String> whiteCol = new TableColumn<>("Biały");
        whiteCol.setCellValueFactory(new PropertyValueFactory<>("moveWhite"));
        table.getColumns().add(whiteCol);

        TableColumn<MoveRow, String> blackCol = new TableColumn<>("Czarny");
        blackCol.setCellValueFactory(new PropertyValueFactory<>("moveBlack"));
        table.getColumns().add(blackCol);
    }

    public void loadMoves(List<Move> moves){

        for(int i = 0; i < moves.size(); i += 2){
            String whiteMove = moves.get(i).toString();
            String blackMove = "-";
            try{
                blackMove = moves.get(i+1).toString();
            } catch (Exception ignored){

            }

            MoveRow moveRow = new MoveRow(whiteMove, blackMove);
            tableData.add(moveRow);
        }
    }

    public Scene getScene() {
        return scene;
    }
}
