/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.BlackJack;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author marin
 */
public class BlackJackController implements Initializable {

    @FXML
    private Button putchip;
    @FXML
    private Button deletechip;
    @FXML
    private Button hit;
    @FXML
    private Button stand;
    @FXML
    private Button doubledown;
    private Label pool;
    private Label balance;
    
    int pool = 0;
    int balance = 0;
    @FXML
    private Label lpool;
    @FXML
    private Label lbalance;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clickPutChips(ActionEvent event) {
    }

    @FXML
    private void clickDeleteChips(ActionEvent event) {
    }

    @FXML
    private void clickHit(ActionEvent event) {
    }

    @FXML
    private void clickStand(ActionEvent event) {
    }

    @FXML
    private void clickDouble(ActionEvent event) {
    }

    @FXML
    private void clickHundred(ActionEvent event) {
    }

    @FXML
    private void clickTwentyfive(ActionEvent event) {
    }

    @FXML
    private void clickFive(ActionEvent event) {
    }

    @FXML
    private void clickTwo(ActionEvent event) {
        this.pool.textProperty().bind(balance.textProperty());
    }
    
}
