/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.loginregister;

import ch.bbbaden.casino.DataManager;
import ch.bbbaden.casino.PaneManager;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void register(MouseEvent event) throws IOException {
        SceneManager.getInstance().changeScene("/fxml/Register.fxml");
    }

    @FXML
    private void login(ActionEvent event) throws IOException, InterruptedException {
        boolean namecorrect = true;
        boolean passcorrect = true;
        if (namefield.getText().isEmpty() || namefield.getText().length() > 50) {
            alertname.setText("This field must be filled out and within the length of 50 characters");
            namecorrect = false;
        }
        if (passwordfield.getText().isEmpty() || passwordfield.getText().length() > 200) {
            alertpassword.setText("Please fill out the field");
            passcorrect = false;
        }
        if (passcorrect != false && namecorrect != false) {
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
                        Thread.sleep(100);
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
                SceneManager.getInstance().changeScene(SceneManager.homeFXML);
                sa.close();
            } else {
                ap.getChildren().add(PaneManager.createPane(ap.getWidth(), ap.getHeight(), "Password or Username is incorrect"));
            }
        } catch (IOException e) {
            ap.getChildren().add(PaneManager.createPane(ap.getWidth(), ap.getHeight(), "Password or username is incorrect"));
        }
    }
    private int count = 0;

}
