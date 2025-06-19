package Server;

import data.Data;
import database.DatabaseConnectionException;
import database.EmptySetException;
import database.NoValueException;
import mining.QTMiner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

/**
 * Classe che si occupa di gestire le richieste del singolo client istanziando Thread.
 */
public class ServerOneClient extends Thread {
    /**
     * Riferimento socket del Client.
     */
    private Socket socket;
    /**
     * Riferimento stream di input contenuto nella socket passata al costruttore.
     */
    private ObjectInputStream in;
    /**
     * Riferimento stream di output contenuto nella socket passata al costruttore.
     */
    private ObjectOutputStream out;
    /**
     * riferimento all'oggetto di classe QTMiner
     */
    private QTMiner qtminer;
    /**
     * riferimento alla tabella scaricata da DB e salvata all'interno di un oggetto Data
     */
    private Data data;

    /**
     * Comportamento: costruttore di classe. inizializza gli attributi socket, in, out e avvia il thread.
     * @param s
     * @throws IOException
     */
    public ServerOneClient(Socket s) throws IOException {
        socket = s;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        start();
    }

    /**
     * Comportamento: gestisce le richieste del client.
     */
    public void run() {
        try {
            while (true) {
                int comando = (int) in.readObject();
                switch (comando) {
                    case -1: //esce dallo switch e dal while
                        break;

                    case 0: //storeTableFromDb
                        try {
                            String nomeTabella = (String) in.readObject();
                            data = new Data(nomeTabella);
                            out.writeObject("OK");
                        } catch (DatabaseConnectionException e) {
                            out.writeObject("Errore di connessione al database: " + e.getMessage());
                        } catch (ClassNotFoundException e) {
                            out.writeObject("Errore di lettura: " + e.getMessage());
                        } catch (SQLException e) {
                            out.writeObject("Errore nella query: " + e.getMessage());
                        } catch (EmptySetException e) {
                            out.writeObject("Tabella vuota: " + e.getMessage());
                        } catch (NoValueException e) {
                            out.writeObject("Errore di lettura: " + e.getMessage());
                        }
                        break;

                    case 1: //learningFromDbTable
                        try{
                            double radius = (double) in.readObject();
                            qtminer = new QTMiner(radius);
                            int numclusters = qtminer.compute(data);
                            out.writeObject("OK");
                            out.writeObject(numclusters);
                            out.writeObject(qtminer.getC().toString(data));
                        } catch (Exception e) {
                            out.writeObject("Errore: " + e.getMessage());
                        }
                        break;

                    case 2: //storeClusterInFile
                        try {
                            String nomeFile = (String) in.readObject();
                            qtminer.salva(nomeFile + ".txt");
                            out.writeObject("OK");
                        } catch (Exception e) {
                            out.writeObject("Errore di salvataggio: " + e.getMessage());
                        }
                        break;

                    case 3: //learning from File
                        try{
                            String nomeFile = (String) in.readObject();
                            Data data = new Data("playtennis"); //deve corrispondere al nome della tabella DB
                            String fileName = (nomeFile + ".txt");
                            qtminer = new QTMiner(fileName);
                            out.writeObject("OK");
                            out.writeObject(qtminer.getC().toString(data));
                        } catch (Exception e) {
                            out.writeObject("Errore: "+ e.getMessage());
                        }
                        break;
                }
                if (comando == -1) break; //esce dal while
            }
        } catch (IOException e) {
            System.out.println("Errore di I/O: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Errore di lettura: " + e.getMessage());
        } catch (ClassCastException e){
            System.out.println("Errore di ClassCast: " + e.getMessage());
        } finally {
            try {
                socket.close();
                System.out.println("Client disconnesso.");
            } catch (IOException e) {
                System.out.println("Errore di chiusura del socket: " + e);
            }
        }
    }
}