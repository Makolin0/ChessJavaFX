package chess.chessjavafx.game;

import java.io.*;
import java.util.List;

public class Engine {
    private final Process process;
    private final BufferedReader reader;
    private final Writer writer;

    public Engine() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("/home/adamz/Documents/stockfish/stockfish-ubuntu-x86-64-avx2");
        processBuilder.redirectErrorStream(true);
        process = processBuilder.start();
        reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        writer = new OutputStreamWriter(process.getOutputStream());
    }

    public void initialize(List<Move> moves) throws IOException {
        writer.write("uci\n");
        writer.flush();

        String line;
        while ((line = reader.readLine()) != null) {

            if (line.equals("uciok")) {
                break;
            }
        }

        StringBuilder startPos = new StringBuilder("position startpos");
        if(moves != null && !moves.isEmpty()) {
            startPos.append(" moves");
            for (Move move : moves) {
                startPos.append(" ").append(move.toString());
            }
        }

        writer.write(startPos + "\n");
        writer.flush();
    }

    public Move calculateMove() throws IOException {
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
        System.exit(1);
        return null;
    }


    public static void main(String[] args) throws IOException {
        Engine engine = new Engine();

        engine.initialize(null);
        engine.calculateMove();
    }

}
