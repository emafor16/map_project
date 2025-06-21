/*import database.DatabaseConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DbAccess {

    // Attributi di configurazione
    private final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private final String DBMS = "jdbc:mysql";
    private final String SERVER = "localhost";
    private final String DATABASE = "MapDB";
    private final String PORT = "3306";
    private final String USER_ID = "MapUser";
    private final String PASSWORD = "map";

    // Connessione JDBC
    private Connection conn;


    // Inizializza la connessione al database e chiama DatabaseConnectionException in caso di errore nella connessione
    public void initConnection() throws DatabaseConnectionException {
        try {
            // Debug: stampa la stringa di connessione
            System.out.println("Tentativo di connessione con la stringa: " + DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE
                    + "?user=" + USER_ID + "&password=" + PASSWORD + "&serverTimezone=UTC");

            // Carica il driver
            Class.forName(DRIVER_CLASS_NAME);

            // Costruisce la stringa di connessione
            String connectionString = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE
                    + "?user=" + USER_ID + "&password=" + PASSWORD + "&serverTimezone=UTC";

            // Crea la connessione
            conn = DriverManager.getConnection(connectionString);

            // Debug: verifica se la connessione è null
            if (conn == null) {
                throw new DatabaseConnectionException("La connessione al database non è stata stabilita.");
            } else {
                System.out.println("Connessione al database stabilita con successo.");
            }
        } catch (ClassNotFoundException e) {
            throw new DatabaseConnectionException("Driver MySQL non trovato: " + e.getMessage());
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Errore nella connessione al database: " + e.getMessage());
        }
    }

    //Restituisce la connessione aperta
    public Connection getConnection() {
        return conn;
    }

    //Chiude la connessione attiva, se presente
    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la chiusura della connessione: " + e.getMessage());
        }
    }
}
*/

package QTServer.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbAccess {
    // Parametri di connessione
    private final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private final String DBMS = "jdbc:mysql";
    private final String SERVER = "localhost";
    private final String DATABASE = "MapDB";
    private final String PORT = "3306";
    private final String USER_ID = "MapUser";
    private final String PASSWORD = "map";

    private Connection conn;

    // Costruttore che inizializza subito la connessione
    public DbAccess() throws SQLException {
        try {
            // Caricamento del driver
            Class.forName(DRIVER_CLASS_NAME);

            // Costruzione stringa di connessione
            String connectionString = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE +
                    "?user=" + USER_ID + "&password=" + PASSWORD + "&serverTimezone=UTC";

            // Apertura connessione
            conn = DriverManager.getConnection(connectionString);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC non trovato: " + e.getMessage());
        } catch (SQLException e) {
            throw new SQLException("Errore di connessione al database: " + e.getMessage());
        }
    }

    // Restituisce la connessione aperta
    public Connection getConnection() {
        return conn;
    }

    // Chiude la connessione (opzionale, se vuoi gestire la chiusura manualmente)
    public void closeConnection() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}
