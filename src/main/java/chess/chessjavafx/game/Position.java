package chess.chessjavafx.game;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    public Position(int position){
        this.x = position % 8;
        this.y = position / 8;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getInt(){
        return 8*y + x;
    }
    public Integer[] getArray(){
        return new Integer[]{x, y};
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void setPosition(Integer[] position){
        this.x = position[0];
        this.y = position[1];
    }
    public void setPosition(int position){
        this.x = position % 8;
        this.y = position / 8;
    }

    @Override
    public String toString() {
        return (char)(getX()+'a') + "" + (getY()+1);
    }

    @Override
    public boolean equals(Object obj) {

        return this.x == ((Position) obj).x && this.y == ((Position) obj).y;
    }
}
