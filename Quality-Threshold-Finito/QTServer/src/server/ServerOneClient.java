package server;

import data.Data;
import database.DatabaseConnectionException;
import database.EmptySetException;
import database.NoValueException;
import mining.QTMiner;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

/**
 * Classe ServerOneClient per gestire le richieste di un singolo client.
 * Estende la classe Thread per permettere l'esecuzione in parallelo.
 */
public class ServerOneClient extends Thread {

    /**
     * Attributi della classe ServerOneClient:
     * - socket: riferimento alla socket del client.
     * - in: stream di input per leggere gli oggetti inviati dal client.
     * - out: stream di output per inviare oggetti al client.
     * - qtminer: riferimento all'oggetto di classe QTMiner, per gestire il mining dei dati.
     * - data: riferimento all'oggetto Data, che contiene la tabella scaricata dal database.
     */
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
     * Il metodo entra in un ciclo infinito in cui attende comandi dal client.
     * Gestisce i seguenti comandi:
     * - -1: termina il ciclo e chiude la connessione.
     * - 0: carica una tabella dal database e la memorizza in un oggetto Data.
     * - 1: esegue il mining dei dati dalla tabella caricata, utilizzando un raggio specificato.
     * - 2: salva i cluster ottenuti dal mining in un file specificato.
     * - 3: esegue il mining dei dati da un file specificato e restituisce i cluster ottenuti.
     * Il metodo gestisce le eccezioni che possono verificarsi durante le operazioni, come errori di I/O, di lettura, di serializzazione,
     * di database, di file non trovato, di set vuoto e di valori mancanti.
     * Se si verifica un'eccezione, viene inviata una risposta al client con il messaggio di errore.
     * Alla fine del ciclo, se il comando -1 viene ricevuto, il metodo chiude la connessione e termina l'esecuzione del thread.
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
                            System.err.println("[SERVER] Errore durante la lettura del file: " + e.getMessage());
                            e.printStackTrace();
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
