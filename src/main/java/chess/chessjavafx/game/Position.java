package chess.chessjavafx.game;

public class Position {
    private int x;
    private int y;

    private Move castling;


    public Position(int x, int y){
        checkIfValid(x, y);
        this.x = x;
        this.y = y;
        this.castling = null;
    }
    public Position(int position){
        checkIfValid(position);
        this.x = position % 8;
        this.y = position / 8;
        this.castling = null;
    }

    public Position(String string){
        checkIfValid(string);
        x = string.charAt(0)-'a';
        y = string.charAt(1) - '1';
        this.castling = null;
    }

    public Move getCastling() {
        return castling;
    }

    public void setCastling(Move castling) {
        this.castling = castling;
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
        checkIfValid(x, y);
        this.x = x;
        this.y = y;
    }
    public void setPosition(Integer[] position){
        checkIfValid(position);
        this.x = position[0];
        this.y = position[1];
    }
    public void setPosition(int position){
        checkIfValid(position);
        this.x = position % 8;
        this.y = position / 8;
    }

    private void checkIfValid(int x, int y){
        if(x > 7 || x < 0 || y > 7 || y < 0){
            throw new IllegalArgumentException("Niepoprawna pozycja");
        }
    }
    private void checkIfValid(int pos){
        int x = pos % 8;
        int y = pos / 8;
        checkIfValid(x, y);
    }
    private void checkIfValid(Integer[] pos){
        int x = pos[0];
        int y = pos[1];
        checkIfValid(x, y);
    }
    private void checkIfValid(String pos){
        int x = pos.charAt(0)-'a';
        int y = pos.charAt(1) - '1';
        checkIfValid(x, y);
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
