/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino;

import ch.bbbaden.casino.slotmachine.SlotMachineController;
import java.io.IOException;
import java.net.URL;
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
            stage = openWindow(fxml);
            return;
        }

        Parent root = FXMLLoader.load(getClass().getResource(fxml));

        Scene scene = new Scene(root);

        stage.setScene(scene);
    }

    public Stage getStage() {
        return stage;
    }

    public Stage openWindow(String fxml) throws IOException {
        Stage s = new Stage(StageStyle.UTILITY);
        s.setResizable(false);

        Parent root = FXMLLoader.load(getClass().getResource(fxml));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("https://fonts.googleapis.com/css?family=Roboto");
        scene.getStylesheets().add("https://fonts.googleapis.com/css?family=Aldrich");

        s.setTitle("Casino");
        s.setScene(scene);
        s.show();

        return s;
    }

}
