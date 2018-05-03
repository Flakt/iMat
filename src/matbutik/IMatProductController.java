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
import java.net.URL;
import java.util.ResourceBundle;

public class IMatProductController implements Initializable {
    private Product product;
    private IMatDataHandler productHandler;
    @FXML private AnchorPane productPane;
    @FXML private ImageView productImage;
    @FXML private ImageView productImageEko;
    @FXML private Label productName;
    @FXML private Label productInfo;
    @FXML private Label productTotalPrice;
    @FXML private FlowPane flowpaneResults;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
