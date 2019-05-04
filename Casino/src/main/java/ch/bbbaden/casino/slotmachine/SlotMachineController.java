/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.slotmachine;

import ch.bbbaden.casino.Tuple;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author User
 */
public class SlotMachineController implements Initializable {

    private final SlotMachine machine = new SlotMachine(this);

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
    private boolean setup = true;
    private boolean animateLights = true;
    private ArrayList<Object> lights = new ArrayList<>();

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
    @FXML
    private VBox lightrow3;
    @FXML
    private HBox lightrow;
    @FXML
    private HBox lightrow2;
    @FXML
    private VBox lightrow1;
    @FXML
    private TextField freespinsText;

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

        lights.addAll(Arrays.asList(lightrow.getChildren().toArray()));
        lights.addAll(Arrays.asList(lightrow1.getChildren().toArray()));

        ArrayList<Object> lightsList3 = new ArrayList<>(lights);

        Object[] lights1 = lightrow3.getChildren().toArray();
        ArrayList<Object> lights1List = new ArrayList<>(Arrays.asList(lights1));

        Object[] lights2 = lightrow2.getChildren().toArray();
        ArrayList<Object> lights2List = new ArrayList<>(Arrays.asList(lights2));

        ArrayList<Object> lightsList = new ArrayList<>(lights1List);
        lightsList.addAll(lights2List);

        Collections.reverse(lights1List);
        Collections.reverse(lights2List);
        lights.addAll(lights2List);
        lights.addAll(lights1List);

        for (ScrollPane pane : scrollPanes) {
            pane.addEventFilter(ScrollEvent.SCROLL, event -> {
                event.consume();
            });
        }

        betSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1, 1));
        betSpinner.valueProperty().addListener((observable) -> {
            setBet(betSpinner.getValue());
        });

        updateImages();
        setBet(betSpinner.getValue());

        final double startWidth = scrollBar.getPrefWidth();
        final double startVisible = scrollBar.getVisibleAmount();
        final double startX = scrollBar.getLayoutX() - 40;

        scrollBar.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldVal, Number newVal) -> {
            if (setup) {
                double startY = scrollBar.getPrefHeight() / 2 + scrollBar.getLayoutY() - 100;
                barLine.setStartY(startY);
                setup = false;
            }

            double x = scrollBar.getPrefWidth() / 2 + scrollBar.getLayoutX() - 25;
            barLine.setStartX(x);
            barLine.setEndX(x);

            double newEndY = ((scrollBar.getValue() / scrollBar.getMax()) * (scrollBar.getHeight() - 75)) + scrollBar.getLayoutY() - 65;
            barLine.setEndY(newEndY);

            double normalized = (scrollBar.getMax() - Math.abs(scrollBar.getMax() * 0.75 / 2 - scrollBar.getValue() * 0.75)) / scrollBar.getMax();
            scrollBar.setVisibleAmount(normalized * startVisible);
            scrollBar.setPrefWidth(normalized * startWidth);
            scrollBar.setLayoutX(startX + (startWidth - scrollBar.getPrefWidth() / 2));

            if (!listenScroll) {
                return;
            }
            double diff = newVal.doubleValue() - oldVal.doubleValue();
            if (diff > 0.4) {
                play();
            } else if (scrollBar.getValue() > scrollBar.getMax() / 2) {
                listenScroll = false;
                Timeline timeline = new Timeline();
                double time = 1 - scrollBar.getValue() / 10;
                KeyValue endValue = new KeyValue(scrollBar.valueProperty(), 0);
                timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(time), endValue));
                timeline.setOnFinished((event) -> {
                    listenScroll = true;
                });
                timeline.play();
            }
        });

        for (Object l : lights) {
            Circle light = (Circle) l;
            light.setOpacity(0.25);
        }

        PauseTransition transition = new PauseTransition(Duration.seconds(1));
        transition.setOnFinished((event) -> {
            int reps = 2;
            animateLights(lightsList3, reps);
            animateLights(lightsList, reps);
        });
        transition.play();
    }

    public void setVars(boolean animateLights) {
        this.animateLights = animateLights;
    }

    private void animateLights(ArrayList<Object> lts, int reps) {
        if (!animateLights) {
            return;
        }

        final double anTime = 0.075;
        double totalTime = lts.size() * anTime;

        for (int i = 0; i < reps; i++) {
            PauseTransition transition = new PauseTransition(Duration.seconds(i * totalTime));
            transition.setOnFinished((event) -> {
                for (int j = 0; j < lts.size(); j++) {
                    Node element = (Node) lts.get(j);
                    PauseTransition transition1 = new PauseTransition(Duration.seconds(j * anTime));
                    transition1.setOnFinished((e) -> {
                        element.setOpacity(1.0);
                        PauseTransition transition2 = new PauseTransition(Duration.seconds(anTime));
                        transition2.setOnFinished((evt) -> {
                            element.setOpacity(0.25);
                        });
                        transition2.play();
                    });
                    transition1.play();
                }
            });
            transition.play();
        }
    }

    private void flashNode(Node node, double anTime, int reps) {
        for (int i = 0; i < reps; i++) {
            PauseTransition trans = new PauseTransition(Duration.seconds(i * anTime * 2));
            trans.setOnFinished((e) -> {
                freespinsText.getStyleClass().add("freespinsBorder");
                PauseTransition transition = new PauseTransition(Duration.seconds(anTime));
                transition.setOnFinished((event) -> {
                    freespinsText.getStyleClass().remove("freespinsBorder");
                });
                transition.play();
            });
            trans.play();
        }
    }

    private void setBet(int bet) {
        machine.setBet(bet);
        updateGUI();
    }

    private void play() {
        machine.rotate();
        machine.evaluate();
        updateImages();
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

        timeline.setOnFinished((event) -> {
            scrollBar.setDisable(false);
            betSpinner.setDisable(false);

            updateGUI();

            listenScroll = true;

            if (machine.getWon()) {
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

                    KeyValue ev1 = new KeyValue(pane.opacityProperty(), 0.25);
                    KeyValue ev2 = new KeyValue(pane.opacityProperty(), 1.0);

                    double anTime = 0.25;
                    double reps = 6;
                    for (double i = anTime; i <= anTime * reps; i += anTime) {
                        KeyValue ev = i % (anTime * 2) == 0 ? reps % 2 == 0 ? ev2 : ev1 : reps % 2 == 0 ? ev1 : ev2;
                        tl.getKeyFrames().add(new KeyFrame(Duration.seconds(i), ev));
                    }
                }
                tl.play();

                SlotFace face = tuples.get(0).x;
                if (face == SlotFace.SUPERCHERRY) {
                    animateLights(lights, 2);
                }
            }
        });
        timeline.play();
    }

    private void updateGUI() {
        multiplierLabel.setText("x" + Integer.toString(machine.getMultiplier()));
        betText.setText(Integer.toString(machine.getBet() * machine.getMultiplier()));
        bankText.setText(Integer.toString(machine.getBank()));
        freespinsText.setText(Integer.toString(machine.getFreespins()));

        if (machine.getUpdateFreespins()) {
            flashNode(freespinsText, 0.75, 5);
        }
    }

    @FXML
    private void straight(ActionEvent event) {
        machine.rotate();
        while (!machine.getStraight().isPresent() || !machine.getWon()) {
            machine.rotate();
            machine.evaluateWon();
        }
        machine.evaluate();
        updateImages();
    }

    @FXML
    private void diagonal(ActionEvent event) {
        machine.rotate();
        while (!machine.getDiagonal().isPresent() || !machine.getWon()) {
            machine.rotate();
            machine.evaluateWon();
        }
        machine.evaluate();
        updateImages();
    }

    @FXML
    private void maxBet(ActionEvent event) {
        machine.setBet(10);
        betSpinner.getValueFactory().setValue(machine.getBet());
        betText.setText(Integer.toString(machine.getBet()));
        updateGUI();
    }


    @FXML
    private void superCherry(ActionEvent event) {
        machine.rotate();
        while (!machine.getStraight().isPresent() || !machine.getWon() || machine.getStraight().get().get(0).x != SlotFace.SUPERCHERRY) {
            machine.rotate();
            machine.evaluateWon();
        }
        machine.evaluate();
        updateImages();
    }
}
