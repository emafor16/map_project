package mining;

import data.Data;
import data.Tuple;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * Classe che rappresenta un cluster di dati.
 * Un cluster è definito da un centroide e contiene un insieme di indici di dati che appartengono a quel cluster.
 * Implementa l'interfaccia Iterable per consentire l'iterazione sugli indici dei dati nel cluster.
 * Implementa anche Comparable per confrontare i cluster in base alla loro dimensione.
 */
public class Cluster implements Iterable<Integer> , Comparable<Cluster>, Serializable {

	/**
	 * Serial Version UID per la serializzazione dell'oggetto Cluster.
	 */
	private Tuple centroid;
	/**
	 * Insieme di indici di dati che appartengono a questo cluster.
	 * Utilizza un HashSet per garantire l'unicità degli indici e per operazioni efficienti di aggiunta e rimozione.
	 */
	private Set<Integer> clusteredData;

	/**
	 * Costruttore che inizializza il cluster con un centroide specificato.
	 * Crea un HashSet vuoto per memorizzare gli indici dei dati che appartengono a questo cluster.
	 *
	 * @param centroid Il centroide del cluster, rappresentato come un oggetto Tuple.
	 */
	Cluster(Tuple centroid){
		this.centroid=centroid;
		this.clusteredData=new HashSet<>();

	}

	/**
	 * Restituisce il centroide del cluster.
	 *
	 * @return Il centroide del cluster come oggetto Tuple.
	 */
	Tuple getCentroid(){
		return centroid;
	}

	/**
	 * Aggiunge un indice di dato al cluster.
	 * Accetta diversi tipi di input (Integer, Double, String) e li converte in Integer.
	 * Se l'indice è già presente, non viene aggiunto nuovamente.
	 *
	 * @param id L'indice del dato da aggiungere al cluster.
	 * @return true se l'indice è stato aggiunto con successo, false se era già presente.
	 */
	boolean addData(Object id) {
		Integer idx = null;
		if (id instanceof Integer) {
			idx = (Integer) id;
		} else if (id instanceof Double) {
			idx = ((Double) id).intValue();
		} else if (id instanceof String) {
			try {
				idx = Integer.parseInt((String) id);
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
		return clusteredData.add(idx);
	}

	/**
	 * Aggiunge un indice di dato al cluster.
	 * Accetta un intero come input e lo converte in Integer.
	 * Se l'indice è già presente, non viene aggiunto nuovamente.
	 *
	 * @param id L'indice del dato da aggiungere al cluster.
	 * @return true se l'indice è stato aggiunto con successo, false se era già presente.
	 */
	boolean addData(int id) {
		return addData((Object) id);
	}

	/**
	 * Verifica se un indice di dato è già presente nel cluster.
	 * Accetta diversi tipi di input (Integer, Double, String) e li converte in Integer.
	 *
	 * @param id L'indice del dato da verificare.
	 * @return true se l'indice è presente nel cluster, false altrimenti.
	 */
	boolean contain(Object id) {
		Integer idx = null;
		if (id instanceof Integer) {
			idx = (Integer) id;
		} else if (id instanceof Double) {
			idx = ((Double) id).intValue();
		} else if (id instanceof String) {
			try {
				idx = Integer.parseInt((String) id);
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
		return clusteredData.contains(idx);
	}

	/**
	 * Verifica se un indice di dato è già presente nel cluster.
	 * Accetta un intero come input e lo converte in Integer.
	 *
	 * @param id L'indice del dato da verificare.
	 * @return true se l'indice è presente nel cluster, false altrimenti.
	 */
	boolean contain(int id) {
		return contain((Object) id);
	}

	/**
	 * Rimuove un indice di dato dal cluster.
	 * Accetta diversi tipi di input (Integer, Double, String) e li converte in Integer.
	 * Se l'indice non è presente, non viene effettuata alcuna operazione.
	 *
	 * @param id L'indice del dato da rimuovere dal cluster.
	 */
	void removeTuple(Object id) {
		Integer idx = null;
		if (id instanceof Integer) {
			idx = (Integer) id;
		} else if (id instanceof Double) {
			idx = ((Double) id).intValue();
		} else if (id instanceof String) {
			try {
				idx = Integer.parseInt((String) id);
			} catch (NumberFormatException e) {
				return;
			}
		} else {
			return;
		}
		clusteredData.remove(idx);
	}

	/**
	 * Rimuove un indice di dato dal cluster.
	 * Accetta un intero come input e lo converte in Integer.
	 * Se l'indice non è presente, non viene effettuata alcuna operazione.
	 *
	 * @param id L'indice del dato da rimuovere dal cluster.
	 */
	void removeTuple(int id) {
		removeTuple((Object) id);
	}

	/**
	 * Restituisce la dimensione del cluster, ovvero il numero di indici di dati che contiene.
	 *
	 * @return La dimensione del cluster come intero.
	 */
	public int getSize(){
		return clusteredData.size();
	}

	/**
	 * Restituisce un iteratore sugli indici dei dati che appartengono a questo cluster.
	 * Permette di iterare attraverso gli indici in modo efficiente.
	 *
	 * @return Un iteratore sugli indici dei dati nel cluster.
	 */
	public Iterator<Integer> iterator(){
		return clusteredData.iterator();
	}

	/**
	 * Confronta questo cluster con un altro cluster in base alla loro dimensione.
	 * Implementa l'interfaccia Comparable per consentire il confronto tra cluster.
	 *
	 * @param other Il cluster da confrontare con questo.
	 * @return Un intero che indica il risultato del confronto:
	 *         - Un valore negativo se questo cluster è più piccolo di 'other'.
	 *         - Zero se i due cluster hanno la stessa dimensione.
	 *         - Un valore positivo se questo cluster è più grande di 'other'.
	 */
	public int compareTo(Cluster other) {
		return Integer.compare(this.getSize(), other.getSize());
	}

	/**
	 * Restituisce una rappresentazione in stringa del cluster, inclusi il centroide e gli esempi che lo compongono.
	 * Mostra anche la distanza media degli esempi dal centroide.
	 *
	 * @param data Riferimento ai dati utilizzati per ottenere i valori degli esempi.
	 * @return Una stringa che rappresenta lo stato del cluster.
	 */
	public String toString(Data data) {
		StringBuilder str = new StringBuilder("Centroid=(");
		for (int i = 0; i < centroid.getLength(); i++) {
			str.append(centroid.get(i)).append(" ");
		}

		// Aggiunta della classe (PlayTennis) da un esempio, se esiste
		Iterator<Integer> it = this.iterator();
		if (it.hasNext()) {
			int exampleIndex = it.next();
			str.append(data.getValue(exampleIndex, data.getNumberOfAttributes() - 1));
		}
		str.append(")\nExamples:\n");

		double totalDistance = 0;
		int count = 0;
		for (int id : this) {
			str.append("[");
			for (int j = 0; j < data.getNumberOfAttributes(); j++) {
				str.append(data.getValue(id, j)).append(" ");
			}
			double dist = centroid.getDistance(data.getItemSet(id));
			totalDistance += dist;
			count++;
			str.append("] dist=").append(dist).append("\n");
		}

		double avgDist = (count > 0) ? totalDistance / count : 0;
		str.append("\nAvgDistance=").append(avgDist);
		return str.toString();
	}

}
