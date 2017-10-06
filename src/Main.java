import brainboxes.io.connection.IConnection;
import brainboxes.io.connection.TCPConnection;
import brainboxes.io.device.EDDevice;
import brainboxes.io.protocol.ASCIIProtocol;
import jssc.SerialPort;
import jssc.SerialPortException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Herzog Digital Card Tester Program 9000");
        System.out.println("Please insert card into tester and power on.");
        if (args.length < 2) {
            System.out.println("Invalid args. Program requires COM port and IP Address");
            System.exit(1);
        }
        SerialPort serialPort = new SerialPort(args[0]);
        EDDevice edDevice = connectToBrainbbox(args[1]);
        System.out.println("Press ENTER to continue.");
        Scanner scanner = new Scanner(System.in);
        scanner.next();
        edDevice.SendCommand("#010001");
        try {
            serialPort.openPort();
            serialPort.addEventListener(new Listener(serialPort, edDevice));
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }
    public static EDDevice connectToBrainbbox(String ip) {
        IConnection tcpConnection = new TCPConnection(ip, 9500);
        EDDevice edDevice = new EDDevice(tcpConnection, new ASCIIProtocol());
        edDevice.Connect();
        if (edDevice.IsConnected()) {
            edDevice.SendCommand("#010000");
        } else {
            System.out.println("Connection to Brainbox failed.");
            System.exit(1);
        }
        return edDevice;
    }
}
