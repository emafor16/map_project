package main;

import data.Data;
import mining.QTMiner;

import java.util.Scanner;

	public class MainTest {

		public static void main(String[] args) {
			Scanner scanner = new Scanner(System.in);
			Data data = new Data();
			System.out.println(data);

			boolean repeat;
			do {
				System.out.print("Inserisci il valore del raggio (radius) maggiore di 0: ");
				double radius = 0;

				// Gestione input numerico
				while (true) {
					try {
						radius = Double.parseDouble(scanner.nextLine());
						if (radius < 0) {
							System.out.print("Il raggio deve essere positivo. Riprova: ");
							continue;
						}
						break;
					} catch (NumberFormatException e) {
						System.out.print("Input non valido. Inserisci un numero reale per il raggio: ");
					}
				}

				QTMiner qt = new QTMiner(radius);
				int numClusters = qt.compute(data);
				System.out.println("Numero dei cluster: " + numClusters);
				System.out.println(qt.getC().toString(data));

				// Chiedi se ripetere
				System.out.print("Vuoi inserire un nuovo valore di radius? (s/n): ");
				String risposta = scanner.nextLine().trim().toLowerCase();
				repeat = risposta.equals("s") || risposta.equals("si");

			} while (repeat);

			scanner.close();
			//System.out.println("Esecuzione terminata.");
		}
	}
