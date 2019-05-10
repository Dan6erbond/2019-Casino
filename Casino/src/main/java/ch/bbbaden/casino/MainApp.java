/* Autor: RaviAnand Mohabir
 * Firma: BBBaden
 * Version: 1.3.1
 * Erstell-Datum: 10. April 2019
 * Letzte Bearbeitung: 10. Mai 2019
 */

package ch.bbbaden.casino;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        SceneManager.getInstance().setHome("/fxml/Login.fxml");
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
