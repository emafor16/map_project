package data;

import java.io.Serializable;
import java.util.TreeSet;
import java.util.Iterator;

/**
 * DiscreteAttribute rappresenta un attributo con valori discreti.
 * Estende la classe Attribute e implementa Iterable<String>
 * per consentire l'iterazione sui valori distinti dell'attributo.
 */
public class DiscreteAttribute extends Attribute implements Iterable<String>, Serializable {

    /**
     * insieme di valori distinti per l'attributo corrente.
     */
    private TreeSet<String> values;

    /**
     * Comportamento: Invoca il costruttore della classe madre e inizializza il membro values con il parametro in input.
     *
     * @param name  valore per inizializzare il nome.
     * @param index valore per inizializzare l'id numerico dell'attributo.
     * @param strVal array di stringhe, una per ciascun valore del dominio discreto.
     */
    public DiscreteAttribute(String name, int index, String strVal[]) {
        super(name, index);
        values = new TreeSet<>();
        for (String value : strVal) {
            this.values.add(value);
        }
    }

    /**
     * Comportamento: Restituisce la dimensione di un attributo discreto.
     *
     * @return un intero rappresentante il numero di valori distinti dell'insieme values.
     */
    public int getNumberOfDistinctValues() {
        return values.size();
    }

    /**
     * Comportamento: Restituisce un iteratore sui valori distinti dell'attributo.
     *
     * @return un iteratore di tipo String sui valori distinti.
     */
    public Iterator<String> iterator() {
        return values.iterator();
    }


}
