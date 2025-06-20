package QTServer.data;

public class ContinuousItem extends QTServer.data.Item {


    public ContinuousItem(Attribute attribute, Double value) {
        super(attribute, value);
    }

    @Override
    public double distance(Object a) {
        if (!(a instanceof Double)) {
            throw new IllegalArgumentException("Il parametro deve essere di tipo Double.");
        }

        double thisScaled = ((ContinuousAttribute) getAttribute()).getScaledValue((Double) getValue());
        double otherScaled = ((ContinuousAttribute) getAttribute()).getScaledValue((Double) a);

        return Math.abs(thisScaled - otherScaled);
    }

}
