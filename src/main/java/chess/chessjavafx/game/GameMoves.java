package chess.chessjavafx.game;

import java.io.*;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GameMoves {
    private final List<Move> moves;
    private final DateTimeFormatter formatter;
    private LocalDateTime startTime;
    private Duration duration;
    private Winner winner;

    public GameMoves() {
        this.moves = new ArrayList<>();
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.startTime = LocalDateTime.now();
        this.duration = null;
        this.winner = Winner.DRAW;
    }

    public GameMoves(Path file) {
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

    public void addMove(Move move){
        moves.add(move);
    }

    public void save(){
        String filename = startTime.format(formatter) + ".txt";

        try(FileWriter fw = new FileWriter("data/" + filename);
            BufferedWriter bw = new BufferedWriter(fw)){

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
            moves.clear();
            winner = Winner.valueOf(br.readLine());
            duration = Duration.parse(br.readLine());
            startTime = LocalDateTime.parse(dateString, formatter);

            String moveString;
            while ((moveString = br.readLine()) != null){
                moves.add(new Move(moveString));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
