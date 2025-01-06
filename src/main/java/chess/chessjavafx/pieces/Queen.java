package chess.chessjavafx.pieces;

import chess.chessjavafx.Team;
import chess.chessjavafx.game.Checkerboard;
import chess.chessjavafx.game.Position;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Queen implements Piece{
    private final Team team;
    private final ImageView img;

    public Queen(Team team) {
        this.team = team;
        this.img = new ImageView(new Image("file:src/imgs/queen" + (team == Team.WHITE ? "W" : "B") + ".png"));
    }

    @Override
    public Team getTeam() {
        return team;
    }

    @Override
    public ImageView getImg() {
        return img;
    }

    @Override
    public List<Position> getMovableList(Checkerboard checkerboard, Position currentPosition) {
        Map<Integer, Piece> allPieces = checkerboard.getBoard();
        List<Position> movableSquares = new ArrayList<>();
        Position pos;

        // Bishop
        // moves down-left
        int x = currentPosition.getX();
        int y = currentPosition.getY();
        while(x > 0 && y > 0){
            x--;
            y--;
            if((pos = Checks.legalMove(x, y, allPieces)) != null){
                movableSquares.add(pos);
            } else {
                break;
            }
        }
        // moves up-left
        x = currentPosition.getX();
        y = currentPosition.getY();
        while(x > 0 && y < 7){
            x--;
            y++;
            if((pos = Checks.legalMove(x, y, allPieces)) != null){
                movableSquares.add(pos);
            } else {
                break;
            }
        }
        // moves down-right
        x = currentPosition.getX();
        y = currentPosition.getY();
        while(x < 7 && y > 0){
            x++;
            y--;
            if((pos = Checks.legalMove(x, y, allPieces)) != null){
                movableSquares.add(pos);
            } else {
                break;
            }
        }
        // moves up-right
        x = currentPosition.getX();
        y = currentPosition.getY();
        while(x < 7 && y < 7){
            x++;
            y++;
            if((pos = Checks.legalMove(x, y, allPieces)) != null){
                movableSquares.add(pos);
            } else {
                break;
            }
        }

        // Rook
        // moves down
        x = currentPosition.getX();
        y = currentPosition.getY();
        while(y > 0){
            y--;
            if((pos = Checks.legalMove(x, y, allPieces)) != null){
                movableSquares.add(pos);
            } else {
                break;
            }
        }
        // moves up
        x = currentPosition.getX();
        y = currentPosition.getY();
        while(y < 7){
            y++;
            if((pos = Checks.legalMove(x, y, allPieces)) != null){
                movableSquares.add(pos);
            } else {
                break;
            }
        }
        // moves left
        x = currentPosition.getX();
        y = currentPosition.getY();
        while(x > 0){
            x--;
            if((pos = Checks.legalMove(x, y, allPieces)) != null){
                movableSquares.add(pos);
            } else {
                break;
            }
        }
        // moves right
        x = currentPosition.getX();
        y = currentPosition.getY();
        while(x < 7){
            x++;
            if((pos = Checks.legalMove(x, y, allPieces)) != null){
                movableSquares.add(pos);
            } else {
                break;
            }
        }

        return movableSquares;
    }

    @Override
    public List<Position> getBeatableList(Checkerboard checkerboard, Position currentPosition) {
        Map<Integer, Piece> allPieces = checkerboard.getBoard();
        List<Position> beatableSquares = new ArrayList<>();
        Position pos;

        // Bishop
        // moves down-left
        int x = currentPosition.getX();
        int y = currentPosition.getY();
        while(x > 0 && y > 0){
            x--;
            y--;
            if((pos = Checks.legalBeat(x, y, team, allPieces)) != null){
                beatableSquares.add(pos);
                break;
            } else if ((pos = Checks.legalPos(x, y)) != null && allPieces.containsKey(pos.getInt())) {
                break;
            }
        }
        // moves up-left
        x = currentPosition.getX();
        y = currentPosition.getY();
        while(x > 0 && y < 7){
            x--;
            y++;
            if((pos = Checks.legalBeat(x, y, team, allPieces)) != null){
                beatableSquares.add(pos);
                break;
            } else if ((pos = Checks.legalPos(x, y)) != null && allPieces.containsKey(pos.getInt())) {
                break;
            }
        }
        // moves down-right
        x = currentPosition.getX();
        y = currentPosition.getY();
        while(x < 7 && y > 0){
            x++;
            y--;
            if((pos = Checks.legalBeat(x, y, team, allPieces)) != null){
                beatableSquares.add(pos);
                break;
            } else if ((pos = Checks.legalPos(x, y)) != null && allPieces.containsKey(pos.getInt())) {
                break;
            }
        }
        // moves up-right
        x = currentPosition.getX();
        y = currentPosition.getY();
        while(x < 7 && y < 7){
            x++;
            y++;
            if((pos = Checks.legalBeat(x, y, team, allPieces)) != null){
                beatableSquares.add(pos);
                break;
            } else if ((pos = Checks.legalPos(x, y)) != null && allPieces.containsKey(pos.getInt())) {
                break;
            }
        }

        // Rook
        // moves down
        x = currentPosition.getX();
        y = currentPosition.getY();
        while(y > 0){
            y--;
            if((pos = Checks.legalBeat(x, y, team, allPieces)) != null){
                beatableSquares.add(pos);
                break;
            } else if ((pos = Checks.legalPos(x, y)) != null && allPieces.containsKey(pos.getInt())) {
                break;
            }
        }
        // moves up
        x = currentPosition.getX();
        y = currentPosition.getY();
        while(y < 7){
            y++;
            if((pos = Checks.legalBeat(x, y, team, allPieces)) != null){
                beatableSquares.add(pos);
                break;
            } else if ((pos = Checks.legalPos(x, y)) != null && allPieces.containsKey(pos.getInt())) {
                break;
            }
        }
        // moves left
        x = currentPosition.getX();
        y = currentPosition.getY();
        while(x > 0){
            x--;
            if((pos = Checks.legalBeat(x, y, team, allPieces)) != null){
                beatableSquares.add(pos);
                break;
            } else if ((pos = Checks.legalPos(x, y)) != null && allPieces.containsKey(pos.getInt())) {
                break;
            }
        }
        // moves right
        x = currentPosition.getX();
        y = currentPosition.getY();
        while(x < 7){
            x++;
            if((pos = Checks.legalBeat(x, y, team, allPieces)) != null){
                beatableSquares.add(pos);
                break;
            } else if ((pos = Checks.legalPos(x, y)) != null && allPieces.containsKey(pos.getInt())) {
                break;
            }
        }

        return beatableSquares;
    }
}
