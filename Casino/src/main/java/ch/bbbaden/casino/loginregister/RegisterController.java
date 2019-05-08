/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.loginregister;

import ch.bbbaden.casino.DataManager;
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
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * FXML Controller class
 *
 * @author aless
 */
public class RegisterController implements Initializable {

    @FXML
    private TextField namefield;
    @FXML
    private PasswordField passwordfield;
    @FXML
    private Label alertname;
    @FXML
    private Label alertpassword;

    private boolean registered;

    ServerAccess sa = ServerAccess.getInstance();
    @FXML
    private AnchorPane ap;
    @FXML
    private Pane popup;
    @FXML
    private Label popupMessage;
    @FXML
    private Label popupTitle;
    @FXML
    private PasswordField passwordConfirmation;
    @FXML
    private Label passwordMatch;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        popup.setVisible(false);
    }

    @FXML
    private void register(ActionEvent event) {
        boolean namecorrect = true;
        boolean passcorrect = true;
        boolean passmatch = true;
        
        if (namefield.getText().isEmpty() || namefield.getText().length() > 50) {
            alertname.setVisible(true);
            namecorrect = false;
        }
        if (passwordfield.getText().isEmpty() || passwordfield.getText().length() > 200) {
            alertpassword.setVisible(true);
            passcorrect = false;
        }
        if (!passwordfield.getText().equals(passwordConfirmation.getText())) {
            passwordMatch.setVisible(true);
            passmatch = false;
        }
        
        if (passcorrect && namecorrect && passmatch) {
            try {
                sa.InitSocket("84.74.61.42", 1757);
                sa.send("register:" + namefield.getText() + ";" + BCrypt.hashpw(passwordfield.getText(), BCrypt.gensalt(12)));// send username and hashed password to server
                check();
            } catch (IOException ex) {
                System.out.println("Not connected to server! Please try using a VPN.");
            }
        }
    }

    public void check() {
        Thread thread = new Thread() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (sa.getmessage() != null) {
                        break;
                    }

                }
            }
        };
        thread.run();

        if ("User added".equals(sa.getmessage())) {
            sa.close();
            DataManager.getInstance().setcurrentuser(namefield.getText());
            popupTitle.setText("Registration successful");
            popupMessage.setText("User successfully added!");
            registered = true;
            popup.setVisible(true);
        } else {
            popupTitle.setText("Registration failed");
            popupMessage.setText("The given username is already taken!");
            popup.setVisible(true);
        }
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).hide();
    }

    @FXML
    private void closePopup(ActionEvent event) throws IOException {
        if (!registered) {
            popup.setVisible(false);
        } else {
            SceneManager.getInstance().setHome("/fxml/Selection.fxml");
        }
    }
}
