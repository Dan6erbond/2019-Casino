/* Autor: RaviAnand Mohabir
 * Firma: BBBaden
 * Version: 2.8.4
 * Erstell-Datum: 10. April 2019
 * Letzte Bearbeitung: 04. Mai 2019
 */
package ch.bbbaden.casino;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SceneManager {    
    
    public static String homeFXML;
    private static SceneManager sceneManager;

    private static Stage stage;
    private static Scene homeScene;

    private SceneManager() {
        sceneManager = this;
    }

    public static SceneManager getInstance() {
        if (sceneManager == null) {
            sceneManager = new SceneManager();
        }
        return sceneManager;
    }
    
    public void setHome(String fxml) throws IOException{
        changeScene(fxml);
        homeFXML = fxml;
    }

    public Object changeScene(String fxml) throws IOException {
        Tuple<Stage, Object> window;
        if (stage == null) {
            window = openWindow(fxml);
            stage = window.x;
            return window.y;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml)); // instead of using a static reference to the FXMLLoader, we create an instance so that the controller can be returned as an Object
        Parent root = loader.load(); // loads the given FXML file
        Object controller = loader.getController(); // returns the controller as an Object

        Scene scene = new Scene(root);
        stage.setScene(scene);

        return controller;
    }
    
    public Tuple<Stage, Object> openWindow(String fxml) throws IOException {
        Stage s = new Stage(StageStyle.UTILITY);
        s.setResizable(false);
        
        s.setOnHidden((event) -> { // setOnCloseRequest() only works for external requests
            try {
                if (s.getScene() != homeScene) { // making sure we aren't in the scene that we shouldn't be forwarding to
                    stage = openWindow(SceneManager.homeFXML).x; // using a static reference to ensure changes aren't ignored
                }
            } catch (IOException ex) {
                Logger.getLogger(SceneManager.class.getName()).log(Level.SEVERE, null, ex); // not an expected exception and must be fixed
            }
        });
        
        if (homeFXML == null){
             homeFXML = "/fxml/Selection.fxml"; // setting a default for rare events
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        Object controller = loader.getController();

        Scene scene = new Scene(root);

        scene.getStylesheets().add("https://fonts.googleapis.com/css?family=Roboto");
        scene.getStylesheets().add("https://fonts.googleapis.com/css?family=Aldrich");

        if (fxml.equals(homeFXML)) {
            homeScene = scene;
        }

        s.setTitle("Casino");
        s.setScene(scene);
        s.show();

        return new Tuple(s, controller);
    }
}
