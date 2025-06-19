package database;

/**
 * Classe eccezione personalizzata che estende Exception.
 * Modella il fallimento della connessione al Database.
 */
public class DatabaseConnectionException extends Exception{
    /**
     * Comportamento: Costruttore che stampa il messaggio di errore contenuto nella stringa passata come parametro.
     * Richiama il costruttore della superclasse Exception.
     *
     * @param message stringa contenente il messaggio di errore.
     */
    DatabaseConnectionException(String message){
        super(message);
    }
}
