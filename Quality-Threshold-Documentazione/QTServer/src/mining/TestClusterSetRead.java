package mining;

public class TestClusterSetRead {
    public static void main(String[] args) {
        try {
            ClusterSet cs = ClusterSet.loadFromFile("qwe.txt");
            System.out.println("File deserializzato correttamente!");
            System.out.println(cs);
        } catch (Exception e) {
            System.err.println("Errore durante la lettura/deserializzazione:");
            e.printStackTrace();
        }
    }
}
