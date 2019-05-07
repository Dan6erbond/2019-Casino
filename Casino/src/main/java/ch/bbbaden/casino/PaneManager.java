/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 *
 * @author aless
 */
public class PaneManager {

    public static Pane createPane(double height, double width, String text) {
        //create pane
        final Pane pane = new Pane();
        pane.setStyle("-fx-background-color: blueviolet; -fx-background-radius: 15 15 15 15;");
        pane.setMinSize(height / 2, width / 2);
        pane.setVisible(true);
        pane.setLayoutX(150);
        pane.setLayoutY(100);
        pane.toFront();
        //create label
        Label label = new Label();
        label.setText(text);
        label.layoutXProperty().bind(pane.widthProperty().subtract(pane.getWidth() + (width / 13.3)).divide(6));
        label.layoutYProperty().bind(pane.heightProperty().subtract(label.heightProperty()).divide(2));
        pane.getChildren().add(label);
        //create button
        Button continuebutton = new Button();
        continuebutton.setText("continue");
        continuebutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                pane.setVisible(false);
            }
        });
        continuebutton.layoutYProperty().bind(pane.heightProperty().subtract(pane.getHeight() - 150).divide(2));
        continuebutton.layoutXProperty().bind(pane.widthProperty().subtract(continuebutton.widthProperty()).divide(2));
        pane.getChildren().add(continuebutton);

        return pane;
    }

    public static Pane createPane(double height, double width, String text, final String fxml) {
        final Pane pane = new Pane();
        pane.setStyle("-fx-background-color: blueviolet; -fx-background-radius: 18 18 18 18;");
        pane.setMinSize(height / 2, width / 2);
        pane.setVisible(true);
        pane.setLayoutX(150);
        pane.setLayoutY(100);
        pane.toFront();
        //create label
        Label label = new Label();
        label.setText(text);
        label.layoutXProperty().bind(pane.widthProperty().subtract(pane.getWidth() + 100).divide(2));
        label.layoutYProperty().bind(pane.heightProperty().subtract(label.heightProperty()).divide(2));
        pane.getChildren().add(label);
        //create button
        Button continuebutton = new Button();
        continuebutton.setText("continue");
        continuebutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    SceneManager.getInstance().changeScene(fxml);
                } catch (IOException ex) {
                    Logger.getLogger(PaneManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        continuebutton.layoutYProperty().bind(pane.heightProperty().subtract(pane.getHeight() + 40));
        continuebutton.layoutXProperty().bind(pane.widthProperty().subtract(continuebutton.widthProperty()).divide(2));
        pane.getChildren().add(continuebutton);

        return pane;
    }
}
