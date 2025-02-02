package chess.chessjavafx.game;

import java.io.*;
import java.util.List;

public class Engine {
    private BufferedReader reader;
    private Writer writer;

    // difficulty from 0 to 20
    public Engine(int difficulty) {
        try{
            ProcessBuilder processBuilder = new ProcessBuilder("./stockfish.exe");
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            writer = new OutputStreamWriter(process.getOutputStream());

            writer.write("uci\n");
            writer.flush();

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("uciok")) {
                    break;
                }
            }

            writer.write("setoption name Skill Level value " + difficulty + "\n");

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(1);
        }
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
                    return new Move(bestMove);
                }
            }
        } catch (IOException ignored) {
        }

        System.out.println("Error while communicating with stockfish");
        System.exit(1);
        return null;
    }

}
