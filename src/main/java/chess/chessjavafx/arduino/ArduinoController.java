package chess.chessjavafx.arduino;

import chess.chessjavafx.game.GameController;
import chess.chessjavafx.game.Position;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.util.TimerTask;

public class ArduinoController extends TimerTask implements SerialPortDataListener {
    private final GameController gameController;
    private String board;
    private boolean alarm;

    public ArduinoController(GameController gameController) {
        this.gameController = gameController;
        this.board = "";
        this.alarm = false;
    }

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        if(serialPortEvent.getEventType() == SerialPort.LISTENING_EVENT_DATA_RECEIVED){
            byte[] receivedData = serialPortEvent.getReceivedData();
            System.out.println("Received: " + new String(receivedData));
//            gameController.sendPosition(new Position(1, 1));
        }
    }

    @Override
    public void run() {
        // System.out.println("Time elapsed: " +(System.currentTimeMillis() - this.timeStart) + " miliseconds ");
    }

    public void setBoard(String board) {
        if(!alarm){
            loadBoard(board);
        } else {
            if (this.board.equals(board)) {
                alarm = false;
                gameController.disableAlarm();
            }
        }
    }

    private void loadBoard(String bytes) {
        if(bytes.length() != 64){
            throw new IllegalArgumentException("bytes length must be 64");
        }

        if(bytes.equals(board)) {
            return;
        }

        boolean positive = true;
        for(int i = 0; i < 64; i++) {
            if(board.charAt(i) != bytes.charAt(i)) {
                if(bytes.charAt(i) == '0'){
                    positive = gameController.pickUp(new Position(i)) && positive;
                } else {
                    positive = gameController.place(new Position(i)) && positive;
                }
            }
        }

        if(positive) {
            board = bytes;
        } else {
            alarm = true;
            gameController.enableAlarm();
        }
    }

}
