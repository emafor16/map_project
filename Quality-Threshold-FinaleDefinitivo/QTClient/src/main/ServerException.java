package main;

/**
 * Eccezione sollevata in caso di errori lato server.
 */
public class ServerException extends Exception {

    /**
     * Serial Version UID per la serializzazione.
     */
    public ServerException() {
        super();
    }

    /**
     * Costruisce una nuova ServerException con il messaggio specificato.
     *
     * @param message il messaggio di dettaglio
     */
    public ServerException(String message) {
        super(message);
    }

    /**
     * Costruisce una nuova ServerException con il messaggio e la causa specificati.
     *
     * @param message il messaggio di dettaglio
     * @param cause   la causa dell'eccezione
     */
    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Costruisce una nuova ServerException con la causa specificata.
     *
     * @param cause la causa dell'eccezione
     */
    public ServerException(Throwable cause) {
        super(cause);
    }

}