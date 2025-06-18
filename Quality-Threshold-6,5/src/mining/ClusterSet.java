package mining;

import data.Data;

import java.io.*;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class ClusterSet implements Iterable<Cluster>, Serializable {

    private static final long serialVersionUID = 1L;
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
        int i = 1;
        for (Cluster cluster : C) {
            str.append(i++).append(":").append(cluster.toString(data)).append("\n");
        }
        return str.toString();
    }

    @Override
    public Iterator<Cluster> iterator() {
        return C.iterator();
    }

    // Metodo per salvare su file
    public void saveToFile(String fileName) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(this);
        }
    }

    // Metodo per caricare da file
    public static ClusterSet loadFromFile(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (ClusterSet) in.readObject();
        }
    }
}




