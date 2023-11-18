package chess.chessjavafx;

import chess.chessjavafx.Pieces.Pawn;
import chess.chessjavafx.Pieces.Piece;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;

public class PiecesController {
    Group root;
    private List<Piece> pieces;

    public PiecesController(Group root){
        this.root = root;

        pieces = new ArrayList<>();
        setPieces();
    }

    private void setPieces(){

        Piece p = new Pawn(Piece.Team.WHITE, Piece.Type.PAWN, 1, 4);
        pieces.add(p);
        root.getChildren().add(p.getImg());

        for (int i = 0; i < 8; i++) {
            Piece piece = new Pawn(Piece.Team.WHITE, Piece.Type.PAWN, i, 1);
            pieces.add(piece);
            root.getChildren().add(piece.getImg());
        }
    }


    public Piece checkForPiece(int x, int y){
        return pieces.stream().filter(element -> element.checkPlace(x, y)).findFirst().orElse(null);
    }


}
