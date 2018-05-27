package matbutik;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ResourceBundle;

public class IMatHelpController extends IMatController implements Initializable {

    @FXML
    FlowPane shoppingCartFlowPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateShoppingItems();

    }
}

