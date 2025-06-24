package data;

import java.io.Serializable;

/**
 * Classe che modella modella una coppia Attributo continuo - valore numerico.
 * (e.g., Temperature = 30.5)
 *
 * Estende la classe astratta Item.
 */
public class ContinuousItem extends Item implements Serializable {


    /**
     * Comportamento: Invoca il costruttore della classe madre con i parametri passati in input
     *
     * @param attribute attributo continuo.
     * @param value intervallo.
     */
    public ContinuousItem(Attribute attribute, Double value) {
        super(attribute, value);
    }


    @Override
    /**
     * Comportamento: Determina la distanza (in valore assoluto) tra il valore
     * scalato memorizzato nello item corrente (this.getValue()) e quello
     * scalato associato al parametro a.
     *
     * @param a contiene il secondo valore scalato con cui effettuare il confronto
     * @return double rappresentante la distanza tra i due valori scalati
     */
    public double distance(Object a) {
        if (!(a instanceof Double)) {
            throw new IllegalArgumentException("Il parametro deve essere di tipo Double.");
        }

        double thisScaled = ((ContinuousAttribute) getAttribute()).getScaledValue((Double) getValue());
        double otherScaled = ((ContinuousAttribute) getAttribute()).getScaledValue((Double) a);

        return Math.abs(thisScaled - otherScaled);
    }

}
