package matbutik;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class confirmationPage implements Initializable {





    @FXML Label text;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void onBack(Event event){
        ScreenController.getInstance().activate("Main", text.getScene().getRoot());
    }
}
