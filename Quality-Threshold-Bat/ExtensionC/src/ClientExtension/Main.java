package ClientExtension;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Classe contenente il metodo principale main, apre la finestra iniziale di connessione al Server.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception  {
        ClientExtension.Utility.newWindow(stage, getClass(), "/Resources/QT.fxml", "Quality-Threshold");
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}