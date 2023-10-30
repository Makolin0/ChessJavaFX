package chess.chessjavafx.tests;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Learn extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        //jak w teatrze, najpierw tworzymy elementy by postawić je na scenie, która jest w teatrze
        Group root = new Group();
        Scene scene = new Scene(root/*, Color.GRAY*/);
        stage.setScene(scene);

        Image icon = new Image("file:src/imgs/pawnW.png");
        stage.getIcons().add(icon);
        stage.setTitle("Learning javaFX");
        stage.setWidth(300);
        stage.setHeight(300);
        stage.setResizable(false);
//        stage.setFullScreen(true);

        Text text = new Text();
        text.setText("message");
        text.setFont(Font.font("Verdana", 50));
        text.setFill(Color.RED);
        text.setX(50);
        text.setY(50);

        Line line = new Line();
        line.setStartX(100);
        line.setStartY(100);
        line.setEndX(200);
        line.setEndY(100);
        line.setStrokeWidth(6);
        line.setStroke(Color.LIME);
        line.setOpacity(0.4);
        line.setRotate(20);

        Rectangle rectangle = new Rectangle();
        rectangle.setX(100);
        rectangle.setY(100);
        rectangle.setWidth(40);
        rectangle.setHeight(40);
        rectangle.setFill(Color.BLUE);

        ImageView imageView = new ImageView(icon);
        imageView.setX(0);
        imageView.setY(0);

        root.getChildren().add(text);
        root.getChildren().add(line);
        root.getChildren().add(rectangle);
        root.getChildren().add(imageView);

        //śledzenie myszy
        scene.setOnMouseMoved((MouseEvent event) -> {
            double mouseX = event.getSceneX();
            double mouseY = event.getSceneY();
            System.out.printf("X: %f Y: %f%n", mouseX, mouseY);
        });


        stage.show();//musi byc na koncu
    }

    //włącza wszystko
    public static void main(String[] args) {
        launch(args);
    }
}
