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
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private BufferedWriter bufferedWriter;

    private final List<Move> moves;
    private LocalDateTime startTime;
    private Duration duration;
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

        this.duration = null;
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

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Team getVsAI() {
        return vsAI;
    }

    public Integer getAiDifficulty() {
        return aiDifficulty;
    }

    public Duration getBlackTimerLeft() {
        return blackTimerLeft;
    }

    public Duration getWhiteTimerLeft() {
        return whiteTimerLeft;
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
    }

    public void save(){
        String filename = startTime.format(formatter) + ".txt";

        try{
            bufferedWriter = new BufferedWriter(new FileWriter("data/" + filename));

            bufferedWriter.write(vsAI + "\n");
            bufferedWriter.write(aiDifficulty + "\n");
            bufferedWriter.write(timerMinutes + "\n");
            bufferedWriter.write(whiteTimerLeft + "\n");
            bufferedWriter.write(blackTimerLeft + "\n");
            bufferedWriter.write(winner + "\n");
            bufferedWriter.write(duration + "\n");

            for (Move move : moves) {
                bufferedWriter.write(move.toString() + "\n");
            }

            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void load(Path file){
        try(BufferedReader br = new BufferedReader(new FileReader(String.valueOf(file)))){
            bufferedWriter = new BufferedWriter(new FileWriter(String.valueOf(file), true));

            String fileName = file.getFileName().toString();
            String dateString = fileName.substring(0, fileName.length()-4);
            startTime = LocalDateTime.parse(dateString, formatter);

            String line = br.readLine();
            System.out.println(line);
            vsAI = "null".equals(line) ? null : Team.valueOf(line);

            line = br.readLine();
            System.out.println(line);
            aiDifficulty = "null".equals(line) ? null : Integer.parseInt(line);

            line = br.readLine();
            System.out.println(line);
            timerMinutes = "null".equals(line) ? null : Integer.parseInt(line);

            line = br.readLine();
            System.out.println(line);
            whiteTimerLeft = "null".equals(line) ? null : Duration.parse(line);

            line = br.readLine();
            System.out.println(line);
            blackTimerLeft = "null".equals(line) ? null : Duration.parse(line);

            line = br.readLine();
            System.out.println(line);
            winner = "null".equals(line) ? null : Winner.valueOf(line);

            line = br.readLine();
            System.out.println(line);
            duration = "null".equals(line) ? null : Duration.parse(line);

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
