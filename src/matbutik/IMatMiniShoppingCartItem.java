package matbutik;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
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

    @Override
    protected void updateOthers() {
        Node parent = this.getParent();
        StackPane thing = ((StackPane) ((AnchorPane) ((GridPane) parent.getParent().getParent().getParent()).getChildren().get(1)).getChildren().get(0));
        TabPane tabpane = (TabPane) thing.getChildren().get(2/**/);
        for (Tab tab : tabpane.getTabs()) {
            FlowPane flowpane = (FlowPane) ((ScrollPane) tab.getContent()).getContent();
            for (Node item : flowpane.getChildren()) {
                IMatProductItem proditem = ((IMatProductItem) item);
                if (proditem.shoppingItem != null)
                    proditem.updateShoppingCart();
            }
        }
    }
}
