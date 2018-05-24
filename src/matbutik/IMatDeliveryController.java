package matbutik;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class IMatDeliveryController extends IMatController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML private void doneOnClick(Event event){
        navigationHandler.toConfirmationPage();
    }
}
