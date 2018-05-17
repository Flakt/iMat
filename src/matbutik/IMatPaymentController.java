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
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.util.ResourceBundle;

public class IMatPaymentController implements Initializable {

    /*
        To do:
            - Implementera resten utav knapparna
            - Testa om backend fungerar som det är tänkt
            - Testa om bytningen mellan AnchorPanes fungerar
     */

    private IMatDataHandler dataHandler;
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
    private Label invoiceNumberOfProductsLabel;
    @FXML
    private Label invoiceSumLabel;
    @FXML
    private Label creditNumberOfProductsLabel;
    @FXML
    private Label creditSumLabel;
    @FXML
    private Label deliveryNumberOfProductsLabel;
    @FXML
    private Label deliverySumLabel;
    @FXML
    private SplitPane paymentOptionsSplitPane;
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
        customer = dataHandler.getCustomer();
        creditCard = dataHandler.getCreditCard();
        numberOfProductsLabel.setText("Antal Varor: " + String.valueOf((int)dataHandler.getShoppingCart().getItems().stream().mapToDouble(item -> item.getProduct().getUnit().substring(item.getProduct().getUnit().length() - 2).equals("st") ?item.getAmount():1).sum()));
        totalCostLabel.setText("Summa: " + String.valueOf(dataHandler.getShoppingCart().getTotal()));
        //paymentOptionsSplitPane.toFront();
        deliveryAnchorPane.toFront();
        headerLabel.setText("Leveransadress");
        fillInSavedDeliveryDetails();
        errorLabel.setVisible(false);
    }

    private void fillInSavedDeliveryDetails() {
        forenameTextField.setText(customer.getFirstName());
        surnameTextField.setText(customer.getLastName());
        adressTextField.setText(customer.getAddress());
        regionTextField.setText(customer.getPostAddress());
        zipcodeTextField.setText(customer.getPostCode());
    }

    private void invoiceInit() {
        invoiceNumberOfProductsLabel.setText("Antal Varor: " + /*String.valueOf(dataHandler.getShoppingCart().getItems().size())*/String.valueOf((int)dataHandler.getShoppingCart().getItems().stream().mapToDouble(item -> item.getProduct().getUnit().substring(item.getProduct().getUnit().length()-2)=="st"?item.getAmount():1).sum()));
        invoiceSumLabel.setText("Summa: " + String.valueOf(dataHandler.getShoppingCart().getTotal()));
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
        creditNumberOfProductsLabel.setText("Antal Varor: " + String.valueOf((int)dataHandler.getShoppingCart().getItems().stream().mapToDouble(item -> item.getProduct().getUnit().substring(item.getProduct().getUnit().length()-2)=="st"?item.getAmount():1).sum()));
        creditSumLabel.setText("Summa: " + String.valueOf(dataHandler.getShoppingCart().getTotal()));
        headerLabel.setText("Du har valt att betala med kreditkort");
        nameTextField.focusedProperty().addListener(new TextFieldListener(nameTextField));
        cardNumberTextField.focusedProperty().addListener(new TextFieldListener(cardNumberTextField));
        monthTextField.focusedProperty().addListener(new TextFieldListener(monthTextField));
        yearTextField.focusedProperty().addListener(new TextFieldListener(yearTextField));
        verificationCodeTextField.focusedProperty().addListener(new TextFieldListener(verificationCodeTextField));
        creditPopulateTextField();
        choice = "credit";
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

    @FXML
    protected void invoicePayAction() {
        if (invoiceSaveDetailsCheckBox.isSelected()) {
            customer.setFirstName(forenameTextField.getText());
            customer.setLastName(surnameTextField.getText());
            customer.setAddress(adressTextField.getText());
            customer.setPostAddress(regionTextField.getText());
            customer.setPostCode(zipcodeTextField.getText());
        }
        choice = "delivery";
        deliveryAnchorPane.toFront();
        headerLabel.setText("Leveransadress");
        deliveryPopulateTextField();

        ScreenController.getInstance().activate("confirmation", creditSumLabel.getScene().getRoot());
    }

    @FXML
    protected void creditPayAction() {
        if (creditSaveDetailsCheckBox.isSelected()) {
            creditCard.setHoldersName(nameTextField.getText());
            creditCard.setCardNumber(cardNumberTextField.getText());
            creditCard.setValidMonth(Integer.parseInt(monthTextField.getText()));
            creditCard.setValidYear(Integer.parseInt(yearTextField.getText()));
            creditCard.setVerificationCode(Integer.parseInt(verificationCodeTextField.getText()));
        }
        choice = "delivery";
        deliveryAnchorPane.toFront();
        headerLabel.setText("Leveransadress");
        deliveryPopulateTextField();

        ScreenController.getInstance().activate("ConfirmationPage", creditSumLabel.getScene().getRoot());

    }

    @FXML
    protected void goBack() {
        if (choice == null) {
            ScreenController.getInstance().navigateToPrevious();
            return;
        }
        if (choice.equals("invoice") || choice.equals("credit")) {
            paymentOptionsSplitPane.toFront();
            headerLabel.setText("Betalningsalternativ");
        }
        else if (choice.equals("delivery")) {
            // Maybe reconsider where the button should lead to in this case
            paymentOptionsSplitPane.toFront();
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

    @FXML
    private void toPaymentAlternatives() {
        if (deliveryFirstNameTextField.getText().equals("") ||
                deliveryAfterNameTextField.getText().equals("") ||
                deliveryAdressTextField.getText().equals("") ||
                deliveryPostAdressTextField.getText().equals("") ||
                deliveryPostCodeTextField.getText().equals("")) {
            errorLabel.setVisible(true);
            return;
        }
        paymentOptionsSplitPane.toFront();
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
