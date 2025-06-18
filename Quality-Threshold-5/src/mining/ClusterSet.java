package mining;

import data.Data;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class ClusterSet implements Iterable<Cluster> {

    private Set<Cluster> C;

    public ClusterSet() {
        // Initialize an empty cluster set
        C = new TreeSet<>();
    }

    public void add(Cluster c){
        C.add(c);
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        int i = 0;
        for (Cluster cluster : C) {
            str.append("Cluster ").append(i++).append(": ").append(cluster.toString()).append("\n");
        }
        return str.toString();
    }

    public String toString(Data data) {
        StringBuilder str = new StringBuilder();
        int i = 0;
        for (Cluster cluster : C) {
            str.append(i++).append(":").append(cluster.toString(data)).append("\n");
        }
        return str.toString();
    }

    @Override
    public Iterator<Cluster> iterator() {
        return C.iterator();
    }
}




