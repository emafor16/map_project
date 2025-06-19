package data;

/**
 * Classe DiscreteItem che estende la classe Item e
 * rappresenta una coppia Attributo discreto-valore discreto
 *     (per esempio Outlook=Sunny ).
 */
class DiscreteItem extends Item {

    /**
     * Comportamento: invoca il costruttore della classe madre.
     *
     * @param attribute valore con cui inizializzare l'attributo discreto.
     * @param value valore con cui inizializzare il valore.
     */
    DiscreteItem (Attribute attribute, Object value){
		super(attribute, value);
	}

    /**
     * Comportamento: Restituisce 0 se (getValue().equals(a)) , 1 altrimenti.
     *
     * @param a oggetto sul quale effettuare la verifica.
     * @return valore di verità 0 o 1.
     */
    protected double distance (Object a) {
        if (getValue().equals(a))
            return 0; 
        else
            return 1;
    }
}
