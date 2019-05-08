/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author User
 */
public class GamePickerController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void playRoulette(ActionEvent event) {
        try {
            SceneManager.getInstance().changeScene("/fxml/Roulette.fxml");
        } catch (IOException ex) {
            Logger.getLogger(GamePickerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void playBingo(ActionEvent event) {
        try {
            SceneManager.getInstance().changeScene("/fxml/BlackJack.fxml");
        } catch (IOException ex) {
            Logger.getLogger(GamePickerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void playBlackJack(ActionEvent event) {
        try {
            SceneManager.getInstance().changeScene("/fxml/BlackJackIntro.fxml");
        } catch (IOException ex) {
            Logger.getLogger(GamePickerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void playSlotMachine(ActionEvent event) {
        try {
            SceneManager.getInstance().changeScene("/fxml/SlotMachineIntro.fxml");
        } catch (IOException ex) {
            Logger.getLogger(GamePickerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
