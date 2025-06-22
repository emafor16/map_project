package main;

import keyboardinput.Keyboard;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

/**
 * Classe che stabilisce la connessione al Server e, una volta avvenuta la connessione,invia e riceve messaggi,
 * dipendentemente dalla scelta effettuata dall'utente. Attraverso un menu, l'utente del client seleziona la attività da svolgere,
 * scoperta/lettura di cluster. Se la scelta è una attività di scoperta si invia al Server il numero di cluster da scoprire,
 * il nome della tabella di database, il nome del file in cui serializzare i cluster scoperti.
 * Se la scelta è una attività di lettura si invia al Server il nome del file in cui sono serializzati i cluster da recuperare.
 * In entrambe le attività il cliente acquisisce il risultato trasmesso dal server o lo visualizza a video.
 */

public class MainTest {

	/**
	 * @param args
	 */

	/**
	 * stream di output
	 */
	private ObjectOutputStream out;
	/**
	 * stream di input
	 */
	private ObjectInputStream in; // stream con richieste del client

	/**
	 * metodo iniziale che si occupa di istanziare la connessione (socket, OutpusStream e InputStream)
	 * (lato client)
	 *
	 * @param ip   IP del Server per la connessione
	 * @param port porta su cui il Server fornisce il servizio
	 * @throws IOException
	 */
	public MainTest(String ip, int port) throws IOException {
		InetAddress addr = InetAddress.getByName(ip); //ip
		System.out.println("addr = " + addr);
		Socket socket = new Socket(addr, port); //Port
		System.out.println(socket);

		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		; // stream con richieste del client
	}

	/**
	 * metodo che gestisce la scelta all'interno del Menu da parte del Client
	 * (lato client)
	 *
	 * @return scelta fatta dall'utente 1 per File 2 per DB
	 */
	private int menu() {
		int answer;

		do {
			System.out.println("(1) Carica Cluster da file");
			System.out.println("(2) Carica Cluster da database");
			System.out.print("(1/2):");
			answer = Keyboard.readInt();
		}
		while (answer <= 0 || answer > 2);
		return answer;

	}

	/**
	 * metodo che gestisce la scoperta da File
	 *
	 * @return dati elaborati da File
	 * (lato client)
	 * @throws SocketException
	 * @throws ServerException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private String learningFromFile() throws SocketException, ServerException, IOException, ClassNotFoundException {
		out.writeObject(3);
		System.out.print("Nome del file in cui sono serializzati i cluster da recuperare:");
		String tabName = Keyboard.readString();
		out.writeObject(tabName);
		String result = (String) in.readObject();
		if (result.equals("OK"))
			return (String) in.readObject();
		else throw new ServerException(result);

	}

	/**
	 * metodo che gestisce l'inserimento della tabella di DB al Server
	 * (lato client)
	 *
	 * @throws SocketException
	 * @throws ServerException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void storeTableFromDb() throws SocketException, ServerException, IOException, ClassNotFoundException {
		out.writeObject(0);
		System.out.print("Nome della tabella:");
		String tabName = Keyboard.readString();
		out.writeObject(tabName);
		String result = (String) in.readObject();
		if (!result.equals("OK"))
			throw new ServerException(result);

	}

	/**
	 * metodo che gestisce la scoperta da DB
	 * (lato client)
	 *
	 * @return dati elaborati da DB
	 * @throws SocketException
	 * @throws ServerException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private String learningFromDbTable() throws SocketException, ServerException, IOException, ClassNotFoundException {
		out.writeObject(1);
		double r = 1.0;
		do {
			System.out.print("Raggio:");
			r = Keyboard.readDouble();
			if (r < 1 || r > 14) {
				System.out.println("Errore: il raggio deve essere compreso tra 1 e 14.");
			}
		} while (r < 1 || r > 14);
		out.writeObject(r);
		String result = (String) in.readObject();
		if (result.equals("OK")) {
			System.out.println("Numero di Cluster:" + in.readObject());
			return (String) in.readObject();
		} else throw new ServerException(result);


	}

	/**
	 * metodo che si occupa del salvataggio dei cluster scoperti all'interno del Server
	 * (lato client)
	 *
	 * @throws SocketException
	 * @throws ServerException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void storeClusterInFile() throws SocketException, ServerException, IOException, ClassNotFoundException {
		out.writeObject(2);
		System.out.print("Nome del file in cui serializzare i cluster scoperti:");
		String fileName = Keyboard.readString();
		out.writeObject(fileName);
		String result = (String) in.readObject();
		if (!result.equals("OK"))
			throw new ServerException(result);

	}

	/**
	 * Main lato Client per la comunicazione con il Server
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		/*String ip = args[0];
		int port = new Integer(args[1]).intValue();
		MainTest main = null;
		try {
			main = new MainTest(ip, port);
		} catch (IOException e) {
			System.out.println(e);
			return;
		}*/
		if (args.length < 2) {
			System.out.println("Uso: java MainTest <ip> <porta>");
			return;
		}
		String ip = args[0];
		int port = Integer.parseInt(args[1]);
		MainTest main = null;
		try {
			main = new MainTest(ip, port);
		} catch (IOException e) {
			System.out.println(e);
			return;
		}


		do {
			int menuAnswer = main.menu();
			switch (menuAnswer) {
				case 1: // learning from file
					try {
						String kmeans = main.learningFromFile();
						System.out.println(kmeans);
					} catch (SocketException e) {
						System.out.println(e);
						break;
					} catch (FileNotFoundException e) {
						System.out.println(e);
						break;
					} catch (IOException e) {
						System.out.println(e);
						break;
					} catch (ClassNotFoundException e) {
						System.out.println(e);
						break;
					} catch (ServerException e) {
						System.out.println(e.getMessage());
					}
					break;
				case 2: // learning from db

					while (true) {
						try {
							main.storeTableFromDb();
							break; //esce fuori dal while
						} catch (SocketException e) {
							System.out.println(e);
							break;
						} catch (FileNotFoundException e) {
							System.out.println(e);
							break;

						} catch (IOException e) {
							System.out.println(e);
							break;
						} catch (ClassNotFoundException e) {
							System.out.println(e);
							break;
						} catch (ServerException e) {
							System.out.println(e.getMessage());
						}
					} //end while [viene fuori dal while con un db (in alternativa il programma termina)

					char answer = 's';//itera per learning al variare di k
					do {
						try {
							String clusterSet = main.learningFromDbTable();
							System.out.println(clusterSet);

							System.out.print("Vuoi salvare i Cluster scoperti?(s/n)");
							answer = Keyboard.readChar();
							if (answer == 's')
								main.storeClusterInFile();
						} catch (SocketException e) {
							System.out.println(e);
							break;
						} catch (FileNotFoundException e) {
							System.out.println(e);
							break;
						} catch (ClassNotFoundException e) {
							System.out.println(e);
							break;
						} catch (IOException e) {
							System.out.println(e);
							break;
						} catch (ServerException e) {
							System.out.println(e.getMessage());
						}
						System.out.print("Vuoi ripetere il processo?(s/n)");
						answer = Keyboard.readChar();
					}
					while (Character.toLowerCase(answer) == 's');
					break; //fine case 2
				default:
					System.out.println("Opzione non valida!");
			}

			System.out.print("Vuoi scegliere un'altra opzione dal menu?(s/n)");
			if (Keyboard.readChar() != 's')
				try {
					main.out.writeObject(-1);
					main.out.close();
					main.in.close();
					return;
				} catch (IOException e) {
					System.out.println(e);
					return;
				}
		} while (true);
	}
}
