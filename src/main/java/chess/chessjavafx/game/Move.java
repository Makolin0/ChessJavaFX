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
        this.startPosition = null;
        this.endPosition = null;
    }
    public Move(Position startPosition, Position endPosition){
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }
    public Move(String string){
        this.startPosition = new Position(string.substring(0, 2));
        this.endPosition = new Position(string.substring(string.length()-2));
    }

    @Override
    public String toString() {
        return (startPosition!=null ? startPosition : "?") + " -> " + (endPosition!=null ? endPosition : "?");
    }
}
