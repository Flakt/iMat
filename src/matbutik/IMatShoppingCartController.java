package matbutik;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.*;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private Map<String, IMatShoppingItem> iMatShoppingItemMap = new HashMap<String, IMatShoppingItem>();
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
        shoppingCart.addShoppingCartListener(new ShoppingCartListener() {
            @Override
            public void shoppingCartChanged(CartEvent cartEvent) {
                updateProductsList();
            }
        });
        populateProductsList();
        // If create a new object for every item in list, run an update method
        this.updateProductsList();
    }

    private void populateProductsList() {
        for (ShoppingItem shoppingItem : dataHandler.getShoppingCart().getItems()) {
            IMatShoppingItem iMatShoppingItem = new IMatShoppingItem(shoppingItem, this);
            iMatShoppingItemMap.put(shoppingItem.getProduct().getName(), iMatShoppingItem);
        }
    }

    private void updateProductsList() {
        cartItemsFlowPane.getChildren().clear();
        List<ShoppingItem> shoppingItems = dataHandler.getShoppingCart().getItems();
        for (ShoppingItem shoppingItem : shoppingItems) {
            IMatShoppingItem iMatShoppingItem = iMatShoppingItemMap.get(shoppingItem.getProduct().getName());
            cartItemsFlowPane.getChildren().add(iMatShoppingItem);
        }
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
        this.updateProductsList();
    }

    public void decrementProductAmount(ShoppingItem item) {
        if (item.getAmount() < 1.0) {
            item.setAmount((item.getAmount() - 1.0));
        }
        else {
            shoppingCart.removeItem(item);
        }
        this.updateProductsList();
    }

}

