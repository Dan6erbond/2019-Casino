/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.slotmachine;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author User
 */
public class SlotMachineController implements Initializable {

    @FXML
    private ImageView A1;
    @FXML
    private ImageView A2;
    @FXML
    private ImageView A3;
    @FXML
    private ImageView B1;
    @FXML
    private ImageView B2;
    @FXML
    private ImageView B3;
    @FXML
    private ImageView C1;
    @FXML
    private ImageView C2;
    @FXML
    private ImageView C3;
    @FXML
    private ImageView D1;
    @FXML
    private ImageView D2;
    @FXML
    private ImageView D3;
    @FXML
    private ImageView E1;
    @FXML
    private ImageView E2;
    @FXML
    private ImageView E3;
    
    private SlotMachine machine = new SlotMachine();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateImages(machine.getStart());
    }
    
    private void updateImages(SlotFace[][] faces){       
        for (int i = 0; i < faces.length; i++){
            for (int j = 0; j < faces[i].length; j++){
                switch (i){
                    case 0:
                        switch (j){
                            case 0:
                                A1.setImage(faces[i][j].getImage());
                                break;
                            case 1:
                                A2.setImage(faces[i][j].getImage());
                                break;
                            case 2:
                                A3.setImage(faces[i][j].getImage());
                                break;
                        }
                        break;
                    case 1:
                        switch (j){
                            case 0:
                                B1.setImage(faces[i][j].getImage());
                                break;
                            case 1:
                                B2.setImage(faces[i][j].getImage());
                                break;
                            case 2:
                                B3.setImage(faces[i][j].getImage());
                                break;
                        }
                        break;
                    case 2:
                        switch (j){
                            case 0:
                                C1.setImage(faces[i][j].getImage());
                                break;
                            case 1:
                                C2.setImage(faces[i][j].getImage());
                                break;
                            case 2:
                                C3.setImage(faces[i][j].getImage());
                                break;
                        }
                        break;
                    case 3:
                        switch (j){
                            case 0:
                                D1.setImage(faces[i][j].getImage());
                                break;
                            case 1:
                                D2.setImage(faces[i][j].getImage());
                                break;
                            case 2:
                                D3.setImage(faces[i][j].getImage());
                                break;
                        }
                        break;
                    case 4:
                        switch (j){
                            case 0:
                                E1.setImage(faces[i][j].getImage());
                                break;
                            case 1:
                                E2.setImage(faces[i][j].getImage());
                                break;
                            case 2:
                                E3.setImage(faces[i][j].getImage());
                                break;
                        }
                        break;
                }
            }
        }
    }

    @FXML
    private void spin(ActionEvent event) {
        updateImages(machine.rotate());
    }
    
}
