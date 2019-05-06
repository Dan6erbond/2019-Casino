/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino;

import ch.bbbaden.casino.slotmachine.SlotMachineController;
import java.io.IOException;
import java.net.URL;
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

    public Object changeScene(String fxml) throws IOException {
        Tuple<Stage,Object> window;
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
    
    public static Stage getStage(){
        return stage;
    }
    
    public static Scene getHomeScene(){
        return homeScene;
    }

    public Tuple<Stage,Object> openWindow(String fxml) throws IOException {
        Stage s = new Stage(StageStyle.UTILITY);
        s.setResizable(false);
        s.setOnCloseRequest((event) -> {
            try {
                if (s.getScene() != homeScene){
                    stage = openWindow("/fxml/GamePicker.fxml").x;
                }
            } catch (IOException ex) {
                Logger.getLogger(SceneManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        Object controller = loader.getController();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("https://fonts.googleapis.com/css?family=Roboto");
        scene.getStylesheets().add("https://fonts.googleapis.com/css?family=Aldrich");        
        
        if (fxml.contains("GamePicker.fxml")){
            homeScene = scene;
        }

        s.setTitle("Casino");
        s.setScene(scene);
        s.show();

        return new Tuple(s, controller);
    }

}
