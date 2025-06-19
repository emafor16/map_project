package mining;

import data.Data;
import data.Tuple;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe che modella un Cluster.
 *
 * @author map tutor
 */
public class Cluster implements Serializable {
	/**
	 * contiene il centroide
	 */
	private Tuple centroid;

	/**
	 * List contiene i dati clusterizzati
	 */
	private Set<Integer> clusteredData;

	/**
	 * costruttore di classe
	 * @param centroid
	 */
	Cluster(Tuple centroid){
		this.centroid=centroid;
		clusteredData=new HashSet<Integer>();
		
	}

	/**
	 * metodo get per l'attributo centroid
	 * @return restituisce i centroidi
	 */
	Tuple getCentroid(){
		return centroid;
	}

	/**
	 * calcola i centroidi del cluster
	 * @param data
	 */
	void computeCentroid(Data data){
		for(int i=0;i<centroid.getLength();i++){
			centroid.get(i).update(data, clusteredData);
			
		}	
	}

	/**
	 * Comportamento: remove the tuple that has changed the cluster
	 * @param id
	 * @return
	 */
	boolean addData(int id){
		return clusteredData.add(id);
		
	}

	/**
	 * Comportamento: verifica se una transazione è clusterizzata nell'array corrente
	 */
	boolean contain(int id){
		return clusteredData.contains(id);
	}


	/**
	 * Comportamento: remove the tuple that has changed the cluster
	 * @param id
	 */
	void removeTuple(int id){
		clusteredData.remove(id);
		
	}

	/**
	 * Overriding del metodo toString per adattarsi alla classe.
	 * @return  restituisce una stringa che rappresenta lo stato di Cluster.
	 */
	@Override
	public String toString(){
		String str="Centroid=(";
		for(int i=0;i<centroid.getLength();i++)
			str+=centroid.get(i);
		str+=")";
		return str;
		
	}

	/**
	 * Overriding del metodo toString per adattarsi alla classe con input Data
	 * @param data
	 * @return restituisce una stringa che rappresenta lo stato di Cluster.
	 */
	public String toString(Data data){
		String str="Centroid=(";
		for(int i=0;i<centroid.getLength();i++)
			str+=centroid.get(i)+ " ";
		str+=")\nExamples:\n";
		for(int k : clusteredData){
			str+="[";
			for(int j=0;j<Data.getNumberOfAttributes();j++)
				str+=data.getAttributeValue(k, j)+" ";
			str+="] dist="+getCentroid().getDistance(data.getItemSet(k))+"\n";
			
		}
		str+="\nAvgDistance="+getCentroid().avgDistance(data, clusteredData.toArray(Integer[]::new));
		return str;
		
	}

}
