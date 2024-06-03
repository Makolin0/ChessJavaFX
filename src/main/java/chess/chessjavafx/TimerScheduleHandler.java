package chess.chessjavafx;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.util.TimerTask;

public class TimerScheduleHandler extends TimerTask implements SerialPortDataListener {
    private final long timeStart;
    public TimerScheduleHandler(long timeStart) {
        this.timeStart = timeStart;
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
            System.out.println(serialPortEvent.getSerialPort());
            System.out.println("Arduino is alive");
        }
    }
}
