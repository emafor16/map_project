package database;


//Eccezione sollevata in caso di fallimento nella connessione al database.
public class DatabaseConnectionException extends Exception {

    public DatabaseConnectionException(String message) {
        super(message);
    }

    public DatabaseConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
