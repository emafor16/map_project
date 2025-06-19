package ClientExtension;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe che gestisce il menu principale,
 * fornisce la scelta tra lettura e scoperta di cluster.
 */
public class ControllerMenu {
    /**
     * metodo che apre la finestra per l'opzione di Scoperta
     * @param actionEvent
     * @throws IOException
     */
    public void learningFromDb(ActionEvent actionEvent) throws IOException{
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Utility.newWindow(stage, getClass(), "/Resources/DBconnection.fxml", "K-Means");
    }

    /**
     * metodo che apre la finestra per l'opzione di Lettura
     * @param actionEvent
     * @throws IOException
     */
    public void learningFromFile(ActionEvent actionEvent) throws IOException{
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Utility.newWindow(stage, getClass(), "/Resources/Lettura.fxml", "K-Means");
    }
}
