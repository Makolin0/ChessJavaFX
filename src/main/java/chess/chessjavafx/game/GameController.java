package chess.chessjavafx.game;

import chess.chessjavafx.javaFX.Game;
import chess.chessjavafx.packages.Moveset;
import chess.chessjavafx.Team;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameController {
    private ScheduledExecutorService scheduler;
    private Duration timeLeft;

    private final Checkerboard checkerboard;
    private final GameData gameData;
    private final Game game;
    private Engine engine;

    private Team currentPlayer;
    private LocalDateTime currentTurnStart;
    private Moveset currentPieceMoveset;
    private Boolean isIllegal;

    private final String enginePath = "/home/adamz/Documents/stockfish/stockfish-ubuntu-x86-64-avx2";

    public GameController(Stage stage, Integer timerMinutes, Team vsAI, Integer difficulty) throws IOException {
        this.checkerboard = new Checkerboard();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game.fxml"));
        Parent root = loader.load();
        this.game = loader.getController();
        game.updateAllPieces(checkerboard);
        game.setGameController(this);

        this.gameData = new GameData(timerMinutes, vsAI, difficulty);

        game.setInfo(vsAI, difficulty, timerMinutes);
        game.updateTimer(Team.WHITE, gameData.getWhiteTimerLeftString());
        game.updateTimer(Team.BLACK, gameData.getBlackTimerLeftString());

        this.currentPlayer = Team.WHITE;
        this.currentTurnStart = LocalDateTime.now();
        this.currentPieceMoveset = null;
        this.isIllegal = false;

        if(vsAI != null)
            this.engine = new Engine(difficulty, enginePath);


        if(vsAI == Team.WHITE)
            aiMove();

        stage.getScene().setRoot(root);
        stage.show();

        timeLeft = currentPlayer == Team.WHITE ? gameData.getWhiteTimerLeft() : gameData.getBlackTimerLeft();
        startTimer();
    }

    public GameController(Stage stage, GameData gameData) throws IOException {
        this.checkerboard = new Checkerboard();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game.fxml"));
        Parent root = loader.load();
        this.game = loader.getController();
        game.updateAllPieces(checkerboard);
        game.setGameController(this);

        this.gameData = gameData;
        loadGame();

        game.setInfo(gameData.getVsAI(), gameData.getAiDifficulty(), gameData.getTimerMinutes());
        game.updateTimer(Team.WHITE, gameData.getWhiteTimerLeftString());
        game.updateTimer(Team.BLACK, gameData.getBlackTimerLeftString());

        this.currentTurnStart = LocalDateTime.now();
        this.currentPieceMoveset = null;
        this.isIllegal = false;

        if(gameData.getVsAI() != null)
            this.engine = new Engine(gameData.getAiDifficulty(), enginePath);

        stage.getScene().setRoot(root);
        stage.show();

        timeLeft = currentPlayer == Team.WHITE ? gameData.getWhiteTimerLeft() : gameData.getBlackTimerLeft();
        startTimer();
    }

    private void swapTeam(){
        currentPlayer = currentPlayer == Team.WHITE ? Team.BLACK : Team.WHITE;
    }

    private void saveMove(Move move){
        gameData.addMove(move);

        gameData.lowerTimer(currentPlayer, Duration.between(currentTurnStart, LocalDateTime.now()));
        if(currentPlayer == Team.WHITE)
            game.updateTimer(Team.WHITE, gameData.getWhiteTimerLeftString());
        if(currentPlayer == Team.BLACK)
            game.updateTimer(Team.BLACK, gameData.getBlackTimerLeftString());

        game.updateAllPieces(checkerboard);
        game.clearBoard();
        game.saveMove(move);
        swapTeam();
        game.setPlayer(currentPlayer);
        currentPieceMoveset = null;
        currentTurnStart = LocalDateTime.now();

        Team checkTeam = checkerboard.lookForCheck();
        game.modifyCheck(checkTeam);
        if(checkTeam != null){
            if(checkerboard.lookForCheckmate(checkTeam)){
                // TODO - co zrobic po wykryciu szach mat
            }
        }

        timeLeft = currentPlayer == Team.WHITE ? gameData.getWhiteTimerLeft() : gameData.getBlackTimerLeft();
    }

    private void aiMove() {
        Move move = engine.calculateMove(gameData.getMoves());

        checkerboard.move(move);
        saveMove(move);
    }

    public boolean pickUp(Position position){
        if(!isIllegal && currentPlayer == checkerboard.getPieceTeam(position)){
            currentPieceMoveset = checkerboard.possibleMoves(position);
            game.showMoveset(currentPieceMoveset);
            return true;
        } else {
            enableAlarm();
            return false;
        }
    }

    public boolean place(Position destination) {
        if(!isIllegal){
//            podniesienie gdy legalne
            if(currentPieceMoveset.getMovableList().contains(destination) || currentPieceMoveset.getBeatableList().contains(destination)){
//                legalny ruch
                Move move = new Move(currentPieceMoveset.getCurrentPosition(), destination);

                checkerboard.move(move);
                saveMove(move);

                if (gameData.getVsAI() == currentPlayer) {
                    aiMove();
                }

                return true;
            } else if (currentPieceMoveset.getCurrentPosition().equals(destination)) {
//                odłożenie figury
                game.clearBoard();
                currentPieceMoveset = null;

                return true;
            } else {
//                nielegalny ruch
                enableAlarm();
                return false;
            }
        }
        return false;
    }

    public void enableAlarm(){
        isIllegal = true;
        game.setAlarmVisibility(true);
        game.clearBoard();
        currentPieceMoveset = null;
    }

    public void disableAlarm(){
        isIllegal = false;
        game.setAlarmVisibility(false);
        game.clearBoard();
        currentPieceMoveset = null;
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

    public void startTimer() {
        scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> {
            timeLeft = timeLeft.minusSeconds(1);
            game.updateTimer(currentPlayer, timeLeft.toMinutesPart() + String.format(":%02d", timeLeft.toSecondsPart()));
            if (timeLeft.isNegative()) {
                stopTimer();
                // TODO - CO ZROBIC JAK SKONCZY SIE CZAS
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    public void stopTimer() {
        scheduler.shutdown();
    }
}
