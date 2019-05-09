package ch.bbbaden.casino.roulette;

import ch.bbbaden.casino.PaneManager;
import ch.bbbaden.casino.SceneManager;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.*;
import javafx.fxml.*;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author alessandro
 */
public class RouletteController implements Initializable {

    @FXML
    private GridPane numbers;
    @FXML
    private Label test;
    @FXML
    private AnchorPane ap;
    @FXML
    private Label balance;
    @FXML
    private Label totalbet;
    @FXML
    private Circle ball;
    @FXML
    private Rectangle rec00;
    @FXML
    private Rectangle rec27;
    @FXML
    private Rectangle rec10;
    @FXML
    private Rectangle rec25;
    @FXML
    private Rectangle rec29;
    @FXML
    private Rectangle rec12;
    @FXML
    private Rectangle rec8;
    @FXML
    private Rectangle rec19;
    @FXML
    private Rectangle rec31;
    @FXML
    private Rectangle rec18;
    @FXML
    private Rectangle rec6;
    @FXML
    private Rectangle rec21;
    @FXML
    private Rectangle rec33;
    @FXML
    private Rectangle rec16;
    @FXML
    private Rectangle rec4;
    @FXML
    private Rectangle rec23;
    @FXML
    private Rectangle rec35;
    @FXML
    private Rectangle rec14;
    @FXML
    private Rectangle rec2;
    @FXML
    private Rectangle rec0;
    @FXML
    private Rectangle rec28;
    @FXML
    private Rectangle rec9;
    @FXML
    private Rectangle rec26;
    @FXML
    private Rectangle rec30;
    @FXML
    private Rectangle rec11;
    @FXML
    private Rectangle rec7;
    @FXML
    private Rectangle rec20;
    @FXML
    private Rectangle rec32;
    @FXML
    private Rectangle rec17;
    @FXML
    private Rectangle rec5;
    @FXML
    private Rectangle rec22;
    @FXML
    private Rectangle rec34;
    @FXML
    private Rectangle rec15;
    @FXML
    private Rectangle rec3;
    @FXML
    private Rectangle rec24;
    @FXML
    private Rectangle rec36;
    @FXML
    private Rectangle rec13;
    @FXML
    private Rectangle rec1;
    @FXML
    private Pane wheel;
    @FXML
    private Button Bspin;

