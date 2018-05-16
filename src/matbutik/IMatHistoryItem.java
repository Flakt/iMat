package matbutik;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;


import java.io.IOException;
import java.util.ArrayList;

public class IMatHistoryItem extends AnchorPane {

    private Order order;
    private IMatHistoryController historyController;

    @FXML
    private Label dateLabel;
    @FXML
    private Label numberOfProductsLabel;
    @FXML
    private Label sumLabel;

    public IMatHistoryItem(Order order, IMatHistoryController historyController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("IMatHistoryItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        }
        catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.order = order;
        this.historyController = historyController;
        dateLabel.setText(String.valueOf(order.getDate()));
        if (order.getItems().size() > 1) {
            numberOfProductsLabel.setText(String.valueOf(order.getItems().size()) + " st varor");
        } else {
            numberOfProductsLabel.setText(String. valueOf(order.getItems().size() + " vara"));
        }
        sumLabel.setText(String.valueOf(String.valueOf(calcSum(order))) + " kr");
    }

    private double calcSum(Order order) {
        return historyController.sumOfAllProducts(order);
    }

    @FXML
    protected void onClick(Event event) {
        historyController.setHistoryDetailView(order);
    }
}
