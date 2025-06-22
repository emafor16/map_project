package QTServer.mining;

/**
 * Classe eccezione personalizzata che estende Exception.
 * Modella un errore durante il clustering quando tutte le tuple finiscono in un unico cluster.
 */
public class ClusteringRadiusException extends Exception {

    /**
     * Comportamento: Costruttore che stampa un messaggio di errore predefinito.
     * Richiama il costruttore della superclasse Exception.
     */
    public ClusteringRadiusException() {
        super("Clustering fallito: tutte le tuple sono finite in un unico cluster.");
    }

    /**
     * Comportamento: Costruttore che stampa il messaggio di errore contenuto nella stringa passata come parametro.
     * Richiama il costruttore della superclasse Exception.
     *
     * @param message stringa contenente il messaggio di errore.
     */
    public ClusteringRadiusException(String message) {
        super(message);
    }
}
