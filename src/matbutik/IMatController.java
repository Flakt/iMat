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
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.*;

public class IMatController implements Initializable {
    private IMatDataHandler iMatDataHandler;
    private Product product;
    private Map<String, IMatProductController> iMatProductControllerMap = new HashMap<String, IMatProductController>();
    @FXML private TabPane categories;
    @FXML private FlowPane productFlowPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateList();
        updateProduct();
        }
    public void populateList(){
        for(Product product : iMatDataHandler.getProducts()){
            iMatProductControllerMap.put(product.getName(), new IMatProductController(product,iMatDataHandler));
        }

    }

    public void updateProduct(){
        productFlowPane.getChildren().clear();
        List<Product> products = iMatDataHandler.getProducts();
        for (Product product : products) {
            IMatProductController iMatProductController = iMatProductControllerMap.get(product.getName());
            productFlowPane.getChildren().add(iMatProductController);
        }
    }
}
