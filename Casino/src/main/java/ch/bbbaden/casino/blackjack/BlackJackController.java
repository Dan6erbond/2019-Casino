/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.blackjack;

import ch.bbbaden.casino.SceneManager;
import com.sun.prism.Texture;
import com.sun.prism.impl.BaseResourceFactory;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventType;
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
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author marin
 */
public class BlackJackController implements Initializable {

    int pool = 0;
    int balance = 1000;
    ArrayList<Card> cards = new ArrayList<>();
    ArrayList<Card> pcards = new ArrayList<>();
    ArrayList<Card> bcards = new ArrayList<>();
    int rand1;
    int rand2;
    int rand3;
    final int pxcoordinate = -300;
    final int bxcoordinate = 220;
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
    @FXML
    private Label pval;
    @FXML
    private Label bval;
    @FXML
    private Button again;
    @FXML
    private Button leave;
    @FXML
    private Label pmessage;
    @FXML
    private Label bmessage;
    @FXML
    private Pane imgpane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lbalance.setText(Integer.toString(balance));

        HideButtAction();

        setAllCards();
        cards.sort(Card.comparator);
    }

    private void setAllCards() {
        for (int j = 0; j < 4; j++) {
            CardFace face;
            switch (j) {
                case 0:
                    face = CardFace.HEART;
                    break;
                case 1:
                    face = CardFace.CLUB;
                    break;
                case 2:
                    face = CardFace.DIAMOND;
                    break;
                default:
                    face = CardFace.SPADES;
                    break;
            }

            for (int i = 0; i < 13; i++) {
                CardValue value = CardValue.values()[i];
                Card card = new Card(face, value);
                cards.add(card);
            }

        }
    }

    private void setPlayerCards(int x) {
        Random rnd = new Random();
        int random;

        rand1 = rnd.nextInt(cards.size() / 3 * 2);
        rand2 = rnd.nextInt(cards.size() / 3);
        rand3 = rnd.nextInt(100);

        if (rand3 > 10) {
            random = (int) (rand1 + rand2);
            rand3 -= 20;
        } else {
            random = (int) rand3;
            rand3 += 20;
        }

        Card card = cards.get(random);
        pvalue += card.getValue().getPValue();
        pcards.add(card);

        ImageView iv = new ImageView(card.getImage());
        iv.setLayoutX(x);
        iv.setLayoutY(-400);
        iv.setScaleX(0.18);
        iv.setScaleY(0.18);
        imgpane.getChildren().add(iv);

        cards.remove(card);
    }

    private void setBankCards(int x) {
        Random rnd = new Random();
        int random;

        rand1 = rnd.nextInt(cards.size() / 3 * 2);
        rand2 = rnd.nextInt(cards.size() / 3);
        rand3 = rnd.nextInt(100);

        if (rand3 > 15) {
            random = (int) rand1 + rand2;
            rand3 += 25;

        } else {
            random = (int) rand3;
            rand3 -= 25;
        }

        Card card = cards.get(random);
        bvalue += card.getValue().getBValue();
        bcards.add(card);

        ImageView iv = new ImageView(card.getImage());
        iv.setLayoutX(x);
        iv.setLayoutY(-400);
        iv.setScaleX(0.18);
        iv.setScaleY(0.18);
        imgpane.getChildren().add(iv);

        cards.remove(card);
    }

    @FXML
    private void clickPutChips(ActionEvent event) throws InterruptedException {
        hit.setDisable(false);
        doubledown.setDisable(false);
        stand.setDisable(false);

        again.setDisable(true);

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
            pval.setText(Integer.toString(pvalue));
            bval.setText(Integer.toString(bvalue));

            if (pvalue == bvalue && pvalue == 21) {
                Draw();
                HideButtAction();
            } else if (pvalue == 21) {
                WinBJ();
                HideButtAction();
            } else if (bvalue == 21) {
                LoseBJ();
                HideButtAction();
            }
        });
        pause.play();
        pval.setText(Integer.toString(pvalue));
        bval.setText(Integer.toString(bvalue));

        if (pool > balance) {
            doubledown.setDisable(true);
        }
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
        pval.setText(Integer.toString(pvalue));

        boolean pace = false;
        boolean bace = false;
        for (Card c : pcards) {
            if (c.getValue().equals(CardValue.ACE)) {
                pace = true;
            }
        }
        for (Card c : bcards) {
            if (c.getValue().equals(CardValue.ACE)) {
                bace = true;
            }
        }
        if (pvalue > 21 && pace == true) {
            pvalue -= 10;
            pval.setText(Integer.toString(pvalue));
            pcards.clear();
        }
        if (bvalue > 21 && bace == true) {
            bvalue -= 10;
            bval.setText(Integer.toString(bvalue));
            bcards.clear();
        }
        if (pvalue > 21) {
            Lose();
            HideButtAction();
        }
        if (bvalue <= 16) {
            setBankCards(bxcoordinate + 80);
            bval.setText(Integer.toString(bvalue));
        }
        if (pvalue == bvalue && bvalue == 21) {
            Draw();
            HideButtAction();
        } else if (pvalue == 21 || bvalue > 21) {
            Win();
            HideButtAction();
        }
    }

    @FXML
    private void clickStand(ActionEvent event) {

        do {
            if (bvalue <= 16) {
                setBankCards(bxcoordinate + 80);
                bval.setText(Integer.toString(bvalue));
                if (pvalue < bvalue && bvalue < 22) {
                    Lose();
                    HideButtAction();
                } else if (bvalue > 21) {
                    Win();
                }
            }
            if (pvalue > bvalue && pvalue < 22) {
                Win();
                HideButtAction();
            } else if (pvalue < bvalue && bvalue < 22) {
                Lose();
                HideButtAction();
            } else if (pvalue == bvalue && bvalue < 22) {
                Draw();
                HideButtAction();
            }
        } while (bvalue < 16);
    }

    @FXML
    private void clickDouble(ActionEvent event) {
        balance -= pool;
        pool *= 2;

        HideButtAction();

        lpool.setText(Integer.toString(pool));
        lbalance.setText(Integer.toString(balance));

        setPlayerCards(pxcoordinate + 80);
        pval.setText(Integer.toString(pvalue));

        do {
            if (pvalue > 21) {
                Lose();
                break;
            }
            if (bvalue <= 16) {
                setBankCards(bxcoordinate + 80);
                bval.setText(Integer.toString(bvalue));
                if (pvalue < bvalue && bvalue < 22) {
                    Lose();
                    HideButtAction();
                } else if (bvalue > 21) {
                    Win();
                }
            }
            if (pvalue > bvalue && pvalue < 22) {
                Win();
                HideButtAction();
            } else if (pvalue < bvalue && bvalue < 22) {
                Lose();
                HideButtAction();
            } else if (pvalue == bvalue && bvalue < 22) {
                Draw();
                HideButtAction();
            }
        } while (bvalue < 16);

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

    private void Lose() {
        pmessage.setText("Verloren");
        pool = 0;
        again.setDisable(false);

        lbalance.setText(Integer.toString(balance));
        lpool.setText(Integer.toString(pool));
    }

    private void Win() {
        pmessage.setText("Gewonnen!");
        balance += pool * 2;
        pool = 0;
        again.setDisable(false);

        lbalance.setText(Integer.toString(balance));
        lpool.setText(Integer.toString(pool));
    }

    private void WinBJ() {
        pmessage.setText("Black Jack!");
        bmessage.setText("Verloren");
        balance += pool * 2.5;
        pool = 0;
        again.setDisable(false);
    }

    private void LoseBJ() {
        bmessage.setText("Black Jack!");
        pmessage.setText("Verloren");
        balance += pool * 2.5;
        pool = 0;
        again.setDisable(false);
    }

    private void Draw() {
        pmessage.setText("Unentschieden");
        bmessage.setText("Unentschieden");
        balance += pool;
        pool = 0;
        again.setDisable(false);

        lbalance.setText(Integer.toString(balance));
        lpool.setText(Integer.toString(pool));
    }

    private void HideButtAction() {
        hit.setDisable(true);
        doubledown.setDisable(true);
        stand.setDisable(true);
    }

    @FXML
    private void clickAgain(ActionEvent event) {
        pmessage.setText("");
        bmessage.setText("");
        pval.setText("");
        bval.setText("");

        pvalue = 0;
        bvalue = 0;

        cards.clear();
        pcards.clear();
        bcards.clear();
        
        for (int i = 0; i < imgpane.getChildren().size(); i++) {
            imgpane.getChildren().remove(i);
        }
        
        setAllCards();

        hit.setDisable(true);
        doubledown.setDisable(true);
        stand.setDisable(true);

        again.setDisable(true);

        chip100.setDisable(false);
        chip100.setOpacity(1);
        chip25.setDisable(false);
        chip25.setOpacity(1);
        chip5.setDisable(false);
        chip5.setOpacity(1);
        chip2.setDisable(false);
        chip2.setOpacity(1);
        update();

        putchip.setDisable(true);
        deletechip.setDisable(true);
    }

    @FXML
    private void clickLeave(ActionEvent event) {
        try {
            SceneManager.getInstance().changeScene("/fxml/Login.fxml");
        } catch (IOException ex) {
            Logger.getLogger(BlackJackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
