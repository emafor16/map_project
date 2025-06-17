package data;

public class DiscreteAttribute extends Attribute {

    private String[] values;

    public DiscreteAttribute(String name, int index, String values[]) {
        super(name, index);
        this.values = values;
    }

    public int getNumberOfDistinctValues() {
        return values.length;
    }

    public String getValue(int i) {
        if (i >= 0 && i < values.length) {
            return values[i];
        } else {
            throw new IndexOutOfBoundsException("Indice fuori dal range del dominio discreto.");
        }
    }


}
