package data;

import java.sql.SQLException;
import java.util.*;

import database.*;

/**
 * Classe che si occupa di modellare l'insieme di transazioni o tuple.
 */
public class Data {
	/**
	 * matrice MxN di tipo Object dove ogni riga modella una transazione
	 */
	private List<Example> data;
	/**
	 * cardinalità dell'insieme di transazioni (numero di righe in data)
	 */
	private int numberOfExamples;
	/**
	 * Lista degli attributi in ciascuna tupla (schema della tabella di dati)
	 */
    private static List<Attribute> attributeSet;
	/**
	 * stringa contenente lo schema della tabella in data
	 */
    private String schemaTabella;
	/**
	 * numero di tuple distinte in data
	 */
	private int distinctTuples;

	/**
	 * Il costruttore di data si occupa di caricare i dati di addestramento da una tabella della base di dati.
	 * Il nome della tabella è un parametro del costruttore
	 * Inizializza la matrice data [ ][ ] con transazioni di esempio
	 * Inizializza attributeSet creando cinque oggetti di tipo DiscreteAttribute, uno per ciascun attributo
	 * Inizializza numberOfExamples
	 * Utilizza la classe database.Example
	 *
	 * Comportamento: costruttore della classe Data. Carica i dati di addestramento da una tabella della base di dati.
	 * Il nome della tabella è un parametro del costruttore.
	 *
	 * @param table riferimento ad un oggetto di tipo String contenente la tabella.
	 * @throws SQLException
	 * @throws DatabaseConnectionException
	 * @throws EmptySetException
	 * @throws NoValueException
	 */
	public Data(String table) throws SQLException, DatabaseConnectionException, EmptySetException, NoValueException {
		DbAccess db = new DbAccess();
		db.initConnection();
		TableData td = new TableData(db);
		TableSchema ts = new TableSchema(db, table);
		data = td.getDistinctTransazioni(table);
		
		// numberOfExamples
		numberOfExamples = data.size();
		//explanatory Set
		attributeSet = new LinkedList<Attribute>();

		for (int i = 0; i < ts.getNumberOfAttributes(); i++) {
            if (ts.getColumn(i).isNumber()) {
                attributeSet.add(new ContinuousAttribute(ts.getColumn(i).getColumnName(), i, (float) td.getAggregateColumnValue(table, ts.getColumn(i), QUERY_TYPE.MIN), (float) td.getAggregateColumnValue(table, ts.getColumn(i), QUERY_TYPE.MAX)));
            } else {
                attributeSet.add(new DiscreteAttribute(ts.getColumn(i).getColumnName(), i, td.getDistinctColumnValues(table, ts.getColumn(i)).toArray(new String[0])));
            }
        }
		db.closeConnection();

		//distinctTuples
		distinctTuples = countDistinctTuples();
	}

	/**
	 * Comportamento: Restituisce la cardinalità dell'insieme di transizioni.
	 *
	 * @return un intero rappresentante il numero di transazioni presenti in data.
	 */
	public int getNumberOfExamples(){
		return numberOfExamples;
	}

	/**
	 * Comportamento: Restituisce la dimensione di attributeSet, ovvero il grado del DB
	 *
	 * @return un intero rappresentante il numero di attributi del DB, cioè la dimensione del DB.
	 */
	public static int getNumberOfAttributes(){
		return attributeSet.size();
	}

	/**
	 * Comportamento: Restituisce attributeSet
	 *
	 * @return riferimento ad attributeSet.
	 */
	List<Attribute> getAttributeSchema(){
        return attributeSet;
    }

	/**
	 * Comportamento: restituisce il numero di attributi esplicativi
	 *
	 * @return intero contenente la dimensione di attributeSet.
	 */
	public int getNumberOfExplanatoyAttributes(){
		return attributeSet.size();
	}

	/**
	 * Restituisce l'oggetto di data in posizione [exampleIndex][attributeIndex].
	 *
	 * @param exampleIndex   indice di riga per la matrice data che corrisponde ad una specifica transizione.
	 * @param attributeIndex indice di colonna per un attributo.
	 * @return un oggetto rappresentante il valore assunto in data dall'attributo in posizione [exampleIndex - attributeIndex].
	 */
	public Object getAttributeValue(int exampleIndex, int attributeIndex){
		return data.get(exampleIndex).get(attributeIndex);
	}

	/**
	 * Comportamento: crea una stringa in cui memorizza lo schema della tabella e
	 * le transazioni memorizzate in data, opportunamente enumerate.
	 *
	 * @return stringa contenente lo schema.
	 */
	public String toString(){
		schemaTabella = "Outlook, Temperature, Humidity, Wind, Playtennis";
		StringBuilder sb = new StringBuilder();
		sb.append("Schema tabella: ").append(schemaTabella).append("\n");
		sb.append("Transazioni:\n");
		for (int i = 0; i < data.size(); i++) {
			sb.append(i+1).append(") ").append(data.get(i)).append("\n");
		}
		return sb.toString();
	}

