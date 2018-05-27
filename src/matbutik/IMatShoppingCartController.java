package matbutik;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Screen;
import se.chalmers.cse.dat216.project.*;

import java.net.URL;
import java.util.*;

public class IMatShoppingCartController extends IMatModularCartController implements Initializable {

    private IMatNavigationHandler navigationHandler;
    // When object-oriented programming counteracts you
    private static IMatShoppingCartController instance = null;

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
    @FXML
    private Label emptyCartLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        navigationHandler = IMatNavigationHandler.getInstance();
        instance = this;
        //
        // TEST
        // add something to the shopping cart
        // System.out.println(dataHandler.getProduct(1));

        // Why does the following require a reference to this class?
        /*ShoppingItem sI  = new ShoppingItem(dataHandler.getProduct(1));

        IMatShoppingItem iMatShoppingItem = new IMatShoppingItem(sI, this);
        iMatShoppingItemMap.put("Gröna ärter", new IMatShoppingItem(new ShoppingItem(dataHandler.getProduct(1)), this));
        shoppingCart.addItem(new ShoppingItem(dataHandler.getProduct(1)));


        updateProductsList();*/
        // TEST END
        shoppingCart.addShoppingCartListener(new ShoppingCartListener() {
            @Override
            public void shoppingCartChanged(CartEvent cartEvent) {
                updateProductsList();
                checkCart();
                setLabels();
            }
        });
        for (ShoppingItem shoppingItem : shoppingCart.getItems()) {
            iMatShoppingItemMap.put(shoppingItem.getProduct().getProductId(), new IMatShoppingItem(shoppingItem,this,this::updateProductsList));
        }
        populateFlowPane();
        updateProductsList();
        setLabels();
        checkCart();
    }

    public static IMatShoppingCartController getInstance() {
        return instance;
    }

    protected void checkCart() {
        if (shoppingCart.getItems().size() == 0) {
            toPaymentButton.setDisable(true);
            toPaymentButton.setTooltip(new Tooltip("Du har inga varor"));
        }
    }

    protected void setLabels() {
        numberOfProductsLabel.setText("Antal Varor: " + String.valueOf((int)dataHandler.getShoppingCart().getItems().stream().mapToDouble(item -> item.getProduct().getUnit().substring(item.getProduct().getUnit().length() - 2).equals("st") ?item.getAmount():1).sum()));
        totalCostLabel.setText("Summa: " + String.format("%.2f",dataHandler.getShoppingCart().getTotal()) + " kr");
    }

    @FXML
    private void navigateBack(Event event){
        ScreenController.getInstance().activate("");
    }

    @Override protected void populateFlowPane() {
        emptyCartLabel.setVisible(true);
        cartItemsFlowPane.getChildren().clear();
        //super.updateProductsList();
        for (IMatShoppingItem item : super.iMatShoppingItemMap.values()) {
            if (item.shoppingItem != null)
                cartItemsFlowPane.getChildren().add(item);
        }
        emptyCartLabel.setVisible(false);
    }

    @FXML
    protected void removeAction() {
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
            iMatShoppingItemMap.put(item.getProduct().getProductId(), new IMatShoppingItem(item,this,this::updateProductsList));
            shoppingCart.addItem(item);
        }
        backupShoppingItems.clear();
    }

    @FXML private void navigateToHistory(Event e){
        navigationHandler.toHistory();
    }
    @FXML private void navigateToAccount(Event e){
        navigationHandler.toAccount();
    }
    @FXML private void navigateToHelp(Event e){
        navigationHandler.toHelp();
    }
    @FXML private void toPayment(Event e) {
        if (shoppingCart.getItems().size() > 0) {
            navigationHandler.toPaymentDeliveryAddress();
        }
    }
}

