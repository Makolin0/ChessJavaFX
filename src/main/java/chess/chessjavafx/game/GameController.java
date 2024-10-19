package chess.chessjavafx.game;

import chess.chessjavafx.javaFX.Game;
import chess.chessjavafx.javaFX.GameSceneController;
import chess.chessjavafx.packages.Moveset;
import chess.chessjavafx.pieces.Piece;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class GameController {
    private Checkerboard checkerboard;
    private Piece.Team currentPlayer;
    private Moveset currentPieceMoveset;
    private GameMoves gameMoves;
    private Game game;

    public GameController(Stage stage) throws IOException {
        this.checkerboard = new Checkerboard();
        this.currentPlayer = Piece.Team.WHITE;
        this.currentPieceMoveset = null;
        this.gameMoves = new GameMoves();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game.fxml"));
        Parent root = loader.load();
        this.game = loader.getController();

        game.updateAllPieces(checkerboard);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


        Thread thread = new Thread(() -> {
            terminalController();
        });
        thread.start();
    }

    private void swapTeam(){
        currentPlayer = currentPlayer == Piece.Team.WHITE ? Piece.Team.BLACK : Piece.Team.WHITE;
    }

    public void pickUp(Position position){
        if(currentPlayer == checkerboard.getPieceTeam(position)){
            currentPieceMoveset = checkerboard.possibleMoves(position);
            game.showMoveset(currentPieceMoveset);
        }
    }

    public void makeMove(Position destination){
        Move move = new Move(currentPieceMoveset.getCurrentPosition(), destination);
        if(currentPieceMoveset.getMovableList().contains(destination) || currentPieceMoveset.getBeatableList().contains(destination)){
            // ruch
            checkerboard.move(move);
            swapTeam();
            gameMoves.addMove(move);
//            gameSceneController.movePiece(move);
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
            // odstawiamy w poprzednie miejsce
            game.clearBoard();
            currentPieceMoveset = null;
        } else{
            // ani możliwy ruch ani odłożenie na poprzednie pole
            throw new IllegalArgumentException("Niemożliwy ruch!");
        }
    }

    public void sendPosition(Position position){
        if(currentPieceMoveset == null){
            pickUp(position);
        } else {
            makeMove(position);
        }
    }


    private void terminalController(){
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String text = scanner.next();
            System.out.println("napisano " + text);
            Position pos = new Position(text);
            System.out.println("pozycja " + pos);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    sendPosition(pos);
                }
            });
        }

    }
}
