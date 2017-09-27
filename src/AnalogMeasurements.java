public class AnalogMeasurements {
    public double AC1, AC2, AC3, AC4, Vref;
    public boolean passed = false;
    public AnalogMeasurements(String input) {
        int index = input.indexOf('$')+1;
        AC1 = hexToVoltage(input.substring(index, index+2));
        index = input.indexOf('$', index)+1;
        AC2 = hexToVoltage(input.substring(index, index+2));
        index = input.indexOf('$', index)+1;
        AC3 = hexToVoltage(input.substring(index, index+2));
        index = input.indexOf('$', index)+1;
        AC4 = hexToVoltage(input.substring(index, index+2));
        index = input.indexOf('$', index)+1;
        Vref = hexToVoltage(input.substring(index, index+2));

        passed = input.contains("Passed");
    }
    private double hexToVoltage(String hex) {
        return (double)(5*Integer.parseInt(hex, 16))/256.0;
    }
}
