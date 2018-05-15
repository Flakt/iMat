package matbutik;

import javafx.scene.image.Image;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

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
    }

    public void updateProductsList() {
        List<ShoppingItem> shoppingItems = shoppingCart.getItems();
        for (ShoppingItem shoppingItem : shoppingItems) {
            IMatShoppingItem iMatShoppingItem = iMatShoppingItemMap.get(shoppingItem.getProduct().getName());
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


    // Actually check if it loads the correct image
    public Image getCartItemImage(ShoppingItem item) {
        return new Image("file:" + System.getProperty("user.home") + "/.dat215/imat/images/" + item.getProduct().getImageName());
    }

    public void incrementProductAmount(ShoppingItem item) {
        item.setAmount((item.getAmount() + 1.0));
    }

    public void decrementProductAmount(ShoppingItem item) {
        if (item.getAmount() > 1.0) {
            item.setAmount((item.getAmount() - 1.0));

        }
        else {
            shoppingCart.removeItem(item);

        }
    }

}
