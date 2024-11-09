package chess.chessjavafx.game;

import chess.chessjavafx.javaFX.Game;
import chess.chessjavafx.packages.Moveset;
import chess.chessjavafx.pieces.Piece;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private final Checkerboard checkerboard;
    private Boolean isIllegal;
    private Piece.Team currentPlayer;
    private Moveset currentPieceMoveset;
    private final GameMoves gameMoves;
    private final Game game;

    public GameController(Stage stage) throws IOException {
        this.checkerboard = new Checkerboard();
        this.currentPlayer = Piece.Team.WHITE;
        this.currentPieceMoveset = null;
        this.gameMoves = new GameMoves();
        this.isIllegal = false;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game.fxml"));
        Parent root = loader.load();
        this.game = loader.getController();

        game.updateAllPieces(checkerboard);

        game.setGameController(this);

        stage.getScene().setRoot(root);
        stage.show();
    }

    private void swapTeam(){
        currentPlayer = currentPlayer == Piece.Team.WHITE ? Piece.Team.BLACK : Piece.Team.WHITE;
    }

    public void pickUp(Position position){
        System.out.println("pick up " + position);
        if(!isIllegal && currentPlayer == checkerboard.getPieceTeam(position)){
            currentPieceMoveset = checkerboard.possibleMoves(position);
            game.showMoveset(currentPieceMoveset);
        } else {
            System.out.println("illegal!");
            setAlarm();
        }
    }

    public void place(Position destination){
        System.out.println("place " + destination);

        if(!isIllegal){
//            podniesienie gdy legalne
            if(currentPieceMoveset.getMovableList().contains(destination) || currentPieceMoveset.getBeatableList().contains(destination)){
//                legalny ruch
                Move move = new Move(currentPieceMoveset.getCurrentPosition(), destination);
                System.out.println("move " + move);

                checkerboard.move(move);
                gameMoves.addMove(move);
                game.updateAllPieces(checkerboard);
                game.clearBoard();
                game.saveMove(move);
                game.swapPlayer();
                swapTeam();
                currentPieceMoveset = null;

                Piece.Team checkTeam = checkerboard.lookForCheck();
                game.modifyCheck(checkTeam);
                if(checkTeam != null){
                    if(checkerboard.lookForCheckmate(checkTeam)){
                        // TODO - co zrobic po wykryciu szach mat
                    }
                }

            } else if (currentPieceMoveset.getCurrentPosition().equals(destination)) {
                System.out.println("revert");
//                odłożenie figury
                game.clearBoard();
                currentPieceMoveset = null;
            } else {
                System.out.println("illegal place with piece!");
//                nielegalny ruch
                setAlarm();
            }
        }
    }

    private void setAlarm(){
        System.out.println("ALARM");
        isIllegal = true;
        game.setAlarmVisibility(true);
        game.clearBoard();
        currentPieceMoveset = null;
    }

    public void fixBoard(List<Integer> physicalBoard){
        if(checkerboard.checkIfLegal(physicalBoard)){
            isIllegal = false;
            game.setAlarmVisibility(false);
            game.clearBoard();
            currentPieceMoveset = null;
        }
    }
}
