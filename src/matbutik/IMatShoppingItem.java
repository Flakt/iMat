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

    public boolean removeMe = false;

    protected Command cartUpdater;

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

     IMatShoppingItem(ShoppingItem shoppingItem, IMatModularCartController shoppingCartController, Command cartUpdater) {

         setupFxml();


         this.shoppingCartController = shoppingCartController;
         this.shoppingItem = shoppingItem;

         cartItemImageView.setImage(shoppingCartController.getCartItemImage(this.shoppingItem));
         double amount = shoppingCartController.getCartItemAmount(this.shoppingItem);
         cartItemAmountTextField.setText(
                 (int) amount == amount ?
                         ((Integer) (int) amount).toString() :
                         String.format("%.1f", (Double) amount));
         updatePrice();
         setEcoLabel();
         //cartItemUnit.setText(shoppingCartController.getCartSuffix(this.shoppingItem));
         cartItemProductPrice.setText(((Double) (this.shoppingItem.getProduct().getPrice())).toString() +
                 " kr / " + shoppingCartController.getCartSuffix(this.shoppingItem));
         cartItemName.setText(shoppingCartController.getCartItemName(this.shoppingItem).replace("Ekologisk", ""));

         this.cartUpdater = cartUpdater;
    }
    protected void setEcoLabel(){
        if(!shoppingItem.getProduct().isEcological()){
            cartItemEco.setVisible(false);
        }
    }

    public void updateCartItemAmountTextField() {
        cartItemAmountTextField.setText(amountToString(shoppingItem.getAmount()));
    }


    @FXML
    protected void incItem(Event event) {
        //System.out.println(this.getClass().toString());
        double amount = shoppingItem.getAmount();
        shoppingCartController.incrementProductAmount(this.shoppingItem, isAPiece()?1:0.1);
        //shoppingCartController.getCartItemAmount(this.shoppingItem);

        cartItemAmountTextField.setText(amountToString(amount + (isAPiece()?1:0.1)));

        updatePrice(); long t0 = System.nanoTime();
        updateOthers(); long t1 = System.nanoTime(); System.out.println("[BenchmarkTime]IMatShoppingItem::incItem: " + (t1-t0)/1.0E09 + " s");
        IMatShoppingCartController.getInstance().setLabels();
    }

    private String amountToString(double amount) {
        return (isAPiece() ? ((Integer)(int)amount).toString() : String.format("%.1f",(Double)amount));
    }

    protected void removeItem() {

    }

    @FXML
    protected void decItem(Event event) {
        double amount = shoppingItem.getAmount() - (isAPiece()?1:0.1);
        if (amount < 0.00001) {
            shoppingCartController.shoppingCart.removeItem(shoppingItem);
            shoppingItem.setAmount(0);
            shoppingItem = null;
            removeMe = true;
            updateOthers();
            cartUpdater.runCommand();
            shoppingCartController.populateFlowPane();
            IMatShoppingCartController.getInstance().setLabels();
        } else {
            shoppingCartController.decrementProductAmount(shoppingItem, isAPiece()?1:0.1);
            cartItemAmountTextField.setText(amountToString(amount));
            updatePrice();
            updateOthers();
            IMatShoppingCartController.getInstance().setLabels();
        }
    }

    private boolean isAPiece() {
        return Arrays.stream(new String[]{"st", "rp", "se", "rk"}).anyMatch(x->x.equals(shoppingItem.getProduct().getUnit().substring(shoppingItem.getProduct().getUnit().length()-2)));
    }

    private void updatePrice() {
        cartItemTotalPrice.setText((" =" + String.format("%.2f",this.shoppingItem.getTotal())) + " kr"  );
    }
    //STOPP SKYDDSOBJEKT ÄNDRA EJ!
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

    protected void updateOthers() {
        Parent parent = getRoot(this);
        for (IMatProductItem item : getDescendantProductItems(getChildrenStream(parent))) {
            if (item.getProduct().getProductId() == this.shoppingItem.getProduct().getProductId()) {
                if (item.shoppingItem != null) {
                    item.updateAmountText();
                }
                break;
            }
        }
    }

    private Parent getRoot(Parent node) {
        Parent parent = node;
        do {
            parent = parent.getParent();
        } while (parent.getClass() != AnchorPane.class || !parent.getStyleClass().contains("root"));
        return parent;
    }
}
