package database;

/**
 * Classe che estende Exception per modellare l’assenza di un valore all’interno di un resultset.
 */
public class NoValueException extends Exception{
    public NoValueException(String message){
        super(message);
    }
}
