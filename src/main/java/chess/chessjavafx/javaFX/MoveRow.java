package chess.chessjavafx.javaFX;

public class MoveRow {
    private String moveWhite;
    private String moveBlack;

    public MoveRow(String moveWhite, String moveBlack) {
        this.moveWhite = moveWhite;
        this.moveBlack = moveBlack;
    }
    public MoveRow() {
        this.moveWhite = "";
        this.moveBlack = "";
    }

    public String getMoveWhite() {
        return moveWhite;
    }

    public String getMoveBlack() {
        return moveBlack;
    }

    public void setMoveWhite(String moveWhite) {
        this.moveWhite = moveWhite;
    }

    public void setMoveBlack(String moveBlack) {
        this.moveBlack = moveBlack;
    }

    // 0 - empty
    // 1 - white moved
    // 2 - full
    public int getState(){
        if(moveWhite.isBlank())
            return 0;
        if(moveBlack.isBlank())
            return 1;
        return 2;
    }
}
