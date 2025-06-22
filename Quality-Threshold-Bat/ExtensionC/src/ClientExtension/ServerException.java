package ClientExtension;

import java.io.Serializable;

/**
 *  Classe eccezione sollevata in caso di errori lato Server.
 */
public class ServerException extends Exception implements Serializable {
    public ServerException(String message){
        super(message);
    }
    public String toString() {
        return "ClientExtension.ServerException: " + getMessage();
    }
}
