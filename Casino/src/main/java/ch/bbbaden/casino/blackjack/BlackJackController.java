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
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author marin
 */
public class BlackJackController implements Initializable {

    int pool = 0;
    int balance = 100;
    ArrayList<Image> cards = new ArrayList<>();
    ArrayList<Integer> cardvalue = new ArrayList<>();
    int random;
    final int pxcoordinate = -100;
    final int bxcoordinate = 320;
    int pvalue;
    int bvalue;

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
    @FXML
    private AnchorPane ap;
    @FXML
    private Line line;
    @FXML
    private Pane pane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lbalance.setText(Integer.toString(balance));
        
        hit.setDisable(true);
        doubledown.setDisable(true);
        stand.setDisable(true);

        for (int i = 2; i <= 10; i++) {
            cards.add(new Image("/img/cards/" + i + "C.png"));
            cards.add(new Image("/img/cards/" + i + "D.png"));
            cards.add(new Image("/img/cards/" + i + "H.png"));
            cards.add(new Image("/img/cards/" + i + "S.png"));
        }

        cards.add(new Image("/img/cards/JC.png"));
        cards.add(new Image("/img/cards/JD.png"));
        cards.add(new Image("/img/cards/JH.png"));
        cards.add(new Image("/img/cards/JS.png"));
        cards.add(new Image("/img/cards/QC.png"));
        cards.add(new Image("/img/cards/QD.png"));
        cards.add(new Image("/img/cards/QH.png"));
        cards.add(new Image("/img/cards/QS.png"));
        cards.add(new Image("/img/cards/KC.png"));
        cards.add(new Image("/img/cards/KD.png"));
        cards.add(new Image("/img/cards/KH.png"));
        cards.add(new Image("/img/cards/KS.png"));
        cards.add(new Image("/img/cards/AC.png"));
        cards.add(new Image("/img/cards/AD.png"));
        cards.add(new Image("/img/cards/AH.png"));
        cards.add(new Image("/img/cards/AS.png"));

        for (int i = 2; i < 10; i++) {
            cardvalue.add(i);
            cardvalue.add(i);
            cardvalue.add(i);
            cardvalue.add(i);
        }
        for (int i = 0; i < 4; i++) {
            cardvalue.add(10);
            cardvalue.add(10);
            cardvalue.add(10);
            cardvalue.add(10);
        }
        for (int i = 0; i < 4; i++) {
            cardvalue.add(11);
        }
    }

    private void setPlayerCards(int x) {
        Random rnd = new Random();
        random = rnd.nextInt(cards.size() + 1);

        ImageView iv = new ImageView(cards.get(random));
        iv.setLayoutX(x);
        iv.setLayoutY(-290);
        iv.setScaleX(0.18);
        iv.setScaleY(0.18);
        ap.getChildren().add(iv);

    }

    private void setBankCards(int x) {
        Random rnd = new Random();
        random = rnd.nextInt(cards.size());

        ImageView iv = new ImageView(cards.get(random));
        iv.setLayoutX(x);
        iv.setLayoutY(-290);
        iv.setScaleX(0.18);
        iv.setScaleY(0.18);
        ap.getChildren().add(iv);
    }

    @FXML
    private void clickPutChips(ActionEvent event) throws InterruptedException {
        hit.setDisable(false);
        doubledown.setDisable(false);
        stand.setDisable(false);
        
        chip100.setDisable(true);
        chip100.setOpacity(0.4);
        chip25.setDisable(true);
        chip25.setOpacity(0.4);
        chip5.setDisable(true);
        chip5.setOpacity(0.4);
        chip2.setDisable(true);
        chip2.setOpacity(0.4);

        putchip.setDisable(true);
        deletechip.setDisable(true);
        line.setVisible(true);

        PauseTransition pause = new PauseTransition(Duration.seconds(1));

        setPlayerCards(pxcoordinate);
        setBankCards(bxcoordinate);
        pause.setOnFinished((ActionEvent e)
                -> {
            setPlayerCards(pxcoordinate + 40);
            setBankCards(bxcoordinate + 40);
        });
        pause.play();
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
        setPlayerCards(pxcoordinate + 80);
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

        if (balance < 100) {
            chip100.setDisable(true);
            chip100.setOpacity(0.4);
        }
        if (balance < 25) {
            chip25.setDisable(true);
            chip25.setOpacity(0.4);
        }
        if (balance < 5) {
            chip5.setDisable(true);
            chip5.setOpacity(0.4);
        }
        if (balance < 2) {
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