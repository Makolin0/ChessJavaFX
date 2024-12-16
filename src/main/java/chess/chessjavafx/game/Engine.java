package chess.chessjavafx.game;

import java.io.*;
import java.util.List;

public class Engine {
    private Process process;
    private BufferedReader reader;
    private Writer writer;

    public Engine() {
        try{
            ProcessBuilder processBuilder = new ProcessBuilder("/home/adamz/Documents/stockfish/stockfish-ubuntu-x86-64-avx2");
            processBuilder.redirectErrorStream(true);
            process = processBuilder.start();
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            writer = new OutputStreamWriter(process.getOutputStream());
        } catch (IOException e) {
            System.exit(1);
        }
    }

    public void initialize() {
        try {
            writer.write("uci\n");
            writer.flush();

            String line;
            while ((line = reader.readLine()) != null) {

                if (line.equals("uciok")) {
                    break;
                }
            }
        } catch (IOException ignored) {}
    }

    public Move calculateMove(List<Move> moves) {
        try {
            StringBuilder startPos = new StringBuilder("position startpos");
            if (moves != null && !moves.isEmpty()) {
                startPos.append(" moves");
                for (Move move : moves) {
                    startPos.append(" ").append(move.toString());
                }
            }

            writer.write(startPos + "\n");
            writer.flush();


            writer.write("go depth 10\n");
            writer.flush();

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("bestmove")) {
                    String bestMove = line.split(" ")[1];
                    System.out.println("Best move: " + bestMove);
                    return new Move(bestMove);
                }
            }
        } catch (IOException ignored) {
        }

        System.exit(1);
        return null;
    }

}
