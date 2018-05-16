package matbutik;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Screen;
import se.chalmers.cse.dat216.project.*;

import java.net.URL;
import java.util.*;

public class IMatShoppingCartController extends IMatModularCartController implements Initializable {

    /*
        Untested as of 2018-05-02

        This Class should have:
            - All necessary Fx:id
            - A method to display all products in customer shopping cart
            - Methods/Functionality to be assigned to all the buttons

     */
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
        //
        // TEST
        // add something to the shopping cart
        System.out.println(dataHandler.getProduct(1));

        // Why does the following require a reference to this class?
        ShoppingItem sI  = new ShoppingItem(dataHandler.getProduct(1));

        IMatShoppingItem iMatShoppingItem = new IMatShoppingItem(sI, this);
        iMatShoppingItemMap.put("Gröna ärter", new IMatShoppingItem(new ShoppingItem(dataHandler.getProduct(1)), this));
        shoppingCart.addItem(new ShoppingItem(dataHandler.getProduct(1)));


        updateProductsList();
        // TEST END
        shoppingCart.addShoppingCartListener(new ShoppingCartListener() {
            @Override
            public void shoppingCartChanged(CartEvent cartEvent) {
                updateProductsList();
            }
        });
        for (ShoppingItem shoppingItem : shoppingCart.getItems()) {
            iMatShoppingItemMap.put(shoppingItem.getProduct().getName(), new IMatShoppingItem(shoppingItem,this));
        }
        populateFlowPane();
         updateProductsList();
    }

    @FXML
    private void navigateBack(Event event){
        ScreenController.getInstance().navigateToPrevious();
    }

    protected void populateFlowPane() {
        cartItemsFlowPane.getChildren().clear();
        super.updateProductsList();
        for (IMatShoppingItem item : super.iMatShoppingItemMap.values()) {
            cartItemsFlowPane.getChildren().add(item);
        }
    }

    @FXML
    protected void removeAllAction() {
        // Not assigned yet
        cartItemsFlowPane.getChildren().clear();
        backupShoppingItems.addAll(shoppingCart.getItems());   // May or may not work
        shoppingCart.clear();
        iMatShoppingItemMap.clear();
    }

    @FXML
    protected void regretRemove() {
        // Not assigned yet
        for (ShoppingItem item : backupShoppingItems) {
            iMatShoppingItemMap.put(item.getProduct().getName(), new IMatShoppingItem(item,this));
            shoppingCart.addItem(item);
        }
        backupShoppingItems.clear();
    }

}

