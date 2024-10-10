package chess.chessjavafx.packages;

import chess.chessjavafx.game.Position;

import java.util.ArrayList;
import java.util.List;

public class Moveset {
    private List<Position> movableList;
    private List<Position> beatableList;
    private Position currentPosition;

    public Moveset(){
        movableList = new ArrayList<>();
        beatableList = new ArrayList<>();
        currentPosition = null;
    }

    public List<Position> getMovableList() {
        return movableList;
    }

    public void setMovableList(List<Position> movableList) {
        this.movableList = movableList;
    }

    public List<Position> getBeatableList() {
        return beatableList;
    }

    public void setBeatableList(List<Position> beatableList) {
        this.beatableList = beatableList;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Position currentPosition) {
        this.currentPosition = currentPosition;
    }
}
