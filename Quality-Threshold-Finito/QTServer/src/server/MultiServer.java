package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe MultiServer che gestisce un server in ascolto su una porta specificata.
 * Accetta connessioni da più client e crea un nuovo thread per gestire ciascun client.
 */
public class MultiServer {
    /**
     * Porta su cui il server accetta le connessioni.
     * Puoi modificare questo valore o leggerlo da args.
     */
    private int PORT = 8080;

    /**
     * Metodo main che avvia il server.
     * Puoi specificare la porta come argomento della riga di comando.
     *
     * @param args Argomenti della riga di comando (non utilizzati in questo esempio).
     */
    public static void main(String[] args) {
        new MultiServer(8080); // puoi anche leggere la porta da args
    }

    /**
     * Costruttore che inizializza il server sulla porta specificata.
     *
     * @param port La porta su cui il server deve ascoltare.
     */
    public MultiServer(int port) {
        this.PORT = port;
        run();
    }

    /**
     * Metodo che avvia il server e accetta le connessioni dai client.
     * Per ogni connessione accettata, crea un nuovo thread ServerOneClient per gestire il client.
     */
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server in ascolto sulla porta " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connessione accettata da " + clientSocket.getInetAddress());
                new ServerOneClient(clientSocket);
            }

        } catch (IOException e) {
            System.err.println("Errore nel server: " + e.getMessage());
        }
    }
}
