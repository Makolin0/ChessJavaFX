package chess.chessjavafx.Pieces;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Pawn implements Piece{
    private Team team;
    private Type type;
    private Integer[] place;
    private ImageView img;

    public Pawn(Team team, Type type, int x, int y) {
        this.team = team;
        this.type = type;
        this.place = new Integer[2];
        img = new ImageView(new Image("file:src/imgs/pawnW.png"));
        setPlace(x, y);
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public Team getTeam() {
        return team;
    }

    @Override
    public ImageView getImg() {
        return img;
    }

    @Override
    public boolean checkPlace(int x, int y) {
        return x == place[0] && y == place[1];
    }

    @Override
    public List<Integer[]> movableList() {
        List<Integer[]> movable = new ArrayList<>();

        movable.add(place);
        if(Team.WHITE.equals(team)){
            if(place[1] == 1){
                movable.add(new Integer[]{place[0], 3});
            }
            if(place[1] < 7)
                movable.add(new Integer[]{place[0], place[1] + 1});
        }
        else if(Team.BLACK.equals(team)){
            if(place[1] == 6){
                movable.add(new Integer[]{place[0], 4});
            }
            if(place[1] > 0)
                movable.add(new Integer[]{place[0], place[1] - 1});
        }

        return movable;
    }

    @Override
    public void moveFree(int x, int y) {
        img.setX(x - 50);
        img.setY(y - 50);
    }

    @Override
    public void moveBack() {
        img.setX(place[0]);
        img.setY(place[1]);
    }

    @Override
    public void setPlace(int x, int y) {
        img.setX(x*100);
        img.setY(y*100);

        place[0] = x;
        place[1] = y;
    }
}
