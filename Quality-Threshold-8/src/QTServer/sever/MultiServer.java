package QTServer.sever;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer {
    private int PORT = 8080;

    public static void main(String[] args) {
        new MultiServer(8080); // puoi anche leggere la porta da args
    }

    public MultiServer(int port) {
        this.PORT = port;
        run();
    }

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
