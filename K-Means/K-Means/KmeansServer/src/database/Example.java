package database;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che si occupa di modellare ciascuna transazione.
 * La classe implementa l'interfaccia Comparable e sviluppa il metodo compareTO.
 */
public class Example implements Comparable<Example>{
	private List<Object> example=new ArrayList<Object>();

	/**
	 * aggiunge o in coda ad example
	 * @param o
	 */
	public void add(Object o){
		example.add(o);
	}

	/**
	 * restituice lo i-esimo riferimento collezionato in example
	 * @param i
	 * @return
	 */
	public Object get(int i){
		return example.get(i);
	}

	/**
	 * restituisce 0, -1, 1 sulla base del risultato
	 * del confronto. 0 se i due esempi includono gli stessi valori. Altrimenti il
	 * risultato del compareTo(...) invocato sulla prima coppia di valori in disaccordo
	 *
	 * @param ex the object to be compared.
	 * @return
	 */
	@Override
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
	 * overriding del metodo toString per adattarsi alla classe.
	 * @return restituisce una stringa che rappresenta lo stato di example
	 */
	@Override
	public String toString(){
		String str="";
		for(Object o:example)
			str+=o.toString()+ " ";
		return str;
	}
	
}