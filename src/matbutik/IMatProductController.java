package matbutik;

import javafx.fxml.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import javafx.fxml.Initializable;
import se.chalmers.cse.dat216.project.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class IMatProductController extends AnchorPane implements Initializable {
    private IMatDataHandler iMatDataHandler;
    private Product product;
    private Map<String, IMatProductController> iMatProductControllerMap = new HashMap<String, IMatProductController>();
    @FXML private FlowPane productFlowPane;
    @FXML private AnchorPane productPane;
    @FXML private ImageView productImage;
    @FXML private ImageView productImageEko;
    @FXML private Label productName;
    @FXML private Label productInfo;
    @FXML private Label productTotalPrice;
    @FXML private Label productPrice;
    @FXML private AnchorPane spinner;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateList();
        updateProduct();
        populateProductDetail();
    }

    public IMatProductController(Product p,IMatDataHandler h){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("recipe_listitem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.product = p;
        this.iMatDataHandler = h;
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

    private void isEco(){
        if(product.isEcological()){
            productImageEko.setImage(new Image("resouces/Eko_logo.jpg"));
        }
    }

    private void populateProductDetail(){
        productImage.setImage(iMatDataHandler.getFXImage(product));
        productName.setText(product.getName());
        productInfo.setText(product.toString());
        productPrice.setText(((Double)product.getPrice()).toString() + " kr");
        productTotalPrice.setText(((Double)product.getPrice()).toString()); //TODO: fixar en metod som gör uträkning för totalt pris.
        isEco();
    }

}




