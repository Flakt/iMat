package matbutik;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;


public class IMatOrderDetailItem {
    private Product product;
    private IMatHistoryController historyController;

    @FXML
    private Button orderDetailIncButton;
    @FXML
    private Button orderDetailDecButton;
    @FXML
    private ImageView orderDetailImage;
    @FXML
    private ImageView orderDetailEcoImage;
    @FXML
    private Label orderDetailProductNameLabel;
    @FXML
    private Label orderDetailProductPriceLabel;

    IMatOrderDetailItem(Product p, IMatHistoryController hc ) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("IMatOrderDetailItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException io) {
            throw new RuntimeException(io);
        }
        
        product = p;
        historyController = hc;
    }
}
