package matbutik;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class IMatShoppingItem extends AnchorPane {

    // Untested as of 2018-05-02, should be functional

    protected IMatModularCartController shoppingCartController;
    protected ShoppingItem shoppingItem;
    protected IMatDataHandler dataHandler;
 //   protected Product product;
 //   @FXML protected Button cartItemIncreaseButton;
 //   @FXML protected Button cartItemDecreaseButton;
    @FXML protected TextField cartItemAmountTextField;
    @FXML private Label cartItemTotalPrice;
    @FXML protected ImageView cartItemImageView;
    @FXML protected Label cartItemEco;
   // @FXML private Label cartItemUnit;
    @FXML private Label cartItemProductPrice;
    @FXML protected Label cartItemName;


    IMatShoppingItem(){

    }


    public void setupFxml(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("IMatShoppingItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        dataHandler = IMatDataHandler.getInstance();
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

     IMatShoppingItem(ShoppingItem shoppingItem, IMatModularCartController shoppingCartController) {

        setupFxml();


        this.shoppingCartController = shoppingCartController;
        this.shoppingItem = shoppingItem;

        cartItemImageView.setImage(shoppingCartController.getCartItemImage(this.shoppingItem));
        double amount = shoppingCartController.getCartItemAmount(this.shoppingItem);
        cartItemAmountTextField.setText(
                (int)amount == amount ?
                        ((Integer)(int)amount).toString() :
                        ((Double)amount).toString());
        updatePrice();
        setEcoLabel();
        //cartItemUnit.setText(shoppingCartController.getCartSuffix(this.shoppingItem));
        cartItemProductPrice.setText(((Double)(this.shoppingItem.getProduct().getPrice())).toString() +
                " kr / " + shoppingCartController.getCartSuffix(this.shoppingItem));
        cartItemName.setText(shoppingCartController.getCartItemName(this.shoppingItem));
    }
   protected void setEcoLabel(){
        if(dataHandler.getProduct(1).isEcological()){
        cartItemEco.setText("Ekologisk");
        }
    }

    @FXML
    protected void incItem(Event event) {
        System.out.println(this.getClass().toString());
        double amount = shoppingItem.getAmount();
        shoppingCartController.incrementProductAmount(this.shoppingItem);
        shoppingCartController.getCartItemAmount(this.shoppingItem);

        String unit = shoppingItem.getProduct().getUnit();
        boolean isAPiece = unit.substring(unit.length() - 2).equals("st");
        cartItemAmountTextField.setText((isAPiece ? ((Integer)(int)(amount + 1)).toString() : ((Double)(amount + 1)).toString()));

        updatePrice();
        updateOthers();
    }

    @FXML
    protected void decItem(Event event) {
        /*double amount = shoppingItem.getAmount();
        shoppingCartController.decrementProductAmount(this.shoppingItem);
        if (amount > 1) {
            cartItemAmountTextField.setText(String.valueOf(amount));
        }*/


        double amount = shoppingItem.getAmount();
        if (amount == 1) {
            shoppingCartController.shoppingCart.removeItem(shoppingItem);
            shoppingItem = null;
        } else {
            shoppingCartController.decrementProductAmount(shoppingItem);

            String unit = shoppingItem.getProduct().getUnit();
            boolean isAPiece = unit.substring(unit.length() - 2).equals("st");
            cartItemAmountTextField.setText((isAPiece ? ((Integer)(int)(amount - 1)).toString() : ((Double)(amount - 1)).toString()));
        }

        updatePrice();
        updateOthers();
    }

    private void updatePrice() {
        cartItemTotalPrice.setText((" =" + shoppingCartController.getCartItemPrice(this.shoppingItem)) + " kr"  );
    }

    protected void updateOthers() {

    }
}
