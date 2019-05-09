/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.blackjack;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author marin
 */
public class BlackJackRules implements Initializable {

    boolean ruleactive = false;
    String img;
    int xcord;
    int ycord;
    double xscale;
    double yscale;
    ArrayList<ImageView> rulearr = new ArrayList<>();

    @FXML
    private Button cardvalue;
    @FXML
    private Button decide;
    @FXML
    private Button end;
    @FXML
    private AnchorPane ap;
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
    private void clickCardvalue(ActionEvent event) {
        xcord = -195;
        ycord = -125;
        xscale = 0.63;
        yscale = 0.68;
        img = "/img/black-jack rules1.jpg";
        setImage();
    }

    @FXML
    private void clickDecide(ActionEvent event) {
        xcord = -195;
        ycord = -68;
        xscale = 0.85;
        yscale = 0.80;
        img = "/img/black-jack rules2.jpg";
        setImage();
    }

    @FXML
    private void clickEnd(ActionEvent event) {
        xcord = -195;
        ycord = -105;
        xscale = 0.7;
        yscale = 0.7;
        img = "/img/black-jack rules3.jpg";
        setImage();
    }

    @FXML
    private void clickBack(ActionEvent event) {
        if (ruleactive) {
            ap.getChildren().removeAll(rulearr);
            ruleactive = false;
        } else {
            Stage stage = (Stage) back.getScene().getWindow();
            stage.close();
        }
    }

    private void setImage() {
        ImageView iv = new ImageView(img);
        iv.setScaleX(xscale);
        iv.setScaleY(yscale);
        iv.setLayoutX(xcord);
        iv.setLayoutY(ycord);
        ap.getChildren().add(iv);
        rulearr.add(iv);

        back.toFront();
        ruleactive = true;
    }
}
