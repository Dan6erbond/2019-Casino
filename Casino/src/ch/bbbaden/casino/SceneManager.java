/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino;

import java.io.IOException;
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
    private Stage stage;

    private SceneManager() {
        sceneManager = this;
    }

    public static SceneManager getInstance() {
        if (sceneManager == null) {
            sceneManager = new SceneManager();
        }
        return sceneManager;
    }

    public void changeScene(String fxml) throws IOException {
        if (stage == null) {
            stage = new Stage(StageStyle.UTILITY);
        }
        
        Parent root = FXMLLoader.load(getClass().getResource(fxml));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
    
    public Stage getStage(){
        return stage;
    }

    public Stage openWindow(String fxml) throws IOException {
        Stage s = new Stage(StageStyle.UTILITY);

        Parent root = FXMLLoader.load(getClass().getResource(fxml));

        Scene scene = new Scene(root);

        s.setScene(scene);
        s.show();

        return s;
    }

}
