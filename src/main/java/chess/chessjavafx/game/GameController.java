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

        for(Position pos : currentPieceMoveset.getBeatableList()){
            System.out.println("bicie " + pos.getX() + " " + pos.getY());
        }

        gameSceneController.showMoveset(currentPieceMoveset);
    }

    public void makeMove(Position destination){
        Move move = new Move(currentPieceMoveset.getCurrentPosition(), destination);
        if(currentPieceMoveset.getMovableList().contains(destination) || currentPieceMoveset.getBeatableList().contains(destination)){
            // ruch
            checkerboard.move(move);
            swapTeam();
            gameMoves.addMove(move);
//            gameSceneController.movePiece(move);
            gameSceneController.updateAllPieces(checkerboard);
            gameSceneController.clearBoard();
            currentPieceMoveset = null;
        } else if (currentPieceMoveset.getCurrentPosition().equals(destination)) {
            // odstawiamy w poprzednie miejsce
            gameSceneController.clearBoard();
            currentPieceMoveset = null;
        } else{
            // ani możliwy ruch ani odłożenie na poprzednie pole
            throw new IllegalArgumentException("Niemożliwy ruch!");
        }
    }

    public void sendPosition(Position position){
        if(currentPieceMoveset == null){
            System.out.println("podnosi");
            pickUp(position);
        } else {
            System.out.println("stawia");
            makeMove(position);
        }
    }
}
