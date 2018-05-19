package matbutik;

import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IMatModularCartController {

    protected IMatDataHandler dataHandler;
    protected ShoppingCart shoppingCart;
    protected Map<String, IMatShoppingItem> iMatShoppingItemMap = new HashMap<String, IMatShoppingItem>();
    protected List<ShoppingItem> backupShoppingItems = new ArrayList<>();



    IMatModularCartController() {


        dataHandler = IMatDataHandler.getInstance();
        shoppingCart = dataHandler.getShoppingCart();

        shoppingCart.addShoppingCartListener(new ShoppingCartListener() {
            @Override
            public void shoppingCartChanged(CartEvent cartEvent) {
                updateProductsList();

            }
        });
    }

    public void updateProductsList() {
        List<ShoppingItem> shoppingItems = shoppingCart.getItems();
        for (ShoppingItem shoppingItem : shoppingItems) {
            IMatShoppingItem iMatShoppingItem = iMatShoppingItemMap.get(shoppingItem.getProduct().getName());
            iMatShoppingItemMap.values().stream().forEach(x->{
                if (x.removeMe) {
                    iMatShoppingItemMap.remove(iMatShoppingItemMap.keySet().stream().filter(y -> iMatShoppingItemMap.get(y) == x || iMatShoppingItemMap.get(y).shoppingItem == null).findAny());
                    shoppingItems.remove(x.shoppingItem);
                    shoppingCart.removeItem(x.shoppingItem);
                    populateFlowPane();
                }
            });
        }
    }

    public double getCartItemAmount(ShoppingItem item) {
        return item.getAmount();
    }

    public String getCartUnit(ShoppingItem item){
        return String.valueOf(item.getProduct().getUnit());
    }

    public String getCartSuffix(ShoppingItem item){
        return String.valueOf(item.getProduct().getUnitSuffix());
    }
    public String getCartItemPrice(ShoppingItem item) {
        return String.valueOf(item.getTotal());
    }
    public String getCartItemName(ShoppingItem item){return String.valueOf(item.getProduct().getName());}


    public Image getCartItemImage(ShoppingItem item) {
        return new Image("file:" + System.getProperty("user.home") + "/.dat215/imat/images/" + item.getProduct().getImageName());
    }

    public void incrementProductAmount(ShoppingItem item) {
        incrementProductAmount(item,1);
    }
    public void incrementProductAmount(ShoppingItem item, double step) {
        item.setAmount((item.getAmount() + step));
    }

    public void setProductAmount(ShoppingItem item, double amount){
        item.setAmount(amount);

    }

    public void decrementProductAmount(ShoppingItem item) {
        decrementProductAmount(item,1);
    }
    public void decrementProductAmount(ShoppingItem item, double step) {
        if (item.getAmount() > step) {
            item.setAmount((item.getAmount() - step));
        }
        else {
            shoppingCart.removeItem(item);
        }
    }

    protected FlowPane cartItemsFlowPane;
    protected void populateFlowPane() {
        cartItemsFlowPane.getChildren().clear();
        updateProductsList();
        for (IMatShoppingItem item : iMatShoppingItemMap.values()) {
            cartItemsFlowPane.getChildren().add(item);
        }
    }
}
