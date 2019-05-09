/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.bank;

import ch.bbbaden.casino.DataManager;
import ch.bbbaden.casino.SceneManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
    @FXML
    private Button confirm;
    @FXML
    private Pane popup;
    @FXML
    private Label popupTitle;
    @FXML
    private Label popupMessage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        amount.setText("Your chips: " + dm.getchipamount());
        chips.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, 1));
        chips.getValueFactory().setValue(100);
        popup.setVisible(false);
    }
	//takes the number in the spinner and adds it to your account
    @FXML
    private void addamount(ActionEvent event) {
        int number;
        try {
            number = Integer.parseInt(chips.getEditor().getText());

            if (number > 0) {
                dm.setchipamount(dm.getchipamount() + number);
                popup.setVisible(true);
                popupMessage.setText(number + " were added to your in-game bank!");
                amount.setText("Your chips: " + dm.getchipamount());
            }
        } catch (NumberFormatException ex) {
            chips.getValueFactory().setValue(100);
        }
    }
    //brings you back
    @FXML
    private void back(ActionEvent event) throws IOException {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).hide();
    }

    @FXML
    private void closePopup(ActionEvent event) {
        popup.setVisible(false);
    }

}
