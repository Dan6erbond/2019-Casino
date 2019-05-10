/* Autor: RaviAnand Mohabir
 * Firma: BBBaden
 * Version: 3.4
 * Erstell-Datum: 10. April 2019
 * Letzte Bearbeitung: 09. Mai 2019
 */
package ch.bbbaden.casino.slotmachine;

import ch.bbbaden.casino.Tuple;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.SPACE;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SlotMachineController implements Initializable {

    private SlotMachine machine;

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
    private boolean playable;
    private boolean setup = true;
    private boolean animateLights = true;
    private final ArrayList<Object> lights = new ArrayList<>();

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
    @FXML
    private Pane pane;
    @FXML
    private Label paneTitle;
    @FXML
    private Label stats;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pane.setVisible(false);

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

        final double startWidth = scrollBar.getPrefWidth();
        final double startVisible = scrollBar.getVisibleAmount();
        final double startX = scrollBar.getLayoutX() - 40;

        scrollBar.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldVal, Number newVal) -> {
            if (setup) {
                double startY = scrollBar.getPrefHeight() / 2 + scrollBar.getLayoutY() - 100;
                barLine.setStartY(startY);
                Scene scene = bankText.getScene();
                if (scene != null) {
                    scene.setOnKeyPressed((event) -> {
                        System.out.println(event.getCode());
                        switch (event.getCode()) {
                            case SPACE:
                                play(false);
                        }
                    });
                    setup = false;
                }
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

            if (!playable) {
                return;
            }

            double diff = newVal.doubleValue() - oldVal.doubleValue();
            if (diff > 0.4) {
                play(false);
            } else if (scrollBar.getValue() > scrollBar.getMax() / 2) {
                animateLever(1.5);
            }
        });

        for (Object l : lights) {
            Circle light = (Circle) l;
            light.setOpacity(0.25);
        }

        PauseTransition transition = new PauseTransition(Duration.seconds(1));
        transition.setOnFinished((event) -> {
            int reps = 2;
            blinkRow(lightsList3, reps);
            blinkRow(lightsList, reps);
        });
        transition.play();
    }

    public void setVars(int bank, boolean animateLights) {
        machine = new SlotMachine(bank, 5);
        updateImages();
        setBet(betSpinner.getValue());
        
        this.animateLights = animateLights;

        ((Stage) bankText.getScene().getWindow()).setOnCloseRequest(event -> {
            machine.endGame();
        });
    }

    private void animateLever(double timeUp) {
        scrollBar.setDisable(true);
        scrollBar.setOpacity(1.0);
        betSpinner.setDisable(true);
        playable = false;

        Timeline timeline = new Timeline();
        double time = 1 - scrollBar.getValue() / scrollBar.getMax();

        KeyValue down = new KeyValue(scrollBar.valueProperty(), scrollBar.getMax());
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(time), down));

        KeyValue up = new KeyValue(scrollBar.valueProperty(), 0);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(time + timeUp), up));

        timeline.setOnFinished((event) -> {
            playable = true;
            betSpinner.setDisable(false);
            scrollBar.setDisable(false);
        });

        timeline.play();
    }

    private Optional<Double> blinkRow(ArrayList<Object> lts, int reps) {
        if (!animateLights) {
            Optional.empty();
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
        return Optional.of(totalTime * reps);
    }

    private void flashFreespinsText() {
        double anTime = 0.5;
        for (int i = 0; i < 4; i++) {
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

    private void breathNode(Node node) {
        int reps = 6;
        double anTime = 0.25;
        double startOpac = 0.25;
        double endOpac = 1;
        breathNode(node, reps, anTime, startOpac, endOpac);
    }

    private void breathNode(Node node, int reps, double anTime, double startOpac, double endOpac) {
        Timeline tl = new Timeline();

        KeyValue ev1 = new KeyValue(node.opacityProperty(), startOpac);
        KeyValue ev2 = new KeyValue(node.opacityProperty(), endOpac);

        for (double i = anTime; i <= anTime * reps; i += anTime) {
            KeyValue ev = i % (anTime * 2) == 0 ? reps % 2 == 0 ? ev2 : ev1 : reps % 2 == 0 ? ev1 : ev2;
            tl.getKeyFrames().add(new KeyFrame(Duration.seconds(i), ev));
        }

        tl.play();
    }

    private void setBet(int bet) {
        machine.setBet(bet);
        updateGUI();
    }

    private void play(boolean developer) {
        if (!playable) {
            return;
        }

        int spinnable = machine.getSpinnable();

        if (spinnable == -1) {
            if (!developer) {
                machine.rotate();
            }
            machine.handleWinState();
            updateImages();
        } else {
            ScrollPane pane = scrollPanes[spinnable];
            double t = SlotMachine.EXTRA / 15;
            machine.spinSpinnable();
            if (developer) {
                while (!machine.evaluateFace(spinnable)) {
                    machine.generateRoll(spinnable);
                }
            }
            animateLever(t - 1);
            spinPane(spinnable, t);
            PauseTransition transition = new PauseTransition(Duration.seconds(t));
            transition.setOnFinished((e) -> {
                if (machine.evaluateFace(spinnable)) {
                    machine.handleFace(spinnable);
                    ObservableList<Node> children = vBoxes[spinnable].getChildren();
                    breathNode(children.get(children.size() - 2));
                }
                updateGUI();
            });
            transition.play();
        }

        Optional<WinState> winState = machine.getWinState();
        if (winState.isPresent() && winState.get().equals(WinState.LOST)) {
            scrollBar.setDisable(true);
            pane.setVisible(true);
            paneTitle.setText("Game Over!");
            stats.setText(machine.toString());
        }
    }

    private void updateImages() {
        double highestTime = SlotMachine.EXTRA / 15 + 0.75 * (scrollPanes.length - 1);

        animateLever(highestTime - 1);
        PauseTransition transition = new PauseTransition(Duration.seconds(highestTime));

        for (int i = 0; i < scrollPanes.length; i++) {
            double time = SlotMachine.EXTRA / 15 + 0.75 * i;
            spinPane(i, time);
        }

        transition.setOnFinished((event) -> {
            updateGUI();

            Optional<WinState> winState = machine.getWinState();
            if (winState.isPresent() && winState.get() == WinState.SPIN) {
                Object[] lights2 = lightrow2.getChildren().toArray();
                ArrayList<Object> lights2List = new ArrayList<>(Arrays.asList(lights2));
                Collections.reverse(lights2List);
                Optional<Double> waitTime = blinkRow(lights2List, 1);
                if (waitTime.isPresent()) {
                    PauseTransition trans = new PauseTransition(Duration.seconds(waitTime.get()));
                    trans.setOnFinished((e) -> {
                        Collections.reverse(lights2List);
                        blinkRow(lights2List, 1);
                    });
                    trans.play();
                }
            }

            Optional<ArrayList<Tuple<SlotFace, Tuple<Integer, Integer>>>> winTuples = machine.getWinTuples();
            if (winTuples.isPresent()) {
                for (Tuple tuple : winTuples.get()) {
                    Tuple pos = (Tuple) tuple.y;
                    int x = (int) pos.x;
                    int y = (int) pos.y;
                    StackPane pane = (StackPane) vBoxes[y].getChildren().get(x);

                    breathNode(pane);
                }

                SlotFace face = winTuples.get().get(0).x;
                if (face == SlotFace.SUPERCHERRY) {
                    blinkRow(lights, 2);
                }
            }
        });
        transition.play();
    }

    private void spinPane(int index, double time) {
        ScrollPane pane = scrollPanes[index];
        pane.setVvalue(0);
        VBox vBox = vBoxes[index];
        vBox.getChildren().removeAll(vBox.getChildren());

        SlotFace[][] faces = machine.getFaces();
        for (SlotFace face : faces[index]) {
            StackPane p = new StackPane();
            p.setMinSize(0, 150);
            ImageView iv = new ImageView(face.getImage());
            StackPane.setAlignment(iv, Pos.CENTER);
            p.getChildren().add(iv);
            iv.fitWidthProperty().bind(p.widthProperty());
            vBox.getChildren().add(p);
        }

        Timeline timeline = new Timeline();
        KeyValue endValue = new KeyValue(pane.vvalueProperty(), 1);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(time), endValue));
        timeline.play();
    }

    private void updateGUI() {
        multiplierLabel.setText("x" + Integer.toString(machine.getMultiplier()));
        betText.setText(Integer.toString(machine.getBet() * machine.getMultiplier()));
        bankText.setText(Integer.toString(machine.getBank()));
        freespinsText.setText(Integer.toString(machine.getFreespins()));

        if (machine.getUpdateFreespins()) {
            flashFreespinsText();
        }
    }

    private void straight(ActionEvent event) {
        toWinState(WinState.GREENSEVEN, WinState.GREENBAR, WinState.YELLOWSEVEN, WinState.CHERRY);
    }

    private void diagonal(ActionEvent event) {
        toWinState(WinState.REDSEVEN, WinState.GREENSTAR, WinState.YELLOWBAR);
    }

    @FXML
    private void maxBet(ActionEvent event) {
        machine.setBet(10);
        betSpinner.getValueFactory().setValue(machine.getBet());
        betText.setText(Integer.toString(machine.getBet()));
        updateGUI();
    }

    private void superCherry(ActionEvent event) {
        toWinState(WinState.SUPERCHERRY);
    }

    private void spin(ActionEvent event) {
        toWinState(WinState.SPIN);
    }

    private void toWinState(WinState... states) {
        machine.rotate();
        Optional<WinState> state = machine.getWinState();
        while (!state.isPresent() || !Arrays.asList(states).contains(state.get())) {
            machine.rotate();
            state = machine.getWinState();
        }
        play(true);
    }

    @FXML
    private void leave(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).hide();
    }

    @FXML
    private void openStats(ActionEvent event) {
        pane.setVisible(true);
        paneTitle.setText("Game Statistics");
        stats.setText(machine.toString());
    }

    @FXML
    private void closePane(ActionEvent event) {
        Optional<WinState> winState = machine.getWinState();
        if (winState.isPresent() && winState.get().equals(WinState.LOST)){
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).hide();
        } else {
            pane.setVisible(false);
        }
    }
}
