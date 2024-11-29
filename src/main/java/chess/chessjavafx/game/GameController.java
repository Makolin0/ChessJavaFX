package chess.chessjavafx.game;

import chess.chessjavafx.javaFX.Game;
import chess.chessjavafx.packages.Moveset;
import chess.chessjavafx.pieces.Piece;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
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

    public GameController(Stage stage, GameMoves gameMoves) throws IOException {
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

        loadGame(gameMoves);

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

                Position extractedEndPosition = currentPieceMoveset.getMovableList().stream().filter(pos -> {
                    System.out.println("extracted " + pos + " - " + move.getEndPosition() + " - " + (pos.equals(move.getEndPosition())));
                    return pos.equals(move.getEndPosition());
                }).findFirst().get();

                if(extractedEndPosition.getCastling() != null) {
                    checkerboard.move(extractedEndPosition.getCastling(), true);
                }

                gameMoves.addMove(move);
                game.updateAllPieces(checkerboard);
                game.clearBoard();
                game.saveMove(move);
                swapTeam();
                game.setPlayer(currentPlayer);
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
        // TODO - Informuj płytkę Arduino żeby wysyłała cały stan
    }

    public void fixBoard(List<Integer> physicalBoard){
        if(checkerboard.checkIfLegal(physicalBoard)){
            isIllegal = false;
            game.setAlarmVisibility(false);
            game.clearBoard();
            currentPieceMoveset = null;
        }
    }

    private void loadGame(GameMoves gameMoves) throws IOException {
        for(Move move : gameMoves.getMoves()){
            checkerboard.move(move);
            game.saveMove(move);
        }
        currentPlayer = gameMoves.getMoves().size() % 2 == 0 ? Piece.Team.WHITE : Piece.Team.BLACK;
        game.updateAllPieces(checkerboard);


        game.setPlayer(currentPlayer);

        Piece.Team checkTeam = checkerboard.lookForCheck();
        game.modifyCheck(checkTeam);
    }
}
