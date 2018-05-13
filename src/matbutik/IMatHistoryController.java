package matbutik;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class IMatHistoryController implements Initializable{

    private IMatDataHandler dataHandler;
    private List<Order> orders = new ArrayList<>();

    @FXML
    private FlowPane historyOrdersFlowPane;
    @FXML
    private Label numberOfProductsLabel;
    @FXML
    private Label sumLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataHandler = IMatDataHandler.getInstance();
        // Have to sort them depending on order number, which should be set @IMatPaymentController
        for (Order order : dataHandler.getOrders()) {
            orders.add(order);
        }
    }

    private void populateOrders() {
        historyOrdersFlowPane.getChildren().clear();
        for (Order order : orders) {
            IMatHistoryItem historyItem = new IMatHistoryItem(order, this);
            historyOrdersFlowPane.getChildren().add(historyItem);
        }
    }
    public double sumOfAllProducts(Order order) {
        double sum = 0;
        for (ShoppingItem item : order.getItems()) {
            sum += item.getProduct().getPrice();
        }
        return sum;
    }


}
