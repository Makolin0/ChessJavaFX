package chess.chessjavafx.arduino;

import chess.chessjavafx.game.GameController;
import chess.chessjavafx.game.Position;

public class ArduinoController {
    // porownoje stan aktualnej tablicy z poprzednim
    // po wykryciu zmiany wykonuje odpowiednie działanie (podniesienie / wykonanie ruchu)
    // obsługa alarmu. zmuszenie do powrotu do ostatniego legalnego stanu

    private final GameController gameController;
    private String board;

    public ArduinoController(GameController gameController) {
        this.gameController = gameController;
        board = "";
    }

    public void loadBoard(String bytes) {
        if(bytes.length() != 64){
            throw new IllegalArgumentException("bytes length must be 64");
        }

        if(bytes.equals(board)) {
            return;
        }

        for(int i = 0; i < 64; i++) {
            if(board.charAt(i) != bytes.charAt(i)) {
                if(bytes.charAt(i) == '0'){
                    gameController.pickUp(new Position(i));
                } else {
                    gameController.place(new Position(i));
                }
            }
        }
    }
}
