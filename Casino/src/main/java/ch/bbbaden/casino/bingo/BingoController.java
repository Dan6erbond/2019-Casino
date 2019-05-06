package ch.bbbaden.casino.bingo;

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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BingoController implements Initializable {

    public int boards;

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
    private TextField txtbetrag;
    @FXML
    private Button btnbingo;
    @FXML
    private Button btnstart;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cboxboards.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));
        Kugelziehen();

    }

    public void start(Stage stage) throws Exception {

    }

    @FXML
    private void setstart() {
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

        ArrayList<Integer> shuffled = new ArrayList<Integer>(Arrays.asList(card));

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

        Timeline pause = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
            int zahl;

            @Override
            public void handle(ActionEvent event) {
                showNumber(zahl++);
                checkgegner(2);
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
        ArrayList<Node> children = new ArrayList<Node>();

        ArrayList<GridPane> tabcontent = new ArrayList<GridPane>();

        for (Tab t : tabpane.getTabs()) {
            if (t.getContent() instanceof GridPane) {
                tabcontent.add((GridPane) t.getContent());
            }

        }

        for (int i = 0; i < tabcontent.size(); i++) {
            g = (GridPane) tabcontent.get(i);

            for (int j = 0; j < 5; j = j + 5) {
                children.addAll(g.getChildren().subList(j, 5));
            }

        }
        int key = 0;
        for (int j = 7; j < g.getChildren().size() - 1; j = j + 7) {
            if (g.getChildren().get(j) instanceof Button) {
                ObservableList<Node> n = g.getChildren();

                for (int i = 1; i <= 7; i++) {
                    Button b = (Button) n.get(j - i);
                    if (b.getStyle().contains("-fx-background-color: #FFFFFF;") || (b.getStyle().contains("-fx-background-color: #000000;"))) {
                        if (bereitsgezogen.contains(Integer.valueOf(b.getText()))) {
                            key++;
                            n.get(j - i).setStyle("-fx-background-color:#214bbc");
                        }
                    }
                }
                if (key >= 7) {
                    System.out.println("erreicht");
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

    private void checkgegner(int anz) {
        ArrayList<GridPane> gridpanes = new ArrayList<GridPane>();
        ArrayList<Button> btn = new ArrayList<Button>();
        for (int i = 0; i < tabpane.getTabs().size() - boards; i++) {
            gridpanes.add((GridPane) tabpane.getTabs().get(i).getContent());
        }

        for (int i = 0; i < gridpanes.size(); i++) {
            for (int j = 0; j < gridpanes.get(i).getChildren().size(); j++) {
                btn.add((Button) gridpanes.get(i).getChildren().get(j));
            }
            for (int k = 0; k < btn.size(); k++) {
                Button b = btn.get(k);
                if (bereitsgezogen.contains(Integer.valueOf(b.getText()))) {
                    b.setStyle("-fx-background-color:#000000");
                }

                int key = 0;
                for (int j = 0; j < btn.size(); j++) {
                    for (int l = btn.size(); l < 7; l = l - 7) {
                        b = btn.get(j - i);
                        if (b.getStyle().equals("-fx-background-color:#000000")) {

                        }

                    }

                }
            }
        }
    }

    @FXML
    private void btnbingo(ActionEvent event) {
        checkbingo();
    }
}
