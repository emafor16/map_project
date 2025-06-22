package QTServer.data;

import java.io.Serializable;

/**
 * Classe astratta Item che rappresenta un elemento generico
 * con un attributo e un valore associato.
 * Implementa l'interfaccia Serializable per consentire la serializzazione
 * dell'oggetto.
 */
public abstract class Item implements Serializable {
    /**
     * Attributo coinvolto nell'item.
     */
    private Attribute attribute;
    /**
     * Valore assegnato all'attributo.
     */
    private Object value;

    /**
     * Inizializza i valori dei membri attributi.
     *
     * @param attribute oggetto di tipo Attribute per inizializzare attribute.
     * @param value oggetto per inizializzare value.
     */
    public Item(Attribute attribute, Object value) {
        this.attribute = attribute;
        this.value = value;
    }

    /**
     * Comportamento: restituisce l'attributo.
     *
     * @return un oggetto di tipo Attribute coinvolto nell'Item.
     */
    public Attribute getAttribute() {
        return attribute;
    }

    /**
     * Comportamento: restituisce il valore.
     *
     * @return un oggetto rappresentante il valore assegnato all'attributo.
     */
    public Object getValue() {
        return value;
    }

    /**
     * Comportamento: restituisce una rappresentazione in stringa del valore.
     *
     * @return una rappresentazione in stringa dell'oggetto value.
     */
    public String toString() {
        return value.toString();
    }

    /**
     * Comportamento: calcola la distanza tra il valore dell'item corrente e un altro oggetto.
     * Deve essere implementato dalle classi concrete che estendono Item.
     *
     * @param a oggetto con cui calcolare la distanza.
     * @return un double rappresentante la distanza tra i due valori.
     */
    public abstract double distance(Object a);

}
