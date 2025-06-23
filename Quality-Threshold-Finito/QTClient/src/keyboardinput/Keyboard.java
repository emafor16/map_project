package keyboardinput;

import java.io.*;
import java.util.*;

/**
 * Classe che gestisce l'input da tastiera.
 * @author map tutor
 */
public class Keyboard {
	// ************* Error Handling Section **************************
	/**
	 * Indica se gli errori di input devono essere stampati su standard output.
	 */
	private static boolean printErrors = true;
	/**
	 * Contatore degli errori di input.
	 */
	private static int errorCount = 0;

	/**
	 * Restituisce il numero corrente di errori di input.
	 * @return il numero di errori di input
	 */
	public static int getErrorCount() {
		return errorCount;
	}

	/**
	 * Reimposta il contatore degli errori di input a zero.
	 * @param count il numero di errori da resettare (non utilizzato)
	 */
	public static void resetErrorCount(int count) {
		errorCount = 0;
	}

	/**
	 * Restituisce true se gli errori di input vengono stampati su standard output.
	 * @return true se gli errori sono stampati, false altrimenti
	 */
	public static boolean getPrintErrors() {
		return printErrors;
	}

	/**
	 * Imposta se gli errori di input devono essere stampati su standard output.
	 * @param flag true per stampare gli errori, false altrimenti
	 */
	public static void setPrintErrors(boolean flag) {
		printErrors = flag;
	}

	/**
	 * Incrementa il contatore degli errori e stampa il messaggio di errore se necessario.
	 * @param str il messaggio di errore da stampare
	 */
	private static void error(String str) {
		errorCount++;
		if (printErrors)
			System.out.println(str);
	}

	/**
	 * Token corrente letto dall'input.
	 */
	private static String current_token = null;

	/**
	 * Lettore di token per l'input.
	 */
	private static StringTokenizer reader;

	/**
	 * Lettore di input da tastiera.
	 */
	private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Restituisce il prossimo token letto dall'input.
	 * @return il prossimo token letto dall'input
	 */
	private static String getNextToken() {
		return getNextToken(true);
	}

	/**
	 * Restituisce il prossimo token letto dall'input.
	 * @param skip indica se i token di spazio devono essere saltati
	 * @return il prossimo token letto dall'input
	 */
	private static String getNextToken(boolean skip) {
		String token;

		if (current_token == null)
			token = getNextInputToken(skip);
		else {
			token = current_token;
			current_token = null;
		}

		return token;
	}

	/**
	 * Restituisce il prossimo token letto dall'input, leggendo da standard input se necessario.
	 * @param skip indica se i token di spazio devono essere saltati
	 * @return il prossimo token letto dall'input
	 */
	private static String getNextInputToken(boolean skip) {
		final String delimiters = " \t\n\r\f";
		String token = null;

		try {
			if (reader == null)
				reader = new StringTokenizer(in.readLine(), delimiters, true);

			while (token == null || ((delimiters.indexOf(token) >= 0) && skip)) {
				while (!reader.hasMoreTokens())
					reader = new StringTokenizer(in.readLine(), delimiters,
							true);

				token = reader.nextToken();
			}
		} catch (Exception exception) {
			token = null;
		}

		return token;
	}

	/**
	 * Restituisce true se non ci sono più token da leggere sulla riga corrente di input.
	 * @return true se non ci sono più token, false altrimenti
	 */
	public static boolean endOfLine() {
		return !reader.hasMoreTokens();
	}

	// ************* Reading Section *********************************

	/**
	 * Restituisce una stringa letta da standard input.
	 * @return la stringa letta dall'input
	 */
	public static String readString() {
		String str;

		try {
			str = getNextToken(false);
			while (!endOfLine()) {
				str = str + getNextToken(false);
			}
		} catch (Exception exception) {
			error("Errore nella lettura della stringa, restituito valore null.");
			str = null;
		}
		return str;
	}

	/**
	 * Restituisce una parola (delimitata da spazi) letta da standard input.
	 * @return la parola letta dall'input
	 */
	public static String readWord() {
		String token;
		try {
			token = getNextToken();
		} catch (Exception exception) {
			error("Errore nella lettura della parola, restituito valore null.");
			token = null;
		}
		return token;
	}

	/**
	 * Restituisce un booleano letto da standard input.
	 * @return il valore booleano letto dall'input
	 */
	public static boolean readBoolean() {
		String token = getNextToken();
		boolean bool;
		try {
			if (token.toLowerCase().equals("true"))
				bool = true;
			else if (token.toLowerCase().equals("false"))
				bool = false;
			else {
				error("Errore nella lettura del booleano, il valore deve essere true o false, restituito false.");
				bool = false;
			}
		} catch (Exception exception) {
			error("Errore nella lettura del booleano, restituito valore false.");
			bool = false;
		}
		return bool;
	}

	/**
	 * Restituisce un carattere letto da standard input.
	 * Se l'input non è un singolo carattere, restituisce il primo carattere dell'input.
	 * Se non viene fornito alcun carattere, restituisce Character.MIN_VALUE.
	 * @return il carattere letto dall'input
	 */
	public static char readChar() {
		String token = getNextToken(false);
		char value;
		try {
			if (token.length() > 1) {
				current_token = token.substring(1, token.length());
			} else
				current_token = null;
			value = token.charAt(0);
		} catch (Exception exception) {
			error("Errore nella lettura del carattere, restituito MIN_VALUE.");
			value = Character.MIN_VALUE;
		}

		return value;
	}

	/**
	 * Restituisce un intero letto da standard input.
	 * Se l'input non è valido, restituisce Integer.MIN_VALUE.
	 * @return il valore intero letto dall'input
	 */
	public static int readInt() {
		String token = getNextToken();
		int value;
		try {
			value = Integer.parseInt(token);
		} catch (Exception exception) {
			error("Errore nella lettura dell'intero, il valore deve essere un intero, restituito MIN_VALUE.");
			value = Integer.MIN_VALUE;
		}
		return value;
	}

	/**
	 * Restituisce un long letto da standard input.
	 * Se l'input non è valido, restituisce Long.MIN_VALUE.
	 * @return il valore long letto dall'input
	 */
	public static long readLong() {
		String token = getNextToken();
		long value;
		try {
			value = Long.parseLong(token);
		} catch (Exception exception) {
			error("Errore nella lettura del long, il valore deve essere un long, restituito Long.MIN_VALUE.");
			value = Long.MIN_VALUE;
		}
		return value;
	}

	/**
	 * Restituisce un float letto da standard input.
	 * Se l'input non è valido, restituisce Float.NaN.
	 * @return il valore float letto dall'input
	 */
	public static float readFloat() {
		String token = getNextToken();
		float value;
		try {
			value = Float.parseFloat(token);
		} catch (Exception exception) {
			error("Errore nella lettura del float, il valore deve essere un float, restituito Float.NaN.");
			value = Float.NaN;
		}
		return value;
	}

	/**
	 * Restituisce un double letto da standard input.
	 * Se l'input non è valido, restituisce Double.NaN.
	 * @return il valore double letto dall'input
	 */
	public static double readDouble() {
		String token = getNextToken();
		double value;
		try {
			value = Double.parseDouble(token);
		} catch (Exception exception) {
			error("Errore nella lettura del double, il valore deve essere un double, restituito Double.NaN.");
			value = Double.NaN;
		}
		return value;
	}
}