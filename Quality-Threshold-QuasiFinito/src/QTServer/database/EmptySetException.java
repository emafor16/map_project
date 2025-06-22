package QTServer.database;

/**
 * Classe eccezione personalizzata che estende Exception.
 * Modella un set vuoto.
 */
public class EmptySetException extends Exception {

    /**
     * Comportamento: Costruttore che stampa il messaggio di errore contenuto nella stringa passata come parametro.
     * Richiama il costruttore della superclasse Exception.
     *
     * @param message stringa contenente il messaggio di errore.
     */
    public EmptySetException(String message) {
        super(message);
    }
}

