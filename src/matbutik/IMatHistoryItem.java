package matbutik;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class IMatHistoryItem extends AnchorPane {

    private Order order;
    private IMatHistoryController historyController;

    @FXML
    private Label dateLabel;
    @FXML
    private Label numberOfProductsLabel;
    @FXML
    private Label sumLabel;


    protected void setupFXML(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("IMatHistoryItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        }
        catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public IMatHistoryItem(Order order, IMatHistoryController historyController) {

        setupFXML();
        this.order = order;
        this.historyController = historyController;
        setDateLabel();
        if (order.getItems().size() > 1) {
            numberOfProductsLabel.setText(String.valueOf(order.getItems().size()) + " st varor");
        } else {
            numberOfProductsLabel.setText(String. valueOf(order.getItems().size() + " vara"));
        }
        sumLabel.setText(String.format("%.2f", calcSum(order)) + " kr");
    }

    private void setDateLabel(){
        String input = order.getDate().toString();
        SimpleDateFormat parser = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
        Date date = null;
        try {
            date = parser.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(date);
        dateLabel.setText("Kundvagn från " + formattedDate);

    }
    private double calcSum(Order order) {
        return historyController.sumOfAllProducts(order);
    }

    @FXML
    protected void onClick(Event event) {
        historyController.setHistoryDetailView(order);
    }
}
