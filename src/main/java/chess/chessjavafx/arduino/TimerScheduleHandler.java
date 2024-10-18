package chess.chessjavafx.arduino;

import chess.chessjavafx.game.GameController;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.util.TimerTask;

public class TimerScheduleHandler extends TimerTask implements SerialPortDataListener {
    private final long timeStart;
    private GameController gameController;
    public TimerScheduleHandler(long timeStart, GameController gameController) {
        this.timeStart = timeStart;
        this.gameController = gameController;
    }

    @Override
    public void run() {
        // System.out.println("Time elapsed: " +(System.currentTimeMillis() - this.timeStart) + " miliseconds ");
    }

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent){
        if(serialPortEvent.getEventType() == SerialPort.LISTENING_EVENT_DATA_RECEIVED){
//            gameController.sendPosition(new Position(1, 1));
        }
    }
}
