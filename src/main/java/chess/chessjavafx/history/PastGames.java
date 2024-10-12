package chess.chessjavafx.history;

import chess.chessjavafx.game.GameMoves;

import java.util.ArrayList;
import java.util.List;

public class PastGames {
    private List<GameMoves> gameList;

    public PastGames(){
        this.gameList = new ArrayList<>();
    }

    public void loadGames(){
        gameList.clear();


    }

}
