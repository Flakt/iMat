package matbutik;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;

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
    private Button backButton;
    @FXML
    private Button accountButton;
    @FXML
    private Button historyButton;
    @FXML
    private Button helpButton;
    @FXML
    private Button payButton;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataHandler = IMatDataHandler.getInstance();
        customer = dataHandler.getCustomer();
        creditCard = dataHandler.getCreditCard();
        numberOfProductsLabel.setText("Antal Varor: " + String.valueOf(dataHandler.getShoppingCart().getItems().size()));
        totalCostLabel.setText("Summa: " + String.valueOf(dataHandler.getShoppingCart().getTotal()));
        paymentOptionsSplitPane.toFront();
    }

    private void invoiceInit() {
        invoiceNumberOfProductsLabel.setText("Antal Varor: " + String.valueOf(dataHandler.getShoppingCart().getItems().size()));
        invoiceSumLabel.setText("Summa: " + String.valueOf(dataHandler.getShoppingCart().getTotal()));
        headerLabel.setText("Du har valt att betala med faktura");
        forenameTextField.focusedProperty().addListener(new TextFieldListener(forenameTextField));
        surnameTextField.focusedProperty().addListener(new TextFieldListener(surnameTextField));
        adressTextField.focusedProperty().addListener(new TextFieldListener(adressTextField));
        zipcodeTextField.focusedProperty().addListener(new TextFieldListener(zipcodeTextField));
        regionTextField.focusedProperty().addListener(new TextFieldListener(regionTextField));
        choice = "invoice";
    }

    private void creditInit() {
        creditNumberOfProductsLabel.setText("Antal Varor: " + String.valueOf(dataHandler.getShoppingCart().getItems().size()));
        creditSumLabel.setText("Summa: " + String.valueOf(dataHandler.getShoppingCart().getTotal()));
        headerLabel.setText("Du har valt att betala med kreditkort");
        nameTextField.focusedProperty().addListener(new TextFieldListener(nameTextField));
        cardNumberTextField.focusedProperty().addListener(new TextFieldListener(cardNumberTextField));
        monthTextField.focusedProperty().addListener(new TextFieldListener(monthTextField));
        yearTextField.focusedProperty().addListener(new TextFieldListener(yearTextField));
        verificationCodeTextField.focusedProperty().addListener(new TextFieldListener(verificationCodeTextField));
        choice = "credit";
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
        // Redirect to "you have payed"
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
        // Redirect to "you have payed"
    }

    @FXML
    protected void goBack() {
        if (choice.equals("invoice") || choice.equals("credit")) {
            paymentOptionsSplitPane.toFront();
        }
        else {
            // Redirect tillbaka till vart användaren var förut
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

}
