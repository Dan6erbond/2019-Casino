/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.loginregister;

import ch.bbbaden.casino.Databankmanager;
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
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * FXML Controller class
 *
 * @author User
 */
public class LoginController implements Initializable,Observer {
    @FXML
    private Label registerlabel;
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
    private Button login;
    @FXML
    private Label alertname;
    @FXML
    private Label alertpassword;
    
    private ServerAccess sa = ServerAccess.getInstance();
    @FXML
    private Label title;
    @FXML
    private Label alertcheck;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServerAccess.getInstance().addobserver(this);
    }    

    @FXML
    private void register(MouseEvent event) throws IOException {
        sa.close();
        SceneManager.getInstance().changeScene("/fxml/register.fxml");
    }

    @FXML
    private void login(ActionEvent event) {
        if(namefield.getText().trim() == null || namefield.getText().trim() == "")
        {
            alertname.setText("This field must be filled out, without any special symbols and within the length of 50 characters");
        }
        if(passwordfield.getText().trim() == null || passwordfield.getText().trim() == "")
        {
            alertpassword.setText("Please fill out the field");
        }
        if(namefield.getText().trim() != null && passwordfield.getText().trim() != null){
            try {
            sa.InitSocket("84.74.61.42", 1757);
            sa.send("login:"+namefield.getText());// send username and hashed password to server
            } catch (IOException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        title.setText((String)arg);
        if(BCrypt.checkpw(passwordfield.getText(), (String) arg))
        {
            sa.close();
            JOptionPane.showMessageDialog(null, "Login successfull");
        }
        else
        {
            alertpassword.setText("The username or password is incorrect");
        }
    }
}
