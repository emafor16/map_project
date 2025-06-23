package mining;

import data.Tuple;
import database.EmptyDatasetException;
import data.Data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;


public class QTMiner implements Serializable {

    private ClusterSet C;
    private double radius;

    /**
     * Costruttore che inizializza un QTMiner con un ClusterSet vuoto e un raggio specificato.
     *
     * @throws ClusteringRadiusException Se il raggio è minore o uguale a zero.
     */
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

    /**
     * Costruttore che inizializza un QTMiner con un ClusterSet vuoto e un raggio specificato.
     *
     * @param radius Il raggio per la clusterizzazione.
     * @throws ClusteringRadiusException Se il raggio è minore o uguale a zero.
     */
    public QTMiner(double radius) throws ClusteringRadiusException {
        if (radius <= 0) {
            throw new ClusteringRadiusException("Errore: inserire un valore di raggio maggiore di 0.");
        }
        this.C = new ClusterSet();
        this.radius = radius;
    }


    /**
     * Salva l'oggetto ClusterSet in un file specificato.
     *
     * @param fileName Il nome del file in cui salvare l'oggetto ClusterSet.
     * @throws FileNotFoundException Se il file non può essere trovato.
     * @throws IOException           Se si verifica un errore durante la scrittura del file.
     */
    public void salva(String fileName) throws FileNotFoundException, IOException {

        try {
            C.saveToFile(fileName);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File non trovato: " + fileName);
        } catch (IOException e) {
            throw new IOException("Errore durante la scrittura del file: " + fileName);
        }
    }


    /**
     * Restituisce l'insieme di cluster.
     *
     * @return L'insieme di cluster.
     */
    public ClusterSet getC() {
        return C;
    }

    /**
     * Calcola i cluster nel dataset fornito utilizzando l'algoritmo Quality Threshold.
     *
     * @param data Il dataset da clusterizzare.
     * @return Il numero di cluster trovati.
     * @throws ClusteringRadiusException Se il raggio è minore o uguale a zero.
     * @throws EmptyDatasetException      Se il dataset è vuoto.
     */
    public int compute(Data data) throws ClusteringRadiusException, EmptyDatasetException {
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
            Cluster c = buildCandidateCluster(data, isClustered);
            C.add(c);
            numclusters++;
            for (int id : c) {
                isClustered[id] = true;
            }
            countClastered += c.getSize();
        }

        // QT classico: se è stato creato un solo cluster, lancia eccezione
        if (numclusters == 1) {
            throw new ClusteringRadiusException("Tutte le tuple sono in un unico cluster.");
        }
        return numclusters;
    }

    /**
     * Costruisce un cluster candidato basato sui dati forniti e sullo stato di clustering.
     *
     * @param data        I dati da utilizzare per costruire il cluster.
     * @param isClustered Un array booleano che indica se un esempio è già stato clusterizzato.
     * @return Il cluster candidato con il maggior numero di esempi all'interno del raggio specificato.
     */
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