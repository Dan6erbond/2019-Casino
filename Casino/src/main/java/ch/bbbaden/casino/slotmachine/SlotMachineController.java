/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.slotmachine;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author User
 */
public class SlotMachineController implements Initializable {

    private final SlotMachine machine = new SlotMachine();

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
    private boolean listenScroll;

    @FXML
    private ScrollBar scrollBar;
    @FXML
    private Spinner<Integer> betSpinner;
    @FXML
    private Text multiplierLabel;
    @FXML
    private TextField betText;
    @FXML
    private TextField bankText;
    @FXML
    private Line barLine;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
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

        betSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1, 1));
        betSpinner.valueProperty().addListener((observable) -> {
            machine.setBet(betSpinner.getValue());
            updateGUI();
        });

        updateImages();
        machine.setBet(betSpinner.getValue());
        updateGUI();

        scrollBar.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldVal, Number newVal) -> {
            double newStartY = scrollBar.getHeight() / 2 + scrollBar.getLayoutY();
            double newEndY = ((scrollBar.getValue() / scrollBar.getMax()) * (scrollBar.getHeight() - 40)) + scrollBar.getLayoutY() + 25;
            barLine.setStartY(newStartY);
            barLine.setEndY(newEndY);

            if (!listenScroll) {
                return;
            }
            double diff = newVal.doubleValue() - oldVal.doubleValue();
            if (diff > 0.4) {
                machine.rotate();
                updateImages();
            }/*else {
                scrollBar.setDisable(true);
                double time = 1 - scrollBar.getValue() / 10;
                Animation animation = new Timeline(
                        new KeyFrame(Duration.seconds(time), new KeyValue(scrollBar.valueProperty(), 0))
                );
                animation.setOnFinished((event) -> {
                    scrollBar.setDisable(false);
                });
                animation.play();
            }*/
        });
    }

    private void updateImages() {
        scrollBar.setDisable(true);
        scrollBar.setOpacity(1.0);
        betSpinner.setDisable(true);

        SlotFace[][] faces = machine.getFaces();
        listenScroll = false;

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

        Timeline timeline = new Timeline();
        double lowestTime = 10;
        double highestTime = 0;
        for (int i = 0; i < scrollPanes.length; i++) {
            ScrollPane pane = scrollPanes[i];
            KeyValue endValue = new KeyValue(pane.vvalueProperty(), 1);
            // Random random = new Random();
            double time = SlotMachine.EXTRA / 15 + 0.75 * i; // increase number to speed up animation
            if (time > highestTime) {
                highestTime = time;
            }
            if (time < lowestTime) {
                lowestTime = time;
            }
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(time), endValue));
        }
        KeyValue endValue1 = new KeyValue(scrollBar.valueProperty(), 10);
        double time = 1 - scrollBar.getValue() / 10;
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(time), endValue1));
        KeyValue endValue2 = new KeyValue(scrollBar.valueProperty(), 0);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(highestTime), endValue2));

        Animation animation = (Animation) timeline;
        animation.setOnFinished((event) -> {
            scrollBar.setDisable(false);
            betSpinner.setDisable(false);

            listenScroll = true;

            Timeline tl = new Timeline();
            ArrayList<Tuple<SlotFace, Tuple<Integer, Integer>>> tuples = new ArrayList<>();
            if (machine.getDiagonal().isPresent()) {
                tuples.addAll(Arrays.asList(machine.getDiagonal().get()));
            } else if (machine.getStraight().isPresent()) {
                tuples.addAll(machine.getStraight().get());
            }
            for (Tuple tuple : tuples) {
                Tuple pos = (Tuple) tuple.y;
                int x = (int) pos.x;
                int y = (int) pos.y;
                StackPane pane = (StackPane) vBoxes[y].getChildren().get(x);
                //pane.getStyleClass().add("marked");

                KeyValue ev1 = new KeyValue(pane.opacityProperty(), 0.25);
                KeyValue ev2 = new KeyValue(pane.opacityProperty(), 1.0);

                double anTime = 0.25;
                double reps = 6;
                for (double i = anTime; i <= anTime * reps; i += anTime) {
                    KeyValue ev = i % (anTime * 2) == 0 ? reps % 2 == 0 ? ev2 : ev1 : reps % 2 == 0 ? ev1 : ev2;
                    tl.getKeyFrames().add(new KeyFrame(Duration.millis(i * 1000), ev));
                }
            }
            updateGUI();
            tl.play();
        });
        animation.play();
    }

    private void updateGUI() {
        multiplierLabel.setText("x" + Integer.toString(machine.getMultiplier()));
        betText.setText(Integer.toString(machine.getBet() * machine.getMultiplier()));
        bankText.setText(Integer.toString(machine.getBank()));
    }

    @FXML
    private void straight(ActionEvent event) {
        machine.rotate();
        while (!machine.getStraight().isPresent()) {
            machine.rotate();
        }
        updateImages();
    }

    @FXML
    private void diagonal(ActionEvent event) {
        machine.rotate();
        while (!machine.getDiagonal().isPresent()) {
            machine.rotate();
        }
        updateImages();
    }
}
