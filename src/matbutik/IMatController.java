package matbutik;

import javafx.collections.FXCollections;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Flow;

public class IMatController implements Initializable {

    Stage stage;
    @FXML FlowPane searchResult;
    @FXML AnchorPane mainPage;
    @FXML Button goToCashDesk;
    @FXML AnchorPane rootPane;




    IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    private Map<String, IMatProductItem> iMatProductItemMap = new HashMap<>();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listItems();

        }

        private void listItems() {

            for (Product product : dataHandler.getProducts()) {
                searchResult.getChildren().add(new IMatProductItem(product, dataHandler));
            }
    }

    @FXML
    public void openCashDesk(Event event) throws IOException {
        screenController.setScreen("shoppingCart");

    }

}
