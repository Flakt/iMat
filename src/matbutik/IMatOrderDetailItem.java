package matbutik;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;


public class IMatOrderDetailItem extends AnchorPane {
    private ShoppingItem shoppingItem;
    private IMatHistoryController historyController;

    @FXML protected TextField cartItemAmountTextField;
    @FXML private Label cartItemTotalPrice;
    @FXML protected ImageView cartItemImageView;
    @FXML protected Label cartItemEco;
    @FXML private Label cartItemProductPrice;
    @FXML protected Label cartItemName;


    IMatOrderDetailItem(ShoppingItem sp, IMatHistoryController hc ) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("IMatOrderDetailItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException io) {
            throw new RuntimeException(io);
        }

        shoppingItem = sp;
        historyController = hc;
        cartItemImageView.setImage(new Image("file:" + System.getProperty("user.home") + "/.dat215/imat/images/" + shoppingItem.getProduct().getImageName()));
        if (shoppingItem.getProduct().isEcological()) {
            //car.setImage(new Image("/matbutik/resources/Eko_Logo.jpg"));
        }
        cartItemName.setText(shoppingItem.getProduct().getName());
        cartItemProductPrice.setText(String.format("%.2f", shoppingItem.getTotal()) + " kr totalt");
        cartItemAmountTextField.setText(String.format("%.2f", shoppingItem.getAmount()) + " st");
    }

    public ShoppingItem getShoppingItem() {
        return this.shoppingItem;
    }

    @FXML
    public void addButtonAction() {
        historyController.addToShoppingCart(shoppingItem);
    }

    @FXML
    public void removeButtonAction() {
        historyController.removeFromShoppingCart(shoppingItem);
    }
}
