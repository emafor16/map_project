package data;

import database.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Classe che si occupa di modellare l'insieme di transazioni o tuple.
 */
public class Data {

	/**
	 * matrice MxN di tipo Object dove ogni riga modella una transazione
	 */
	private List<Example> data = new ArrayList<>();
	/**
	 * cardinalità dell'insieme di transazioni (numero di righe in data)
	 */
	private int numberOfExamples;
	/**
	 * Lista degli attributi in ciascuna tupla (schema della tabella di dati)
	 */
	private List<Attribute> attributeSet;

	/**
	 * Il costruttore di data si occupa di caricare i dati di addestramento da una tabella della base di dati.
	 * Il nome della tabella è un parametro del costruttore
	 * Inizializza la matrice data [ ][ ] con transazioni di esempio
	 * Inizializza attributeSet creando oggetti di tipo DiscreteAttribute o ContinuousAttribute, a seconda del tipo di attributo
	 * Inizializza numberOfExamples
	 * Utilizza la classe database.Example
	 * Comportamento: costruttore della classe Data. Carica i dati di addestramento da una tabella della base di dati.
	 * Il nome della tabella è un parametro del costruttore.
	 * @param tableName riferimento ad un oggetto di tipo String contenente il nome della tabella.
	 * @throws SQLException se si verifica un errore durante l'accesso al database.
	 * @throws EmptySetException se la tabella non contiene dati.
	 * @throws DatabaseConnectionException se non è possibile stabilire una connessione al database.
	 * @throws NoValueException se non sono presenti valori nella tabella.
	 */

	public Data(String tableName) throws SQLException, EmptySetException, DatabaseConnectionException, NoValueException {
	    try {
	        DbAccess dbAccess = new DbAccess();
	        TableData tableData = new TableData(dbAccess);
	        data = tableData.getDistinctTransazioni(tableName);
	        TableSchema tableSchema = new TableSchema(dbAccess, tableName);
	        attributeSet = new LinkedList<>();
	        for (int i = 0; i < tableSchema.getNumberOfAttributes(); i++) {
	            TableSchema.Column col = tableSchema.getColumn(i);
	            if (col.isNumber()) {
	                attributeSet.add(new ContinuousAttribute(col.getColumnName(), i, -Double.MAX_VALUE, Double.MAX_VALUE));
	            } else {
	                Set<Object> distinctValues = new TableData(dbAccess).getDistinctColumnValues(tableName, col);
	                String[] valuesArray = distinctValues.stream().map(Object::toString).toArray(String[]::new);
	                attributeSet.add(new DiscreteAttribute(col.getColumnName(), i, valuesArray));
	            }
	        }
	        numberOfExamples = data.size();
	    } catch (SQLException e) {
	        String msg = e.getMessage();
	        if (msg == null || msg.isEmpty()) {
	            throw new SQLException("Tabella " + tableName + " non trovata o inesistente.");
	        } else {
	            throw e;
	        }
	    }
	}

	/**
	 * Comportamento: Restituisce la cardinalità dell'insieme di transizioni.
	 *
	 * @return un intero rappresentante il numero di transazioni presenti in data.
	 */
	public int getNumberOfExamples() {
		return data.size();
	}

	/**
	 * Comportamento: Restituisce la dimensione di attributeSet, ovvero il grado del DB
	 *
	 * @return un intero rappresentante il numero di attributi del DB, cioè la dimensione del DB.
	 */
	public int getNumberOfAttributes(){
		return attributeSet.size();
	}

	/**
	 * Comportamento: Restituisce attributeSet
	 *
	 * @return riferimento ad attributeSet.
	 */
	public List<Attribute> getAttributeSchema(){
		return attributeSet;
	}

	/**
	 * Comportamento: Restituisce l'oggetto Example che rappresenta la transazione all'indice specificato.
	 * @param exampleIndex l'indice della transazione desiderata.
	 * @return l'oggetto Example corrispondente alla transazione.
	 */
	public Object getValue(int exampleIndex, int attributeIndex) {
		return data.get(exampleIndex).get(attributeIndex);
	}





	@Override
	/**
	 * Comportamento: Restituisce una rappresentazione in forma di stringa dell'oggetto Data.
	 * La stringa contiene i nomi degli attributi seguiti dai valori di ogni esempio.
	 *
	 * @return una stringa che rappresenta l'oggetto Data.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();

		// Intestazione: nomi degli attributi
		for (int i = 0; i < attributeSet.size(); i++) {
			sb.append(attributeSet.get(i).getName());
			if (i < attributeSet.size() - 1) sb.append(",");
			else sb.append("\n");
		}

		// Riga per ogni esempio
		for (int i = 0; i < data.size(); i++) {
			sb.append(i).append(":");
			Example ex = data.get(i);
			for (int j = 0; j < attributeSet.size(); j++) {
				sb.append(ex.get(j));
				if (j < attributeSet.size() - 1) sb.append(",");
			}
			sb.append("\n");
		}

		return sb.toString();
	}



	/**
	 * Comportamento: Restituisce un oggetto Tuple che rappresenta l'insieme di attributi esplicativi
	 * per l'esempio all'indice specificato.
	 * @param index l'indice dell'esempio desiderato.
	 * @return un oggetto Tuple contenente gli attributi esplicativi dell'esempio.
	 */
	public Tuple getItemSet(int index) {
		int numExplanatory = getNumberOfExplanatoryAttributes();
		Tuple tuple = new Tuple(numExplanatory);

		Example example = data.get(index);
		for (int i = 0; i < numExplanatory; i++) {
			Attribute attribute = attributeSet.get(i);
			Object value = example.get(i);

			if (attribute instanceof DiscreteAttribute) {
				tuple.add(new DiscreteItem(attribute, (String) value), i);
			} else if (attribute instanceof ContinuousAttribute) {
				tuple.add(new ContinuousItem(attribute, (Double) value), i);
			}
		}
		return tuple;
	}

	/**
	 * Comportamento: Restituisce il numero di attributi esplicativi.
	 * Si assume che l'ultimo attributo sia la variabile target.
	 *
	 * @return un intero rappresentante il numero di attributi esplicativi.
	 */
	public int getNumberOfExplanatoryAttributes() {
		return attributeSet.size() - 1; // Assuming the last attribute is the target variable
	}
}
