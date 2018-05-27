package matbutik;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;


public class IMatOrderDetailItem extends AnchorPane {
    private ShoppingItem shoppingItem;
    private IMatHistoryController historyController;
    private Order order;

    @FXML protected ImageView cartItemImageView;
    @FXML protected Label cartItemName;
    @FXML protected Label cartItemProductPrice;
    @FXML protected Label cartItemEco;
    @FXML protected Label cartItemTotalPrice;
    @FXML protected Label cartItemTotalProduct;



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
        cartItemEco.setVisible(false);
        cartItemImageView.setImage(new Image("file:" + System.getProperty("user.home") + "/.dat215/imat/images/" + shoppingItem.getProduct().getImageName()));
        cartItemName.setText(shoppingItem.getProduct().getName());
        cartItemProductPrice.setText(String.format("%.2f", shoppingItem.getProduct().getPrice()) + " " +shoppingItem.getProduct().getUnit() );
        cartItemTotalPrice.setText(String.format("%.2f",shoppingItem.getTotal()) + " kr");
        cartItemTotalProduct.setText(String.format("%.1f",shoppingItem.getAmount()) +" " +shoppingItem.getProduct().getUnitSuffix() );
        if(shoppingItem.getProduct().isEcological()){
            cartItemEco.setVisible(true);
        }
    }

    public ShoppingItem getShoppingItem() {
        return this.shoppingItem;
    }

    @FXML
    public void addButtonAction() {
        historyController.addToShoppingCart(shoppingItem);
    }

}
