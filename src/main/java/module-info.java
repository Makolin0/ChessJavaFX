module chess.chessjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fazecast.jSerialComm;
    requires java.desktop;


    opens chess.chessjavafx to javafx.fxml;
    opens chess.chessjavafx.javaFX to javafx.fxml;
    opens chess.chessjavafx.game to javafx.fxml;
    opens chess.chessjavafx.arduino to javafx.fxml;
    opens chess.chessjavafx.packages to javafx.fxml;
    opens chess.chessjavafx.pieces to javafx.fxml;

    exports chess.chessjavafx;
    exports chess.chessjavafx.arduino;
    exports chess.chessjavafx.game;
    exports chess.chessjavafx.javaFX;
    exports chess.chessjavafx.packages;
    exports chess.chessjavafx.pieces;
}