package chess.chessjavafx.pieces;

import chess.chessjavafx.game.Position;

import java.util.Map;
import java.util.Objects;

public class Checks {
    // checks if move doesn't leave board and field is empty
    public static Position legalMove(int x, int y, Map<Integer, Piece> allPieces) {
        if(x < 0 || x > 7 || y < 0 || y > 7)
            return null;

        try{
            Position pos = new Position(x, y);
            if(Objects.isNull(allPieces.get(pos.getInt())))
                return pos;
            else
                return null;
        } catch(Exception e){
            return null;
        }
    }

    // checks if beat doesn't leave board and field is enemy piece
    public static Position legalBeat(int x, int y, Piece.Team myTeam, Map<Integer, Piece> allPieces) {
        if(x < 0 || x > 7 || y < 0 || y > 7)
            return null;

        try{
            Position pos = new Position(x, y);
            Piece enemy = allPieces.get(pos.getInt());
            if(!Objects.isNull(enemy) && enemy.getTeam() != myTeam)
                return pos;
            else
                return null;
        } catch(Exception e){
            return null;
        }
    }
}
