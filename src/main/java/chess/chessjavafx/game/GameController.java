package chess.chessjavafx.game;

import chess.chessjavafx.javaFX.Game;
import chess.chessjavafx.packages.Moveset;
import chess.chessjavafx.Team;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class GameController {
    private final Checkerboard checkerboard;
    private final GameData gameData;
    private final Game game;
    private final Engine engine;

    private Team currentPlayer;
    private Moveset currentPieceMoveset;
    private Boolean isIllegal;

    private Team vsAI;
    private final String enginePath = "/home/adamz/Documents/stockfish/stockfish-ubuntu-x86-64-avx2";

    public GameController(Stage stage, Team vsAI) throws IOException {
        this.checkerboard = new Checkerboard();
        this.gameData = new GameData(vsAI);

        this.currentPlayer = Team.WHITE;
        this.currentPieceMoveset = null;
        this.isIllegal = false;

        this.vsAI = vsAI;
        this.engine = new Engine(20, enginePath);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game.fxml"));
        Parent root = loader.load();
        this.game = loader.getController();
        game.updateAllPieces(checkerboard);
        game.setGameController(this);

        if(vsAI == Team.WHITE)
            aiMove();

        stage.getScene().setRoot(root);
        stage.show();
    }

    public GameController(Stage stage, GameData gameData) throws IOException {
        this.checkerboard = new Checkerboard();
        this.gameData = gameData;
        loadGame();

        this.currentPlayer = Team.WHITE;
        this.currentPieceMoveset = null;
        this.isIllegal = false;

        this.engine = new Engine(20, enginePath);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game.fxml"));
        Parent root = loader.load();
        this.game = loader.getController();
        game.updateAllPieces(checkerboard);
        game.setGameController(this);

        stage.getScene().setRoot(root);
        stage.show();
    }

    private void swapTeam(){
        currentPlayer = currentPlayer == Team.WHITE ? Team.BLACK : Team.WHITE;
    }

    private void saveMove(Move move){
        gameData.addMove(move);
        game.updateAllPieces(checkerboard);
        game.clearBoard();
        game.saveMove(move);
        swapTeam();
        game.setPlayer(currentPlayer);
        currentPieceMoveset = null;

        Team checkTeam = checkerboard.lookForCheck();
        game.modifyCheck(checkTeam);
        if(checkTeam != null){
            if(checkerboard.lookForCheckmate(checkTeam)){
                // TODO - co zrobic po wykryciu szach mat
            }
        }
    }

    private void aiMove() {
        Move move = engine.calculateMove(gameData.getMoves());

        checkerboard.move(move);
        saveMove(move);
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

    public void place(Position destination) {
        System.out.println("place " + destination);

        if(!isIllegal){
//            podniesienie gdy legalne
            if(currentPieceMoveset.getMovableList().contains(destination) || currentPieceMoveset.getBeatableList().contains(destination)){
//                legalny ruch
                Move move = new Move(currentPieceMoveset.getCurrentPosition(), destination);
                System.out.println("move " + move);

                checkerboard.move(move);
                saveMove(move);

                if (vsAI == currentPlayer) {
                    aiMove();
                }

            } else if (currentPieceMoveset.getCurrentPosition().equals(destination)) {
//                odłożenie figury
                System.out.println("revert");
                game.clearBoard();
                currentPieceMoveset = null;
            } else {
//                nielegalny ruch
                System.out.println("illegal place with piece!");
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

    private void loadGame() {
        for(Move move : gameData.getMoves()){
            checkerboard.move(move);
            game.saveMove(move);
        }
        game.updateAllPieces(checkerboard);
        currentPlayer = gameData.getMoves().size() % 2 == 0 ? Team.WHITE : Team.BLACK;
        game.setPlayer(currentPlayer);
        game.modifyCheck(checkerboard.lookForCheck());
    }
}
