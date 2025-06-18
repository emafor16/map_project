package mining;

import data.Data;
import data.Tuple;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class Cluster implements Iterable<Integer> , Comparable<Cluster> {

	private Tuple centroid;

	private Set<Integer> clusteredData;

	Cluster(Tuple centroid){
		this.centroid=centroid;
		this.clusteredData=new HashSet<>();
		
	}
		
	Tuple getCentroid(){
		return centroid;
	}
	
	//return true if the tuple is changing cluster
	boolean addData(int id){
		return clusteredData.add(id);
		
	}
	
	//verifica se una transazione è clusterizzata nell'array corrente
	boolean contain(int id){
		return clusteredData.contains(id);
	}
	

	//remove the tuple that has changed the cluster
	void removeTuple(int id){
		clusteredData.remove(id);
		
	}
	
	public int getSize(){
		return clusteredData.size();
	}
	
	
	public Iterator<Integer> iterator(){
		return clusteredData.iterator();
	}
	
	/*public String toString(){
		String str="Centroid=(";
		for(int i=0;i<centroid.getLength();i++)
			str+=centroid.get(i);
		str+=")";
		return str;
		
	}*/
	

	/*public String toString(Data data){
		String str="Centroid=(";
		for(int i=0; i < centroid.getLength(); i++)
			str += centroid.get(i) + " ";
		if (clusteredData.size() > 0) {
			int exampleIndex = clusteredData.toArray()[0];
			str += data.getValue(exampleIndex, data.getNumberOfAttributes() - 1);
		}

		str+=")\nExamples:\n";
		int array[]=clusteredData.toArray();
		for(int i=0;i<array.length;i++){
			str+="[";
			for(int j=0; j < data.getNumberOfAttributes(); j++)
				str += data.getValue(array[i], j) + " ";
			str+="] dist="+getCentroid().getDistance(data.getItemSet(array[i]))+"\n";
			
		}
		str+="\nAvgDistance="+getCentroid().avgDistance(data, array);
		return str;
		
	}*/

	public int compareTo(Cluster other) {
		return Integer.compare(this.getSize(), other.getSize());
	}

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
