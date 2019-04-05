/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * @author User
 */
public class Casino extends Application {

    String[] fonts = {"fonts/Roboto/Roboto-Regular.ttf", "fonts/Aldrich/Aldrich-Regular.ttf"};

    @Override
    public void start(Stage stage) throws IOException {
        SceneManager.getInstance().changeScene("SlotMachine.fxml", Root.SLOTMACHINE);
        //importFonts();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void importFonts() {
        for (String font : fonts) {
            try {
                Font.loadFont(getClass().getResource(font).toExternalForm(), 10);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

}
