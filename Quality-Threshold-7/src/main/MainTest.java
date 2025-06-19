/*package main;

import data.Data;
import exceptions.ClusteringRadiusException;
import exceptions.EmptyDatasetException;
import mining.QTMiner;
import database.DbAccess;

import java.util.Scanner;

public class MainTest {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		// Inizializza la connessione al database
		DbAccess dbAccess = new DbAccess();
		try {
			dbAccess.initConnection();
		} catch (Exception e) {
			System.out.println("Errore durante la connessione al database: " + e.getMessage());
			return;
		}

		System.out.print("Inserisci il nome della tabella: ");
		String tableName = scanner.nextLine().trim();

		Data data = null;
		try {
			data = new Data(tableName);  // usa il costruttore che carica da DB con nome tabella
		} catch (Exception e) {
			System.out.println("Errore nel caricamento dei dati: " + e.getMessage());
			scanner.close();
			return;
		}

		System.out.println(data);

		boolean repeat;
		do {
			System.out.print("Inserisci il valore del raggio (radius) maggiore di 0: ");
			double radius = 0;

			// Gestione input numerico
			while (true) {
				try {
					radius = Double.parseDouble(scanner.nextLine());
					if (radius <= 0) {  // correggo il controllo: radius > 0 richiesto
						System.out.print("Il raggio deve essere positivo. Riprova: ");
						continue;
					}
					break;
				} catch (NumberFormatException e) {
					System.out.print("Input non valido. Inserisci un numero reale per il raggio: ");
				}
			}

			QTMiner qt = new QTMiner(radius);

			try {
				int numClusters = qt.compute(data);
				System.out.println("Numero dei cluster: " + numClusters);
				System.out.println(qt.getC().toString(data));
			} catch (EmptyDatasetException e) {
				System.out.println("Eccezione: " + e.getMessage());
			} catch (ClusteringRadiusException e) {
				System.out.println("Eccezione: " + e.getMessage());
			}


			// Chiedi se ripetere
			System.out.print("Vuoi inserire un nuovo valore di radius? (s/n): ");
			String risposta = scanner.nextLine().trim().toLowerCase();
			repeat = risposta.equals("s") || risposta.equals("si");

		} while (repeat);

		scanner.close();

		// Chiude la connessione al database
		dbAccess.closeConnection();
	}

}*/

package main;

import data.Data;
import exceptions.ClusteringRadiusException;
import exceptions.EmptyDatasetException;
import mining.QTMiner;
import database.DbAccess;

import java.sql.SQLException;
import java.util.Scanner;

public class MainTest {

	public static void main(String[] args) throws SQLException {
		DbAccess dbAccess = new DbAccess();
		Scanner scanner = new Scanner(System.in);

		// Inizializza la connessione al database
		//DbAccess dbAccess = new DbAccess();
		try {
			dbAccess = new DbAccess(); // Connessione stabilita nel costruttore
		} catch (Exception e) {
			System.out.println("Errore durante la connessione al database: " + e.getMessage());
			scanner.close();
			return;
		}

		System.out.print("Inserisci il nome della tabella: ");
		String tableName = scanner.nextLine().trim();

		Data data = null;
		try {
			data = new Data(tableName, dbAccess);  // usa il costruttore che carica da DB con nome tabella
		} catch (Exception e) {
			System.out.println("Errore nel caricamento dei dati: " + e.getMessage());
			scanner.close();
			return;
		}

		System.out.println(data);

		boolean repeat;
		do {
			System.out.print("Inserisci il valore del raggio (radius) maggiore di 0: ");
			double radius = 0;

			while (true) {
				try {
					radius = Double.parseDouble(scanner.nextLine());
					if (radius <= 0) {
						System.out.print("Il raggio deve essere positivo. Riprova: ");
						continue;
					}
					break;
				} catch (NumberFormatException e) {
					System.out.print("Input non valido. Inserisci un numero reale per il raggio: ");
				}
			}

			QTMiner qt = new QTMiner(radius);

			try {
				int numClusters = qt.compute(data);
				System.out.println("Numero dei cluster: " + numClusters);
				System.out.println(qt.getC().toString(data));
			} catch (EmptyDatasetException | ClusteringRadiusException e) {
				System.out.println("Eccezione: " + e.getMessage());
			}

			System.out.print("Vuoi inserire un nuovo valore di radius? (s/n): ");
			String risposta = scanner.nextLine().trim().toLowerCase();
			repeat = risposta.equals("s") || risposta.equals("si");

		} while (repeat);

		scanner.close();

		try {
			dbAccess.closeConnection();
		} catch (Exception e) {
			System.out.println("Errore nella chiusura della connessione: " + e.getMessage());
		}
	}
}

