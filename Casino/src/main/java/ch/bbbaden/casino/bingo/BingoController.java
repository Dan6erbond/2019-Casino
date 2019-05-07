package ch.bbbaden.casino.bingo;

import ch.bbbaden.casino.Databankmanager;
import static java.lang.Math.abs;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JButton;

public class BingoController implements Initializable {

    public int boards;
    private Databankmanager dm = Databankmanager.getInstance();

    int won;
    int key = 0;
    private int money;

    Boolean[] bingorow = {false, false, false, false, false, false, false, false, false, false};
    Boolean[] bingocol = {false, false, false, false, false, false, false, false, false, false};
    private List<Button> ButtonList = new ArrayList<Button>();
    private List tabs = new ArrayList();
    private Random rand = new Random();
    private int Kugel = rand.nextInt(75);
    private List alleziehungen = new ArrayList();
    private List<Integer> bereitsgezogen = new ArrayList();

    @FXML
    private ChoiceBox<Integer> cboxboards;
    @FXML
    private Button btncheck;
    @FXML
    private TabPane tabpane;
    @FXML
    private TitledPane titledPane;
    @FXML
    private Label label;
    @FXML
    private Label lblziehung;
    @FXML
    private Label lblanzahl;
    @FXML
    private ChoiceBox<Integer> txtbetrag;
    @FXML
    private Button btnbingo;
    @FXML
    private Button btnstart;
    @FXML
    private Label lblgeldbetrag;
    @FXML
    private Label lblgewonnen;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cboxboards.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));
        txtbetrag.setItems(FXCollections.observableArrayList(10, 50, 100, 500, 1000));
        cboxboards.getSelectionModel().selectFirst();
        txtbetrag.getSelectionModel().selectFirst();
        Kugelziehen();
        lblgeldbetrag.setText(String.valueOf(getbalance()));

    }

    public void start(Stage stage) throws Exception {

    }

    @FXML
    private void setstart() {
        checkmoney();
        if (money != 0) {
            btnstart.visibleProperty().set(true);
            btnbingo.visibleProperty().set(true);
            createGegenspieler(2);
            if (cboxboards.getSelectionModel().getSelectedItem() != null) {
                boards = cboxboards.getSelectionModel().getSelectedItem();

                tabpane.visibleProperty().set(true);
                titledPane.visibleProperty().set(false);

                List grids = new ArrayList<GridPane>();

                for (int i = 0; i < boards; i++) {
                    createbtn();
                    grids.add(i, fillgrid(creategrid(5, 7, new GridPane()), getButtonList()));
                }

                List tabs = createtabs(boards);

                for (int i = 0; i < tabs.size(); i++) {
                    Tab t = (Tab) tabs.get(i);
                    t.setContent((Node) grids.get(i));
                    tabpane.getTabs().add((Tab) tabs.get(i));
                }

            }
        }
    }

    public List<Button> getButtonList() {
        return ButtonList;
    }

    private GridPane fillgrid(GridPane g, List ButtonList) {
        int count = 1;
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 7; i++) {
                g.add((Node) ButtonList.get(count), i, j);
                count++;
            }
        }
        return g;
    }

    private GridPane creategrid(int spalten, int zeilen, GridPane grid) {

        for (int i = 0; i < zeilen; i++) {
            RowConstraints row = new RowConstraints(50);
            grid.getRowConstraints().add(row);
        }

        for (int i = 0; i < spalten; i++) {
            ColumnConstraints col = new ColumnConstraints();
            grid.getColumnConstraints().add(col);
        }
        return grid;
    }

    private List createtabs(int anztabs) {

        for (int i = 0; i < anztabs; i++) {
            Tab t = new Tab("Spielbrett " + Integer.toString(i + 1));
            t.getStyleClass().add("bingostyle.css");
            t.setId(Integer.toString(i));
            tabs.add(t);
        }

        return tabs;
    }

    private void createbtn() {
        ButtonList.clear();
        Integer[] card = new Integer[75];

        for (int i = 0; i <= 74; i++) {
            card[i] = i;
        }

        ArrayList<Integer> shuffled = new ArrayList<>(Arrays.asList(card));

        Collections.shuffle(shuffled);

        for (int j = 0; j <= 35; j++) {
            final int number = shuffled.get(j);

            Button b = new Button(Integer.toString(number));
            b.setMinHeight(40);
            b.setMinWidth(40);
            b.setMaxSize(40, 40);
            b.setStyle("-fx-background-color: #214bbc");
            b.setOnAction((ActionEvent) -> {
                boolean hoi = handleButton(number);
                if (hoi == true) {
                    b.setStyle("-fx-background-color: #FFFFFF;");
                } else if (b.getStyle().equals("-fx-background-color: #214bbc;")) {
                    b.setStyle("-fx-background-color: #000000;");
                } else {
                    b.setStyle("-fx-background-color: #214bbc;");
                }

            });
            ButtonList.add(b);
        }

        ButtonList.sort((o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.getText()), Integer.parseInt(o2.getText()));
        });
    }

    private boolean handleButton(int number) {
        if (lblziehung.getText().equals(String.valueOf(number))) {
            return true;
        }
        return false;
    }

    private void start() {

        Timeline pause = new Timeline(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
            int zahl;

            @Override
            public void handle(ActionEvent event) {
                showNumber(zahl++);
                showgegner(2);
            }
        }));

        pause.setCycleCount(75);

        pause.play();

    }

    public void Kugelziehen() {

        while (alleziehungen.size() < 75) {
            Kugel = rand.nextInt(75);
            if (!alleziehungen.contains(Kugel)) {
                alleziehungen.add(Kugel);
            }

        }

    }

    public void showNumber(int zahl) {
        Integer nummer = (Integer) alleziehungen.get(zahl);
        bereitsgezogen.add(nummer);
        lblziehung.setText(lblziehung.getText() + " " + Integer.toString(nummer));
        if (zahl % 10 == 0) {
            lblziehung.setText(lblziehung.getText() + "\r\n");
        }
    }

    public void checkbingo() {
        /* todo:  All Children of Gridpane in a list consisting of Seven nodes each row
                   with 5 column .sublist()
         */

        GridPane g = null;

        ArrayList<GridPane> tabcontent = new ArrayList<GridPane>();

        for (Tab t : tabpane.getTabs()) {
            if (t.getContent() instanceof GridPane) {
                tabcontent.add((GridPane) t.getContent());
            }

        }

        for (int i = 0; i < tabcontent.size(); i++) {
            g = (GridPane) tabcontent.get(i);

        }
        int key = 0;
        for (int j = 7; j < g.getChildren().size() - 1; j = j + 7) {
            if (g.getChildren().get(j) instanceof Button) {
                ObservableList<Node> n = g.getChildren();

                for (int i = 1; i <= 7; i++) {
                    Button b = (Button) n.get(j - i);
                    if (b.getStyle().contains("-fx-background-color: #FFFFFF;") || (b.getStyle().contains("-fx-background-color: #000000;")) && bereitsgezogen.contains(Integer.valueOf(b.getText()))) {
                        key++;
                        n.get(j - i).setStyle("-fx-background-color:#214bbc");
                    }
                }
                if (key >= 7) {
                    System.out.println("erreicht");
                    dm.setchipamount(dm.getchipamount() + getMoney());
                    key = 0;
                }
            }

        }

    }

    @FXML
    private void Kugelziehenstart(ActionEvent event
    ) {
        if (btnstart.getText().equals("Start")) {
            btnstart.setText("Spiel beenden");
            start();
        } else if (btnstart.getText().equals("Spiel beenden")) {
            System.exit(0);
        }
    }

    private void createGegenspieler(int anz) {
        List grids = new ArrayList<GridPane>();
        List<Button> buttons = getButtonList();

        for (int i = 0; i < anz; i++) {
            Tab t = new Tab("Gegenspieler");
            tabpane.getTabs().add(t);
        }

        for (int i = 0; i < anz; i++) {
            createbtn();
            for (Button b : buttons) {
                b.setMouseTransparent(true);
            }
            grids.add(i, fillgrid(creategrid(5, 7, new GridPane()), getButtonList()));
            (tabpane.getTabs().get(boards + i)).setContent((GridPane) grids.get(i));
        }
        lblanzahl.setText(String.valueOf(anz));

    }

    private void showgegner(int cnt) {
        int[][] btnset = new int[6][7];
        int[][] row = new int[2][6];
        int[][] col = new int[2][7];

        ArrayList<GridPane> gridpanes = new ArrayList<GridPane>();
        ArrayList<Button> btn = new ArrayList<Button>();

        for (int i = 0; i < tabpane.getTabs().size() - boards; i++) {
            gridpanes.add((GridPane) tabpane.getTabs().get(i).getContent());

        }

        for (int i = 0; i < gridpanes.size(); i++) {
            for (int j = 0; j < gridpanes.get(i).getChildren().size(); j++) {
                btn.add((Button) gridpanes.get(i).getChildren().get(j));
            }

            for (Button b : btn) {
                if (bereitsgezogen.contains(Integer.valueOf(b.getText()))) {
                    b.setStyle("-fx-background-color:#000000");
                }
            }

            for (int j = 0; j < gridpanes.get(i).getChildren().size(); j++) {

                Button b = (Button) gridpanes.get(i).getChildren().get(j);

                if (b.getStyle().equals("-fx-background-color:#000000") && bereitsgezogen.contains(Integer.valueOf(b.getText()))) {
                    row[i][GridPane.getRowIndex(b)] += 1;
                    col[i][GridPane.getColumnIndex(b)] += 1;
                }
                
                int num = 0;
                for (int k = 0; k < 5; k++) {
                    if (row[i][k] == 7) {
                        for (int l = 0; l < 6; l++) {
                            if (col[i][l] == 1) {
                                num++;
                            }
                            if (num==7) {
                                System.out.println("Bingogegner");
                            }
                        }
                    }
                }
//
//                if (row[i][GridPane.getRowIndex(b)] == gridpanes.get(i).getRowConstraints().size() && bingorow[GridPane.getRowIndex(b)] == false) {
//                    bingorow[GridPane.getRowIndex(b)] = true;
//                    System.out.println("Bingogegner");
//                    setMoney(0);
//                }

//                if (row[GridPane.getColumnIndex(b)] == gridpanes.get(i).getColumnConstraints().size() && bingorow[GridPane.getColumnIndex(b)] == false) {
//                    bingorow[GridPane.getRowIndex(b)] = true;
//                    System.out.println("Bingogegner");
//                    setMoney(0);
//                }
            }

        }

    }

    public int getbalance() {
        return dm.getchipamount();
    }

    private void checkgegner() {

//        ArrayList<GridPane> grid = new ArrayList<GridPane>();
//        ArrayList<Button> btn = new ArrayList<Button>();
//
//        for (int i = 0; i < tabpane.getTabs().size() - boards; i++) {
//            grid.add((GridPane) tabpane.getTabs().get(i).getContent());
//        }
//
//        for (int i = 0; i < grid.size(); i++) {
//            for (int j = 0; j < grid.get(i).getChildren().size(); j++) {
//                btn.add((Button) grid.get(i).getChildren().get(i));
//            }
//
//            int key = 0;
//            for (Button b : btn) {
//                if (b.getStyle().equals("-fx-background-color:#000000") && bereitsgezogen.contains(Integer.valueOf(b.getText()))) {
//                    key++;
//                }
//
//                if (key >= 7) {
//                    System.out.println("Bingo gegner");
//                    key = 0;
//                }
//            }
//        }
    }

    @FXML
    private void btnbingo(ActionEvent event) {
        checkbingo();
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    private void checkmoney() {
        int money2 = txtbetrag.getSelectionModel().getSelectedItem();
        if (getbalance() < money2) {
            setMoney(money2);
//            dm.setchipamount(abs(dm.getchipamount() - money2));
        }
    }

}
