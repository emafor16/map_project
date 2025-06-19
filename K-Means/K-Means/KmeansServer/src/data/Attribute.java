package data;

import java.io.Serializable;

/**
 * Classe astratta che modella un generico attributo di tipo discreto o continuo.
 *
 * Implementa l'interfaccia Serializable cosi che lo stato dell'oggetto può essere serializzato
 * in uno stream per essere letto o scritto utilizzando le classi ObjectOutputStream e ObjectInputStream.
 */
abstract class Attribute implements Serializable {

    /**
     * Nome simbolico dell'attributo.
     */
    private String name;
    /**
     * Identificatico numerico dell'attributo.
     */
    private int index;

    /**
     * Comportamento: inizializza gli attributi con i valori passati
     * in input dai membri name e index.
     *
     * @param name  Valore che inizzializza il nome.
     * @param index Valore che inizzializza l'id numerico dell'attributo.
     */
    Attribute(String name, int index) {
        this.name=name;
        this.index=index;
    }

    /**
     * Comportamento: Restituisce il nome dell'attributo.
     *
     * @return stringa contenente il nome dell'attributo.
     */
    String getName() {
        return name;
    }

    /**
     * Comportamento: Restituisce l'id numerico dell'attributo.
     *
     * @return intero contenente l'id numerico dell'attributo.
     */
    int getIndex() {
        return index;
    }

    /**
     * Comportamento: Restituisce la stringa raprresentate lo stato dell'oggetto.
     *
     * @return stringa rappresentante il nome dell'attributo.
     */
    public String toString() {
        return name;
    }
}
