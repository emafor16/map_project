package data;

import java.io.Serializable;

/**
 * Classe che modella un attributo continuo (numerico)
 * ne rappresenta il dominio con i valori [min, max].
 *
 * Estende la classe Attribute.
 */
public class ContinuousAttribute  extends Attribute  implements Serializable {

    /**
     * Estremo superiore dell'intervallo.
     */
    private double max;
    /**
     * Estremo inferiore dell'intervallo.
     */
    private double min;

    /**
     * Comportamento: Invoca il costruttore della classe madre e inizializza i membri aggiunti per estensione.
     *
     * @param name  valore per inizializzare il nome.
     * @param index valore per inizializzare l'id numerico dell'attributo.
     * @param minValue   valore per inizializzare l'estremo inferiore dell'intervallo.
     * @param maxValue   valore per inizializzare l'estremo superiore dell'intervallo.
     */
    public ContinuousAttribute(String name, int index, double minValue, double maxValue) {
        super(name, index);
        this.min = minValue;
        this.max = maxValue;
    }

    /**
     * Comportamento: Calcola e restituisce il valore normalizzato del parametro v passato in input
     * La normalizzazione ha come codominio l'intervallo [0,1].
     *
     * @param v valore da normalizzare.
     *
     * @return valore normalizzato.
     */
    public double getScaledValue(double v) {
        if (v < min || v > max) {
            throw new IllegalArgumentException("Valore fuori dall'intervallo: " + v + " non è compreso tra " + min + " e " + max);
        }
        return (v - min) / (max - min);
    }
}
