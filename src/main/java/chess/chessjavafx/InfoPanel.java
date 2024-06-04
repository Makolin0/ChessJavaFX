package chess.chessjavafx;

import javafx.scene.Group;
import javafx.scene.control.Menu;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class InfoPanel {
    private Group root;
    private Text check;

    public InfoPanel(Group root){
        this.root = root;
        this.check = new Text("-");


        check.setX(8*100 + 10);
        check.setY(10);

        root.getChildren().add(check);
    }

    public Text getCheck(){
        return check;
    }



}
