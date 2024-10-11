package chess.chessjavafx.game;

import chess.chessjavafx.javaFX.GameSceneController;
import chess.chessjavafx.packages.Moveset;
import chess.chessjavafx.pieces.Piece;
import javafx.scene.Group;

public class GameController {
    private Checkerboard checkerboard;
    private Piece.Team currentPlayer;
    private Moveset currentPieceMoveset;
    private GameMoves gameMoves;
    private GameSceneController gameSceneController;

    public GameController() {
        this.checkerboard = new Checkerboard();
        this.currentPlayer = Piece.Team.WHITE;
        this.currentPieceMoveset = null;
        this.gameMoves = new GameMoves();
        this.gameSceneController = new GameSceneController();

        gameSceneController.updateAllPieces(checkerboard);

        gameSceneController.movePiece(new Move(new Position(1, 1), new Position(1, 2)));
        gameSceneController.movePiece(new Move(new Position(0, 0), new Position(6, 6)));

        gameSceneController.showMoveset(checkerboard.possibleMoves(new Position(1, 0)));
        gameSceneController.clearBoard();
    }

    private void swapTeam(){
        currentPlayer = currentPlayer == Piece.Team.WHITE ? Piece.Team.BLACK : Piece.Team.WHITE;
    }

    public GameSceneController getGameSceneController() {
        return gameSceneController;
    }

    public void pickUp(Position position){
        currentPieceMoveset = checkerboard.possibleMoves(position);
        gameSceneController.showMoveset(currentPieceMoveset);
    }

    public void makeMove(Position destination){
        Move move = new Move(currentPieceMoveset.getCurrentPosition(), destination);
        if(currentPieceMoveset.getMovableList().contains(destination) || currentPieceMoveset.getBeatableList().contains(destination)){
            // ruch
            checkerboard.move(move);
            swapTeam();
            gameMoves.addMove(move);
        } else if (!currentPieceMoveset.getCurrentPosition().equals(destination)) {
            // ani możliwy ruch ani odłożenie na poprzednie pole
            throw new IllegalArgumentException("Niemożliwy ruch!");
        }
    }
}
