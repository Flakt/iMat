package matbutik;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class IMatMiniShoppingCartItem extends IMatShoppingItem {

    @Override
    public void setupFxml(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("IMatMiniShoppingCartItem.fxml"));
        //System.out.println(this.getClass().toString());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        dataHandler = IMatDataHandler.getInstance();
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    IMatMiniShoppingCartItem(ShoppingItem shoppingItem, IMatModularCartController shoppingCartController, Command cartUpdater){
        super(shoppingItem, shoppingCartController, cartUpdater);

    }

    @FXML
    protected void removeAction(Event e) {
        shoppingItem.setAmount(shoppingCartController.iMatShoppingItemMap.get(shoppingItem.getProduct().getProductId()).isAPiece() ?
            1 : 0.1);
        super.decItem(e);
        /*shoppingCartController.shoppingCart.removeItem(shoppingItem);
        shoppingItem.setAmount(0);
        updateOthers();
        shoppingItem = null;
        removeMe = true;
        cartUpdater.runCommand();
        shoppingCartController.populateFlowPane();
        IMatShoppingCartController.getInstance().setLabels();*/
    }
}
