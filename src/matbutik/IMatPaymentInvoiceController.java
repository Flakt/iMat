package matbutik;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.net.URL;
import java.util.ResourceBundle;

public class IMatPaymentInvoiceController extends IMatController implements Initializable {

    private IMatDataHandler dataHandler;
    private Customer customer;
    @FXML private CheckBox invoiceSaveDetailsCheckBox;
    @FXML private Label invoiceFirstNameErrorLabel;
    @FXML private Label invoiceLastNameErrorLabel;
    @FXML private Label invoicePostCodeErrorLabel;
    @FXML private Label invoicePostAdressErrorLabel;
    @FXML private Label invoiceErrorLabel;
    @FXML private TextField forenameTextField;
    @FXML private TextField surnameTextField;
    @FXML private TextField adressTextField;
    @FXML private TextField zipcodeTextField;
    @FXML private TextField regionTextField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataHandler = IMatDataHandler.getInstance();
        customer = dataHandler.getCustomer();
        fillInSavedDeliveryDetails();
        invoiceInit();
        clearInvoiceTextFieldsError();
        clearTextFieldsBorderErrors();
        setNumberLabels();
    }

    private void fillInSavedDeliveryDetails() {
        forenameTextField.setText(customer.getFirstName());
        surnameTextField.setText(customer.getLastName());
        adressTextField.setText(customer.getAddress());
        regionTextField.setText(customer.getPostAddress());
        zipcodeTextField.setText(customer.getPostCode());
    }

    private void invoiceInit() {
        forenameTextField.focusedProperty().addListener(new TextFieldListener(forenameTextField));
        surnameTextField.focusedProperty().addListener(new TextFieldListener(surnameTextField));
        adressTextField.focusedProperty().addListener(new TextFieldListener(adressTextField));
        zipcodeTextField.focusedProperty().addListener(new TextFieldListener(zipcodeTextField));
        regionTextField.focusedProperty().addListener(new TextFieldListener(regionTextField));
        invoicePopulateTextField();

    }

    private void invoicePopulateTextField() {
        forenameTextField.setText(customer.getFirstName());
        surnameTextField.setText(customer.getLastName());
        adressTextField.setText(customer.getAddress());
        zipcodeTextField.setText(customer.getPostCode());
        regionTextField.setText(customer.getPostAddress());
    }


    private void clearInvoiceTextFieldsError() {
        invoiceFirstNameErrorLabel.setVisible(false);
        invoiceLastNameErrorLabel.setVisible(false);
        invoicePostCodeErrorLabel.setVisible(false);
        invoicePostAdressErrorLabel.setVisible(false);
        invoiceErrorLabel.setVisible(false);
    }

    private void clearTextFieldsBorderErrors() {
        // All textfields are in separate anchorpanes, making getChildren not a viable option
        forenameTextField.setStyle(null);
        surnameTextField.setStyle(null);
        adressTextField.setStyle(null);
        zipcodeTextField.setStyle(null);
        regionTextField.setStyle(null);
    }

    private void setEmptyTextFieldsErrors() {
        if (forenameTextField.getText().equals("")) {
            forenameTextField.setStyle("-fx-text-box-border: red;");
        }
        if (surnameTextField.getText().equals("")) {
            surnameTextField.setStyle("-fx-text-box-border: red;");
        }
        if (adressTextField.getText().equals("")) {
            adressTextField.setStyle("-fx-text-box-border: red;");
        }
        if (zipcodeTextField.getText().equals("")) {
            zipcodeTextField.setStyle("-fx-text-box-border: red;");
        }
        if (regionTextField.getText().equals("")) {
            regionTextField.setStyle("-fx-text-box-border: red;");
        }
    }

    private void showInvoiceTextFieldsError() {
        if (forenameTextField.getText().matches(".*\\d+.*")) {
            invoiceFirstNameErrorLabel.setVisible(true);
        }
        if (surnameTextField.getText().matches(".*\\d+.*")) {
            invoiceLastNameErrorLabel.setVisible(true);
        }
        if (regionTextField.getText().matches(".*\\d+.*")) {
            invoicePostAdressErrorLabel.setVisible(true);
        }
        if (!zipcodeTextField.getText().matches("[0-9]+")) {
            invoicePostCodeErrorLabel.setVisible(true);
        }
    }

    @FXML protected void invoicePayAction() {
        clearInvoiceTextFieldsError();
        clearTextFieldsBorderErrors();
        if (forenameTextField.getText().equals("") || surnameTextField.getText().equals("") ||
                adressTextField.getText().equals("") || zipcodeTextField.getText().equals("") ||
                regionTextField.getText().equals("")) {
            invoiceErrorLabel.setVisible(true);
            setEmptyTextFieldsErrors();
            return;
        }
        showInvoiceTextFieldsError();
        if (invoiceFirstNameErrorLabel.isVisible() || invoiceLastNameErrorLabel.isVisible() ||
                invoicePostCodeErrorLabel.isVisible() || invoicePostAdressErrorLabel.isVisible()) {
            return;
        }

        if (invoiceSaveDetailsCheckBox.isSelected()) {
            customer.setFirstName(forenameTextField.getText());
            customer.setLastName(surnameTextField.getText());
            customer.setAddress(adressTextField.getText());
            customer.setPostAddress(regionTextField.getText());
            customer.setPostCode(zipcodeTextField.getText());
        }
        dataHandler.placeOrder(true);
        navigationHandler.toConfirmationPage();
    }
}