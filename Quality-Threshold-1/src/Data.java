public class Data {
	
	private Object data [][];
	private int numberOfExamples;
	private Attribute attributeSet[];
	
	
	public Data(){

		numberOfExamples=14;
		data = new Object [numberOfExamples][5];


		data[0] = new Object[]{"sunny", "hot", "high", "weak", "no"};
		data[1] = new Object[]{"sunny", "hot", "high", "strong", "no"};
		data[2] = new Object[]{"overcast", "hot", "high", "weak", "yes"};
		data[3] = new Object[]{"rain", "mild", "high", "weak", "yes"};
		data[4] = new Object[]{"rain", "cool", "normal", "weak", "yes"};
		data[5] = new Object[]{"rain", "cool", "normal", "strong", "no"};
		data[6] = new Object[]{"overcast", "cool", "normal", "strong", "yes"};
		data[7] = new Object[]{"sunny", "mild", "high", "weak", "no"};
		data[8] = new Object[]{"sunny", "cool", "normal", "weak", "yes"};
		data[9] = new Object[]{"rain", "mild", "normal", "strong", "yes"};
		data[10] = new Object[]{"sunny", "mild", "normal", "strong", "yes"};
		data[11] = new Object[]{"overcast", "mild", "high", "strong", "yes"};
		data[12] = new Object[]{"overcast", "hot", "normal", "weak", "yes"};
		data[13] = new Object[]{"rain", "mild", "high", "strong", "no"};

		//explanatory Set

		attributeSet = new Attribute[5];
		
		String outlookValues[] = {"sunny", "overcast", "rain"};
		attributeSet[0] = new DiscreteAttribute("Outlook",0, outlookValues);

		String temperatureValues[] = {"hot", "mild", "cool"};
		attributeSet[1] = new DiscreteAttribute("Temperature",1, temperatureValues);

		String humidityValues[] = {"high", "normal"};
		attributeSet[2] = new DiscreteAttribute("Humidity",2, humidityValues);

		String windValues[] = {"weak", "strong"};
		attributeSet[3] = new DiscreteAttribute("Wind",3, windValues);

		String playTennisValue[] = {"yes", "no"};
		attributeSet[4] = new DiscreteAttribute("PlayTennis",4, playTennisValue);
	}
	
	public int getNumberOfExamples(){
		return numberOfExamples;
	}
	
	public int getNumberOfAttributes(){
		return attributeSet.length;
	}

	public Attribute[] getAttributeSchema(){
		return attributeSet;
	}
	
	public Object getValue(int exampleIndex, int attributeIndex){
		return data[exampleIndex][attributeIndex];
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		// Intestazione: nomi degli attributi separati da virgole
		for (int i = 0; i < attributeSet.length; i++) {
			sb.append(attributeSet[i].getName());
			if (i < attributeSet.length - 1) {
				sb.append(",");
			} else {
				sb.append("\n");
			}
		}

		// Riga per ogni esempio
		for (int i = 0; i < numberOfExamples; i++) {
			sb.append(i + 1).append(":");
			for (int j = 0; j < attributeSet.length; j++) {
				sb.append(data[i][j]);
				sb.append(",");
			}
			sb.append("\n");
		}

		return sb.toString();
	}



	public static void main(String args[]){
		Data trainingSet=new Data();
		System.out.println(trainingSet);
		
		
	}

}
