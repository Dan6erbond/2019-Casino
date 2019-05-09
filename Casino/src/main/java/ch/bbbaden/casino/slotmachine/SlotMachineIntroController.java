package ch.bbbaden.casino.slotmachine;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import ch.bbbaden.casino.DataManager;
import ch.bbbaden.casino.SceneManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

/**
 * FXML Controller class
 *
 * @author User
 */
public class SlotMachineIntroController implements Initializable {

    @FXML
    private CheckBox lightsCheck;
    @FXML
    private Spinner<Integer> startPot;
    @FXML
    private Button playButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int chipAmount = DataManager.getInstance().getchipamount();
        int startValue = chipAmount / 5;
        startPot.valueProperty().addListener((observable) -> {
            if (startPot.getValue() <= 0){
                playButton.setDisable(true);
            } else {
                playButton.setDisable(false);
            }
        });
        startPot.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, chipAmount, startValue, 1));
    }

    @FXML
    private void play(ActionEvent event) throws IOException {
        SlotMachineController controller = (SlotMachineController) SceneManager.getInstance().changeScene("/fxml/SlotMachine.fxml");
        controller.setVars(startPot.getValue(), false);
    }

}
