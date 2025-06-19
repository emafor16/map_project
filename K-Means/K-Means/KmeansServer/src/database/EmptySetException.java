package database;

/**
 * Classe che estende Exception per modellare la restituzione di un resultset vuoto.
 */
public class EmptySetException extends Exception {
        public EmptySetException(String message) {
        super(message);
    }    
}