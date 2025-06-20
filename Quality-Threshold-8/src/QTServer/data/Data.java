package QTServer.data;

import QTServer.database.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Data {


	private List<Example> data = new ArrayList<>();
	private int numberOfExamples;
	private List<Attribute> attributeSet;


	public Data(String tableName) throws SQLException, EmptySetException, DatabaseConnectionException, NoValueException {
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
	}

	public int getNumberOfExamples() {
		return data.size();
	}


	public int getNumberOfAttributes(){
		return attributeSet.size();
	}

	public List<Attribute> getAttributeSchema(){
		return attributeSet;
	}

	public Object getValue(int exampleIndex, int attributeIndex) {
		return data.get(exampleIndex).get(attributeIndex);
	}





	@Override
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



	//Crea e restituisce un oggetto di Tuple che modella come sequenza di coppie Attributo-valore la i-esima riga in data
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

	public int getNumberOfExplanatoryAttributes() {
		return attributeSet.size() - 1; // Assuming the last attribute is the target variable
	}
}
