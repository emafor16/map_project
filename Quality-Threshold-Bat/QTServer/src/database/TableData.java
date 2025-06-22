package database;

import database.TableSchema.Column;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Classe che si occupa di gestire le operazioni sui dati di una tabella del database.
 * Permette di ottenere transazioni distinte, valori distinti di una colonna e valori aggregati.
 */
public class TableData {

	/**
	 * Riferimento all'oggetto DbAccess per accedere al database.
	 */
	DbAccess db;

	/**
	 * Costruttore della classe TableData.
	 * Inizializza il riferimento al DbAccess.
	 *
	 * @param db Riferimento all'oggetto DbAccess per accedere al database.
	 */
	public TableData(DbAccess db) {
		this.db=db;
	}

	/**
	 * Ottiene le transazioni distinte dalla tabella specificata.
	 * Esegue una query per estrarre le tuple distinte e le restituisce come una lista di oggetti Example.
	 *
	 * @param table Nome della tabella da cui estrarre le transazioni.
	 * @return Lista di oggetti Example che rappresentano le transazioni distinte.
	 * @throws SQLException Se si verifica un errore durante l'esecuzione della query.
	 * @throws EmptySetException Se non ci sono transazioni distinte nella tabella.
	 */
	public List<Example> getDistinctTransazioni(String table) throws SQLException, EmptySetException{
		LinkedList<Example> transSet = new LinkedList<Example>();
		Statement statement;
		TableSchema tSchema=new TableSchema(db,table);


		String query="select distinct ";

		for(int i=0;i<tSchema.getNumberOfAttributes();i++){
			Column c=tSchema.getColumn(i);
			if(i>0)
				query+=",";
			query += c.getColumnName();
		}
		if(tSchema.getNumberOfAttributes()==0)
			throw new SQLException();
		query += (" FROM "+table);

		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		boolean empty=true;
		while (rs.next()) {
			empty=false;
			Example currentTuple=new Example();
			for(int i=0;i<tSchema.getNumberOfAttributes();i++)
				if(tSchema.getColumn(i).isNumber())
					currentTuple.add(rs.getDouble(i+1));
				else
					currentTuple.add(rs.getString(i+1));
			transSet.add(currentTuple);
		}
		rs.close();
		statement.close();
		if(empty) throw new EmptySetException("Nessuna transazione distinta nella tabella " + table);


		return transSet;

	}

	/**
	 * Ottiene i valori distinti di una colonna specificata dalla tabella.
	 * Esegue una query per estrarre i valori distinti ordinati e li restituisce come un insieme.
	 *
	 * @param table Nome della tabella da cui estrarre i valori distinti.
	 * @param column Colonna di cui ottenere i valori distinti.
	 * @return Un insieme di oggetti che rappresentano i valori distinti della colonna.
	 * @throws SQLException Se si verifica un errore durante l'esecuzione della query.
	 */
	public  Set<Object>getDistinctColumnValues(String table,Column column) throws SQLException{
		Set<Object> valueSet = new TreeSet<Object>();
		Statement statement;
		TableSchema tSchema=new TableSchema(db,table);


		String query="select distinct ";

		query+= column.getColumnName();

		query += (" FROM "+table);

		query += (" ORDER BY " +column.getColumnName());



		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		while (rs.next()) {
			if(column.isNumber())
				valueSet.add(rs.getDouble(1));
			else
				valueSet.add(rs.getString(1));

		}
		rs.close();
		statement.close();

		return valueSet;

	}

	/**
	 * Ottiene il valore aggregato (minimo o massimo) di una colonna specificata dalla tabella.
	 * Esegue una query per calcolare il valore aggregato e lo restituisce.
	 *
	 * @param table Nome della tabella da cui estrarre il valore aggregato.
	 * @param column Colonna di cui ottenere il valore aggregato.
	 * @param aggregate Tipo di operazione aggregata (MIN o MAX).
	 * @return Il valore aggregato della colonna.
	 * @throws SQLException Se si verifica un errore durante l'esecuzione della query.
	 * @throws NoValueException Se non c'è un valore aggregato disponibile.
	 */
	public  Object getAggregateColumnValue(String table,Column column,QUERY_TYPE aggregate) throws SQLException,NoValueException{
		Statement statement;
		TableSchema tSchema=new TableSchema(db,table);
		Object value=null;
		String aggregateOp="";

		String query="select ";
		if(aggregate==QUERY_TYPE.MAX)
			aggregateOp+="max";
		else
			aggregateOp+="min";
		query+=aggregateOp+"("+column.getColumnName()+ ") FROM "+table;


		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		if (rs.next()) {
			if(column.isNumber())
				value=rs.getFloat(1);
			else
				value=rs.getString(1);

		}
		rs.close();
		statement.close();
		if(value==null)
			throw new NoValueException("No " + aggregateOp+ " on "+ column.getColumnName());

		return value;

	}





}
