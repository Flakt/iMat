package matbutik;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class IMatPaymentAlternatives extends IMatController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setNumberLabels();
    }
    @FXML
    protected void cardOnClick(Event event) {
        navigationHandler.toPaymentCard();
    }

    @FXML protected void invoiceOnClick(Event event) {
        navigationHandler.toPaymentInvoice();
    }
}
