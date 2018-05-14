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
    IMatProductItem productItem;
    private Map<String, IMatProductItem> iMatProductItemMap = new HashMap<String, IMatProductItem>();
    @FXML private FlowPane mainPage;
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
        populateProductDetail();
        updateProduct();
    }


    public void populateList(){
        for(Product product : iMatDataHandler.getProducts()){
            iMatProductItemMap.put(product.getName(), new IMatProductItem(product, this.iMatDataHandler));
        }

    }

    public void updateProduct(){
        mainPage.getChildren().clear();
        List<Product> products = iMatDataHandler.getProducts();
        for (Product product : products) {
            IMatProductItem iMatProductItem = (IMatProductItem) this.iMatProductItemMap.get(product.getName());
            mainPage.getChildren().add(iMatProductItem);
        }
    }

    private void setEcoImage(){
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
        setEcoImage();
    }

    // Gör uträkning för alla produkter inom iMatProductItemMap
    private double calcProdSum() {
        double sum = 0.0;
        for (IMatProductItem item : iMatProductItemMap.values()) {
            sum += item.getProduct().getPrice();
        }
        return sum;
    }

}




