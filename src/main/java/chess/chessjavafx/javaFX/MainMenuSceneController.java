package chess.chessjavafx.javaFX;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class MainMenuSceneController {
    private Stage stage;
    private Scene scene;
    private VBox root;
    private Button gameButton;
    private Button historyButton;
    private Button exitButton;

    public MainMenuSceneController(Stage stage){
        this.stage = stage;
        this.root = new VBox(10);
        root.setMinWidth(1000);
        root.setMinHeight(1000);

//        Font font = new Font("");
        gameButton = new Button("Gra");
//        gameButton.setFont(new Font("Cantarell", 64.0));
//        gameButton.setTextFill(Color.RED);
        historyButton = new Button("Historia");
        exitButton = new Button("Wyjdź");

        exitButton.setOnAction(event -> {
            stage.close();
        });

        root.getChildren().add(gameButton);
        root.getChildren().add(historyButton);
        root.getChildren().add(exitButton);
        root.setAlignment(Pos.CENTER);

        this.scene = new Scene(root, Color.RED);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/fontstyle.css")).toExternalForm());

    }

    public void setGameButton(Scene gameScene){
        gameButton.setOnAction(event -> {
            stage.setScene(gameScene);
        });
    }
    public void setHistoryButton(Scene historyScene){
        historyButton.setOnAction(event -> {
            stage.setScene(historyScene);
        });
    }

    public Scene getScene() {
        return scene;
    }


}
