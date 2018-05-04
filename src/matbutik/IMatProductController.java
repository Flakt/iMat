package matbutik;

import javafx.fxml.*;
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
import java.util.ResourceBundle;

public class IMatProductController extends AnchorPane {
    private Product product;
    private IMatDataHandler productHandler;
    @FXML private AnchorPane productPane;
    @FXML private ImageView productImage;
    @FXML private ImageView productImageEko;
    @FXML private Label productName;
    @FXML private Label productInfo;
    @FXML private Label productTotalPrice;

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
        this.productHandler = h;
    }
}




