package QTServer.mining;

import QTServer.data.Data;

import java.io.*;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Classe che rappresenta un insieme di cluster.
 * Permette di aggiungere cluster, iterare su di essi e serializzare/deserializzare l'insieme.
 * Implementa l'interfaccia Iterable per consentire l'iterazione sui cluster contenuti.
 */
public class ClusterSet implements Iterable<Cluster>, Serializable {

    private static final long serialVersionUID = 1L;
    private Set<Cluster> C;

    /**
     * Costruttore che inizializza un ClusterSet vuoto.
     * Utilizza un TreeSet per mantenere i cluster ordinati.
     */
    public ClusterSet() {
        // Initialize an empty cluster set
        C = new TreeSet<>();
    }

    /**
     * Aggiunge un cluster all'insieme di cluster.
     *
     * @param c Il cluster da aggiungere.
     */
    public void add(Cluster c){
        C.add(c);
    }

    /**
     * Restituisce una rappresentazione in stringa dell'insieme di cluster.
     * Ogni cluster è rappresentato con il suo indice e la sua descrizione.
     *
     * @return Una stringa che rappresenta tutti i cluster nell'insieme.
     */
    public String toString() {
        StringBuilder str = new StringBuilder();
        int i = 0;
        for (Cluster cluster : C) {
            str.append("Cluster ").append(i++).append(": ").append(cluster.toString()).append("\n");
        }
        return str.toString();
    }

    /**
     * Restituisce una rappresentazione in stringa dell'insieme di cluster,
     * utilizzando i dati forniti per descrivere ogni cluster.
     *
     * @param data I dati da utilizzare per la descrizione dei cluster.
     * @return Una stringa che rappresenta tutti i cluster nell'insieme con i dati specificati.
     */
    public String toString(Data data) {
        StringBuilder str = new StringBuilder();
        int i = 1;
        for (Cluster cluster : C) {
            str.append(i++).append(":").append(cluster.toString(data)).append("\n");
        }
        return str.toString();
    }

    @Override
    /**
     * Restituisce un iteratore sui cluster contenuti nell'insieme.
     *
     * @return Un iteratore che consente di scorrere i cluster.
     */
    public Iterator<Cluster> iterator() {
        return C.iterator();
    }

    /**
     * Metodo per salvare l'insieme di cluster su un file.
     * Utilizza la serializzazione per scrivere l'oggetto su un file specificato.
     *
     * @param fileName Il nome del file in cui salvare l'insieme di cluster.
     * @throws IOException Se si verifica un errore durante la scrittura del file.
     */
    public void saveToFile(String fileName) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(this);
            System.out.println("Salvataggio su file riuscito: " + fileName);
        } catch (IOException e) {
            System.err.println("Errore durante la scrittura del file: " + fileName);
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Metodo per caricare un insieme di cluster da un file.
     * Utilizza la deserializzazione per leggere l'oggetto da un file specificato.
     *
     * @param fileName Il nome del file da cui caricare l'insieme di cluster.
     * @return Un oggetto ClusterSet caricato dal file.
     * @throws IOException            Se si verifica un errore durante la lettura del file.
     * @throws ClassNotFoundException Se la classe ClusterSet non è trovata durante la deserializzazione.
     */
    public static ClusterSet loadFromFile(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (ClusterSet) in.readObject();
        }
    }
}




