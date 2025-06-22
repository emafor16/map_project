package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Classe che modella lo schema di una tabella nel database relazionale.
 * Fornisce informazioni sulle colonne della tabella, inclusi i nomi e i tipi di dati.
 */
public class TableSchema {
	DbAccess db;
	public class Column{
		private String name;
		private String type;
		Column(String name,String type){
			this.name=name;
			this.type=type;
		}
		public String getColumnName(){
			return name;
		}
		public boolean isNumber(){
			return type.equals("number");
		}
		public String toString(){
			return name+":"+type;
		}
	}
	List<Column> tableSchema=new ArrayList<Column>();

	/**
	 * Costruttore della classe TableSchema.
	 * Inizializza lo schema della tabella specificata nel database.
	 *
	 * @param db Riferimento all'oggetto DbAccess per accedere al database.
	 * @param tableName Nome della tabella di cui si vuole ottenere lo schema.
	 * @throws SQLException Se si verifica un errore durante l'accesso ai metadati del database.
	 */
	public TableSchema(DbAccess db, String tableName) throws SQLException{
		this.db=db;
		HashMap<String,String> mapSQL_JAVATypes=new HashMap<String, String>();
		//http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
		mapSQL_JAVATypes.put("CHAR","string");
		mapSQL_JAVATypes.put("VARCHAR","string");
		mapSQL_JAVATypes.put("LONGVARCHAR","string");
		mapSQL_JAVATypes.put("BIT","string");
		mapSQL_JAVATypes.put("SHORT","number");
		mapSQL_JAVATypes.put("INT","number");
		mapSQL_JAVATypes.put("LONG","number");
		mapSQL_JAVATypes.put("FLOAT","number");
		mapSQL_JAVATypes.put("DOUBLE","number");



		Connection con=db.getConnection();
		DatabaseMetaData meta = con.getMetaData();
		ResultSet res = meta.getColumns(null, null, tableName, null);

		while (res.next()) {

			if(mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME")))
				tableSchema.add(new Column(
						res.getString("COLUMN_NAME"),
						mapSQL_JAVATypes.get(res.getString("TYPE_NAME")))
				);



		}
		res.close();



	}


	/**
	 * Restituisce il numero di attributi (colonne) nella tabella.
	 *
	 * @return Il numero di colonne nello schema della tabella.
	 */
	public int getNumberOfAttributes(){
		return tableSchema.size();
	}

	/**
	 * Restituisce la colonna specificata dall'indice.
	 *
	 * @param index L'indice della colonna da restituire.
	 * @return La colonna corrispondente all'indice specificato.
	 */
	public Column getColumn(int index){
		return tableSchema.get(index);
	}


}

		     


