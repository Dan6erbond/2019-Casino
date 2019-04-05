/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.roulette;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
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
    
    private final Field[][] grid = new Field[12][3];
    private final Pane[][] panes = new Pane[12][3];
    
    private final List<Pane> otherpanes = new ArrayList<>();
    private final List<Field> otherfields = new ArrayList<>();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                int count = 0;
        for(int row = 0;row < 12;row++)
        {
            for(int col = 2;col >= 0;col--)
            {
                count++;
                grid[row][col] = new Field(count);
                addPane(row,col);
            }
            
        }
        
        otherpanes.add(low);
        otherpanes.add(redbet);
        otherpanes.add(blackbet);
        otherpanes.add(high);
        otherpanes.add(third1);
        otherpanes.add(third2);
        otherpanes.add(third3);
        otherpanes.add(even);
        otherpanes.add(odd);
        otherpanes.add(doublezero);
        otherpanes.add(zero);
    }    
    
     private void addPane(int row, int col)
    {
        BorderPane borderpane = new BorderPane();
         Pane toppane = new Pane();
         Pane centerpane = new Pane();
         Pane bottompane = new Pane();
         Pane rightpane = new Pane();
         Pane leftpane = new Pane();
         Label label = new Label();
         label.setText(grid[row][col].getnumber()+""); 
         label.setStyle("-fx-text-fill: white;");
         centerpane.getChildren().add(label); // put label in pane to show it's number
         BorderPane.setAlignment(toppane, Pos.TOP_CENTER);
         BorderPane.setAlignment(bottompane, Pos.BOTTOM_CENTER);
         BorderPane.setAlignment(leftpane, Pos.CENTER_LEFT);
         BorderPane.setAlignment(rightpane, Pos.TOP_CENTER);
         label.layoutXProperty().bind(centerpane.widthProperty().subtract(label.widthProperty()).divide(2)); // set label at center of pane's x location
         label.layoutYProperty().bind(centerpane.heightProperty().subtract(label.heightProperty()).divide(2)); // set label at center of pane's y location
         if((grid[row][col].getnumber() < 11 || (grid[row][col].getnumber() <29 && grid[row][col].getnumber() > 18) ? grid[row][col].getnumber() % 2 == 0 : grid[row][col].getnumber() % 2 != 0)) // to look if cell should be red or black
         {
             borderpane.setStyle("-fx-border-width: 1px; -fx-border-color: white; -fx-background-color: black;");
             grid[row][col].setisblack(true);
         }
         else
         {
              borderpane.setStyle("-fx-border-width: 1px; -fx-border-color: white; -fx-background-color: red;");
              grid[row][col].setisblack(false);
         }
         panes[row][col] = borderpane;
         
         numbers.add(borderpane, row, col);
    }
    
}
