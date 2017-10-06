import brainboxes.io.device.EDDevice;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Listener implements SerialPortEventListener {
    Scanner scanner = new Scanner(System.in);
    SerialPort serialPort = null;
    EDDevice edDevice = null;
    public Listener(SerialPort serialPort, EDDevice edDevice) {
        this.serialPort = serialPort;
        this.edDevice = edDevice;
    }

    List<CompleteTest> currentTests = new ArrayList<>();
    int failedTests = 0;
    int maxConsecutive = 0;
    int testNumber = 0;
    boolean testDone = false;

    String currentTest = "";
    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        if (serialPortEvent.isRXCHAR()) {
            try {
                serialPort.setParams(SerialPort.BAUDRATE_57600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                byte[] buffer = serialPort.readBytes(serialPort.getInputBufferBytesCount());
                String str = new String(buffer);
                if (str.contains("DM1 Tester=V3")) {
                    currentTest = "";
                    testDone = false;
                }
                if (!testDone) {
                    currentTest += str;
                    if (str.contains("Test: ")) {
                        printCurrent();
                    }
                }

            }catch (SerialPortException e) {
                e.printStackTrace();
            }
        }

    }

    public void printCurrent() {
        testNumber++;
        CompleteTest completeTest = new CompleteTest(currentTest);
        currentTest = "";
        boolean currentPassed = completeTest.testPassed();

        if (completeTest.firstTest) {
            System.out.println("__________New Card__________");
            currentTests.clear();
            failedTests = 0;
            maxConsecutive = 0;
            testNumber = 1;
        }

        currentTests.add(completeTest);


        if (currentPassed) {
            if (currentTests.size() > maxConsecutive) {
                maxConsecutive = currentTests.size();
            }
            System.out.println("Test " + testNumber + " passed. " + currentTests.size() +
                    " consecutive passed tests (Max is " + maxConsecutive + "). Failed " + failedTests + " time(s).");
            if (maxConsecutive >= 10) {
                testDone = true;
                System.out.println("Completed 10 consecutive tests successfully. Card is considered good.  Plaese power off tester and insert new card.");
                edDevice.SendCommand("#010002");
                System.out.println("PASSED");
                System.out.println("Press ENTER to start new test.");
                scanner.next();
                edDevice.SendCommand("#010001");
            }
        } else {
            System.out.println(completeTest.testText);
            System.out.println("Test " + testNumber + " failed. Trying to get 10 consecutive tests.");
            currentTests.clear();
            failedTests++;
            if (failedTests > 1 && maxConsecutive < 10) {
                testDone = true;
                System.out.println("Failed to complete 10 consecutive tests twice. Card considered failed. Plaese power off tester and insert new card.");
                edDevice.SendCommand("#010004");
                System.out.println("FAILED");
                System.out.println("Press ENTER to start new test.");
                scanner.next();
                edDevice.SendCommand("#010001");
            }
        }

    }

}
