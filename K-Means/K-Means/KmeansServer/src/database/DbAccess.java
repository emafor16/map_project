package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *  Classe che realizza l'accesso alla base di dati per la lettura dei dati di trining.
 */
public class DbAccess {
    
    private String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private final String DBMS = "jdbc:mysql";
    /**
     * Contiene l'identificativo del server su cui risiede la base di dati
     */
    private final String SERVER = "localhost";
    /**
     * Contiene il nome della base di dati
     */
    private final String DATABASE = "MapDB";
    /**
     * Porta su cui il DBMS MySQL accetta le connessioni
     */
    private final String PORT = "8080";
    /**
     * Contiene il nome dell'utente per l'accesso alla base di dati
     */
    private final String USER_ID = "MapUser";
    /**
     * Contiene la password di autentificazione per l'utente identificato da USER_ID
     */
    private final String PASSWORD = "map";
    /**
     * gestisce una connessione
     */
    private Connection conn;

    /**
     * Impartisce al class loader l’ordine di caricare il driver mysql, inizializza la connessione riferita da conn.
     * Il metodo solleva e propaga una eccezione di tipo DatabaseConnectionException in caso di fallimento nella connessione al database
     * @throws DatabaseConnectionException
     */
    public void initConnection() throws DatabaseConnectionException {
        try {
            Class.forName(DRIVER_CLASS_NAME).getDeclaredConstructor().newInstance();
            String connectionString = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE
                    + "?user=" + USER_ID + "&password=" + PASSWORD + "&serverTimezone=UTC";
            conn = DriverManager.getConnection(connectionString);
        } catch (Exception e) {
            throw new DatabaseConnectionException(e.getMessage());
        }
    }

    /**
     * Comportamento: Restituisce la connesione
     * @return
     */
    Connection getConnection() {
        return conn;
    }

    /**
     * Comportamento: Chiude la connessione
     */
    public void closeConnection() {
        try {
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Esegue una query SQL e stampa i risultati.
     * @param query La query SQL da eseguire.
     */
    public void executeQuery(String query) {
        try {
            System.out.println("Esecuzione query...");
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            System.out.println("Query eseguita.");

            while (rs.next()) {
                System.out.println("Dati: " + rs.getString(1)); // Stampa il primo campo della query
            }
        } catch (Exception e) {
            System.err.println("Errore durante l'esecuzione della query: " + e.getMessage());
        }
    }
}