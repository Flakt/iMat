package matbutik;

import javafx.collections.FXCollections;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class IMatController implements Initializable {

    private Map<String, IMatProductController> recipeListItemMap = new HashMap<String, IMatProductController>();
    @FXML private TabPane categories;
    @FXML private FlowPane flowpaneResults;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //categories = new ListView<>();
        //categories.getItems().addAll(
        //        "Erbjudanden", "Mejeri", "Chark & Pålägg", "Fisk", "Frukt & Grönt", "Bröd", "Kryddhyllan",
        //        "Dryck", "Frys", "Konfektyr & Kaffebröd");
        //categories.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);



    }
}
