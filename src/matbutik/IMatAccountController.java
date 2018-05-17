package matbutik;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.net.URL;
import java.util.ResourceBundle;

public class IMatAccountController implements Initializable {

    private IMatDataHandler dataHandler;
    private Customer customer;
    private CreditCard creditCard;

    @FXML private AnchorPane accountRootPane;
    @FXML private Label accountNumberOfProductsLabel;
    @FXML private Label accountSumLabel;
    @FXML private TextField accountCardNumberTextField;
    @FXML private TextField accountMonthTextField;
    @FXML private TextField accountYearTextField;
    @FXML private TextField accountFirstNameTextField;
    @FXML private TextField accountLastNameTextField;
    @FXML private TextField accountAdressTextField;
    @FXML private TextField accountZipCodeTextField;
    @FXML private TextField accountPostAdressTextField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataHandler = IMatDataHandler.getInstance();
        customer = dataHandler.getCustomer();
        creditCard = dataHandler.getCreditCard();
        setShoppingCart();
        setTextFields();
        addTextFieldListeners();
    }

    private void setShoppingCart() {
        accountNumberOfProductsLabel.setText("Antal Varor: " + String.valueOf(dataHandler.getShoppingCart().getItems().stream().mapToDouble(item -> item.getProduct().getUnit().substring(item.getProduct().getUnit().length() - 2) == "st"?item.getAmount():1).sum()));
        accountSumLabel.setText(String.valueOf("Summa :" + dataHandler.getShoppingCart().getTotal()));
    }

    private void setTextFields() {
        accountCardNumberTextField.setText(creditCard.getCardNumber());
        accountMonthTextField.setText(String.valueOf(creditCard.getValidMonth()));
        accountYearTextField.setText(String.valueOf(creditCard.getValidYear()));
        accountFirstNameTextField.setText(customer.getFirstName());
        accountLastNameTextField.setText(customer.getLastName());
        accountAdressTextField.setText(customer.getAddress());
        accountZipCodeTextField.setText(customer.getPostCode());
        accountPostAdressTextField.setText(customer.getPostAddress());
    }

    private void addTextFieldListeners() {
        accountCardNumberTextField.focusedProperty().addListener(new TextFieldListener(accountCardNumberTextField));
        accountMonthTextField.focusedProperty().addListener(new TextFieldListener(accountMonthTextField));
        accountYearTextField.focusedProperty().addListener(new TextFieldListener(accountYearTextField));
        accountFirstNameTextField.focusedProperty().addListener(new TextFieldListener(accountFirstNameTextField));
        accountLastNameTextField.focusedProperty().addListener(new TextFieldListener(accountLastNameTextField));
        accountAdressTextField.focusedProperty().addListener(new TextFieldListener(accountAdressTextField));
        accountZipCodeTextField.focusedProperty().addListener(new TextFieldListener(accountZipCodeTextField));
        accountPostAdressTextField.focusedProperty().addListener(new TextFieldListener(accountPostAdressTextField));
    }

    @FXML
    protected void saveDetailsAction() {
        creditCard.setCardNumber(accountCardNumberTextField.getText());
        creditCard.setValidMonth(Integer.parseInt(accountMonthTextField.getText()));
        creditCard.setValidYear(Integer.parseInt(accountYearTextField.getText()));
        customer.setFirstName(accountFirstNameTextField.getText());
        customer.setLastName(accountLastNameTextField.getText());
        customer.setAddress(accountAdressTextField.getText());
        customer.setPostCode(accountZipCodeTextField.getText());
        customer.setPostAddress(accountPostAdressTextField.getText());
    }

    @FXML
    private void navigateBack(Event event) {
        ScreenController.getInstance().navigateToPrevious();
    }

    @FXML
    private void toShoppingCart() {
        ScreenController.getInstance().activate("ShoppingCart", accountRootPane.getScene().getRoot());
    }

    @FXML
    private void toHistory() {
        ScreenController.getInstance().activate("History", accountRootPane.getScene().getRoot());
    }


    private class TextFieldListener implements ChangeListener<Boolean> {
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
}
