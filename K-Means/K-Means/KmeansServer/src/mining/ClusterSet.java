package mining;

import data.Data;
import data.OutOfRangeSampleSize;
import data.Tuple;

import java.io.Serializable;

/**
 *  Classe che rappresenta un insieme di cluster (determinati da k-means).
 *
 * Implementa l'interfaccia Serializable cosi che lo stato dell'oggetto può essere serializzato
 * in uno stream per essere letto o scritto utilizzando le classi ObjectOutputStream e ObjectInputStream.
 */
public class ClusterSet implements Serializable {
    private Cluster C[];
    /**
     * posizione valida per la memorizzazione di un nuovo cluster in C.
     */
    private transient int i =0;

    /**
     * Comportamento: creo l'oggetto array riferito da C.
     * @param k numero di cluster.
     */
    ClusterSet(int k) {
        C = new Cluster[k];
    }

    /**
     * Comportamento: assegna c a C[i] e incrementa i.
     * @param c riferimento ad oggetto di tipo Cluster.
     */
    void add(Cluster c){
        C[i] = c;
        i++;
    }

    /**
     * Comportamento: restituisce C[i].
     * @param i
     * @return
     */
    Cluster get(int i) {
        return C[i];
    }

    /**
     * Comportamento: sceglie i centroidi, crea un cluster per ogni centroide e lo memorizza in C.
     * @param data
     */
    void initializeCentroids(Data data) throws OutOfRangeSampleSize {
        int centroidIndexes[]=data.sampling(C.length);
        for(int i=0;i<centroidIndexes.length;i++) {
            Tuple centroidI=data.getItemSet(centroidIndexes[i]);
            add(new Cluster(centroidI));
        }
    }

    /**
     * Comportamento: calcola la distanza tra la tupla riferita da tuple ed il
     * centroide di ciascun cluster in C e restituisce il cluster più vicino
     * @param tuple
     * @return
     */
    Cluster nearestCluster (Tuple tuple) {
        Cluster nearest = null;
        double distance = Double.MAX_VALUE;
        for (int i = 0; i < this.i; i++) {
            if (tuple.getDistance(C[i].getCentroid()) < distance) {
                nearest = C[i];
                distance = tuple.getDistance(C[i].getCentroid());
            }
        }

        return nearest;
    }

    /**
    * Comportamento: Identifica e restituisce il cluster a cui la tupla rappresentate l'esempio identificato da id
    * Se la tupla non è inclusa in nessun cluster restituisce null
    */
    Cluster currentCluster(int id) {
        for (int i = 0; i < this.i; i++)
            if (C[i].contain(id)) return C[i];
        return null;
    }

    /**
     * Comportamento: calcola il nuovo centroide per ciascun cluster in C.
     */
    void updateCentroids(Data data) {
         for (int i = 0; i < this.i; i++) {
            C[i].computeCentroid(data);
        }
    }

    /**
     * Comportamento: restituisce una stringa fatta da ciascun centroide dell'insieme dei cluster.
     */
    public String toString() {
        String s = "";
        for (int i = 0; i < this.i; i++) {
            s += "mining.Cluster " + i + " centroid: " + C[i].getCentroid().toString();
        }
        return s;
    }

    /**
     * Comportamento: restituisce una stringa che descriva lo stato di ciascun cluster in C.
     * @param data
     * @return
     */
    public String toString(Data data){
        String str="";
        for(int i=0;i<C.length;i++){
            if (C[i]!=null){
                str+=(i+1)+":"+C[i].toString(data)+"\n";
            }
        }
        return str;
    }
}