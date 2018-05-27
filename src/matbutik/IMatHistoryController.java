package matbutik;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.awt.*;
import java.net.URL;
import java.text.DateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.Flow;

public class IMatHistoryController extends IMatController implements Initializable{
    @FXML private FlowPane historyFlowPane;
    @FXML private AnchorPane historyMainAnchorPane;
    @FXML private AnchorPane historyDetailAnchorPane;
    @FXML private FlowPane historyDetailFlowPane;
    @FXML private Label historyDetailDateLabel;
    @FXML private FlowPane shoppingCartFlowPane;
    private IMatDataHandler dataHandler;
    private List<Order> orders = new ArrayList<>();
    private List<IMatOrderDetailItem> orderDetailItems = new ArrayList<>();




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataHandler = IMatDataHandler.getInstance();
        navigationHandler = IMatNavigationHandler.getInstance();
        for (Order order : dataHandler.getOrders()) {
            orders.add(order);
        }

        historyMainAnchorPane.toFront();
        populateOrders();
        updateShoppingItems();
    }

    private void populateOrders() {
        historyFlowPane.getChildren().clear();
        for (Order order : orders) {
            IMatHistoryItem historyItem = new IMatHistoryItem(order, this);
            historyFlowPane.getChildren().add(historyItem);
        }
    }


    public double sumOfAllProducts(Order order) {
        double sum = 0;
        for (ShoppingItem item : order.getItems()) {
            sum += (item.getProduct().getPrice() * item.getAmount());
        }
        return sum;
    }

    public void populateHistoryDetailView(Order order) {
        historyDetailFlowPane.getChildren().clear();
        orderDetailItems.clear();
        Date input = order.getDate();
        DateFormat parser = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
        String formattedDate = parser.format(input);
        historyDetailDateLabel.setText("Kundvagn från " + formattedDate);
        for (ShoppingItem item : order.getItems()) {
            IMatOrderDetailItem orderDetailItem = new IMatOrderDetailItem(item,order);
            orderDetailItems.add(orderDetailItem);
            historyDetailFlowPane.getChildren().add(orderDetailItem);
        }
    }

    public void setHistoryDetailView(Order order){
        historyDetailAnchorPane.toFront();
        populateHistoryDetailView(order);
    }

    @FXML
    protected void putAllInCart() {
        // Clear shoppingCart before?
        for (IMatOrderDetailItem item : orderDetailItems) {
            dataHandler.getShoppingCart().addItem(item.getShoppingItem());
        }
        updateShoppingItems();
    }

}



