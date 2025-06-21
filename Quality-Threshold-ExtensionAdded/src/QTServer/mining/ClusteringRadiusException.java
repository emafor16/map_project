package QTServer.mining;

public class ClusteringRadiusException extends Exception {

    public ClusteringRadiusException() {
        super("Clustering fallito: tutte le tuple sono finite in un unico cluster.");
    }

    public ClusteringRadiusException(String message) {
        super(message);
    }
}
