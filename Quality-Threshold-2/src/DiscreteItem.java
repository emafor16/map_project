//definire DiscreteItem che estende Item e rappresentea una coppia <attributo discreteo / valore discreteo>
public class DiscreteItem extends Item {

    // invoca il costriuttore della superclasse Item
    public DiscreteItem(Attribute attribute, String value) {
        super(attribute, value);
    }

    // restituisce 0 se (getValue().equals(a)) , altrimenti 1
    public double distance(Object a) {
        if (getValue().equals(a)) {
            return 0.0; // Nessuna distanza se i valori sono uguali
        } else {
            return 1.0; // Distanza unitaria per valori diversi
        }
    }
}
