/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.bank;

import ch.bbbaden.casino.DataManager;
import ch.bbbaden.casino.PaneManager;
import ch.bbbaden.casino.SceneManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author aless
 */
public class BankController implements Initializable {
    @FXML
    private Spinner<Integer> chips;
    @FXML
    private AnchorPane ap;
    @FXML
    private Label amount;
        DataManager dm = DataManager.getInstance();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        amount.setText(amount.getText()+dm.getchipamount());
        chips.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000,1));
    }    
    //takes the number in the spinner and adds it to your account
    @FXML
    private void addamount(ActionEvent event) {
        int number = Integer.parseInt(chips.getEditor().getText());
        if(number > 0)
        {
            dm.setchipamount(dm.getchipamount() + number);
            amount.setText("Your chips: "+dm.getchipamount());
        }
        else{
        ap.getChildren().add(PaneManager.createPane(ap.getHeight(), ap.getWidth(), "You can't add 0 or a negative number \r\n to your account"));
        }
    }
    //brings you back
    @FXML
    private void back(ActionEvent event) throws IOException {
        SceneManager.getInstance().changeScene("/fxml/Selection.fxml");
    }
    
}
