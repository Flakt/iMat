package matbutik;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class IMatShoppingItem extends AnchorPane {

    // Untested as of 2018-05-02, should be functional

    private IMatShoppingCartController shoppingCartController;
    private ShoppingItem shoppingItem;
    @FXML
    private Button cartItemIncreaseButton;
    @FXML
    private Button cartItemDecreaseButton;
    @FXML
    private Label cartItemAmountLabel;
    @FXML
    private Label cartItemPriceLabel;
    @FXML
    private ImageView cartItemImageView;

    public IMatShoppingItem(ShoppingItem shoppingItem, IMatShoppingCartController shoppingCartController) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("IMatShoppingItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.shoppingCartController = shoppingCartController;
        this.shoppingItem = shoppingItem;

        cartItemImageView.setImage(shoppingCartController.getCartItemImage(this.shoppingItem));
        cartItemAmountLabel.setText(shoppingCartController.getCartItemAmount(this.shoppingItem));
        cartItemPriceLabel.setText(shoppingCartController.getCartItemPrice(this.shoppingItem));
    }

    @FXML
    protected void incItem(Event event) {
        shoppingCartController.incrementProductAmount(this.shoppingItem);
    }

    @FXML
    protected void decItem(Event event) {
        shoppingCartController.decrementProductAmount(this.shoppingItem);
    }
}
