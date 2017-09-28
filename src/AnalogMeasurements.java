public class AnalogMeasurements {
    public double AC1, AC2, AC3, AC4, Vref;
    public boolean passed = false;
    public AnalogMeasurements(String input, boolean init) {
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
        if (init) {
            if (!passedInit()) {
                passed = false;
            }
        } else {
            if (!passedFinal()) {
                passed = false;
            }
        }

    }
    private double hexToVoltage(String hex) {
        return (double)(5*Integer.parseInt(hex, 16))/256.0;
    }
    public boolean passedFinal() {
        boolean result = !((AC1 > 0.91796875) || (AC2 > 0.91796875) || (AC3 > 0.04) || (AC4 > 0.04) || (Vref > 2.51953125));
        return result;
    }
    public boolean passedInit() {
        boolean result = !((AC1 > 0.04) || (AC2 > 0.04) || (AC3 > 0.04) || (AC4 > 0.04) || (Vref > 2.51953125));
        return result;
    }
}
