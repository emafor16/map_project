package data;

/**
 * Classe che modella un attributo continuo (numerico)
 * ne rappresenta il dominio con i valori [min, max].
 *
 * Estende la classe Attribute.
 */
class ContinuousAttribute extends Attribute{

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
     * @param min   valore per inizializzare l'estremo inferiore dell'intervallo.
     * @param max   valore per inizializzare l'estremo superiore dell'intervallo.
     */
    ContinuousAttribute (String name, int index, double min, double max) {
        super(name, index);
        this.min=min;
        this.max=max;
    }

    /**
     * Comportamento: Calcola e restituisce il valore normalizzato del parametro v passato in input
     * La normalizzazione ha come codominio l'intervallo [0,1].
     *
     * @param v valore da normalizzare.
     *
     * @return valore normalizzato.
     */
    double getScaledValue(double v) {
        v=(v-min)/(max-min);
        return v;
    }
}