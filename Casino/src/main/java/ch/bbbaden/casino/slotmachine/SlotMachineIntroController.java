package ch.bbbaden.casino.slotmachine;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import ch.bbbaden.casino.SceneManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * FXML Controller class
 *
 * @author User
 */
public class SlotMachineIntroController implements Initializable {

    @FXML
    private CheckBox lightsCheck;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void play(ActionEvent event) throws IOException {
        SlotMachineController controller = (SlotMachineController) SceneManager.getInstance().changeScene("/fxml/SlotMachine.fxml");
        if (!lightsCheck.isSelected()) {
            controller.setVars(false);
        }
    }

}
