/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.loginregister;

import ch.bbbaden.casino.SceneManager;
import ch.bbbaden.casino.ServerAccess;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * FXML Controller class
 *
 * @author aless
 */
public class RegisterController implements Initializable,Observer {
    @FXML
    private TextField namefield;
    @FXML
    private PasswordField passwordfield;
    @FXML
    private Button registerbutton;
    @FXML
    private Label alertname;
    @FXML
    private Label alertpassword;

    /**
     * Initializes the controller class.
     */
    
    ServerAccess sa = ServerAccess.getInstance();
    @FXML
    private Button back;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServerAccess.getInstance().addobserver(this);
    }    

    @FXML
    private void register(ActionEvent event) {
        if(namefield.getText().trim() == null)
        {
            alertname.setText("Please fill out the field");
        }
        if(passwordfield.getText().trim() == null)
        {
            alertpassword.setText("Please fill out the field");
        }
        if(namefield.getText().trim() != null && passwordfield.getText().trim() != null){
            try {
            sa.InitSocket("84.74.61.42", 1757);
            sa.send("register:"+namefield.getText()+";"+BCrypt.hashpw(passwordfield.getText(), BCrypt.gensalt(12)));// send username and hashed password to server
            } catch (IOException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if("User added".equals((String)arg)){
            try {
                sa.close();
                SceneManager.getInstance().changeScene("/fxml/selection.fxml");
            } catch (IOException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            //show panel
        }
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        sa.close();
        SceneManager.getInstance().changeScene("/fxml/Login.fxml");
    }
}
