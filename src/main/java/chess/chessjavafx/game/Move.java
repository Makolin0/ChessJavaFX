package chess.chessjavafx.game;

public class Move {
    private Position startPosition;
    private Position endPosition;

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
