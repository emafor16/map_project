package data;

import java.io.Serializable;

    /**
 * Classe che rappresenta una tupla come sequenza di coppie attributo-valore.
 *
 * Implementa l'interfaccia Serializable cosi che lo stato dell'oggetto può essere serializzato
 * in uno stream per essere letto o scritto tilizzando le classi ObjectOutputStream e ObjectInputStream.
 */
public class Tuple implements Serializable {
    private Item [] tuple;

    /**
     * Comportamento: costruisce l'oggetto riferito da tuple.
     * @param size numero di item che costituirà la tupla.
     */
    Tuple(int size) {
        tuple=new Item[size];
    }

    /**
     * Comportamento: restituisce tuple.length
     *
     * @return un intero contenente la lunghezza di tuple.
     */
    public int getLength() {
        return tuple.length;
    }

    /**
     * Comportamento: restituisce l'item in posizione i.
     *
     * @param i posizione dell'item.
     * @return un item in posizione i.
     */
    public Item get(int i) {
        return tuple[i];
    }

    /**
     * Comportamneto: memorizza c in tuple[i].
     *
     * @param c riferimento ad oggetto di tipo Item.
     * @param i posizione all'interno di tuple.
     */
    void add(Item c, int i) {
        tuple[i] = c;
    }

    /**
     * Comportamento: determina la distanza tra la tupla riferita da obj e la
     * tupla corrente (riferita da this). La distanza è ottenuta come la somma delle
     * distanze tra gli item in posizioni eguali nelle due tuple.
     *
     * @param obj riferimento ad oggetto di tipo Tuple.
     * @return un double contenente la distanza tra le due tuple.
     */
    public double getDistance(Tuple obj) {
		double distance=0;
		for (int i=0; i<tuple.length; i++) {
			distance += get(i).distance(obj.get(i).getValue());
		}
		return distance;
	}

    /**
     * Comportamento: restituisce la media delle distanze tra la tupla
     * corrente e quelle ottenibili dalle righe della matrice in data aventi indice in clusteredData.
     *
     * @param data riferimento ad oggetto di tipo Data.
     * @param integers rappresenta clusteredData.
     * @return un double contenente la media delle distanze tra le varie tuple.
     */
    public double avgDistance(Data data, Integer[] integers){
        double p=0.0,sumD=0.0;
        for(int i=0;i<integers.length;i++){
            double d= getDistance(data.getItemSet(integers[i]));
            sumD+=d;
        }
        p=sumD/integers.length;
        return p;
    }

}