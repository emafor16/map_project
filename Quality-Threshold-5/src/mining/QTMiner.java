package mining;

import data.Data;
import data.Tuple;
import exceptions.ClusteringRadiusException;
import exceptions.EmptyDatasetException;


public class QTMiner {

    private ClusterSet C;
    private double radius;


    public QTMiner(double radius) {
        this.C = new ClusterSet();
        this.radius = radius;
    }


    public ClusterSet getC() {
        return C;
    }

    public int compute(Data data) throws ClusteringRadiusException, EmptyDatasetException {
        // Controllo se il dataset è vuoto
        if (data.getNumberOfExamples() == 0) {
            throw new EmptyDatasetException("Il dataset è vuoto, impossibile clusterizzare.");
        }

        int numclusters = 0;
        boolean isClustered[] = new boolean[data.getNumberOfExamples()];
        for (int i = 0; i < isClustered.length; i++) {
            isClustered[i] = false;
        }

        int countClastered = 0;
        while (countClastered != data.getNumberOfExamples()) {

            // Ricerca cluster più popoloso
            Cluster c = buildCandidateCluster(data, isClustered);
            C.add(c);
            numclusters++;

            // Rimuovo tuple clusterizzate da dataset
            for (int id : c) {
                isClustered[id] = true;
            }

            countClastered += c.getSize();
        }

        // Se è stato creato un solo cluster, lancio l'eccezione
        if (numclusters == 1) {
            throw new ClusteringRadiusException("Tutte le tuple sono in un unico cluster.");
        }

        return numclusters;
    }



    /*public Cluster buildCandidateCluster(Data data, boolean[] isClustered) {
        int numExamples = data.getNumberOfExamples();
        int numAttributes = data.getNumberOfExplanatoryAttributes();
        Cluster bestCluster = null;
        int maxSize = -1;

        for (int i = 0; i < numExamples; i++) {
            if (!isClustered[i]) {
                // Costruisci il centroide iniziale usando la tupla i-esima
                Tuple centroid = data.getItemSet(i);
                Cluster candidateCluster = new Cluster(centroid);

                for (int j = 0; j < numExamples; j++) {
                    if (!isClustered[j]) {
                        Tuple tuple = data.getItemSet(j);
                        // Aggiunge al cluster se è più vicino al centroide (qui si aggiungono tutte, anche il centroide stesso)
                        candidateCluster.addData(j); // salva solo l'indice, non la tupla
                    }
                }

                if (candidateCluster.getSize() > maxSize) {
                    bestCluster = candidateCluster;
                    maxSize = candidateCluster.getSize();
                }
            }
        }

        return bestCluster;
    }*/

    public Cluster buildCandidateCluster(Data data, boolean[] isClustered) {
        int numExamples = data.getNumberOfExamples();
        Cluster bestCluster = null;
        int maxSize = -1;

        for (int i = 0; i < numExamples; i++) {
            if (!isClustered[i]) {
                Tuple centroid = data.getItemSet(i);
                Cluster candidateCluster = new Cluster(centroid);

                for (int j = 0; j < numExamples; j++) {
                    if (!isClustered[j]) {
                        Tuple tuple = data.getItemSet(j);
                        double dist = centroid.getDistance(tuple);
                        if (dist <= radius) {
                            candidateCluster.addData(j);
                        }
                    }
                }

                if (candidateCluster.getSize() > maxSize) {
                    bestCluster = candidateCluster;
                    maxSize = candidateCluster.getSize();
                }
            }
        }

        return bestCluster;
    }

}
