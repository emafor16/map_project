package QTServer.database;


/**
 * Classe eccezione personalizzata che estende Exception.
 * Modella il fallimento della connessione al Database.
 */
public class DatabaseConnectionException extends Exception {

    /**
     * Comportamento: Costruttore che stampa il messaggio di errore contenuto nella stringa passata come parametro.
     * Richiama il costruttore della superclasse Exception.
     *
     * @param message stringa contenente il messaggio di errore.
     */
    public DatabaseConnectionException(String message) {
        super(message);
    }

    /**
     * Comportamento: Costruttore che stampa il messaggio di errore e la causa dell'eccezione.
     * Richiama il costruttore della superclasse Exception.
     *
     * @param message stringa contenente il messaggio di errore.
     * @param cause   la causa dell'eccezione.
     */
    public DatabaseConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}