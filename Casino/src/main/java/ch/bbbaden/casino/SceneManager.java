/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

/**
 *
 * @author User
 */
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        Object controller = loader.getController();

        Scene scene = new Scene(root);
        stage.setScene(scene);

        return controller;
    }
    
    public Tuple<Stage, Object> openWindow(String fxml) throws IOException {
        Stage s = new Stage(StageStyle.UTILITY);
        s.setResizable(false);
        
        s.setOnHidden((event) -> {
            try {
                if (s.getScene() != homeScene) {

                    stage = openWindow(SceneManager.homeFXML).x;
                }
            } catch (IOException ex) {
                Logger.getLogger(SceneManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        if (homeFXML == null){
             homeFXML = "/fxml/Selection.fxml";
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
