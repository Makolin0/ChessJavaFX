package chess.chessjavafx.game;

import chess.chessjavafx.Winner;
import chess.chessjavafx.Team;

import java.io.*;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GameData {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm");

    private BufferedWriter bufferedWriter;

    private final List<Move> moves;
    private LocalDateTime startTime;
    private Duration gameDuration;
    private Winner winner;

    private Team vsAI;
    private Integer aiDifficulty;

    // null means infinite time
    private Integer timerMinutes;
    private Duration blackTimerLeft;
    private Duration whiteTimerLeft;


    public GameData(Integer timerMinutes, Team vsAI, Integer aiDifficulty) {
        this.moves = new ArrayList<>();
        this.startTime = LocalDateTime.now();

        this.gameDuration = Duration.ZERO;
        this.winner = null;
        this.vsAI = vsAI;
        this.aiDifficulty = aiDifficulty;

        this.timerMinutes = timerMinutes;
        this.whiteTimerLeft = timerMinutes == null ? null : Duration.ofMinutes(timerMinutes);
        this.blackTimerLeft = timerMinutes == null ? null : Duration.ofMinutes(timerMinutes);

        save();
    }

    public GameData(Path file) {
        this.moves = new ArrayList<>();

        load(file);
    }


    public Winner getWinner() {
        return winner;
    }

    public void setWinner(Winner winner) {
        this.winner = winner;
    }

    public Duration getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(Duration gameDuration) {
        this.gameDuration = gameDuration;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public String getStartTimeString() {
        return startTime.format(formatter);
    }

    public String getDurationString() {
        if(gameDuration == null) {
            return "";
        }
        if(gameDuration.toHours() >= 1){
            return gameDuration.toHoursPart() + String.format(":%02d", gameDuration.toMinutesPart()) + String.format(":%02d", gameDuration.toSecondsPart());
        }
        return gameDuration.toMinutesPart() + String.format(":%02d", gameDuration.toSecondsPart());
    }

    public Team getVsAI() {
        return vsAI;
    }

    public Integer getAiDifficulty() {
        return aiDifficulty;
    }

    public Integer getTimerMinutes() {
        return timerMinutes;
    }

    public String getTimerMinutesString() {
        return timerMinutes == null ? "inf." : timerMinutes.toString();
    }

    public Duration getBlackTimerLeft() {
        return blackTimerLeft;
    }

    public Duration getWhiteTimerLeft() {
        return whiteTimerLeft;
    }
    public String getWhiteTimerLeftString() {
        return whiteTimerLeft == null ? "inf." : whiteTimerLeft.toMinutesPart() + String.format(":%02d", whiteTimerLeft.toSecondsPart());
    }

    public String getBlackTimerLeftString() {
        return blackTimerLeft == null ? "inf." : blackTimerLeft.toMinutesPart() + String.format(":%02d", blackTimerLeft.toSecondsPart());
    }


    public void addMove(Move move){
        moves.add(move);

        try {
            bufferedWriter.write(move.toString() + "\n");
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void lowerTimer(Team team, Duration duration) {
        if(team == Team.WHITE){
            whiteTimerLeft = whiteTimerLeft.minus(duration);
        }
        if(team == Team.BLACK){
            blackTimerLeft = blackTimerLeft.minus(duration);
        }

        gameDuration = gameDuration.plus(duration);
        save();
    }

    public void save(){
        String filename = startTime.format(formatter) + ".txt";
        System.out.println(filename);
        try{
            bufferedWriter = new BufferedWriter(new FileWriter("data/" + filename));

            bufferedWriter.write(vsAI + "\n");
            bufferedWriter.write(aiDifficulty + "\n");
            bufferedWriter.write(timerMinutes + "\n");
            bufferedWriter.write(whiteTimerLeft + "\n");
            bufferedWriter.write(blackTimerLeft + "\n");
            bufferedWriter.write(winner + "\n");
            bufferedWriter.write(gameDuration + "\n");

            for (Move move : moves) {
                bufferedWriter.write(move.toString() + "\n");
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void load(Path file){
        // opens writer and reader to file
        try(BufferedReader br = new BufferedReader(new FileReader(String.valueOf(file)))){
            bufferedWriter = new BufferedWriter(new FileWriter(String.valueOf(file), true));

            // loads start time from filename
            String fileName = file.getFileName().toString();
            String dateString = fileName.substring(0, fileName.length()-4);
            startTime = LocalDateTime.parse(dateString, formatter);

            // loads game parameters from 7 first lines
            String line = br.readLine();
            vsAI = "null".equals(line) ? null : Team.valueOf(line);
            line = br.readLine();
            aiDifficulty = "null".equals(line) ? null : Integer.parseInt(line);
            line = br.readLine();
            timerMinutes = "null".equals(line) ? null : Integer.parseInt(line);
            line = br.readLine();
            whiteTimerLeft = "null".equals(line) ? null : Duration.parse(line);
            line = br.readLine();
            blackTimerLeft = "null".equals(line) ? null : Duration.parse(line);
            line = br.readLine();
            winner = "null".equals(line) ? null : Winner.valueOf(line);
            line = br.readLine();
            gameDuration = "null".equals(line) ? null : Duration.parse(line);

            // all next lines are moves from start to end
            String moveString;
            moves.clear();
            while ((moveString = br.readLine()) != null){
                moves.add(new Move(moveString));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
