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
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author User
 */
public class GamePickerController implements Initializable {

    @FXML
    private ImageView image;

    private final Tuple[] games = {
        new Tuple("/fxml/RouletteMenu.fxml", new Image("/images/selectroulette.png")),
        new Tuple("/fxml/BingoSettings.fxml", new Image("/images/selectbingo.png")),
        new Tuple("/fxml/BlackJackIntro.fxml", new Image("/images/selectblackjack.png")),
        new Tuple("/fxml/SlotMachineIntro.fxml", new Image("/images/selectslotmachine.png"))
    };

    private int index;
    @FXML
    private Button statisticsButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateGUI();
        if (DataManager.getInstance().getcurrentuser().equals("admin")) {
            statisticsButton.setVisible(true);
        }
    }

    @FXML
    private void openBank(ActionEvent event) {
        try {
            SceneManager.getInstance().changeScene("/fxml/Bank.fxml");
        } catch (IOException ex) {
            Logger.getLogger(GamePickerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void play(ActionEvent event) {
        try {
            SceneManager.getInstance().changeScene((String) games[index].x);
        } catch (IOException ex) {
            Logger.getLogger(GamePickerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void nextGame(ActionEvent event) {
        index++;
        if (index >= games.length) {
            index = 0;
        }
        updateGUI();
    }

    @FXML
    private void lastGame(ActionEvent event) {
        index--;
        if (index < 0) {
            index = games.length - 1;
        }
        updateGUI();
    }

    private void updateGUI() {
        image.setImage((Image) games[index].y);
        System.out.println(index);
    }

    @FXML
    private void openStatistics(ActionEvent event) {
        try {
            SceneManager.getInstance().changeScene("/fxml/Statistic.fxml");
        } catch (IOException ex) {
            Logger.getLogger(GamePickerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
