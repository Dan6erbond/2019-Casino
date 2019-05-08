/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.blackjack;

import ch.bbbaden.casino.SceneManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author marin
 */
public class BlackJackIntroController implements Initializable {

    @FXML
    private Button play;
    @FXML
    private Button back;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void clickPlay(ActionEvent event) throws IOException {
        SceneManager.getInstance().changeScene("/fxml/BlackJack.fxml");
    }

    @FXML
    private void clickBack(ActionEvent event) throws IOException {
        SceneManager.getInstance().changeScene("/fxml/Selection.fxml");
    }

    @FXML
    private void clickRules(MouseEvent event) throws IOException {
        SceneManager.getInstance().changeScene("/fxml/BlackJackRules.fxml");

    }
}
