package matbutik;

import javafx.fxml.FXMLLoader;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class IMatMiniShoppingCartItem extends IMatShoppingItem {





    @Override
    public void setupFxml(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("IMatMiniShoppingCartItem.fxml"));
        System.out.println(this.getClass().toString());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        dataHandler = IMatDataHandler.getInstance();
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    IMatMiniShoppingCartItem(ShoppingItem shoppingItem, IMatModularCartController shoppingCartController){
        super(shoppingItem, shoppingCartController);

    }
}
