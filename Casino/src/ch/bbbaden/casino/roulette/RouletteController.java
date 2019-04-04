/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.roulette;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author aless
 */
public class RouletteController implements Initializable {
    @FXML
    private GridPane numbers;
    @FXML
    private Pane redbet;
    @FXML
    private Pane blackbet;
    @FXML
    private Pane low;
    @FXML
    private Label lowlabel;
    @FXML
    private Pane high;
    @FXML
    private Label highlabel;
    @FXML
    private Pane col1;
    @FXML
    private Label col1label;
    @FXML
    private Pane col2;
    @FXML
    private Label col2label;
    @FXML
    private Pane col3;
    @FXML
    private Label col3label;
    @FXML
    private Pane third1;
    @FXML
    private Label third1label;
    @FXML
    private Pane third2;
    @FXML
    private Label third2label;
    @FXML
    private Pane third3;
    @FXML
    private Label third3label;
    @FXML
    private Pane even;
    @FXML
    private Label evenlabel;
    @FXML
    private Label redlabel;
    @FXML
    private Label blacklabel;
    @FXML
    private Pane odd;
    @FXML
    private Label oddlabel;
    @FXML
    private Pane doublezero;
    @FXML
    private Pane zero;
    
    private final Field[][] grid = new Field[3][12];
    private final Pane[][] panes = new Pane[3][12];
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (int i = 0 ; i < 3 ; i++) {
            for (int j = 0; j < 12; j++) {
                grid[i][j] = new Field(true);
                addPane(i, j);
            }
        }
    }    
    
    private void addPane(int col, int row)
    {
        Pane pane = new Pane();
         pane.setStyle("-fx-border-width: 2px; -fx-border-color: white;");
         panes[col][row] = pane;
        pane.setOnMouseClicked(e -> {

        });
        numbers.add(pane, col, row);
    }
}
