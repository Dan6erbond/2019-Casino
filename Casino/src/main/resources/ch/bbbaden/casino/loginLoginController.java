/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author aless
 */
public class loginLoginController implements Initializable {
    @FXML
    private TextField namefield;
    @FXML
    private PasswordField passwordfield;
    @FXML
    private Label namelabel;
    @FXML
    private Label passwordlabel;
    @FXML
    private Label textlabel;
    @FXML
    private Label registerlabel;
    @FXML
    private Button login;
    @FXML
    private Label alertname;
    @FXML
    private Label alertpassword;
    @FXML
    private Label alertcheck;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void register(MouseEvent event) {
    }

    @FXML
    private void login(ActionEvent event) {
    }
    
}
