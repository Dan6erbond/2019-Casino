/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.loginregister;

import ch.bbbaden.casino.DataManager;
import ch.bbbaden.casino.SceneManager;
import ch.bbbaden.casino.ServerAccess;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * FXML Controller class
 *
 * @author User
 */
public class LoginController implements Initializable {

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
    @FXML
    private AnchorPane ap;
    @FXML
    private Pane loginFailed;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        loginFailed.setVisible(false);
    }

    @FXML
    private void register(MouseEvent event) throws IOException {
        SceneManager.getInstance().changeScene("/fxml/Register.fxml");
    }
    //looks if the fields are empty or above the lengths they should be
    @FXML
    private void login(ActionEvent event) throws IOException, InterruptedException {
        boolean namecorrect = true;
        boolean passcorrect = true;
        if (namefield.getText().isEmpty() || namefield.getText().length() > 50) {
            alertname.setVisible(true);
            namecorrect = false;
        }
        if (passwordfield.getText().isEmpty() || passwordfield.getText().length() > 200) {
            alertpassword.setVisible(true);
            passcorrect = false;
        }
        if (passcorrect && namecorrect) {
            try {
                sa.InitSocket("84.74.61.42", 1757);
                sa.send("login:" + namefield.getText());// send username and hashed password to server
                check();
            } catch (IOException ex) {
                System.out.println("Not connected to server! Please try using a VPN.");
            }
        }
    }
    private String message;

    private void check() {
        Thread thread = new Thread() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    message = sa.getmessage();
                    if (message != null) {
                        break;
                    }

                }
            }
        };
        thread.run();
        try {
            if (BCrypt.checkpw(passwordfield.getText(), message)) {
                DataManager.getInstance().setcurrentuser(namefield.getText());
                SceneManager.getInstance().setHome("/fxml/GamePicker.fxml");
                sa.close();
            } else {
                loginFailed.setVisible(true);
            }
        } catch (IOException e) {
            loginFailed.setVisible(true);
        }
    }

    @FXML
    private void closeLoginFailed(ActionEvent event) {
        loginFailed.setVisible(false);
    }

}
