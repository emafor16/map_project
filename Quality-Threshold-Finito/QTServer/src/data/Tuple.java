package data;

import java.io.Serializable;

/**
 * Classe Tuple che rappresenta una tupla di Item.
 * Implementa l'interfaccia Serializable per consentire la serializzazione dell'oggetto.
 */
public class Tuple implements Serializable {

    Item[] tuple;

    /**
     * Comportamento: inizializza la tupla con un array di Item di dimensione size.
     *
     * @param size Dimensione dell'array di Item che compone la tupla.
     */
    public Tuple(int size) {
        tuple = new Item[size];
    }

    /**
     * Comportamento: restituisce la lunghezza delle tuple.
     *
     * @return tuple.legth
     */
    public int getLength() {
        return tuple.length;
    }

    /**
     * Comportamento: restituisce restituisce l'item in posizione i della tupla.
     *
     * @param i Indice dell'item da restituire.
     */
    public Item get(int i) {
        if (i >= 0 && i < tuple.length) {
            return tuple[i];
        } else {
            throw new IndexOutOfBoundsException("Indice fuori dal range della tupla.");
        }
    }

    /**
     * Comportamento: memorizza l'item c in posizione i della tupla.
     *
     * @param c Item da memorizzare nella tupla.
     */
    public void add(Item c, int i) {
        if (i >= 0 && i < tuple.length) {
            tuple[i] = c;
        } else {
            throw new IndexOutOfBoundsException("Indice fuori dal range della tupla.");
        }
    }

    /**
     * Comportamento: determina la distanza tra la tupla riferita da obj e la tupla corrente (this).
     * La distanza è la somma delle distanze tra gli item in posizioni uguali nelle due tuple.
     * Usa il metodo distance(Object a) di Item.
     *
     * @param obj Tupla con cui calcolare la distanza.
     */
    public double getDistance(Tuple obj) {
        if (obj == null || obj.getLength() != this.getLength()) {
            throw new IllegalArgumentException("Le tuple devono avere la stessa lunghezza e non possono essere null.");
        }
        double totalDistance = 0.0;
        for (int i = 0; i < this.getLength(); i++) {
            Item thisItem = this.get(i);
            Item otherItem = obj.get(i);
            if (thisItem == null || otherItem == null) {
                throw new NullPointerException("Gli elementi delle tuple non possono essere null.");
            }
            totalDistance += thisItem.distance(otherItem.getValue());
        }
        return totalDistance;
    }

    /**
     * Comportamento: calcola la distanza tra la tupla corrente e l'oggetto passato come parametro. L'oggetto deve essere un ItemSet
     * Restituisce la media delle distanze tra la tupla corrente e quelle ottenibili dalle righe della matrice in data aventi indice in clusteredData.
     *
     * @param data oggetto di tipo Data contenente le transazioni.
     */
    double avgDistance(Data data, int clusteredData[]){
        double p=0.0,sumD=0.0;
        for(int i=0;i<clusteredData.length;i++){
            double d= getDistance(data.getItemSet(clusteredData[i]));
            sumD+=d;
        }
        p=sumD/clusteredData.length;
        return p;
    }


}
