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

public class IMatHistoryController extends IMatController implements Initializable{

    private IMatDataHandler dataHandler;
    private List<Order> orders = new ArrayList<>();
    private List<IMatOrderDetailItem> orderDetailItems = new ArrayList<>();
    private IMatNavigationHandler navigationHandler;

    @FXML private AnchorPane historyRootPane;
    @FXML private AnchorPane historyViewAnchorPane;
    @FXML private AnchorPane historyDetailAnchorPane;
    @FXML private FlowPane historyOrdersFlowPane;
    @FXML private FlowPane historyDetailFlowPane;
    @FXML private ImageView historyDetailCloseImage;
    @FXML private Label historyDetailDateLabel;
    @FXML private Label historyDetailSumLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataHandler = IMatDataHandler.getInstance();
        navigationHandler = IMatNavigationHandler.getInstance();
        for (Order order : dataHandler.getOrders()) {
            orders.add(order);

        }
        historyOrdersFlowPane.toFront();
        populateOrders();
        shoppingItems();
    }

    private void populateOrders() {
        historyOrdersFlowPane.getChildren().clear();
        for (Order order : orders) {
            IMatHistoryItem historyItem = new IMatHistoryItem(order, this);
            historyOrdersFlowPane.getChildren().add(historyItem);
        }
    }

    protected void setHistoryDetailView(Order order) {
        historyDetailAnchorPane.toFront();
        historyDetailDateLabel.setText("Kundvagn från " + order.getDate().toString()); // May write out gibberish
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
        shoppingItems();
    }

    @FXML protected void shadowPaneOnClick(Event event) {
        historyViewAnchorPane.toFront();
    }
    @FXML protected void closeImageOnClick(Event event) {
        historyViewAnchorPane.toFront();
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
        shoppingItems();
    }

    public void removeFromShoppingCart(ShoppingItem item) {
        dataHandler.getShoppingCart().removeItem(item);
        shoppingItems();
    }


}
