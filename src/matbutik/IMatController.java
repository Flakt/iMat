package matbutik;

import javafx.collections.FXCollections;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class IMatController implements Initializable {

    @FXML
        private ListView<String> categories;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //categories = new ListView<>();
        categories.getItems().addAll(
                "Erbjudanden", "Mejeri", "Chark & Pålägg", "Fisk", "Frukt & Grönt", "Bröd", "Kryddhyllan",
                "Dryck", "Frys", "Konfektyr & Kaffebröd");
        categories.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
}
