package matbutik;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class IMatHelpController implements Initializable {
    IMatNavigationHandler navigationHandler = IMatNavigationHandler.getInstance();


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
}

