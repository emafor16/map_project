package mining;

import data.Data;

public class ClusterSet {

    private Cluster C[]= new Cluster[0];

    public ClusterSet() {
        // Initialize an empty cluster set
        C = new Cluster[0];
    }

    public void add(Cluster c){
        Cluster tempC[]=new Cluster[C.length+1];
        for(int i=0;i<C.length;i++)
            tempC[i]=C[i];
        tempC[C.length]=c;
        C=tempC;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < C.length; i++) {
            str.append("Cluster ").append(i).append(": ").append(C[i].toString()).append("\n");
        }
        return str.toString();
    }

    public String toString(Data data) {
        String str="";
        for(int i=0;i<C.length;i++){
            if (C[i]!=null){
                str+=i+":"+C[i].toString(data)+"\n";
            }
        }
        return str;
    }
}

/*import data.Data;
import data.DiscreteAttribute;
import data.DiscreteItem;
import data.Tuple;
import mining.Cluster;

public class ClusterSet {

    private Cluster[] C;

    public ClusterSet() {
        C = new Cluster[0];
    }

    public void add(Cluster c) {
        Cluster[] tempC = new Cluster[C.length + 1];
        for (int i = 0; i < C.length; i++) {
            tempC[i] = C[i];
        }
        tempC[C.length] = c;
        C = tempC;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < C.length; i++) {
            str.append("Cluster ").append(i).append(": ").append(C[i].toString()).append("\n");
        }
        return str.toString();
    }

    public String toString(Data data) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < C.length; i++) {
            if (C[i] != null) {
                str.append(i).append(":").append(C[i].toString(data)).append("\n");
            }
        }
        return str.toString();
    }

    public void updateCentroids(Data data) {
        for (int i = 0; i < C.length; i++) {
            int[] clusterIndexes = C[i].iterator();
            Tuple newCentroid = computeCentroid(data, clusterIndexes);
            C[i] = new Cluster(newCentroid);  // sovrascrive il cluster con nuovo centroide
            for (int id : clusterIndexes) {
                C[i].addData(id);  // riaggiunge gli esempi precedenti
            }
        }
    }

    private Tuple computeCentroid(Data data, int[] clusterIndexes) {
        int numAttributes = data.getNumberOfExplanatoryAttributes();
        Tuple centroid = new Tuple(numAttributes);

        for (int j = 0; j < numAttributes; j++) {
            DiscreteAttribute attr = (DiscreteAttribute) data.getAttributeSchema()[j];
            String mostFrequentValue = null;
            int maxCount = -1;

            for (String value : attr.getValues()) {
                int count = 0;
                for (int i = 0; i < clusterIndexes.length; i++) {
                    if (value.equals(data.getValue(clusterIndexes[i], j))) {
                        count++;
                    }
                }
                if (count > maxCount) {
                    maxCount = count;
                    mostFrequentValue = value;
                }
            }

            centroid.add(new DiscreteItem(attr, mostFrequentValue), j);
        }

        return centroid;
    }


    public int getLength() {
        return C.length;
    }

    public Cluster get(int i) {
        return C[i];
    }
}*/

