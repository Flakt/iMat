package matbutik;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.util.ResourceBundle;

public class IMatShoppingCartController implements Initializable {

    /*
        Untested as of 2018-05-02

        This Class should have:
            - All necessary Fx:id
            - A method to display all products in customer shopping cart
            - Methods/Functionality to be assigned to all the buttons

     */
    private IMatDataHandler dataHandler;
    private ShoppingCart shoppingCart;

    @FXML
    private AnchorPane cartItemsPane;
    @FXML
    private AnchorPane cartDetailsPane;
    @FXML
    private FlowPane cartItemsFlowPane;
    @FXML
    private Button backButton;
    @FXML
    private Button historyButton;
    @FXML
    private Button helpButton;
    @FXML
    private Button removeAllButton;
    @FXML
    private Button regretButton;
    @FXML
    private Button toPaymentButton;
    @FXML
    private Label numberOfProductsLabel;
    @FXML
    private Label totalCostLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataHandler = IMatDataHandler.getInstance();
        shoppingCart = dataHandler.getShoppingCart();
        for (ShoppingItem shoppingItem : dataHandler.getShoppingCart().getItems()) {
            // Create new fxml items for each here?, or use already existing class?
        }
        // If create a new object for every item in list, run an update method
        // Should also consider moving this and for-loop as a separate method
    }

    public String getCartItemAmount(ShoppingItem item) {
        return String.valueOf(item.getAmount());
    }

    public String getCartItemPrice(ShoppingItem item) {
        return String.valueOf(item.getTotal());
    }

    // Actually check if it loads the correct image
    public Image getCartItemImage(ShoppingItem item) {
        return new Image("file:" + System.getProperty("user.home") + "/.dat215/imat/images/" + item.getProduct().getImageName());
    }

    public void incrementProductAmount(ShoppingItem item) {
        item.setAmount((item.getAmount() + 1.0));
    }

    public void decrementProductAmount(ShoppingItem item) {
        if (item.getAmount() < 1.0) {
            item.setAmount((item.getAmount() - 1.0));
        }
        else {
            shoppingCart.removeItem(item);
        }
    }

}

