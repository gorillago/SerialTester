import jssc.SerialPort;
import jssc.SerialPortException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Herzog Digital Card Tester Program 9000");
        System.out.println("Please insert card into tester and power on.");
        SerialPort serialPort = new SerialPort(args[0]);
        try {
            serialPort.openPort();
            serialPort.addEventListener(new Listener(serialPort));
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }
}
