package matbutik;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class IMatHelpController extends IMatController implements Initializable {



    @Override
    public void initialize(URL location, ResourceBundle resources) {
    //super.productItem();
    }

    @FXML public void navigateGoBack(Event event) {
        navigationHandler.goBack();
    }

}

