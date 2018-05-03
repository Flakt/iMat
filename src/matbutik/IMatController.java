package matbutik;

import javafx.collections.FXCollections;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class IMatController implements Initializable {

   
    @FXML
        private TabPane categories;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //categories = new ListView<>();
        //categories.getItems().addAll(
        //        "Erbjudanden", "Mejeri", "Chark & Pålägg", "Fisk", "Frukt & Grönt", "Bröd", "Kryddhyllan",
        //        "Dryck", "Frys", "Konfektyr & Kaffebröd");
        //categories.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);



    }
}
