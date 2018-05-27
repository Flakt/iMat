package matbutik;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class ReceiptListItem extends AnchorPane {



    @FXML
    Label name;
    @FXML
    Label amount;
    @FXML
    Label price;

    ReceiptListItem(ShoppingItem shoppingItem) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("receiptListItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        name.setText(shoppingItem.getProduct().getName());
        amount.setText(((Double)(shoppingItem.getAmount())).toString() +" " + shoppingItem.getProduct().getUnitSuffix());
        price.setText(((Double)shoppingItem.getTotal()).toString() + " kr");


    }



}
