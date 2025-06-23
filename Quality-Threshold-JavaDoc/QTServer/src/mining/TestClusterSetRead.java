package mining;

/** * Classe di test per la lettura e deserializzazione di un ClusterSet da un file.
 * Utilizza il metodo statico loadFromFile della classe ClusterSet.
 */
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
