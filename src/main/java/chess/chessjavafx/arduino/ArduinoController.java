package chess.chessjavafx.arduino;

import chess.chessjavafx.game.GameController;
import chess.chessjavafx.game.Position;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.util.TimerTask;

public class ArduinoController extends TimerTask implements SerialPortDataListener {
    private final GameController gameController;
    private boolean alarm;
    private String prevBoard;
    private String newBoard;

    public ArduinoController(GameController gameController) {
        this.gameController = gameController;
        // TODO - ustaw startowe rozłożenie pionków
        this.prevBoard = "0000000000000000000000000000000000000000000000000000000000000000";
        this.newBoard = "";
        this.alarm = false;
    }

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        if(serialPortEvent.getEventType() == SerialPort.LISTENING_EVENT_DATA_RECEIVED){
            String receivedFragment = new String(serialPortEvent.getReceivedData());

            // new message
            if(receivedFragment.startsWith("s")) {
                newBoard = receivedFragment.substring(1);
                return;
            }
            // final part of message
            if(receivedFragment.endsWith("e")){
                newBoard = newBoard.concat(receivedFragment.substring(0,receivedFragment.length()-1));
//                System.out.println("message: " + newMessage);
                if(!alarm) {
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
        if (newBoard.equals(prevBoard)) {
            alarm = false;
            gameController.disableAlarm();
        }
    }

    private void loadBoard() {
        String[] slicedMessage = newBoard.split(" ");
        slicedMessage[0] = String.format("%32s", Long.toBinaryString(Long.parseLong(slicedMessage[0]))).replace(' ', '0');
        slicedMessage[1] = String.format("%32s", Long.toBinaryString(Long.parseLong(slicedMessage[1]))).replace(' ', '0');
        String newBoard = slicedMessage[0].concat(slicedMessage[1]);

//        System.out.println(newBoard);

        if(prevBoard.equals(newBoard)) {
            return;
        }

        boolean noAlarm = true;
        for(int i = 0; i < 64; i++) {
            if(newBoard.charAt(i) != prevBoard.charAt(i)) {
                if(newBoard.charAt(i) == '0'){
//                    noAlarm = gameController.pickUp(new Position(i));
                    System.out.println("pick up: " + new Position(i));
                } else {
//                    noAlarm = gameController.place(new Position(i));
                    System.out.println("place: " + new Position(i));
                }
                break;
            }
        }

        if(noAlarm) {
            prevBoard = newBoard;
        } else {
            enableAlarm();
        }
    }

    private void enableAlarm() {
        alarm = true;
        gameController.enableAlarm();
    }
}
