package matbutik;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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
                        String.format("%.1f",(Double)amount));
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
        shoppingCartController.incrementProductAmount(this.shoppingItem, isAPiece()?1:0.1);
        //shoppingCartController.getCartItemAmount(this.shoppingItem);

        cartItemAmountTextField.setText((isAPiece() ? ((Integer)(int)(amount + (isAPiece()?1:0.1))).toString() : String.format("%.1f",(Double)(amount + (isAPiece()?1:0.1)))));

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


        double amount = shoppingItem.getAmount() - (isAPiece()?1:0.1);
        if (amount < 0.00001) {
            shoppingCartController.shoppingCart.removeItem(shoppingItem);
            shoppingItem = null;
        } else {
            shoppingCartController.decrementProductAmount(shoppingItem);

            cartItemAmountTextField.setText((isAPiece() ? ((Integer)(int)amount).toString() : String.format("%.1f",(Double)amount)));
        }

        updatePrice();
        updateOthers();
    }

    private boolean isAPiece() {
        return Arrays.stream(new String[]{"st", "rp"}).anyMatch(x->x.equals(shoppingItem.getProduct().getUnit().substring(shoppingItem.getProduct().getUnit().length()-2)));
    }

    private void updatePrice() {
        cartItemTotalPrice.setText((" =" + String.format("%.2f",this.shoppingItem.getTotal())) + " kr"  );
    }

    private Stream<Node> getChildrenStream(Node n) {
        if (Pane.class.isAssignableFrom(n.getClass())) {
            return ((Pane)n).getChildren().stream();
        } else if (ScrollPane.class.isAssignableFrom(n.getClass())) {
            Node temp = ((ScrollPane)n).getContent();
            List<Node> tmplst = new ArrayList<>();
            tmplst.add(temp);
            return tmplst.stream();
        } else if (TabPane.class.isAssignableFrom(n.getClass())) {
            return ((TabPane)n).getTabs().stream().map(Tab::getContent);
        } else if (n.getClass() == IMatProductItem.class)
        {
            List<Node> tmplst = new ArrayList<>();
            tmplst.add(n);
            return tmplst.stream();
        } else {
            return (new ArrayList<Node>()).stream();
        }
    }

    private List<IMatProductItem> getDescendantProductItems(Stream<Node> n) {
        List<IMatProductItem> list = new ArrayList<>();
        n.forEach(x->{
            if(x.getClass() == IMatProductItem.class || IMatProductItem.class.isAssignableFrom(x.getClass())){
                list.add((IMatProductItem) x);
            } else if (Node.class.isAssignableFrom(x.getClass())) {
                list.addAll(getDescendantProductItems(getChildrenStream((x))));
            }
        });
        return list;
    }

    private void updateOthers() {
        Parent parent = this;
        do {
            parent = parent.getParent();
        } while (parent.getClass() != AnchorPane.class || !parent.getStyleClass().contains("root"));
        getDescendantProductItems(getChildrenStream(parent)).forEach(item -> {
            if (item.shoppingItem != null)
                item.updateShoppingCart();
        });
    }
}
