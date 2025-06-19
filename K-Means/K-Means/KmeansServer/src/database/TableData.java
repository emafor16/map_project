package database;

import database.TableSchema.Column;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Classe che modella una transazione letta dalla base di dati.
 */
public class TableData {
	/**
	 * riferimento ad oggetto di tipo DBAccess
	 */
	private DbAccess db;

	/**
	 * costruttore di classe
	 * @param db
	 */
	public TableData(DbAccess db) {
		this.db=db;
	}

	/**
	 * Ricava lo schema della tabella con nome table. 
	 * Esegue una interrogazione per estrarre le tuple distinte da tale tabella. 
	 * Per ogni tupla del resultset, si crea un oggetto, istanza della classe Example,
	 * il cui riferimento va incluso nella lista da restituire.
	 * In particolare, per la tupla corrente nel resultset, si estraggono i valori dei singoli campi,
	 * e li si aggiungono all’oggetto istanza della classe Example che si sta costruendo.
	 * Il metodo può propagare un eccezione di tipo SQLException o EmptySetException
	 */
	public List<Example> getDistinctTransazioni(String table) throws SQLException, EmptySetException{
		List<Example> list=new ArrayList<Example>();
		Statement st=db.getConnection().createStatement();
		ResultSet rs=st.executeQuery("SELECT DISTINCT * FROM "+table);
		while(rs.next()) {
			Example ex=new Example();
			for(int i=1;i<=rs.getMetaData().getColumnCount();i++) {
				ex.add(rs.getObject(i));
			}
			list.add(ex);
		}
		if(list.isEmpty())
			throw new EmptySetException("Empty set");
		return list;
	}

	/**
	 * Formula ed esegue una interrogazione SQL per estrarre i valori distinti ordinati di column e popolare un insieme da restituire
	 */
	public  Set<Object>getDistinctColumnValues(String table,Column column) throws SQLException{
		Set<Object> set=new TreeSet<Object>();
		Statement st=db.getConnection().createStatement();
		ResultSet rs=st.executeQuery("SELECT DISTINCT "+column.getColumnName()+" FROM "+table+" ORDER BY "+column.getColumnName());
		while(rs.next()) {
			set.add(rs.getObject(1));
		}
		return set;
	}

	/**
	 * Formula ed esegue una interrogazione SQL per estrarre il valore aggregato (valore minimo o valore massimo)
	 * cercato nella colonna di nome column della tabella di nome table.
	 * Il metodo solleva e propaga una NoValueException se il resultset è vuoto o il valore calcolato è pari a null
	 */
	public  Object getAggregateColumnValue(String table,Column column,QUERY_TYPE aggregate) throws SQLException,NoValueException{
		Statement st=db.getConnection().createStatement();
		ResultSet rs=st.executeQuery("SELECT "+aggregate.toString()+"("+column.getColumnName()+") FROM "+table);
		if(rs.next()) {
			Object o=rs.getObject(1);
			if(o==null)
				throw new NoValueException("No value");
			return o;
		}
		throw new NoValueException("No value");
	}
}
