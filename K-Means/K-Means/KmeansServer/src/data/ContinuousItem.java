package data;

/**
 * Classe che modella modella una coppia Attributo continuo - valore numerico.
 * (e.g., Temperature = 30.5)
 *
 * Estende la classe astratta Item.
 */
class ContinuousItem extends Item{
    /**
     * Comportamento: Invoca il costruttore della classe madre con i parametri passati in input
     *
     * @param attribute attributo continuo.
     * @param value intervallo.
     */
    ContinuousItem(Attribute attribute, Object value) {
        super(attribute, value);
    }

    /**
     * Comportamento: Determina la distanza (in valore assoluto) tra il valore
     * scalato memorizzato nello item corrente (this.getValue()) e quello
     * scalato associato al parametro a.
     *
     * @param a contiene il secondo valore sclato con cui effettuare il confronto
     * @return double rappresentante la distanza tra i due valori scalati
     */
    double distance(Object a) {
        double thisValue = ((Number) this.getValue()).doubleValue();
        double otherValue = ((Number) a).doubleValue();
        return Math.abs(((ContinuousAttribute) this.getAttribute()).getScaledValue(thisValue) - ((ContinuousAttribute) this.getAttribute()).getScaledValue(otherValue));
    }
}