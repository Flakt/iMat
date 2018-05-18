package matbutik;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

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
}

