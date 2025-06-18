public class Tuple {

    Item [] tuple;

    // in input il numero di item che costruirà la tupla. costruisce l'oggetto riferito da tuple
    public Tuple(int size) {
        tuple = new Item[size];
    }

    public int getLength() {
        return tuple.length;
    }

    public Item get(int i) {
        if (i >= 0 && i < tuple.length) {
            return tuple[i];
        } else {
            throw new IndexOutOfBoundsException("Indice fuori dal range della tupla.");
        }
    }

    public void add(Item c, int i) {
        if (i >= 0 && i < tuple.length) {
            tuple[i] = c;
        } else {
            throw new IndexOutOfBoundsException("Indice fuori dal range della tupla.");
        }
    }

    //determina la distanza tra la tupla riferita da obj e la tupla corrente (riferita da this). La distanza è ottenuta come la somma delle distanze tra gli item in posizioni eguali nelle due tuple. Fare uso di double distance(Object a) di Item
    public double getDistance(Tuple obj) {
        if (obj == null || obj.getLength() != this.getLength()) {
            throw new IllegalArgumentException("Le tuple devono avere la stessa lunghezza e non possono essere null.");
        }

        double totalDistance = 0.0;

        for (int i = 0; i < this.getLength(); i++) {
            Item currentItem = this.get(i);
            Item otherItem = obj.get(i);

            if (currentItem == null || otherItem == null) {
                throw new NullPointerException("Gli elementi delle tuple non possono essere null.");
            }

            totalDistance += currentItem.distance(otherItem);
        }

        return totalDistance;
    }

    //doble avgDistance(Data data, int[] clusterData) restituisce la media delle distanze tra la tuplla corrente e quelle ottenibili dalle righe della matrice data aventi indice in clusterDataù
    double avgDistance(Data data, int clusteredData[]){
        double p=0.0;
        double sumD=0.0;
        for(int i=0;i<clusteredData.length;i++){
            double d= getDistance(data.getItemSet(clusteredData[i]));
            sumD+=d;
        }
        p=sumD/clusteredData.length;
        return p;
    }

}
