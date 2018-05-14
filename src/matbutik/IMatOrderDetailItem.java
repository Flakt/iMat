package matbutik;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;


public class IMatOrderDetailItem extends AnchorPane {
    private ShoppingItem shoppingItem;
    private IMatHistoryController historyController;

    @FXML
    private Button orderDetailAddButton;
    @FXML
    private Button orderDetailRemoveButton;
    @FXML
    private ImageView orderDetailImage;
    @FXML
    private ImageView orderDetailEcoImage;
    @FXML
    private Label orderDetailProductNameLabel;
    @FXML
    private Label orderDetailProductPriceLabel;
    @FXML
    private TextField orderDetailTextField;

    IMatOrderDetailItem(ShoppingItem sp, IMatHistoryController hc ) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("IMatOrderDetailItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException io) {
            throw new RuntimeException(io);
        }

        shoppingItem = sp;
        historyController = hc;
        orderDetailImage.setImage(new Image("file:" + System.getProperty("user.home") + "/.dat215/imat/images/" + shoppingItem.getProduct().getImageName()));
        if (shoppingItem.getProduct().isEcological()) {
            orderDetailEcoImage.setImage(new Image("./resources/Eko_Logo.jpg"));
        }
        orderDetailProductNameLabel.setText(shoppingItem.getProduct().getName());
        orderDetailProductPriceLabel.setText(String.valueOf(shoppingItem.getTotal()));
    }

    public ShoppingItem getShoppingItem() {
        return this.shoppingItem;
    }


    //  Delegera vidare?, Ja
    @FXML
    protected void addButtonAction() {
        historyController.addShoppingItem(shoppingItem);
    }

    @FXML
    protected void removeButtonAction() {
        historyController.removeShoppingItem(shoppingItem);
    }
}
