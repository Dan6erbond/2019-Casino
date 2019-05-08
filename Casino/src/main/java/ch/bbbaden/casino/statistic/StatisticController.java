/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.statistic;

import ch.bbbaden.casino.DataManager;
import ch.bbbaden.casino.SceneManager;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
 * @author alessandro
 */
public class StatisticController implements Initializable {
    @FXML
    private ScrollPane table;
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
    @FXML
    private Label playertitle;
    @FXML
    private AnchorPane ap;
    
    DataManager dm = DataManager.getInstance();
    List<String> labelstrings = new ArrayList<>();
    @FXML
    private Label bingowon;
    @FXML
    private Label bingolost;
    @FXML
    private Label bingobet;
    @FXML
    private AnchorPane statistiktable;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addnames();
    }    
    
    private void addnames()
    {
        //adds all names from the databank to the ListView
        playerlist.getItems().addAll(Arrays.asList(dm.readusername("username").split(";")));
        Label [] labels = {totalwin,totallost,totalbet,roulettewon,roulettelost,roulettebet,slotwon,slotlost,slotbet,bingowon,bingolost,bingobet,blackjackwon,blackjacklost,blackjackbet};
        for(Label l : labels)
        {
            labelstrings.add(l.getText());
        }
    }
    //method to look what player you selected and shows their statistics
    @FXML
    private void playerselected(MouseEvent event) {
        Label [] labels = {totalwin,totallost,totalbet,roulettewon,roulettelost,roulettebet,slotwon,slotlost,slotbet,bingowon,bingolost,bingobet,blackjackwon,blackjacklost,blackjackbet};
        table.setVisible(true);
        datatitle.setVisible(true);
        //sets all statistics to the labels
        if(playerlist.getSelectionModel().getSelectedItem() != null){
            String[] statistik = dm.readstatistik("*",playerlist.getSelectionModel().getSelectedItem()).split(";");
            for(int i = 0; i < statistik.length; i++){
                labels[i].setText(labelstrings.get(i)+statistik[i]);
            }
        }
    }
    //allows yout to go back
    @FXML
    private void back(ActionEvent event) throws IOException {
        SceneManager.getInstance().changeScene("/fxml/Selection.fxml");
    }
    
}
