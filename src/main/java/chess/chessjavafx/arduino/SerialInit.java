package chess.chessjavafx.arduino;

import chess.chessjavafx.game.GameController;
import chess.chessjavafx.pieces.Piece;
import com.fazecast.jSerialComm.SerialPort;

import java.util.Map;
import java.util.Timer;

public class SerialInit {
    private final GameController gameController;

    public SerialInit(GameController gameController, String portName, Map<Integer, Piece> board) {
        this.gameController = gameController;
        initiate(portName, board);
    }

    public void initiate(String portName, Map<Integer, Piece> board) {
        var sp = SerialPort.getCommPort(portName);
        System.out.println("sp " + sp);
        sp.setComPortParameters(9600, Byte.SIZE, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);

        var hasOpened = sp.openPort();
        System.out.println("opened " + hasOpened);

        if(!hasOpened){
            throw new IllegalStateException("Failed to open serial port");
        }

        Runtime.getRuntime().addShutdownHook(new Thread(sp::closePort));

        var timer = new Timer();
        var timedSchedule = new ArduinoController(gameController, board);

        sp.addDataListener(timedSchedule);

        System.out.println("Listen: " + timedSchedule.getListeningEvents());
        timer.schedule(timedSchedule, 0, 1000);
    }
}
