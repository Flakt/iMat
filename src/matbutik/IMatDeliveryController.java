package matbutik;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ResourceBundle;

public class IMatDeliveryController extends IMatController implements Initializable {

    @FXML private Button prevDayButton;
    @FXML private Button nextDayButton;
    @FXML private Button dateButton;
    @FXML private Button dateButton1;
    @FXML private Button dateButton2;
    @FXML private Button dateButton3;
    @FXML private Button dateButton4;
    @FXML private Button dateButton5;
    @FXML private Label dateLabel;

    public static String chosenTime;
    public static LocalDateTime date;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        date = LocalDateTime.now();
        addButtonListeners();
        setCurrentDate();
    }

    public static String getChosenTime() {
        if (chosenTime == null) {
            chosenTime = "";
        }
        else {
            return chosenTime;
        }
        return chosenTime;
    }

    public static LocalDateTime getDate() {
        if (date == null) {
            date = LocalDateTime.now();
        }
        else {
            return date;
        }
        return date;
    }

    private void addButtonListeners() {
        dateButton.focusedProperty().addListener(new ButtonListener(dateButton));
        dateButton1.focusedProperty().addListener(new ButtonListener(dateButton1));
        dateButton2.focusedProperty().addListener(new ButtonListener(dateButton2));
        dateButton3.focusedProperty().addListener(new ButtonListener(dateButton3));
        dateButton4.focusedProperty().addListener(new ButtonListener(dateButton4));
        dateButton5.focusedProperty().addListener(new ButtonListener(dateButton5));
    }

    private void setCurrentDate() {
        String day = String.valueOf(date.getDayOfMonth());
        String month = String.valueOf(date.getMonth().getValue());
        String year = String.valueOf(date.getYear()).substring(2,4);
        dateLabel.setText("Datum " + day + "/" + month + "-" + year);
    }

    @FXML private void incDate() {
        date = date.plusDays(1);
        setCurrentDate();
    }

    @FXML private void decDate() {
        date = date.minusDays(1);
        setCurrentDate();
    }

    @FXML private void doneOnClick(Event event){
        navigationHandler.toPaymentOptions();
    }

    private class ButtonListener implements javafx.beans.value.ChangeListener<Boolean> {
        private Button button;

        public ButtonListener(Button button) {
            this.button = button;
        }

        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (newValue) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        button.setStyle("-fx-background-color: #09CDDA;");
                        chosenTime = button.getText();
                    }
                });
            }
            else {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        button.setStyle("null");
                    }
                });
            }
        }
    }
}
