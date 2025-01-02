package chess.chessjavafx.game;

import chess.chessjavafx.pieces.Piece;

import java.io.*;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GameData {
    private final List<Move> moves;
    private final DateTimeFormatter formatter;
    private LocalDateTime startTime;
    private Duration duration;
    private Winner winner;
    private Piece.Team vsAI;

    public GameData(Piece.Team vsAI) {
        this.moves = new ArrayList<>();
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.startTime = LocalDateTime.now();
        this.duration = null;
        this.winner = null;
        this.vsAI = vsAI;
    }

    public GameData(Path file) {
        this.moves = new ArrayList<>();
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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

    public Piece.Team getVsAI() {
        return vsAI;
    }

    public void addMove(Move move){
        moves.add(move);
    }

    public void save(){
        String filename = startTime.format(formatter) + ".txt";

        try(FileWriter fw = new FileWriter("data/" + filename);
            BufferedWriter bw = new BufferedWriter(fw)){

            bw.write(vsAI + "\n");
            bw.write(winner + "\n");
            bw.write(duration + "\n");

            for (Move move : moves) {
                bw.write(move.toString() + "\n");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void load(Path file){
        try(BufferedReader br = new BufferedReader(new FileReader(String.valueOf(file)))){
            String fileName = file.getFileName().toString();
            String dateString = fileName.substring(0, fileName.length()-4);
            startTime = LocalDateTime.parse(dateString, formatter);

            String line = br.readLine();
            vsAI = "null".equals(line) ? null : Piece.Team.valueOf(line);
            line = br.readLine();
            winner = "null".equals(line) ? null : Winner.valueOf(line);
            line = br.readLine();
            duration = Duration.parse(line);

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
