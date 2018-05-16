package matbutik;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.net.URL;
import java.util.ResourceBundle;

public class IMatAccountController implements Initializable {

    private IMatDataHandler dataHandler;
    private Customer customer;
    private CreditCard creditCard;

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
    }

    private void setShoppingCart() {
        accountNumberOfProductsLabel.setText(String.valueOf(dataHandler.getShoppingCart().getItems().size()));
        accountSumLabel.setText(String.valueOf(dataHandler.getShoppingCart().getTotal()));
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
}
