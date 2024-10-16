package chess.chessjavafx.javaFX;

public class MoveRow {
    private String moveWhite;
    private String moveBlack;

    public MoveRow(String moveWhite, String moveBlack) {
        this.moveWhite = moveWhite;
        this.moveBlack = moveBlack;
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
}
