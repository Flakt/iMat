package matbutik;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;

public class IMatProductItem extends AnchorPane {


    private IMatDataHandler iMatDataHandler;
    private Product product;



    public IMatProductItem(Product p, IMatDataHandler h){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("IMatProduct.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.product = p;
        this.iMatDataHandler = h;


    }



    public Product getProduct() {
        return this.product;
    }

}
