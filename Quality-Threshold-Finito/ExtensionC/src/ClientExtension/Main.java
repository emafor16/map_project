package ClientExtension;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Classe contenente il metodo principale main, apre la finestra iniziale di connessione al Server.
 */
public class Main extends Application {

    @Override
    /**
     * Metodo che apre la finestra iniziale di connessione al Server.
     * @param stage La finestra principale dell'applicazione.
     * @throws Exception Se si verifica un errore durante l'apertura della finestra.
     */
    public void start(Stage stage) throws Exception  {
        ClientExtension.Utility.newWindow(stage, getClass(), "/Resources/QT.fxml", "Quality-Threshold");
    }

    /**
     * Metodo principale dell'applicazione, avvia l'applicazione JavaFX.
     * @param args Argomenti della riga di comando (non utilizzati).
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}