package matbutik;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class IMatShoppingItem extends AnchorPane {

    // Untested as of 2018-05-02, should be functional

    private IMatModularCartController shoppingCartController;
    private ShoppingItem shoppingItem;
    private IMatDataHandler dataHandler;
    private Product product;
    @FXML private Button cartItemIncreaseButton;
    @FXML private Button cartItemDecreaseButton;
    @FXML private TextField cartItemAmountTextField;
    @FXML private Label cartItemTotalPrice;
    @FXML private ImageView cartItemImageView;
    @FXML private Label cartItemEco;
    @FXML private Label cartItemUnit;
    @FXML private Label cartItemProductPrice;
    @FXML private Label cartItemName;

    public IMatShoppingItem(ShoppingItem shoppingItem, IMatModularCartController shoppingCartController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("IMatShoppingItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        dataHandler = IMatDataHandler.getInstance();
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.shoppingCartController = shoppingCartController;
        this.shoppingItem = shoppingItem;

        cartItemImageView.setImage(shoppingCartController.getCartItemImage(this.shoppingItem));
        double amount = shoppingCartController.getCartItemAmount(this.shoppingItem);
        cartItemAmountTextField.setText(
                (int)amount == amount ?
                        ((Integer)(int)amount).toString() :
                        ((Double)amount).toString());
        cartItemTotalPrice.setText((" =" + shoppingCartController.getCartItemPrice(this.shoppingItem)) + " kr"  );
        setEcoLabel();
        cartItemUnit.setText(shoppingCartController.getCartSuffix(this.shoppingItem));
        cartItemProductPrice.setText("Jfr " + shoppingCartController.getCartItemPrice(this.shoppingItem) + " " + shoppingCartController.getCartUnit(this.shoppingItem));
        cartItemName.setText(shoppingCartController.getCartItemName(this.shoppingItem));
    }
   private void setEcoLabel(){
        if(dataHandler.getProduct(1).isEcological()){
        cartItemEco.setText("Ekologisk");
        }
    }

    @FXML
    protected void incItem(Event event) {
        double amount = shoppingItem.getAmount();
        shoppingCartController.incrementProductAmount(this.shoppingItem);
        cartItemAmountTextField.setText(String.valueOf(amount + 1));
    }

    @FXML
    protected void decItem(Event event) {
        double amount = shoppingItem.getAmount();
        shoppingCartController.decrementProductAmount(this.shoppingItem);
        if (amount > 1) {
            cartItemAmountTextField.setText(String.valueOf(amount - 1));
        }
    }
}
