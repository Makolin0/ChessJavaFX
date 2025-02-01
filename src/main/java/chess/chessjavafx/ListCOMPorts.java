package chess.chessjavafx;

import jssc.SerialPort;
import jssc.SerialPortList;

public class ListCOMPorts {
    public static void main(String[] args) {

        for (String port : SerialPortList.getPortNames()) {
            String lowerPort = port.toLowerCase();
            if (lowerPort.contains("usb") || lowerPort.contains("arduino") ||
                    lowerPort.contains("ttyacm") || lowerPort.contains("ttyusb")) {
                System.out.println("Possible Arduino found on: " + port);
            } else {
                System.out.println("Other COM port: " + port);
            }
        }
    }
}
