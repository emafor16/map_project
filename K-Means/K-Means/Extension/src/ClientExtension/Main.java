package ClientExtension;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Classe contenente il metodo principale main, apre la finestra iniziale di connessione al Server.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception  {
        Utility.newWindow(stage, getClass(), "/Resources/K-Means.fxml", "K-Means");
    }

    public static void main(String[] args) {
        launch(args);
    }
}