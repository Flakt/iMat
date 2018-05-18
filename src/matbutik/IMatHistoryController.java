package matbutik;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class IMatHistoryController implements Initializable{

    private IMatDataHandler dataHandler;
    private List<Order> orders = new ArrayList<>();
    private List<IMatOrderDetailItem> orderDetailItems = new ArrayList<>();
    private IMatNavigationHandler navigationHandler;

    @FXML
    private AnchorPane historyRootPane;
    @FXML
    private AnchorPane historyViewAnchorPane;
    @FXML
    private AnchorPane historyDetailAnchorPane;
    @FXML
    private AnchorPane historyShadowAnchorPane;
    @FXML
    private FlowPane historyOrdersFlowPane;
    @FXML
    private FlowPane historyDetailFlowPane;
    @FXML
    private ImageView historyDetailCloseImage;
    @FXML
    private Label historyNumberOfProductsLabel;
    @FXML
    private Label historySumLabel;
    @FXML
    private Label historyDetailDateLabel;
    @FXML
    private Label historyDetailSumLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataHandler = IMatDataHandler.getInstance();
        navigationHandler = IMatNavigationHandler.getInstance();
        // Have to sort them depending on order number, which should be set @IMatPaymentController
        for (Order order : dataHandler.getOrders()) {
            orders.add(order);
        }
        // TEST
        /*Order testOrder = new Order();
        testOrder.setOrderNumber(2);
        testOrder.setDate(new Date());
        List<ShoppingItem> items = new ArrayList<>();
        ShoppingItem testItem = new ShoppingItem(dataHandler.getProduct(1));
        testItem.setAmount(2.0);
        items.add(testItem);
        testOrder.setItems(items);
        orders.add(testOrder);*/
        // TEST END
        updateShoppingCart();
        populateOrders();
        historyViewAnchorPane.toFront();
    }

    private void updateShoppingCart() {
        /*
        double amount = 0;
        for (ShoppingItem item : dataHandler.getShoppingCart().getItems()) {
            amount += (item.getAmount() * item.getProduct().getPrice());
        }
        */
        this.historyNumberOfProductsLabel.setText("Antal Varor: " + String.valueOf(dataHandler.getShoppingCart().getItems().size()));
        this.historySumLabel.setText("Summa: " + String.valueOf(dataHandler.getShoppingCart().getTotal()));
    }

    private void populateOrders() {
        historyOrdersFlowPane.getChildren().clear();
        for (Order order : orders) {
            IMatHistoryItem historyItem = new IMatHistoryItem(order, this);
            historyOrdersFlowPane.getChildren().add(historyItem);
        }
    }

    protected void setHistoryDetailView(Order order) {
        historyShadowAnchorPane.toFront();
        historyDetailAnchorPane.toFront();
        historyDetailDateLabel.setText("Kundvagn från " + order.getDate().toString()); // May write out gibberish
        historyDetailSumLabel.setText("Summa: " + String.valueOf(sumOfAllProducts(order)));
        populateHistoryDetailView(order);
    }

    private void populateHistoryDetailView(Order order) {
        historyDetailFlowPane.getChildren().clear();
        orderDetailItems.clear();
        for (ShoppingItem item : order.getItems()) {
            IMatOrderDetailItem orderDetailItem = new IMatOrderDetailItem(item,this);
            orderDetailItems.add(orderDetailItem);
            historyDetailFlowPane.getChildren().add(orderDetailItem);
        }
    }

    @FXML
    protected void putAllInCart() {
        // Clear shoppingCart before?
        for (IMatOrderDetailItem item : orderDetailItems) {
            dataHandler.getShoppingCart().addItem(item.getShoppingItem());
        }
        updateShoppingCart();
    }

    @FXML
    protected void shadowPaneOnClick(Event event) {
        historyViewAnchorPane.toFront();
    }

    @FXML
    protected void closeImageOnClick(Event event) {
        historyViewAnchorPane.toFront();
    }

    @FXML
    protected void toAccount() {
        navigationHandler.toAccount();
    }

    @FXML
    protected void toHistory() {
        // Why would you even press this?
        navigationHandler.toHistory();
    }

    @FXML
    protected void toPayment() {
        navigationHandler.toDestination("Payment");
    }

    @FXML
    protected void navigateBack(Event event){
        navigationHandler.goBack();
    }

    public double sumOfAllProducts(Order order) {
        double sum = 0;
        for (ShoppingItem item : order.getItems()) {
            sum += (item.getProduct().getPrice() * item.getAmount());
        }
        return sum;
    }

    public void addToShoppingCart(ShoppingItem item) {
        dataHandler.getShoppingCart().addItem(item);
        updateShoppingCart();
    }

    public void removeFromShoppingCart(ShoppingItem item) {
        dataHandler.getShoppingCart().removeItem(item);
        updateShoppingCart();
    }

}
