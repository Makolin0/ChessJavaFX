module chess.chessjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fazecast.jSerialComm;


    opens chess.chessjavafx to javafx.fxml;
    exports chess.chessjavafx;
    // exports chess.chessjavafx.tests;
    // opens chess.chessjavafx.tests to javafx.fxml;
}