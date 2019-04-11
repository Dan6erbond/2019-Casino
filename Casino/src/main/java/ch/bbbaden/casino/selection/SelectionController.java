/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.selection;

import ch.bbbaden.casino.SceneManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author aless
 */
public class SelectionController implements Initializable {
    @FXML
    private ImageView BlackJack;
    @FXML
    private ImageView SlotMachine;
    @FXML
    private ImageView Roulette;
    @FXML
    private ImageView bingo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void startgame(MouseEvent event) throws IOException {
        ImageView iv = (ImageView) event.getSource(); // get the ImageView of the image that was clicked that has the same name as the fxml files
        SceneManager.getInstance().changeScene("/fxml/"+iv.getId()+".fxml");
    }
    
}
