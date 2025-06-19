package mining;

import data.Data;
import data.OutOfRangeSampleSize;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Classe che include l'implementazione dell'algoritmo kmeans.
 *
 * Implementa l'interfaccia Serializable cosi che lo stato dell'oggetto può essere serializzato
 * in uno stream per essere letto o scritto tilizzando le classi ObjectOutputStream e ObjectInputStream.
 */
public class KmeansMiner implements Serializable {
    private ClusterSet C;

    /**
     * Comportamento: Crea l'oggetto array riferito da C.
     * @param k  numero di cluster da generare.
     */
    public KmeansMiner(int k) {
        C = new ClusterSet(k);
    }

    /**
     * Comportamento: restituisce C
     * @return riferimento oggetto ClusterSet C.
     */
    public ClusterSet getC() {
        return C;
    }

    /**
    * Comportamento: esegue l'algoritmo k-means eseguendo i passi dello pseudo-codice:
    *  1. Scelta casuale di centroidi per k clusters.
    *  2. Assegnazione di ciascuna riga della matrice in data al cluster avente centroide più vicino all'esempio.
    *  3. Calcolo dei nuovi centroidi per ciascun cluster.
    *  4. Ripete i passi 2 e 3. finché due iterazioni consecuitive non restituiscono centroidi uguali.
    */
    public int kmeans(Data data) throws OutOfRangeSampleSize {
        int numberOfIterations = 0;
        //STEP 1
        C.initializeCentroids(data);
        boolean changedCluster = false;
        do {
            numberOfIterations++;
            //STEP 2
            changedCluster = false;
            for (int i = 0; i < data.getNumberOfExamples(); i++) {
                Cluster nearestCluster = C.nearestCluster(
                        data.getItemSet(i));
                Cluster oldCluster = C.currentCluster(i);
                boolean currentChange = nearestCluster.addData(i);
                if (currentChange)
                    changedCluster = true; //rimuovo la tupla dal vecchio cluster

                if (currentChange && oldCluster != null) //il nodo va rimosso dal suo vecchio cluster
                    oldCluster.removeTuple(i);
            }

            //STEP 3
            C.updateCentroids(data);
        } while (changedCluster);

        return numberOfIterations;
    }

    /**
     * Comportamento: Apre il file identificato da fileName, legge l'oggetto ivi memorizzato e lo assegna a C.
     * @param fileName percorso+ nome file.
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public KmeansMiner(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
        C = (ClusterSet) ois.readObject();
        ois.close();
    }

    /**
     * Comportamento: Apre il file identificato da fileName e sarva l'oggetto riferito da C in tale file.
     * @param fileName  percorso+ nome file.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void salva(final String fileName) throws FileNotFoundException, IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName, false));
        oos.writeObject(C);
        oos.close();
    }

}
