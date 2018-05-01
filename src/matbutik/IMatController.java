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
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class IMatController implements Initializable {
    private IMatDataHandler dataHandler;
    private IMatBackendController backendController;


    @FXML
        private TabPane categories;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataHandler = IMatDataHandler.getInstance();
        initCategories();
    }

<<<<<<< HEAD
    private void initCategories() {
        categories = new ListView<>();
        //categories.getItems().addAll(backendController.getCategories());
        //categories.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
=======
        //categories = new ListView<>();
        //categories.getItems().addAll(
        //        "Erbjudanden", "Mejeri", "Chark & Pålägg", "Fisk", "Frukt & Grönt", "Bröd", "Kryddhyllan",
        //        "Dryck", "Frys", "Konfektyr & Kaffebröd");
        //categories.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);



>>>>>>> 171cfcf31c85675c19456b28086d48b018eeeac0
    }
}
