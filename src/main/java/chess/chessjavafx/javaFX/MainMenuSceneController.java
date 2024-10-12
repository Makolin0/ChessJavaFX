package chess.chessjavafx.javaFX;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainMenuSceneController {
    private Scene scene;
    private VBox root;
    private Button gameButton;
    private Button historyButton;
    private Button exitButton;

    public MainMenuSceneController(Stage stage, Scene gameScene, Scene historyScene){
        this.root = new VBox(10);
        root.setMinWidth(1000);
        root.setMinHeight(1000);

        gameButton = new Button("Gra");
        historyButton = new Button("Historia");
        exitButton = new Button("Wyjdź");

        gameButton.setOnAction(event -> {
            stage.setScene(gameScene);
        });
        historyButton.setOnAction(event -> {
            stage.setScene(historyScene);
        });
        exitButton.setOnAction(event -> {
            stage.close();
        });

        root.getChildren().add(gameButton);
        root.getChildren().add(historyButton);
        root.getChildren().add(exitButton);
        root.setAlignment(Pos.CENTER);

        this.scene = new Scene(root, Color.RED);
    }

    public Scene getScene() {
        return scene;
    }


}
