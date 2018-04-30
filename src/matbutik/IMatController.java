package matbutik;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class IMatController implements Initializable {

    @FXML
    private ListView<String> categories;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        categories = new ListView<>();
        categories.setItems(FXCollections.observableArrayList(
                "Erbjudanden", "Mejeri", "Chark & Pålägg", "Fisk", "Frukt & Grönt", "Bröd", "Kryddhyllan",
                "Dryck", "Frys", "Konfektyr & Kaffebröd"));

        
    }
}
