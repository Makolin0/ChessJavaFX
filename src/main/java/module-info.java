module chess.chessjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens chess.chessjavafx to javafx.fxml;
    exports chess.chessjavafx;
    exports chess.chessjavafx.tests;
    opens chess.chessjavafx.tests to javafx.fxml;
}