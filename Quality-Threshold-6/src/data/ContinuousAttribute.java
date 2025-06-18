package data;

public class ContinuousAttribute  extends Attribute {

    private double max;
    private double min;

    public ContinuousAttribute(String name, int index, double minValue, double maxValue) {
        super(name, index);
        this.min = minValue;
        this.max = maxValue;
    }

    public double getScaledValue(double v) {
        if (v < min || v > max) {
            throw new IllegalArgumentException("Valore fuori dall'intervallo: " + v + " non è compreso tra " + min + " e " + max);
        }
        return (v - min) / (max - min);
    }
}
