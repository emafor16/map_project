package data;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Classe che modella un attributo discreto (categorico)
 * rappresenta l'insieme di valori distinti del relativo dominio.
 *
 * Estende la classe Attribute.
 *
 * Implementa l'interfaccia generics Iterable di tipo String consentendo a un oggetto
 * di essere la destinazione dell'istruzione for migliorata "for-each loop".
 */
class DiscreteAttribute extends Attribute implements Iterable<String> {

    /**
     * insieme di valori distinti per l'attributo corrente.
     */
    private final TreeSet<String> values;

    /**
     * Comportamento: Invoca il costruttore della classe madre e inizializza il membro values con il parametro in input.
     *
     * @param name valore per inizializzare il nome.
     * @param index valore per inizializzare l'id numerico dell'attributo.
     * @param valuesArrayStrings array di stringhe, una per ciascun valore del dominio discreto.
     */
    DiscreteAttribute (String name, int index, String[] valuesArrayStrings) {
        super(name, index);
       values = new TreeSet<>();
       values.addAll(Arrays.asList(valuesArrayStrings));
    }

    /**
     * Comportamento: Restituisce la dimensione di un attributo discreto.
     *
     * @return un intero rappresentate il numero di valori discreti dell'array values.
     */
    int getNumberOfDistinctValues() {
        return values.size();
    }

    /**
     * Comportamento: Determina il numero di volte che il valore v compare
     * in corrispondenza dell'attributo corrente (indice di colonna)
     * negli esempi memorizzati in data e indicizzate (per riga) da idList.
     *
     * @param data riferimento ad un oggetto Data.
     * @param idList riferimento ad un Set di Integer contenente gli indici di riga.
     * @param v valore discreto.
     *
     * @return un intero rappresentante la frequenza con cui compare il valore v.
     */
    int frequency(Data data, Set<Integer> idList, String v) {
    	int cont=0;

    	for(int i=0; i<data.getNumberOfExamples();i++) {
            if(idList.contains(i))
                if (data.getAttributeValue(i, getIndex()).equals(v))
                    cont++;
    	}
    	return cont;
    }

    /**
     * Comportamento: Restituisce un iteratore sull'elemento di tipo String.
     *
     * @return un iteratore del TreeSet values.
     */
    public Iterator<String> iterator() {
        return values.iterator();
    }

}