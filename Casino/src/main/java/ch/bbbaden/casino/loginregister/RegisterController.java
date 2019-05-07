/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.loginregister;

import ch.bbbaden.casino.Databankmanager;
import ch.bbbaden.casino.Panemanager;
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
import javafx.scene.layout.AnchorPane;
//import org.springframework.security.crypto.bcrypt.BCrypt;

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
    @FXML
    private AnchorPane ap;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SceneManager.getInstance().setAnchorPane(ap);
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
//            sa.send("register:"+namefield.getText()+";"+BCrypt.hashpw(passwordfield.getText(), BCrypt.gensalt(12)));// send username and hashed password to server
            check();
            } catch (IOException ex) {
             SceneManager.getInstance().getAnchorPane().getChildren().add(Panemanager.createPane(SceneManager.getInstance().getAnchorPane().getWidth(),SceneManager.getInstance().getAnchorPane().getHeight(),"Sorry, but we coulnd't \r\n connect to the Server. \r\n When you are in a school, \r\n try using a vpn"));
            }
        }
    }

    public void check()
    {
                Thread thread = new Thread()
        {
            public void run()
            {
                while(true)
                {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if(sa.getmessage() != null)
                    {
                        break;
                    }

                }
            }
        };
        thread.run();
        
        if("User added".equals(sa.getmessage())){
            sa.close();
            Databankmanager.getInstance().setcurrentuser(namefield.getText());
            ap.getChildren().add(Panemanager.createPane(ap.getWidth(),ap.getHeight(),"User added successfully","/fxml/selection.fxml"));
            
        }
        else
        {
             ap.getChildren().add(Panemanager.createPane(ap.getWidth(),ap.getHeight(),"Username already taken"));
        }
    }
    @Override
    public void update(Observable o, Object arg) {
        
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        SceneManager.getInstance().changeScene("/fxml/Login.fxml");
    }
}
