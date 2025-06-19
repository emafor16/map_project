package data;

/**
 * Classe eccezione personalizzata che estende la superclasse Exception.
 *
 * Modella una eccezione controllata da considerare qualora il numero k di cluster inserito
 * da tastiera è maggiore maggiore rispetto al numero di centroidi generabili dall'insieme di transazioni.
 */
public class OutOfRangeSampleSize extends Exception{
    /**
     * Costruttore che stampa il messaggio di errore
     * contenuto nella stringa passata come parametro.
     * Richiama il costruttore della superclasse Exception.
     *
     * @param msg stringa contenente il messaggio di errore.
     */
    public OutOfRangeSampleSize(String msg){
        super (msg);
    }
}