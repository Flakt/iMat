package matbutik;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import se.chalmers.cse.dat216.project.*;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;



public class IMatHelpController implements Initializable {
    IMatNavigationHandler navigationHandler = IMatNavigationHandler.getInstance();
    @FXML Button testAddProduct;
    @FXML Button testRemoveProduct;
    @FXML AnchorPane testPane;
    @FXML AnchorPane emptyPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void navigateToHistory(Event event){
        navigationHandler.toHistory();
    }

    public void reselectTabs() {

    }

    @FXML public void shoppingCart(Event event){
        navigationHandler.toDestination("ShoppingCart");
    }
    @FXML public void navigateGoBack(Event event){navigationHandler.goBack();}

    @FXML public void navigateToAccount(Event event){navigationHandler.toAccount();}

    @FXML public void addProduct(Event event){testPane.toFront();
        testPane.setVisible(true);
        emptyPane.setVisible(false);
    }

    @FXML public void removeProduct(Event event){emptyPane.toFront();
        testPane.setVisible(false);
        emptyPane.setVisible(true);}
}

