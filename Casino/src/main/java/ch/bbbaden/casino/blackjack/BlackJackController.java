/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.blackjack;

import com.sun.prism.Texture;
import com.sun.prism.impl.BaseResourceFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;

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
    @FXML
    private Label lpool;
    @FXML
    private Label lbalance;
    @FXML
    private ImageView chip2;
    @FXML
    private ImageView chip5;
    @FXML
    private ImageView chip25;
    @FXML
    private ImageView chip100;

    
    int pool = 0;
    int balance = 100;
    ArrayList<Image>cards = new ArrayList<>();
    @FXML
    private AnchorPane ap;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private void setCards(){
        for (int i = 2; i <= 10; i++) {
            cards.add(new Image("/img/cards/"+i+"C.png"));
            cards.add(new Image("/img/cards/"+i+"D.png"));
            cards.add(new Image("/img/cards/"+i+"H.png"));
            cards.add(new Image("/img/cards/"+i+"S.png"));
        }
        cards.get(0);
        ImageView iv = new ImageView(cards.get(0));
        iv.setLayoutX(400);
        iv.setLayoutX(100);
        iv.setScaleX(0.25);
        iv.setScaleY(0.25);
        ap.getChildren().add(iv);
    }
    
    @FXML
    private void clickPutChips(ActionEvent event) {
        setCards();
        
    }

    @FXML
    private void clickDeleteChips(ActionEvent event) {
        balance += pool;
        pool = 0;
        lpool.setText(Integer.toString(pool));
        lbalance.setText(Integer.toString(balance));
        
        chip100.setDisable(false);
        chip100.setOpacity(1);
        chip25.setDisable(false);
        chip25.setOpacity(1);
        chip5.setDisable(false);
        chip5.setOpacity(1);
        chip2.setDisable(false);
        chip2.setOpacity(1);
        
        putchip.setDisable(true);
        deletechip.setDisable(true);
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

    private void update() {
        
        putchip.setDisable(false);
        deletechip.setDisable(false);
        
        if (balance < 100){
            chip100.setDisable(true);
            chip100.setOpacity(0.4);
        }
        if (balance < 25){
            chip25.setDisable(true);
            chip25.setOpacity(0.4);
        }
        if (balance < 5){
            chip5.setDisable(true);
            chip5.setOpacity(0.4);
        }
        if (balance < 2){
            chip2.setDisable(true);
            chip2.setOpacity(0.4);
        }
        lpool.setText(Integer.toString(pool));
        lbalance.setText(Integer.toString(balance));
    }

    @FXML
    private void clickTwo(MouseEvent event) {
        pool += 2;
        balance -= 2;
        update();
    }

    @FXML
    private void clickFive(MouseEvent event) {
        pool += 5;
        balance -= 5;
        update();
    }

    @FXML
    private void clickTwentyfive(MouseEvent event) {
        pool += 25;
        balance -= 25;
        update();
    }

    @FXML
    private void clickHundred(MouseEvent event) {
        pool += 100;
        balance -= 100;
        update();
    }
}
