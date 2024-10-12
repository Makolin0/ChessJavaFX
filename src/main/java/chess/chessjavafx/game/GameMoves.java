package chess.chessjavafx.game;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GameMoves {
    private List<Move> moves;
    private LocalDateTime startTime;
    private Duration duration;
    private Winner winner;

    public GameMoves() {
        moves = new ArrayList<>();
        startTime = LocalDateTime.now();
        duration = null;
        winner = Winner.DRAW;
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String filename = startTime.format(formatter) + ".txt";

        try(FileWriter fw = new FileWriter(filename);
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

    public void load(String fileName){
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
            moves.clear();
            winner = Winner.valueOf(br.readLine());
            duration = Duration.parse(br.readLine());

            String moveString;
            while ((moveString = br.readLine()) != null){
                moves.add(new Move(moveString));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
