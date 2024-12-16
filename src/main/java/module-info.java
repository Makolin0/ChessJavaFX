module chess.chessjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fazecast.jSerialComm;
    requires java.desktop;


    opens chess.chessjavafx to javafx.fxml;
    exports chess.chessjavafx;
    exports chess.chessjavafx.javaFX;
    opens chess.chessjavafx.javaFX to javafx.fxml;
    exports chess.chessjavafx.game;
    opens chess.chessjavafx.game to javafx.fxml;
    exports chess.chessjavafx.arduino;
    opens chess.chessjavafx.arduino to javafx.fxml;
    // exports chess.chessjavafx.tests;
    // opens chess.chessjavafx.tests to javafx.fxml;
}