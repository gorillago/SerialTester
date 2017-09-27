import jssc.SerialPort;
import jssc.SerialPortException;

public class Main {
    public static void main(String[] args) {
        SerialPort serialPort = new SerialPort(args[0]);
        try {
            serialPort.openPort();
            serialPort.addEventListener(new Listener(serialPort));
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }
}
