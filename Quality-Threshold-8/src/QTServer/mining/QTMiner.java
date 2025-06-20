package QTServer.mining;

import QTServer.database.EmptyDatasetException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;


public class QTMiner implements Serializable {

    private ClusterSet C;
    private double radius;


    // Apre il file identificato da fileName, legge l'oggetto ivi memorizzato e lo assegna a C
    public QTMiner(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
        try {
            C = ClusterSet.loadFromFile(fileName);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File non trovato: " + fileName);
        } catch (IOException e) {
            throw new IOException("Errore durante la lettura del file: " + fileName);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Classe non trovata durante il caricamento del file: " + fileName);
        }
    }

    public QTMiner(double radius) {
        this.C = new ClusterSet();
        this.radius = radius;
    }


    // Apre il file identificato da fileName e salva l'oggetto riferito da C in tale file
    public void salva(String fileName) throws FileNotFoundException, IOException {

        try {
            C.saveToFile(fileName);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File non trovato: " + fileName);
        } catch (IOException e) {
            throw new IOException("Errore durante la scrittura del file: " + fileName);
        }
    }


    public ClusterSet getC() {
        return C;
    }

    public int compute(QTServer.data.Data data) throws ClusteringRadiusException, EmptyDatasetException {
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

    public Cluster buildCandidateCluster(QTServer.data.Data data, boolean[] isClustered) {
        int numExamples = data.getNumberOfExamples();
        Cluster bestCluster = null;
        int maxSize = -1;

        for (int i = 0; i < numExamples; i++) {
            if (!isClustered[i]) {
                QTServer.data.Tuple centroid = data.getItemSet(i);
                Cluster candidateCluster = new Cluster(centroid);

                for (int j = 0; j < numExamples; j++) {
                    if (!isClustered[j]) {
                        QTServer.data.Tuple tuple = data.getItemSet(j);
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