    private final Field[][] grid = new Field[12][3];
    private final RouletteModel model = new RouletteModel();
    private final ImageView iv2 = new ImageView();
    private boolean haschip = false;
    private int chip;
    private final List<ImageView> betchips = new ArrayList<>();
    private double ballx;
    private double bally;
    private final Random r = new Random();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //sets balance to the balance you currently have
        balance.setText("Balance: " + model.getbalance());
        totalbet.setText("Total Bet: " + model.getbetamount());
        ap.getChildren().add(iv2);
        iv2.setVisible(false);
        // some attributes for the ball
        ballx = ball.getLayoutX();
        bally = ball.getLayoutY();
        //to create the number field
        int count = 0;
        for (int row = 0; row < 12; row++) {
            for (int col = 2; col >= 0; col--) {
                count++;
                grid[row][col] = new Field(count);
                addPane(row, col);
            }

        }
        //creates a menu bar
        MenuBar menu = new MenuBar();
        Menu info = new Menu("Info");
        MenuItem help = new MenuItem("Help");
        //when you click on Help
        help.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ap.getChildren().add(PaneManager.createPane(ap.getWidth(), ap.getHeight(), "1) You click on one of the chips. A mini version of it now follows you \r\n 2) You click on a field \r\n 3) You can always see which fields you are selecting in the text above the fields \r\n 4) You press the Button \"spin\" \r\n 5) Hope you win"));
            }
        });
        MenuItem bet = new MenuItem("Bets");
        //when you click on Bets
        bet.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ap.getChildren().add(PaneManager.createPane(ap.getWidth(), ap.getHeight(), "Column - You select one of the \"2 TO 1\" fields 2: 1 \r\n Dozen - You select twelve numbers 2:1 \r\n Even/Odd - You select the field \"Even/ODD\" 1:1 \r\n Red/Black - You select the field \"RED/BLACK\" 1:1 \r\n Low/High - You select the field \"1-18/19-36\" 1:1 \r\n 2:1 means, when you place 10 you get 20 plus your original 10"));
                ap.getChildren().add(PaneManager.createPane(ap.getWidth(), ap.getHeight(), "Straight up bets - You select one number 35:1 \r\n Split bets - You select two numbers 17:1 \r\n Street bets - You select three numbers 11:1 \r\n corner bets - You select four numbers 8:1 \r\n 5 number bets - You select five numbers  \r\n  6 number bets - You select six numbers 5:1"));
            }
        });
        //adds everything
        info.getItems().add(help);
        info.getItems().add(bet);
        menu.getMenus().add(info);
        ap.getChildren().add(menu);
    } 

    private void addPane(final int row, final int col)
    {
        //creates all panes for one field
         BorderPane borderpane = new BorderPane();
         Pane centerpane = new Pane();
         Pane rightpane = new Pane();
         Pane leftpane = new Pane();
         Label label = new Label();
         BorderPane bottompane = new BorderPane();
         BorderPane toppane = new BorderPane();
         Pane bottomcenter = new Pane(); 
         Pane bottomleft = new Pane();
         Pane bottomright = new Pane();
         Pane topcenter = new Pane();
         Pane topleft = new Pane();
         Pane topright = new Pane();
         borderpane.getStyleClass().add("panes");
         label.setText(grid[row][col].getnumber()+""); 
         label.getStyleClass().add("Label");
         //all methods for all the panes so that the label shows all selected fields
         bottomcenter.hoverProperty().addListener((ChangeListener<Boolean>) new ChangeListener<Boolean>() {

             @Override
             public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                 if(col == 2)
                 {
                     test.setText(grid[row][col].getnumber() +","+ grid[row][col-1].getnumber() +","+ grid[row][col-2].getnumber());
                 }
                 else
                 {
                     test.setText(grid[row][col].getnumber() +","+ grid[row][col+1].getnumber());
                 }
             }
         });
         bottomright.hoverProperty().addListener((ChangeListener<Boolean>) new ChangeListener<Boolean>() {

             @Override
             public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                 if(col == 2 && row != 11)
                 {
                     test.setText(grid[row][col].getnumber() +","+ grid[row][col-1].getnumber() +","+ grid[row][col-2].getnumber()+","+grid[row+1][col].getnumber()+","+grid[row+1][col-1].getnumber()+","+grid[row+1][col-2].getnumber());
                 }
                 else if(col == 2)
                 {
                     test.setText(grid[row][col].getnumber() +","+ grid[row][col-1].getnumber() +","+ grid[row][col-2].getnumber());
                 }
                 else if(row != 11)
                 {
                     test.setText(grid[row][col].getnumber()+","+grid[row][col+1].getnumber()+","+grid[row+1][col].getnumber()+","+grid[row+1][col+1].getnumber());
                 }
                 else
                 {
                     test.setText(grid[row][col].getnumber()+","+grid[row][col+1].getnumber());
                 }
             }
         });
         bottomleft.hoverProperty().addListener((ChangeListener<Boolean>) new ChangeListener<Boolean>() {

             @Override
             public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                 if(col == 2 && row != 0)
                 {
                     test.setText(grid[row][col].getnumber() +","+ grid[row][col-1].getnumber() +","+ grid[row][col-2].getnumber()+","+grid[row-1][col].getnumber()+","+grid[row-1][col-1].getnumber()+","+grid[row-1][col-2].getnumber());
                 }
                 else if(row != 0)
                 {
                     test.setText(grid[row][col].getnumber()+","+grid[row][col+1].getnumber()+","+grid[row-1][col].getnumber()+","+grid[row-1][col+1].getnumber());
                 }
                 else if(row == 0 && col == 2)
                 {
                     test.setText(grid[row][col].getnumber() +","+ grid[row][col-1].getnumber() +","+ grid[row][col-2].getnumber()+",00,0");
                 }
                 else if(row == 0 && col == 1)
                 {
                     test.setText(grid[row][col].getnumber() +","+ grid[row][col+1].getnumber() +",0");
                 }
                 else
                 {
                     test.setText(grid[row][col].getnumber() +","+ grid[row][col+1].getnumber() +",00");
                 }
             }
         });
         topcenter.hoverProperty().addListener((ChangeListener<Boolean>) new ChangeListener<Boolean>() {

             @Override
             public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                 if(col == 0)
                 {
                     
                     test.setText(grid[row][col].getnumber() +","+ grid[row][col+1].getnumber() +","+ grid[row][col+2].getnumber());
                 }
                 else
                 {
                     test.setText(grid[row][col].getnumber() +","+ grid[row][col-1].getnumber());
                 }
             }
         });
         topleft.hoverProperty().addListener((ChangeListener<Boolean>) new ChangeListener<Boolean>() {

             @Override
             public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                 if(col == 0 && row != 0)
                 {
                     test.setText(grid[row][col].getnumber() +","+ grid[row][col+1].getnumber() +","+ grid[row][col+2].getnumber()+","+grid[row-1][col].getnumber()+","+grid[row-1][col+1].getnumber()+","+grid[row-1][col+2].getnumber());
                 }
                 else if(row != 0)
                 {
                     test.setText(grid[row][col].getnumber()+","+grid[row][col-1].getnumber()+","+grid[row-1][col].getnumber()+","+grid[row-1][col-1].getnumber());
                 }
                 else if(row == 0 && col == 0)
                 {
                     test.setText(grid[row][col].getnumber() +","+ grid[row][col+1].getnumber() +","+ grid[row][col+2].getnumber()+",00,0");
                 }
                 else if(row == 0 && col == 1)
                 {
                     test.setText(grid[row][col].getnumber() +","+ grid[row][col-1].getnumber() +",00");
                 }
                 else
                 {
                     test.setText(grid[row][col].getnumber() +","+ grid[row][col-1].getnumber() +",0");
                 }
             }
         });
         topright.hoverProperty().addListener((ChangeListener<Boolean>) new ChangeListener<Boolean>() {

             public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                 if(col == 0 && row != 11)
                 {
                     test.setText(grid[row][col].getnumber() +","+ grid[row][col+1].getnumber() +","+ grid[row][col+2].getnumber()+","+grid[row+1][col].getnumber()+","+grid[row+1][col+1].getnumber()+","+grid[row+1][col+2].getnumber());
                 }
                 else if(row != 11)
                 {
                     test.setText(grid[row][col].getnumber()+","+grid[row][col-1].getnumber()+","+grid[row+1][col].getnumber()+","+grid[row+1][col-1].getnumber());
                 }
                 else if(col == 0 && row == 11)
                 {
                      test.setText(grid[row][col].getnumber() +","+ grid[row][col+1].getnumber() +","+ grid[row][col+2].getnumber());
                 }
                 else
                 {
                     test.setText(grid[row][col].getnumber()+","+grid[row][col-1].getnumber());
                 }
             }
         });
         rightpane.hoverProperty().addListener((ChangeListener<Boolean>) new ChangeListener<Boolean>() {

             public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                 if(row != 11){
                     test.setText(grid[row][col].getnumber() +","+grid[row+1][col].getnumber());
                 }
                 else
                 {
                     test.setText(grid[row][col].getnumber()+"");
                 }
             }
         });
         leftpane.hoverProperty().addListener((ChangeListener<Boolean>) new ChangeListener<Boolean>() {

             @Override
             public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                 if(row != 0){
                     test.setText(grid[row][col].getnumber() +","+grid[row-1][col].getnumber());
                 }
                 else if(col == 2)
                 {
                     test.setText(grid[row][col].getnumber()+",0");
                 }
                 else if(col == 1)
                 {
                     test.setText(grid[row][col].getnumber()+",0,00");
                 }
                 else
                 { 
                     test.setText(grid[row][col].getnumber()+",00");
                 }
             }
         });
         centerpane.hoverProperty().addListener((ChangeListener<Boolean>) new ChangeListener<Boolean>() {

             @Override
             public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                 test.setText(grid[row][col].getnumber()+"");
             }
         });
         //set pref height and width so it looks good
         bottomcenter.setPrefHeight(10);
         bottomright.setPrefWidth(10);    
         bottomleft.setPrefWidth(10);
         topcenter.setPrefHeight(10);
         topleft.setPrefWidth(10);
         topright.setPrefWidth(10);
         toppane.setPrefHeight(10);
         bottompane.setPrefHeight(10);
         leftpane.setPrefWidth(10);
         rightpane.setPrefWidth(10);
         // put everything together
         borderpane.setBottom(bottompane);
         centerpane.getChildren().add(label);
         label.layoutXProperty().bind(centerpane.widthProperty().subtract(label.widthProperty()).divide(2));
         label.layoutYProperty().bind(centerpane.widthProperty().subtract(label.heightProperty()).divide(2));
         bottompane.setCenter(bottomcenter);
         bottompane.setRight(bottomright); 
         bottompane.setLeft(bottomleft);
         toppane.setLeft(topleft);
         toppane.setRight(topright);
         toppane.setCenter(topcenter);
         borderpane.setCenter(centerpane);
         borderpane.setLeft(leftpane);
         borderpane.setRight(rightpane);
         borderpane.setBottom(bottompane);
         borderpane.setTop(toppane);
         // set Backgroundcolor of the BorderPane
         if((grid[row][col].getnumber() < 11 || (grid[row][col].getnumber() <29 && grid[row][col].getnumber() > 18) ? grid[row][col].getnumber() % 2 == 0 : grid[row][col].getnumber() % 2 != 0)) // to look if cell should be red or black
         {
             borderpane.setStyle("-fx-background-color: black;");
             grid[row][col].setisblack(true);
         }
         else
         {
              borderpane.setStyle("-fx-background-color: red;");
              grid[row][col].setisblack(false);
         }
         
         numbers.add(borderpane, row, col);

    }
    //adds a mini chip that will follow your cursor or places the chip on the board
    @FXML
    private void makebet(MouseEvent event) {
        if(haschip == false || event.getSource().getClass().equals(iv2.getClass())) {
            // creates the mini chip
            try{
        ImageView iv = (ImageView) event.getSource();
        chip = Integer.parseInt(iv.getId().substring(3));
        if(model.getbalance() >= chip){
        iv2.setImage(iv.getImage());
        iv2.setScaleX(0.03125);
        iv2.setScaleY(0.03125);
        haschip = true;
        }
        else{
            ap.getChildren().add(PaneManager.createPane(ap.getWidth(), ap.getHeight(),"Sorry, but you don't have enough Money"));
        }
            }catch(Exception e){}
        }
        else if(haschip == true){
            // places the mini chip
            Pane pane  = (Pane) event.getSource();
            ImageView iv = new ImageView(iv2.getImage());
            iv.setScaleX(0.015625);
            iv.setScaleY(0.015625);
            iv.setSmooth(true);
            iv.setLayoutX(event.getSceneX() - 378);
            iv.setLayoutY(event.getSceneY() - 378);
            ap.getChildren().add(iv);
            betchips.add(iv);
            model.makebet(test.getText() + ":" + chip);
            haschip = false;
            iv2.setVisible(false);
            totalbet.setText("Total Bet: " + model.getbetamount());
            balance.setText("Balance: " + model.getbalance());
        }
    }
    // so that the mini chip follows the cursor
    @FXML
    private void move(MouseEvent event) {
        if (haschip == true) {
            iv2.setVisible(true);
            iv2.setX(event.getX() - 355);
            iv2.setY(event.getY() - 355);
        }
    }
    // method to spin the wheel and ball

    @FXML
    private void spin(ActionEvent event) throws InterruptedException {
        Bspin.setDisable(true);
        //rotates the wheel
        RotateTransition RT1 = new RotateTransition(Duration.millis(16000), wheel);
            RT1.setCycleCount(1);
            RT1.setByAngle(500f+(1000f*Math.random()));
            RT1.play();         
            //rotates the ball
            final Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, new EventHandler() {

            int movingStep = 0;
            boolean beginning = true;
            int remove = 250;

            @Override
            public void handle(Event event) {
                movingStep++;
                double angleAlpha = movingStep * (Math.PI / 30);
                double midPointx = wheel.getLayoutX() + (wheel.getWidth() / 2) - 215;
                double midPointy = wheel.getLayoutY() + (wheel.getHeight() / 2) - 160;
                double ballorbit = midPointy - ball.getCenterY();
                beginning = false;

                moveBall(ball, midPointx + (ballorbit - remove) * Math.sin(angleAlpha), midPointy - (ballorbit - remove) * Math.cos(angleAlpha));
                // Reset after one orbit.
                if (movingStep == 360) {
                    movingStep = 0;
                }
                if (remove > 207 && movingStep % 2 == 0) {
                    remove -= 1;
                    ball.setVisible(true);
                }
            }
        }), new KeyFrame(Duration.millis(60)));
             timeline.setCycleCount(100+r.nextInt(50));
             timeline.play();
             // waits 17 seconds and then runs the code.
            Timeline pause = new Timeline(new KeyFrame(Duration.millis(17000), new EventHandler<ActionEvent>() {   
            @Override
            public void handle(ActionEvent event) {
                int won = model.check(landedin());
                ap.getChildren().add(PaneManager.createPane(ap.getWidth(), ap.getHeight(), "The ball landed in field:" + landedin() + " \r\n" + (won < 0 ? "You lost " + (won * -1) : "You won " + won)));
                balance.setText("Balance: " + model.getbalance());
                totalbet.setText("Total Bet: " + model.getbetamount());
                // removes all chips on the boards
                for(ImageView iv : betchips){
                ap.getChildren().remove(iv);
                }
                //resets the ball
                ball.setLayoutX(ballx);
                ball.setLayoutY(bally);
                ball.setRotate(0);
                wheel.setRotate(0);
                Bspin.setDisable(false);
            }
        }));
        pause.setCycleCount(1);
        pause.play();

    }
    // method to move the ball to the next place
     private void moveBall(Circle ball, double x, double y) {
         
        TranslateTransition move = TranslateTransitionBuilder.create()
                .node(ball)
                .toX(x)
                .toY(y)
                .duration(Duration.millis(60))
                .build();

        move.playFromStart();
    }
     
    // looks in what field the ball landed in
    private String landedin()
    {
        
         Rectangle[] wheeldata = {rec00,rec27,rec10,rec25,rec29,rec12,rec8,rec19,rec31,rec18,rec6,rec21,rec33,rec16,rec4,rec23,rec35,rec14,rec2,rec0,rec28,rec9,rec26,rec30,rec11,rec7,rec20,rec32,rec17,rec5,rec22,rec34,rec15,rec3,rec24,rec36,rec13,rec1};      
        for (Rectangle rec : wheeldata) {
            Node rectangle = rec;
            Bounds recbounds = rectangle.localToScene(rectangle.getBoundsInLocal());
            Node circleball = ball;
            Bounds ballbounds = circleball.localToScene(circleball.getBoundsInLocal());
            if (ballbounds.intersects(recbounds)) // looks if ball is in a rectangle
            {

                return rec.getId().substring(3); // gives a substring of the rectangle that the ball landed in. The substring of 3 gives the number.
            }
        }
        return "whoops ein Fehler!";
    }
    //for going back to the Selection Screen
    @FXML
    private void back(ActionEvent event) throws IOException {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).hide();
    }

    // for all Panes in the zero BorderPane to show the numbers
    @FXML
    private void zero(MouseEvent event) {
        Pane pane = (Pane) event.getSource();
        String id = pane.getId();
        if ("zerocenter".equals(id) || "zerobottom".equals(id)) {
            test.setText("0");
        } else if ("zeroright".equals(id)) {
            test.setText("0,1,2");
        } else if ("zerotopright".equals(id)) {
            test.setText("0,00,2");
        } else if ("zerobottomright".equals(id)) {
            test.setText("0,00,1,2,3");
        } else if ("zerotop".equals(id)) {
            test.setText("0,00");
        }
    }

    // for all Panes in the doublezero BorderPane to show the numbers
    @FXML
    private void doublezero(MouseEvent event) {
        Pane pane = (Pane) event.getSource();
        String id = pane.getId();
        if ("doublecenter".equals(id) || "doubletop".equals(id)) {
            test.setText("00");
        } else if ("doubleright".equals(id)) {
            test.setText("00,3,2");
        } else if ("doubletopright".equals(id)) {
            test.setText("00,0,3,2,1");
        } else if ("doublebottomright".equals(id)) {
            test.setText("00,0,2");
        } else if ("doublebottom".equals(id)) {
            test.setText("00,0");
        }
    }
    //a method for all the other fields so that you know what you select
    @FXML
    private void hover(MouseEvent event) {
        Pane pane = (Pane) event.getSource();
        switch (pane.getId()) {
        case "low":
            test.setText(forString(1,19,1));
            break;
        case "high":
            test.setText(forString(19,37,1));
            break;
        case "redbet":
            String result = "";
            for(int row = 0;row < 12;row++)
            {
                for(int col = 2;col >= 0;col--)
                {
                    result +=(grid[row][col].isblack() == false ? ","+grid[row][col].getnumber() : "");
                }
                test.setText(result.substring(1));
            }
            break;
        case "blackbet":
            result = "";
            for(int row = 0;row < 12;row++)
            {
                for(int col = 2;col >= 0;col--)
                {
                    result +=(grid[row][col].isblack() == true ? ","+grid[row][col].getnumber() : "");
                }
                test.setText(result.substring(1));
            }
            break;
        case "third1":
            test.setText(forString(1,13,1));
            break;
        case "third2":
            test.setText(forString(13,25,1));
            break;
        case "third3":
            test.setText(forString(25,37,1));
            break;
        case "row1":
            test.setText(forString(3,37,3));
            break;
        case "row2":
            test.setText(forString(2,37,3));
            break;
        case "row3":
            test.setText(forString(1,36,3));
            break;
        case "odd":
            result = "";
            for(int i = 1; i < 37; i++)
            {
                result += (i % 2 != 0 ? "," + i: "");
            }
            test.setText(result.substring(1));
            break;
        case "even":
            result = "";
            for(int i = 1; i < 37; i++)
            {
                result += (i % 2 == 0 ? "," + i: "");
            }
            test.setText(result.substring(1));
            break;
        }
    }
    
    // a method to reduce the code above
    private String forString(int beginning,int number,int plus)
    {
        String result = "";
        for(int i = beginning; i  < number;i += plus)
        {
            result += ","+i;
        } 
        return result.substring(1);
    }
    //resets the text to nothing when you don't select anything
    @FXML
    private void exit(MouseEvent event) {
        test.setText("");
    }
    
}
