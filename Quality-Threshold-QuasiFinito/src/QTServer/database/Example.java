package QTServer.database;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta un esempio, composto da una lista di oggetti.
 * Implementa l'interfaccia Comparable per permettere il confronto tra esempi.
 */
public class Example implements Comparable<Example>{
	/**
	 * Lista che contiene gli oggetti dell'esempio.
	 */
	private List<Object> example=new ArrayList<Object>();
	/**
	 * Aggiunge un oggetto alla lista example.
	 * @param o Oggetto da aggiungere.
	 */
	public void add(Object o){
		example.add(o);
	}
	/**
	 * Restituisce l'oggetto in posizione i nella lista example.
	 * @param i Indice dell'oggetto da restituire.
	 * @return L'oggetto in posizione i.
	 */
	public Object get(int i){
		return example.get(i);
	}
	/**
	 * Confronta l'esempio corrente con un altro esempio.
	 * Restituisce 0 se gli esempi sono uguali, -1 o 1 in base al confronto dei primi valori diversi.
	 * @param ex L'oggetto Example da confrontare.
	 * @return 0, -1 o 1 in base al risultato del confronto.
	 */
	public int compareTo(Example ex) {
		
		int i=0;
		for(Object o:ex.example){
			if(!o.equals(this.example.get(i)))
				return ((Comparable)o).compareTo(example.get(i));
			i++;
		}
		return 0;
	}
	/**
	 * Restituisce una rappresentazione in stringa dell'esempio.
	 * @return Una stringa che rappresenta lo stato della lista example.
	 */
	public String toString(){
		String str="";
		for(Object o:example)
			str+=o.toString()+ " ";
		return str;
	}
	
}