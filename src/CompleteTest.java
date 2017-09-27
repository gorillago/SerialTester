public class CompleteTest {
    public String testText;
    public String modelNumber;
    public AnalogMeasurements initAnalog, finalAnalog;
    public boolean initAnalogPassed, initdigitalPassed, initStartPassed,
            finalAnalogPassed, fifoTestPassed, sgtpPassed, etpPassed,
            utpPassed, finalDigitalPassed;
    public boolean firstTest = false;

    public CompleteTest(String input) {
        testText = input;
        String[] lines = input.split("\n");


        for (String line : lines) {
            if (line.startsWith("DM1 Tester=V3")) {
                firstTest = true;
            }else if (line.startsWith("ModelNumber ")) {
                modelNumber = line.substring(12);
            } else if (line.startsWith("Initial Analog Measurements...")) {
                initAnalog = new AnalogMeasurements(line);
                initAnalogPassed = line.contains("Passed");
            } else if (line.startsWith("Initial Digital Measurements")) {
                initdigitalPassed = line.contains("Passed");
            } else if (line.startsWith("Initialization Start")) {
                initStartPassed = line.contains("Passed");
            } else if (line.startsWith("FIFO Test")) {
                fifoTestPassed = line.contains("Passed");
            } else if (line.startsWith("Self Generated Test Pattern")) {
                sgtpPassed = line.contains("Passed");
            } else if (line.startsWith("External Test Pattern")) {
                etpPassed = line.contains("Passed");
            } else if (line.startsWith("Unloaded Test Pattern")) {
                utpPassed = line.contains("Passed");
            } else if (line.startsWith("Final Analog Measurements")) {
                finalAnalog = new AnalogMeasurements(line);
                finalAnalogPassed = line.contains("Passed");
            } else if (line.startsWith("Final Digital Measurements")) {
                finalDigitalPassed = line.contains("Passed");
            }
        }
    }

    public boolean testPassed() {
        boolean passed = (initAnalogPassed && initdigitalPassed && initStartPassed && finalAnalogPassed &&
                fifoTestPassed && sgtpPassed && etpPassed && utpPassed && finalDigitalPassed);
        return passed;
    }
}
