package chess.chessjavafx.javaFX;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Chess extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // set scene
        Group root = new Group();
        GameSceneController gameSceneController = new GameSceneController(root);
//        stage.setScene(scene);
        stage.setScene(gameSceneController.getScene());
        stage.setResizable(false);

        Image icon = new Image("file:src/imgs/pawnW.png");
        stage.getIcons().add(icon);




        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
