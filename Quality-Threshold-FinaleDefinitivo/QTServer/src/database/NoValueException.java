package database;

/**
 * Classe eccezione personalizzata che estende Exception.
 * Modella l'assenza di un valore atteso in un contesto specifico.
 */
public class NoValueException extends Exception {

    /**
     * Comportamento: Costruttore che stampa il messaggio di errore contenuto nella stringa passata come parametro.
     * Richiama il costruttore della superclasse Exception.
     *
     * @param message stringa contenente il messaggio di errore.
     */
    public NoValueException(String message) {
        super(message);
    }
}
