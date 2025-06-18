public class QTMiner {

    private ClusterSet C;
    private double radius;


    public QTMiner(double radius) {
        this.C = new ClusterSet();
        this.radius = radius;
    }


    public ClusterSet getC() {
        return C;
    }

    public int compute(Data data){

        int numclusters=0;
        boolean isClustered[] = new boolean[data.getNumberOfExamples()];
        for(int i=0; i<isClustered.length; i++){
            isClustered[i] = false;
        }

        int countClastered = 0;
        while(countClastered!= data.getNumberOfExamples()) {

            //Ricerca cluster più popoloso
            Cluster c=buildCandidateCluster(data, isClustered);
            C.add(c);
            numclusters++;

            //Rimuovo tuple clusterizzate da dataset
            int clusteredTuplesId[] = c.iterator();
            for(int i=0; i<clusteredTuplesId.length; i++){
                isClustered[clusteredTuplesId[i]] = true;
            }
            countClastered += c.getSize();
        }

        return numclusters;

    }


    public Cluster buildCandidateCluster(Data data, boolean[] isClustered) {
        int numExamples = data.getNumberOfExamples();
        int numAttributes = data.getNumberOfExplanatoryAttributes();
        Cluster bestCluster = null;
        int maxSize = -1;

        for (int i = 0; i < numExamples; i++) {
            if (!isClustered[i]) {
                // Costruisci il centroide iniziale usando la tupla i-esima
                Tuple centroid = data.getItemSet(i);
                Cluster candidateCluster = new Cluster(centroid);

                for (int j = 0; j < numExamples; j++) {
                    if (!isClustered[j]) {
                        Tuple tuple = data.getItemSet(j);
                        // Aggiunge al cluster se è più vicino al centroide (qui si aggiungono tutte, anche il centroide stesso)
                        candidateCluster.addData(j); // salva solo l'indice, non la tupla
                    }
                }

                if (candidateCluster.getSize() > maxSize) {
                    bestCluster = candidateCluster;
                    maxSize = candidateCluster.getSize();
                }
            }
        }

        return bestCluster;
    }


}
