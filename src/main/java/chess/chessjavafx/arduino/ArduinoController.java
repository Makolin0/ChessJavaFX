package chess.chessjavafx.arduino;

import chess.chessjavafx.game.GameController;
import chess.chessjavafx.game.Position;
import chess.chessjavafx.pieces.Piece;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

public class ArduinoController extends TimerTask implements SerialPortDataListener {
    private final GameController gameController;
    private boolean alarm;
    private String prevLegalBoard;
    private String prevBoard;
    private String newBoard;

    public ArduinoController(GameController gameController, Map<Integer, Piece> board) {
        char[] temp = "0000000000000000000000000000000000000000000000000000000000000000".toCharArray();
        for(Map.Entry<Integer, Piece> entry : board.entrySet()) {
            temp[(entry.getKey()%8)*8 + (entry.getKey()/8)] = '1';
        }
        this.prevBoard = new String(temp);

        this.gameController = gameController;
        this.prevLegalBoard = prevBoard;
        this.newBoard = "";
        enableAlarm();
    }

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        if (serialPortEvent.getEventType() == SerialPort.LISTENING_EVENT_DATA_RECEIVED) {
            String receivedFragment = new String(serialPortEvent.getReceivedData());

            // new message
            if (receivedFragment.startsWith("s")) {
                newBoard = receivedFragment.substring(1);
                return;
            }
            // final part of message
            if (receivedFragment.endsWith("e")) {
                newBoard = newBoard.concat(receivedFragment.substring(0, receivedFragment.length() - 1));
                newBoard = swapToBinary(newBoard);
                if (!alarm) {
                    loadBoard();
                } else {
                    restoreBoard();
                }
                return;
            }
            newBoard = newBoard.concat(receivedFragment);
        }
    }

    @Override
    public void run() {
        // System.out.println("Time elapsed: " +(System.currentTimeMillis() - this.timeStart) + " miliseconds ");
    }

    public void restoreBoard() {
        if (newBoard.equals(prevLegalBoard)) {
            prevBoard = prevLegalBoard;
            disableAlarm();
        } else {
            List<Position> wrongList = new ArrayList<>();
            for (int i = 0; i < 64; i++) {
                if (newBoard.charAt(i) != prevLegalBoard.charAt(i)) {
                    wrongList.add(new Position(i/8, i%8));
                }
            }
            gameController.colorWrongFields(wrongList);
        }
    }

    private void loadBoard() {
        if (prevBoard.equals(newBoard)) {
            return;
        }

        boolean noAlarm = true;
        for (int i = 0; i < 64; i++) {
            if (newBoard.charAt(i) != prevBoard.charAt(i)) {
                if (newBoard.charAt(i) == '0') {
                    noAlarm = gameController.pickUp(new Position(i / 8, i%8));
                    if(noAlarm) {
                        prevBoard = newBoard;
                    } else {
                        enableAlarm();
                        restoreBoard();
                    }
                    System.out.println("pick up: " + new Position(i / 8, i % 8));
                } else {
                    noAlarm = gameController.place(new Position(i / 8, i%8));
                    if(noAlarm) {
                        prevBoard = newBoard;
                        prevLegalBoard = newBoard;
                    } else {
                        enableAlarm();
                        restoreBoard();
                    }
                    System.out.println("place: " + new Position(i / 8, i % 8));
                }
                break;
            }
        }
    }

    private String swapToBinary(String board) {
        String[] slicedMessage = board.split(" ");
        slicedMessage[0] = String.format("%32s", Long.toBinaryString(Long.parseLong(slicedMessage[0]))).replace(' ', '0');
        slicedMessage[1] = String.format("%32s", Long.toBinaryString(Long.parseLong(slicedMessage[1]))).replace(' ', '0');
        return slicedMessage[0].concat(slicedMessage[1]);
    }

    private void enableAlarm() {
        System.out.println("ALARM ENABLED");
        alarm = true;
        gameController.enableAlarm();
    }
    private void disableAlarm() {
        System.out.println("ALARM DISABLED");
        alarm = false;
        gameController.disableAlarm();
    }
}
