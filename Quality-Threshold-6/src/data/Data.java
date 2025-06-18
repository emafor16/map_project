package data;

import java.util.LinkedList;
import java.util.List;

public class Data {
	
	private Object data [][];
	private int numberOfExamples;
	private List<Attribute> attributeSet;


	public Data(){

		numberOfExamples=14;
		data = new Object [numberOfExamples][5];


		data[0] = new Object[]{"sunny", 30.3, "high", "weak", "no"};
		data[1] = new Object[]{"sunny", 30.3, "high", "strong", "no"};
		data[2] = new Object[]{"overcast", 30.0, "high", "weak", "yes"};
		data[3] = new Object[]{"rain", 13.0, "high", "weak", "yes"};
		data[4] = new Object[]{"rain", 0.0, "normal", "weak", "yes"};
		data[5] = new Object[]{"rain", 0.0, "normal", "strong", "no"};
		data[6] = new Object[]{"overcast", 0.1, "normal", "strong", "yes"};
		data[7] = new Object[]{"sunny", 13.0, "high", "weak", "no"};
		data[8] = new Object[]{"sunny", 0.1, "normal", "weak", "yes"};
		data[9] = new Object[]{"rain", 12.0, "normal", "strong", "yes"};
		data[10] = new Object[]{"sunny", 12.5, "normal", "strong", "yes"};
		data[11] = new Object[]{"overcast", 12.5, "high", "strong", "yes"};
		data[12] = new Object[]{"overcast", 29.21, "normal", "weak", "yes"};
		data[13] = new Object[]{"rain", 12.5, "high", "strong", "no"};


		attributeSet = new LinkedList<>();

		attributeSet.add(new DiscreteAttribute("Outlook", 0, new String[]{"sunny", "overcast", "rain"}));
		attributeSet.add(new ContinuousAttribute("Temperature", 1, 0.0, 38.7));
		attributeSet.add(new DiscreteAttribute("Humidity", 2, new String[]{"high", "normal"}));
		attributeSet.add(new DiscreteAttribute("Wind", 3, new String[]{"weak", "strong"}));
		attributeSet.add(new DiscreteAttribute("PlayTennis", 4, new String[]{"yes", "no"}));

	}
	
	public int getNumberOfExamples(){
		return numberOfExamples;
	}
	
	public int getNumberOfAttributes(){
		return attributeSet.size();
	}

	public List<Attribute> getAttributeSchema(){
		return attributeSet;
	}
	
	public Object getValue(int exampleIndex, int attributeIndex){
		return data[exampleIndex][attributeIndex];
	}



	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		// Intestazione: nomi degli attributi separati da virgole
		for (int i = 0; i < attributeSet.size(); i++) {
			sb.append(attributeSet.get(i).getName());
			if (i < attributeSet.size() - 1) {
				sb.append(",");
			} else {
				sb.append("\n");
			}
		}

		// Riga per ogni esempio
		for (int i = 0; i < numberOfExamples; i++) {
			sb.append(i).append(":");
			for (int j = 0; j < attributeSet.size(); j++) {
				sb.append(data[i][j]);
				if (j < attributeSet.size() - 1) {
					sb.append(",");
				}
			}
			sb.append("\n");
		}

		return sb.toString();
	}


	//Crea e restituisce un oggetto di Tuple che modella come sequenza di coppie Attributo-valore la i-esima riga in data
	public Tuple getItemSet(int index) {
		int numExplanatory = getNumberOfExplanatoryAttributes();
		Tuple tuple = new Tuple(numExplanatory);

		for (int i = 0; i < numExplanatory; i++) {
			Attribute attribute = attributeSet.get(i);
			Object value = data[index][i];

			if (attribute instanceof DiscreteAttribute) {
				tuple.add(new DiscreteItem(attribute, (String) value),i);
			} else if (attribute instanceof ContinuousAttribute) {
				tuple.add(new ContinuousItem(attribute, (Double) value), i);
			} else {
				throw new IllegalArgumentException("Tipo di attributo non supportato: " + attribute.getClass().getName());
			}
		}
		return tuple;
	}




	public int getNumberOfExplanatoryAttributes() {
		return attributeSet.size() - 1; // Assuming the last attribute is the target variable
	}
}
