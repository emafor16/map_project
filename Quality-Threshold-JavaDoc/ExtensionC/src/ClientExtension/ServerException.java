package ClientExtension;

import java.io.Serializable;

/**
 *  Classe eccezione sollevata in caso di errori lato Server.
 */
public class ServerException extends Exception implements Serializable {
    /**
     * Serial Version UID per la serializzazione.
     */
    public ServerException(String message){
        super(message);
    }

    /**
     * Costruisce una nuova ServerException con il messaggio e la causa specificati.
     */
    public String toString() {
        return "ClientExtension.ServerException: " + getMessage();
    }
}
