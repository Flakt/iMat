package matbutik;

import javafx.collections.FXCollections;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.util.*;
import java.util.concurrent.Flow;

public class IMatController implements Initializable {



    @FXML FlowPane searchResult;
    //@FXML AnchorPane mainPage;
    @FXML ScrollPane searchResultContainer;

    IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    private Map<String, IMatProductItem> iMatProductItemMap = new HashMap<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listItems();
        searchResultContainer.toFront();
    }

        private void listItems() {

            for (Product product : dataHandler.getProducts()) {
                searchResult.getChildren().add(new IMatProductItem(product, dataHandler));
                System.out.println(product.getProductId() + " — " + product.getName());
            }
        }




}
