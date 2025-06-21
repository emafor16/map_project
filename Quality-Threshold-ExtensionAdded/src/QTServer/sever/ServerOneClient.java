package QTServer.sever;

import QTServer.data.Data;
import QTServer.database.DatabaseConnectionException;
import QTServer.database.EmptySetException;
import QTServer.database.NoValueException;
import QTServer.mining.QTMiner;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;




//Classe che si occupa di gestire le richieste del singolo client istanziando Thread.
public class ServerOneClient extends Thread {

    private Socket socket; // Riferimento socket del Client.
    private ObjectInputStream in; // Riferimento stream di input contenuto nella socket passata al costruttore.
    private ObjectOutputStream out; // Riferimento stream di output contenuto nella socket passata al costruttore.
    private QTMiner qtminer; // Riferimento all'oggetto di classe QTMiner, per gestire il mining dei dati.
    private Data data; // Riferimento alla tabella scaricata da DB e salvata all'interno di un oggetto Data.

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
                            String TableName = (String) in.readObject();
                            data = new Data(TableName);
                            out.writeObject("OK");
                        } catch (ClassNotFoundException e) {
                            out.writeObject("Errore di lettura: " + e.getMessage());
                        } catch (SQLException e) {
                            out.writeObject("Errore nella query: " + e.getMessage());
                        } catch (EmptySetException e) {
                            out.writeObject("Tabella vuota: " + e.getMessage());
                        }  catch (NotSerializableException e) {
                            out.writeObject("Errore di serializzazione: " + e.getMessage());
                        } catch (FileNotFoundException e) {
                            out.writeObject("File non trovato: " + e.getMessage());
                        } catch (NoValueException e) {
                            throw new RuntimeException(e);
                        } catch (DatabaseConnectionException e) {
                            throw new RuntimeException(e);
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
