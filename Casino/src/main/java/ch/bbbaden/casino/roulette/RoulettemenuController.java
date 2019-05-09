/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.roulette;

import ch.bbbaden.casino.DataManager;
import ch.bbbaden.casino.SceneManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.*;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author aless
 */
public class RoulettemenuController implements Initializable {
    @FXML
    private Pane wheel;
    @FXML
    private Circle ball;
    @FXML
    private Label title;
    @FXML
    private Label balance;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //sets your current balance
        balance.setText(balance.getText() + DataManager.getInstance().getchipamount());
        //spins the wheel
        RotateTransition RT1 = new RotateTransition(Duration.millis(16000), wheel);
            RT1.setCycleCount(Timeline.INDEFINITE);
            RT1.setByAngle(500f+(1000f*Math.random()));
            RT1.play();  
            //spins the ball
            final Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, new EventHandler() {
            int movingStep = 0;
            boolean beginning = true;
            @Override
            public void handle(Event event) {
                movingStep++;
                double angleAlpha = movingStep * ( Math.PI / 30 );
                double midPointx = wheel.getLayoutX() + (wheel.getWidth()/2)-245;
                double midPointy = wheel.getLayoutY() + (wheel.getHeight()/2)-53;
                double ballorbit = midPointy - ball.getCenterY();
                    beginning = false;
                
                    moveBall(ball, midPointx + (ballorbit-1) * Math.sin(angleAlpha), midPointy - (ballorbit-1) * Math.cos(angleAlpha));
                // Reset after one orbit.
                if (movingStep == 360) {
                    movingStep = 0;
                }
            }
        }), new KeyFrame(Duration.millis(60)));
             timeline.setCycleCount(Timeline.INDEFINITE);
             timeline.play();
    }    
    //method for the ball that uses math to set the balls next point
    private void moveBall(Circle ball, double x, double y) {
         
        TranslateTransition move = TranslateTransitionBuilder.create()
                .node(ball)
                .toX(x)
                .toY(y)
                .duration(Duration.millis(60))
                .build();
 
        move.playFromStart();
    }
    //starts the game
    @FXML
    private void play(ActionEvent event) throws IOException {
        SceneManager.getInstance().changeScene("/fxml/Roulette.fxml");
    }
    //brings you to the selection screen
    @FXML
    private void back(ActionEvent event) throws IOException {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).hide();
    }
    
}
