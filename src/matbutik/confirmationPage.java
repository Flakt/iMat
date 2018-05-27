package matbutik;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class confirmationPage extends IMatController implements Initializable {



    @FXML Label name;
    @FXML Label time;
    @FXML Label address;
    @FXML Label date;
    @FXML Label city;


    @FXML
    FlowPane receiptFlowPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Order order = dataHandler.getOrders().get(dataHandler.getOrders().size()-1);
        Customer customer = dataHandler.getCustomer();


        name.setText(customer.getFirstName() + " " + customer.getLastName());
        address.setText(customer.getAddress());
        city.setText(customer.getPostAddress());


        for (ShoppingItem shoppingItem : order.getItems()) {
            receiptFlowPane.getChildren().add(new ReceiptListItem(shoppingItem));
            System.out.println("Element added to receipt FlowPane");
        }


     //   time.setText();
     //   city.setText();


    }

    @FXML
    public void onBack(Event event){
        IMatNavigationHandler.getInstance().toDestination("");
    }
}
