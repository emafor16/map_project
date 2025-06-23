package data;

import java.io.Serializable;

/**
 * Classe DiscreteItem che estende la classe Item e
 * rappresenta una coppia Attributo discreto-valore discreto
 *     (per esempio Outlook=Sunny ).
 */
    public class DiscreteItem extends Item implements Serializable {

        /**
         * Comportamento: invoca il costruttore della classe madre.
         *
         * @param attribute valore con cui inizializzare l'attributo discreto.
         * @param value valore con cui inizializzare il valore.
         */
        public DiscreteItem(Attribute attribute, String value) {
            super(attribute, value);
        }

        /**
         * Comportamento: Restituisce 0 se (getValue().equals(a)) , 1 altrimenti.
         *
         * @param a oggetto sul quale effettuare la verifica.
         * @return valore di verità 0 o 1.
         */
        public double distance(Object a) {
            if (getValue().equals(a)) {
                return 0.0; // Nessuna distanza se i valori sono uguali
            } else {
                return 1.0; // Distanza unitaria per valori diversi
            }
        }
    }
