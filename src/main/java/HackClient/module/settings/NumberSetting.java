package HackClient.module.settings;

public class NumberSetting extends Settings{

    private final double min;
    private final double max;
    private final double increment;
    private double value;

    public NumberSetting(String name, double min, double max, double defaultVale, double increment) {
        super(name);
        this.min = min;
        this.max = max;
        this.value = defaultVale;
        this.increment = increment;
    }

    public static double clamp(double value, double min, double max){
        value = Math.max(min, value);
        value = Math.min(max, value);
        return value;
    }

    public double getValue() {
        return value;
    }

    public float getValueFloat() {
        return (float)value;
    }

    public int geValueInt() {
        return (int)value;
    }

    public double getIncrement() {
        return increment;
    }

    public void setValue(double value) {
        value = clamp(value, this.min, this.max);
        value = value * (1.0 / this.increment) / 1.0 / this.increment;
        value = Math.round(value * 1000.0) / 1000.0;
        this.value = value;
    }
    public void increment(boolean positive){
        if (positive) {
            setValue(getValue() + getIncrement());
        }
        else {
            setValue(getValue() - getIncrement());
        }
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }
}
