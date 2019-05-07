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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author User
 */
public class SceneManager {

    private static final String homeFXML = "/fxml/Selection.fxml";
    private static SceneManager sceneManager;

    private static Stage stage;
    private static Scene homeScene;
    private static AnchorPane ap;

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

    public static Stage getStage() {
        return stage;
    }

    public static Scene getHomeScene() {
        return homeScene;
    }

    public Tuple<Stage, Object> openWindow(String fxml) throws IOException {
        Stage s = new Stage(StageStyle.UTILITY);
        s.setResizable(false);
        s.setOnHidden((event) -> {
            try {
                if (s.getScene() != homeScene) {
                    stage = openWindow(homeFXML).x;
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

        if (fxml.equals(homeFXML)) {
            homeScene = scene;
        }

        s.setTitle("Casino");
        s.setScene(scene);
        s.show();

        return new Tuple(s, controller);
    }

    public void setAnchorPane(AnchorPane ap) {
        SceneManager.ap = ap;
    }

    public AnchorPane getAnchorPane() {
        return ap;
    }
}
