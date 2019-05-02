/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.statistik;

import ch.bbbaden.casino.Databankmanager;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author aless
 */
public class StatistikController implements Initializable {
    @FXML
    private ScrollPane table;
    @FXML
    private AnchorPane statistiktable;
    @FXML
    private Label totalwin;
    @FXML
    private ListView<String> playerlist;
    @FXML
    private Label datatitle;
    @FXML
    private Label totallost;
    @FXML
    private Label blackjackwon;
    @FXML
    private Label blackjacklost;
    @FXML
    private Label blackjackbet;
    @FXML
    private Label slotwon;
    @FXML
    private Label slotlost;
    @FXML
    private Label slotbet;
    @FXML
    private Label roulettewon;
    @FXML
    private Label roulettelost;
    @FXML
    private Label roulettebet;
    @FXML
    private Label totalbet;
    
    Databankmanager dm = Databankmanager.getInstance();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playerlist.getItems().addAll(Arrays.asList(dm.readusername("username").split(";")));
    }    

    @FXML
    private void playerselected(MouseEvent event) {
        Label [] labels = {totalwin,totallost,totalbet,blackjackwon,blackjacklost,blackjackbet,slotwon,slotlost,slotbet,roulettewon,roulettelost,roulettebet};
        table.setVisible(true);
        datatitle.setVisible(true);
        if(playerlist.getSelectionModel().getSelectedItem() != null){
            String[] statistik = dm.readstatistik("*",playerlist.getSelectionModel().getSelectedItem()).split(";");
            for(int i = 0; i < statistik.length-1; i++)
            {
                labels[i].setText(labels[i].getText()+statistik[i]);
            }
        }
        
    }
    
}