	/**
	 * Comportamento: restituisce array, di k interi rappresentanti gli indici di riga in data
	 * per le 5tuple inizialmente scelte come centroidi
	 *
	 * @param k intero contenente il numero di cluster da generare.
	 * @return array contenente i k clusters.
	 */
	public int[] sampling(int k) throws OutOfRangeSampleSize{
		if(k <= 0 || k > distinctTuples){
			throw new OutOfRangeSampleSize("Il numero (k) di cluster deve essere compreso tra 1 e " + distinctTuples);
		} else {
			int centroidIndexes[] = new int[k];
			//choose k random different centroids in data.
			Random rand = new Random();
			rand.setSeed(System.currentTimeMillis());
			for (int i = 0; i < k; i++) {
				boolean found = false;
				int c;
				do {
					found = false;
					c = rand.nextInt(getNumberOfExamples());
					// verify that centroid[c] is not equal to a centroide already stored in CentroidIndexes
					for (int j = 0; j < i; j++)
						if (compare(centroidIndexes[j], c)) {
							found = true;
							break;
						}
				}
				while (found);
				centroidIndexes[i] = c;
			}
			return centroidIndexes;
		}
	}

	/**
	 * Comportamento: restituisce vero se le due righe di data contengono gli stessi valori, falso altrimenti
	 *
	 * @param i indice di riga.
	 * @param j indice di colonna.
	 * @return boolean contenente il valore di verità elaborato.
	 */
	private boolean compare(int i,int j) {
		boolean uguali;
		if (i == j)
			uguali=true;
		else
			uguali=false;
		return uguali;
	}

	/**
	 * Comportamento: Conta il numero di transazioni distinte memorizzate in data
	 * @return numero di tuple distinte.
	 */
	private int countDistinctTuples(){
		int distinctTuples = getNumberOfExamples();

		for(int i=0; i<getNumberOfExamples();i++){
			for(int j=i+1; j<getNumberOfExamples();j++)
				if(compare(i,j)) distinctTuples--;

		}
		return distinctTuples;
	}

	/**
	 * Comportamento: Determina il valore che occorre più frequentemente per attribute
	 * nel sottoinsieme di dati individuato da idList
	 *
	 * @param idList Set di Integer contenente i numeri di riga.
	 * @param attribute riferimento ad un oggetto di tipo DiscreteAttribute.
	 * @return String contenente il valore che occore più frequentemente.
	 */
	private String computePrototype(Set<Integer> idList, DiscreteAttribute attribute) {
        Map<Object, Integer> counterMap = new HashMap<>();
        for (String attValue : attribute) {
            counterMap.put(attValue, attribute.frequency(this, idList, attValue));
        }
        Object proto = null;
        int maxVal = Integer.MIN_VALUE;
        for (Map.Entry<Object, Integer> entry : counterMap.entrySet())
            if (entry.getValue() > maxVal) {
                maxVal = entry.getValue();
                proto = entry.getKey();
            }
        return (String) proto;
    }

	/**
	 * Comportamento: Determina il valore prototipo come media dei valori osservati per attribute nelle
	 * transazioni di data aventi indice di riga in idList
	 *
	 * @param idList Set di Integer contenente i numeri di riga.
	 * @param attribute riferimento ad un oggetto di tipo ContinuousAttribute.
	 * @return Double contenente il valore prototipo come media.
	 */
	private Double computePrototype(Set<Integer> idList, ContinuousAttribute attribute) {
		double sum = 0;
		for (Integer i : idList)
			sum += ((Number) getAttributeValue(i, attribute.getIndex())).doubleValue();
		return sum / idList.size();
	}

	/**
	 * Comportamento: usa lo RTTI per determinare se attribute riferisce una istanza di ContinuousAttribute o di DiscreteAttribute.
	 * 	Nel primo caso invoca computePrototype(idList, (ContinuousAttribute)attribute)
	 * 	altrimenti computePrototype(idList, (DiscreteAttribute)attribute);
	 *
	 * @param idList Set di Integer contenente i numeri di riga.
	 * @param attribute riferimento ad un oggetto di tipo Attribute.
	 * @return Object di tipo ContinuousAttribute o DiscreteAttribute.
	 */
	Object computePrototype(Set<Integer> idList, Attribute attribute) {
		if (attribute instanceof ContinuousAttribute)
			return computePrototype(idList, (ContinuousAttribute)attribute);
		else
		return computePrototype(idList, (DiscreteAttribute)attribute);
	}

	/**
	 * Comportamento: Crea un istanza di Tuple che modelli la transazione con indice di riga index in data.
	 * 	Restituisce il riferimento a tale istanza.
	 * 	Usa lo RTTI per distinguere tra ContinuousAttribute e DiscreteAttribute
	 *
	 * @param index indice di riga.
	 * @return riferimento all'istanza creata.
	 */
	public Tuple getItemSet (int index){
		Tuple t=new Tuple(attributeSet.size());
		for(int i=0;i<attributeSet.size();i++){
			t.add(attributeSet.get(i) instanceof DiscreteAttribute ? new DiscreteItem(attributeSet.get(i), data.get(index).get(i)) : new ContinuousItem(attributeSet.get(i), data.get(index).get(i)), i);
			}
		return t;
	}
	
}