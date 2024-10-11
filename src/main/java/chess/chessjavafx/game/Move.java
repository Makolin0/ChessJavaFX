package chess.chessjavafx.game;

public class Move {
    private Position startPosition;
    private Position endPosition;

    public Position getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    public Position getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(Position endPosition) {
        this.endPosition = endPosition;
    }

    public Move(){
        startPosition = null;
        endPosition = null;
    }
    public Move(Position startPosition, Position endPosition){
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    @Override
    public String toString() {
        return (startPosition!=null ? startPosition : "?") + " -> " + (endPosition!=null ? endPosition : "?");
    }
}
