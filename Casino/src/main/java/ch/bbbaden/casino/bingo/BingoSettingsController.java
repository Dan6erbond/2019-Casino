package ch.bbbaden.casino.bingo;

import ch.bbbaden.casino.SceneManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TitledPane;

public class BingoSettingsController implements Initializable {

    
    @FXML
    private TitledPane titledPane;
    @FXML
    private Button btncheck;
    @FXML
    private ChoiceBox<Integer> cboxboards1;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cboxboards1.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));
         
//        scene.getStylesheets().add("yourname.css");
    }

    @FXML
    private void setstart(ActionEvent event) throws IOException {

        if (cboxboards1.getSelectionModel().getSelectedItem() != null) {
             Model.getInstance().setBoards(cboxboards1.getSelectionModel().getSelectedItem());

            try {
                SceneManager.getInstance().changeScene("/fxml/Bingo.fxml");

            } catch (IOException ex) {
                Logger.getLogger(BingoController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
}
