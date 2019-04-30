/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.slotmachine;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author User
 */
public class SlotMachineController implements Initializable {

    private SlotMachine machine = new SlotMachine();

    @FXML
    private VBox vBoxA;
    @FXML
    private VBox vBoxB;
    @FXML
    private VBox vBoxC;
    @FXML
    private VBox vBoxD;
    @FXML
    private VBox vBoxE;

    @FXML
    private ScrollPane scrollA;
    @FXML
    private ScrollPane scrollB;
    @FXML
    private ScrollPane scrollC;
    @FXML
    private ScrollPane scrollD;
    @FXML
    private ScrollPane scrollE;

    private VBox[] vBoxes;
    private ScrollPane[] scrollPanes;
    private boolean listenScroll = true;

    @FXML
    private ScrollBar scrollBar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vBoxes = new VBox[]{vBoxA, vBoxB, vBoxC, vBoxD, vBoxE};
        scrollPanes = new ScrollPane[]{scrollA, scrollB, scrollC, scrollD, scrollE};

        for (ScrollPane pane : scrollPanes) {
            pane.addEventFilter(ScrollEvent.SCROLL, event -> {
                event.consume();
            });
        }

        updateImages(machine.getStart());

        scrollBar.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                if (!listenScroll && scrollBar.getValue() == scrollBar.getMax()){
                    listenScroll = true;
                }
                if (!listenScroll){
                    return;
                }
                double diff = newVal.doubleValue()- oldVal.doubleValue();
                if (diff > 0.5) {
                    updateImages(machine.rotate());
                    listenScroll = false;
                }
            }
        });
    }

    private void updateImages(SlotFace[][] faces) {
        for (ScrollPane pane : scrollPanes) {
            pane.setVvalue(0);
        }

        for (VBox vBox : vBoxes) {
            vBox.getChildren().removeAll(vBox.getChildren());
        }

        for (int i = 0; i < faces.length; i++) {
            for (int j = 0; j < faces[i].length; j++) {
                StackPane p = new StackPane();
                p.setMinSize(0, 150);
                ImageView iv = new ImageView(faces[i][j].getImage());
                StackPane.setAlignment(iv, Pos.CENTER);
                p.getChildren().add(iv);
                iv.fitWidthProperty().bind(p.widthProperty());
                vBoxes[i].getChildren().add(p);
            }
        }

        scrollBar.setDisable(true);
        //scrollBar.setValue(10);
        
        Timeline timeline = new Timeline();
        double lowestTime = 10;
        double highestTime = 0;
        for (int i = 0; i < scrollPanes.length; i++) {
            ScrollPane pane = scrollPanes[i];
            KeyValue endValue = new KeyValue(pane.vvalueProperty(), 1);
            // Random random = new Random();
            double time = SlotMachine.EXTRA / 15 + 0.75 * i; // increase number to speed up animation
            if (time > highestTime){
                highestTime = time;
            } 
            if (time < lowestTime){
                lowestTime = time;
            }
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(time), endValue));
        }
        KeyValue endValue = new KeyValue(scrollBar.disableProperty(), false);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(highestTime), endValue));
        KeyValue endValue1 = new KeyValue(scrollBar.valueProperty(), 10);
        double time = 1-scrollBar.getValue()/10;
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(time), endValue1));
        KeyValue endValue2 = new KeyValue(scrollBar.valueProperty(), 0);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(highestTime), endValue2));
        
        Animation animation = (Animation)timeline;
        animation.play();
    }

    @FXML
    private void straight(ActionEvent event) {
        SlotFace[][] faces = machine.rotate();
        while (!machine.getStraight()) {
            machine.rotate();
        }
        updateImages(faces);
    }

    @FXML
    private void diagonal(ActionEvent event) {
        SlotFace[][] faces = machine.rotate();
        while (!machine.getDiagonal()) {
            machine.rotate();
        }
        updateImages(faces);
    }
}
