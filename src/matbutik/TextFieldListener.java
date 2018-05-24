package matbutik;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class TextFieldListener implements ChangeListener<Boolean> {
    private TextField textField;

    public TextFieldListener(TextField textField) {
        this.textField = textField;
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (newValue) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    textField.selectAll();
                }
            });
        }
        else {
            // Maybe put something here later on
        }
    }
}
