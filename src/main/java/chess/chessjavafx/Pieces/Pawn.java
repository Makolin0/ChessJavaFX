package chess.chessjavafx.Pieces;

import java.util.ArrayList;
import java.util.List;

public class Pawn implements Piece{
    private Team team;
    private Type type;
    private Integer[] place;

    public Pawn(Team team, Type type, Integer[] place) {
        this.team = team;
        this.type = type;
        this.place = place;
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
    public List<Integer[]> movableList() {
        List<Integer[]> movable = new ArrayList<>();

        if(Team.WHITE.equals(team)){
            if(place[1] == 1){
                movable.add(new Integer[]{place[0], 3});
            }
            movable.add(new Integer[]{place[0], place[1] + 1});
        }
        else if(Team.BLACK.equals(team)){
            if(place[1] == 6){
                movable.add(new Integer[]{place[0], 4});
            }
            movable.add(new Integer[]{place[0], place[1] - 1});
        }

        return movable;
    }

    @Override
    public void move(int x, int y) {
        place[0] = x;
        place[1] = y;
    }
}
