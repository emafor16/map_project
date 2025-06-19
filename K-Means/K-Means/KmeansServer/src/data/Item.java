package data;

import java.io.Serializable;
import java.util.Set;

/**
 * Classe astratta che modella un generico item (coppia attributo-valore, per esempio Outlook=Sunny ).
 *
 * Implementa l'interfaccia Serializable cosi che lo stato dell'oggetto può essere serializzato
 * in uno stream per essere letto o scritto tilizzando le classi ObjectOutputStream e ObjectInputStream.
 */
public abstract class Item implements Serializable {
    /**
     * attributo coinvolto nell'item.
     */
    private Attribute attribute;
    /**
     * valore assegnato all'attributo.
     */
    private Object value;

    /**
     * inizializza i valori dei membri attributi.
     *
     * @param attribute oggetto di tipo Attributo per inizializzare attribute.
     * @param value oggetto per inizializzare value.
     */
    Item(Attribute attribute, Object value) {
        this.attribute = attribute;
        this.value = value;
    }

    /**
     * Comportamento: restituisce attribute.
     *
     * @return un oggetto di tipo Attribute coinvolto nell'Item.
     */
    Attribute getAttribute() {
        return attribute;
    }

    /**
     * Comportamento: restituisce value.
     *
     * @return un oggetto rappresentante il valore assegnato all'attributo.
     */
    Object getValue() {
        return value;
    }

    /**
     * Comportamento: restituisce value.
     *
     * @return una rappresentazione in stringa dell'oggetto value.
     */
    public String toString() {
        return value.toString();
    }

    /**
     * Implementazione differente a seconda che l'item sia di tipo discreto o di tipo continuo.
     */
    abstract double distance (Object a);

    /**
     * Comportamento: modifica il membro value, assegnandogli il valore restituito
     * da data.computePrototype(clusteredData, attribute)
     * Distingue se l'Item è di tipo discreto o continuo.
     *
     * @param data riferimento ad un oggetto della classe Data.
     * @param clusteredData insieme di indici delle righe della matrice in data che formano il cluster.
     */
	public void update(Data data, Set<Integer> clusteredData) {
    if (attribute instanceof ContinuousAttribute) {
        value = data.computePrototype(clusteredData, (ContinuousAttribute) attribute);
    } else if (attribute instanceof DiscreteAttribute) {
        value = data.computePrototype(clusteredData, (DiscreteAttribute) attribute);
    }
}
}
