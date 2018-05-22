package matbutik;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import se.chalmers.cse.dat216.project.*;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class IMatPaymentController extends IMatController implements Initializable {

    private IMatDataHandler dataHandler;
    private IMatNavigationHandler navigationHandler;
    private Customer customer;
    private CreditCard creditCard;
    private String choice;

    @FXML
    private AnchorPane cartDetailsPane;
    @FXML
    private AnchorPane creditcardAnchorPane;
    @FXML
    private AnchorPane invoiceAnchorPane;
    @FXML
    private AnchorPane deliveryAnchorPane;
    @FXML
    private Button backButton;
    @FXML
    private Button accountButton;
    @FXML
    private Button historyButton;
    @FXML
    private Button helpButton;
    @FXML
    private Button paymentButton;
    @FXML
    private Button creditPayButton;
    @FXML
    private CheckBox creditSaveDetailsCheckBox;
    @FXML
    private CheckBox invoiceSaveDetailsCheckBox;
    @FXML
    private Label numberOfProductsLabel;
    @FXML
    private Label totalCostLabel;
    @FXML
    private Label headerLabel;
    @FXML
    private Label firstNameErrorLabel;
    @FXML
    private Label lastNameErrorLabel;
    @FXML
    private Label postCodeErrorLabel;
    @FXML
    private Label postAdressErrorLabel;
    @FXML
    private Label invoiceFirstNameErrorLabel;
    @FXML
    private Label invoiceLastNameErrorLabel;
    @FXML
    private Label invoicePostCodeErrorLabel;
    @FXML
    private Label invoicePostAdressErrorLabel;
    @FXML
    private Label invoiceErrorLabel;
    @FXML
    private Label creditCardNameErrorLabel;
    @FXML
    private Label creditCardNumberErrorLabel;
    @FXML
    private Label creditMonthErrorLabel;
    @FXML
    private Label creditYearErrorLabel;
    @FXML
    private Label creditVerifErrorLabel;
    @FXML
    private Label creditErrorLabel;
    @FXML
    private AnchorPane paymentOptionsAnchorPane;
    @FXML
    private TextField forenameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField adressTextField;
    @FXML
    private TextField zipcodeTextField;
    @FXML
    private TextField regionTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField cardNumberTextField;
    @FXML
    private TextField yearTextField;
    @FXML
    private TextField monthTextField;
    @FXML
    private TextField verificationCodeTextField;
    @FXML
    private TextField deliveryFirstNameTextField;
    @FXML
    private TextField deliveryAfterNameTextField;
    @FXML
    private TextField deliveryAdressTextField;
    @FXML
    private TextField deliveryPostCodeTextField;
    @FXML
    private TextField deliveryPostAdressTextField;
    @FXML
    private Label errorLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataHandler = IMatDataHandler.getInstance();
        navigationHandler = IMatNavigationHandler.getInstance();
        customer = dataHandler.getCustomer();
        creditCard = dataHandler.getCreditCard();
        numberOfProductsLabel.setText("Antal Varor: " + String.valueOf((int)dataHandler.getShoppingCart().getItems().stream().mapToDouble(item -> {String un = item.getProduct().getUnit().substring(item.getProduct().getUnit().length() - 2);return un.equals("st") || un.equals("rp") ?item.getAmount():1;}).sum()));
        totalCostLabel.setText("Summa: " + String.valueOf(dataHandler.getShoppingCart().getTotal()));
        //paymentOptionsSplitPane.toFront();
        deliveryAnchorPane.toFront();
        deliveryInit();
        fillInSavedDeliveryDetails();
        errorLabel.setVisible(false);
        clearTextFieldsError();
        clearInvoiceTextFieldsError();
        clearCreditTextFieldsError();
    }

    private void fillInSavedDeliveryDetails() {
        forenameTextField.setText(customer.getFirstName());
        surnameTextField.setText(customer.getLastName());
        adressTextField.setText(customer.getAddress());
        regionTextField.setText(customer.getPostAddress());
        zipcodeTextField.setText(customer.getPostCode());
    }

    private void invoiceInit() {

        headerLabel.setText("Du har valt att betala med faktura");
        forenameTextField.focusedProperty().addListener(new TextFieldListener(forenameTextField));
        surnameTextField.focusedProperty().addListener(new TextFieldListener(surnameTextField));
        adressTextField.focusedProperty().addListener(new TextFieldListener(adressTextField));
        zipcodeTextField.focusedProperty().addListener(new TextFieldListener(zipcodeTextField));
        regionTextField.focusedProperty().addListener(new TextFieldListener(regionTextField));
        invoicePopulateTextField();
        choice = "invoice";
    }

    private void creditInit() {

        headerLabel.setText("Du har valt att betala med kreditkort");
        nameTextField.focusedProperty().addListener(new TextFieldListener(nameTextField));
        cardNumberTextField.focusedProperty().addListener(new TextFieldListener(cardNumberTextField));
        monthTextField.focusedProperty().addListener(new TextFieldListener(monthTextField));
        yearTextField.focusedProperty().addListener(new TextFieldListener(yearTextField));
        verificationCodeTextField.focusedProperty().addListener(new TextFieldListener(verificationCodeTextField));
        creditPopulateTextField();
        choice = "credit";
    }

    private void deliveryInit() {
        headerLabel.setText("Leveransadress");
        deliveryFirstNameTextField.focusedProperty().addListener(new TextFieldListener(deliveryFirstNameTextField));
        deliveryAfterNameTextField.focusedProperty().addListener(new TextFieldListener(deliveryAfterNameTextField));
        deliveryAdressTextField.focusedProperty().addListener(new TextFieldListener(deliveryAdressTextField));
        deliveryPostCodeTextField.focusedProperty().addListener(new TextFieldListener(deliveryPostCodeTextField));
        deliveryPostAdressTextField.focusedProperty().addListener(new TextFieldListener(deliveryPostAdressTextField));
        deliveryPopulateTextField();
    }

    private void invoicePopulateTextField() {
        forenameTextField.setText(customer.getFirstName());
        surnameTextField.setText(customer.getLastName());
        adressTextField.setText(customer.getAddress());
        zipcodeTextField.setText(customer.getPostCode());
        regionTextField.setText(customer.getPostAddress());
    }

    private void creditPopulateTextField() {
        nameTextField.setText(creditCard.getHoldersName());
        cardNumberTextField.setText(creditCard.getCardNumber());
        monthTextField.setText(String.valueOf(creditCard.getValidMonth()));
        yearTextField.setText(String.valueOf(creditCard.getValidYear()));
        verificationCodeTextField.setText(String.valueOf(creditCard.getVerificationCode()));
    }

    private void deliveryPopulateTextField() {
        deliveryFirstNameTextField.setText(customer.getFirstName());
        deliveryAfterNameTextField.setText(customer.getLastName());
        deliveryAdressTextField.setText(customer.getAddress());
        deliveryPostCodeTextField.setText(customer.getPostCode());
        deliveryPostAdressTextField.setText(customer.getPostAddress());
    }

    @FXML
    protected void invoiceOnClick(Event event) {
        // Maybe run init invoice page here (If not initialized in initialize)
        invoiceInit();
        invoiceAnchorPane.toFront();
    }

    @FXML
    protected void creditOnClick(Event event) {
        // Maybe run init credit page here (If not already initialized)
        creditInit();
        creditcardAnchorPane.toFront();
    }

    private void clearInvoiceTextFieldsError() {
        invoiceFirstNameErrorLabel.setVisible(false);
        invoiceLastNameErrorLabel.setVisible(false);
        invoicePostCodeErrorLabel.setVisible(false);
        invoicePostAdressErrorLabel.setVisible(false);
        invoiceErrorLabel.setVisible(false);
    }

    private void clearCreditTextFieldsError() {
        creditCardNameErrorLabel.setVisible(false);
        creditCardNumberErrorLabel.setVisible(false);
        creditMonthErrorLabel.setVisible(false);
        creditYearErrorLabel.setVisible(false);
        creditVerifErrorLabel.setVisible(false);
        creditErrorLabel.setVisible(false);
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
        if (!zipcodeTextField.getText().matches("\\d+")) {
            invoicePostCodeErrorLabel.setVisible(true);
        }
    }

    private void showCreditTextFieldsError() {
        if (nameTextField.getText().matches(".*\\d+.*")) {
            creditCardNameErrorLabel.setVisible(true);
        }
        if (!cardNumberTextField.getText().matches("\\d+")) {
            creditCardNumberErrorLabel.setVisible(true);
        }
        if (!yearTextField.getText().matches("\\d+")) {
            creditYearErrorLabel.setVisible(true);
        }
        if (!monthTextField.getText().matches("\\d+")) {
            creditMonthErrorLabel.setVisible(true);
        }
        if (!verificationCodeTextField.getText().matches("\\d+")) {
            creditVerifErrorLabel.setVisible(true);
        }
    }

    @FXML
    protected void invoicePayAction() {
        clearInvoiceTextFieldsError();
        if (forenameTextField.getText().equals("") || surnameTextField.getText().equals("") ||
                adressTextField.getText().equals("") || zipcodeTextField.getText().equals("") ||
                regionTextField.getText().equals("")) {
            invoiceErrorLabel.setVisible(true);
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
        choice = "delivery";
        dataHandler.placeOrder(true);
        navigationHandler.toDestination("ConfirmationPage");
    }

    @FXML
    protected void creditPayAction() {
        clearCreditTextFieldsError();
        if (nameTextField.getText().equals("") || cardNumberTextField.getText().equals("") ||
                yearTextField.getText().equals("") || monthTextField.getText().equals("") ||
                verificationCodeTextField.getText().equals("")) {
            creditErrorLabel.setVisible(true);
            return;
        }
        showCreditTextFieldsError();
        if (creditCardNameErrorLabel.isVisible() || creditCardNumberErrorLabel.isVisible() ||
                creditMonthErrorLabel.isVisible() ||creditYearErrorLabel.isVisible() ||
                creditVerifErrorLabel.isVisible()) {
            return;
        }

        if (creditSaveDetailsCheckBox.isSelected()) {
            creditCard.setHoldersName(nameTextField.getText());
            creditCard.setCardNumber(cardNumberTextField.getText());
            creditCard.setValidMonth(Integer.parseInt(monthTextField.getText()));
            creditCard.setValidYear(Integer.parseInt(yearTextField.getText()));
            creditCard.setVerificationCode(Integer.parseInt(verificationCodeTextField.getText()));
        }
        choice = "delivery";
        dataHandler.placeOrder(true);
        navigationHandler.toDestination("ConfirmationPage");

    }

    @FXML
    protected void goBack() {
        if (choice == null) {
            navigationHandler.goBack();
            return;
        }
        if (choice.equals("invoice") || choice.equals("credit")) {
            paymentOptionsAnchorPane.toFront();
            headerLabel.setText("Betalningsalternativ");
        }
        else if (choice.equals("delivery")) {
            // Maybe reconsider where the button should lead to in this case
            paymentOptionsAnchorPane.toFront();
            headerLabel.setText("Betalningsalternativ");
        }
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

    private void showTextFieldsError() {
        if (deliveryFirstNameTextField.getText().matches(".*\\d+.*")) {
            firstNameErrorLabel.setVisible(true);
        }
        if (deliveryAfterNameTextField.getText().matches(".*\\d+.*")) {
            lastNameErrorLabel.setVisible(true);
        }
        if (deliveryPostAdressTextField.getText().matches(".*\\d+.*")) {
            postAdressErrorLabel.setVisible(true);
        }
        if (!deliveryPostCodeTextField.getText().matches("\\d+")) {
            postCodeErrorLabel.setVisible(true);
        }
    }

    private void clearTextFieldsError() {
        firstNameErrorLabel.setVisible(false);
        lastNameErrorLabel.setVisible(false);
        postAdressErrorLabel.setVisible(false);
        postCodeErrorLabel.setVisible(false);
        errorLabel.setVisible(false);
    }

    @FXML
    private void toPaymentAlternatives() {
        clearTextFieldsError();
        if (deliveryFirstNameTextField.getText().equals("") ||
                deliveryAfterNameTextField.getText().equals("") ||
                deliveryAdressTextField.getText().equals("") ||
                deliveryPostAdressTextField.getText().equals("") ||
                deliveryPostCodeTextField.getText().equals("")) {
            errorLabel.setVisible(true);
            return;
        }
        showTextFieldsError();
        if (firstNameErrorLabel.isVisible() || lastNameErrorLabel.isVisible() || postAdressErrorLabel.isVisible() || postCodeErrorLabel.isVisible()) {
            return;
        }
        paymentOptionsAnchorPane.toFront();
        headerLabel.setText("Betalningsalternativ");
        customer.setFirstName(deliveryFirstNameTextField.getText());
        customer.setLastName(deliveryAfterNameTextField.getText());
        customer.setAddress(deliveryAdressTextField.getText());
        customer.setPostAddress(deliveryPostAdressTextField.getText());
        customer.setPostCode(deliveryPostCodeTextField.getText());

        forenameTextField.setText(customer.getFirstName());
        surnameTextField.setText(customer.getLastName());
        adressTextField.setText(customer.getAddress());
        regionTextField.setText(customer.getPostAddress());
        zipcodeTextField.setText(customer.getPostCode());
    }

}
