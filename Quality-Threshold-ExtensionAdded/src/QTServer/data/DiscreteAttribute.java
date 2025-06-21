package QTServer.data;

import java.util.TreeSet;
import java.util.Iterator;

public class DiscreteAttribute extends Attribute implements Iterable<String> {

    private TreeSet<String> values;

    public DiscreteAttribute(String name, int index, String strVal[]) {
        super(name, index);
        values = new TreeSet<>();
        for (String value : strVal) {
            this.values.add(value);
        }
    }

    public int getNumberOfDistinctValues() {
        return values.size();
    }

    public Iterator<String> iterator() {
        return values.iterator();
    }


}
