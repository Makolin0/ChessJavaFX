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
    private List<Position> illegalPickUp;
    private List<Position> illegalPlace;
    private Piece.Team currentPlayer;
    private Moveset currentPieceMoveset;
    private final GameMoves gameMoves;
    private final Game game;

    public GameController(Stage stage) throws IOException {
        this.checkerboard = new Checkerboard();
        this.currentPlayer = Piece.Team.WHITE;
        this.currentPieceMoveset = null;
        this.gameMoves = new GameMoves();
        this.illegalPlace = new ArrayList<>();
        this.illegalPickUp = new ArrayList<>();

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
        if(illegalPickUp.isEmpty() && currentPlayer == checkerboard.getPieceTeam(position)){
            currentPieceMoveset = checkerboard.possibleMoves(position);
            game.showMoveset(currentPieceMoveset);
        } else {
            System.out.println("illegal!");
            illegalPickUp.add(position);
            game.clearBoard();
            game.colorIllegalPlace(illegalPlace);
            game.colorIllegalPickUp(illegalPickUp);
        }
    }

    public void place(Position destination){
        System.out.println("place " + destination);
        if(illegalPickUp.isEmpty() && illegalPlace.isEmpty()){
            if(currentPieceMoveset.getMovableList().contains(destination) || currentPieceMoveset.getBeatableList().contains(destination)){
                Move move = new Move(currentPieceMoveset.getCurrentPosition(), destination);
                System.out.println("move " + move);
                // move
                checkerboard.move(move);
                swapTeam();
                gameMoves.addMove(move);
                game.updateAllPieces(checkerboard);
                game.clearBoard();
                game.swapPlayer();
                game.saveMove(move);
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
                // previous place
                game.clearBoard();
                currentPieceMoveset = null;
            } else {
                System.out.println("illegal place with piece!");
                // illegal place
                illegalPlace.add(destination);
                illegalPickUp.add(currentPieceMoveset.getCurrentPosition());
                game.clearBoard();
                game.colorIllegalPlace(illegalPlace);
                game.colorIllegalPickUp(illegalPickUp);
                currentPieceMoveset = null;
            }
        } else {
            System.out.println("illegal place unknown object!");
            // illegal place
            illegalPlace.add(destination);
            game.clearBoard();
            game.colorIllegalPlace(illegalPlace);
            game.colorIllegalPickUp(illegalPickUp);
            currentPieceMoveset = null;
        }
    }

}
