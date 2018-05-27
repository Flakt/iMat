package matbutik;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.TextFieldSkin;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class IMatAccountController extends IMatController implements Initializable {

    private IMatDataHandler dataHandler;
    private Customer customer;
    private CreditCard creditCard;
    private IMatNavigationHandler navigationHandler;
    @FXML private Label accountSaveLabel;
    @FXML private Label accountCardNumberErrorLabel;
    @FXML private Label accountYearErrorLabel;
    @FXML private Label accountMonthErrorLabel;
    @FXML private Label accountFirstNameErrorLabel;
    @FXML private Label accountLastNameErrorLabel;
    @FXML private Label accountPostCodeErrorLabel;
    @FXML private Label accountPostAdressErrorLabel;
    @FXML private GridPane accountMainGridPane;
    @FXML private TextField accountCardNumberTextField;
    @FXML private TextField accountCardNumberTextField1;
    @FXML private TextField accountCardNumberTextField2;
    @FXML private TextField accountCardNumberTextField3;
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
        navigationHandler = IMatNavigationHandler.getInstance();
        customer = dataHandler.getCustomer();
        creditCard = dataHandler.getCreditCard();
        clearErrorLabels();
        clearTextFieldsBordersErrors();
        setTextFields();
        addTextFieldListeners();
        updateShoppingItems();

    }


    private void setTextFields() {
        accountCardNumberTextField.setText(getCreditNumberSplit(0));
        accountCardNumberTextField1.setText(getCreditNumberSplit(1));
        accountCardNumberTextField2.setText(getCreditNumberSplit(2));
        accountCardNumberTextField3.setText(getCreditNumberSplit(3));
        accountMonthTextField.setText(String.valueOf(creditCard.getValidMonth()));
        accountYearTextField.setText(String.valueOf(creditCard.getValidYear()));
        accountFirstNameTextField.setText(customer.getFirstName());
        accountLastNameTextField.setText(customer.getLastName());
        accountAdressTextField.setText(customer.getAddress());
        accountZipCodeTextField.setText(customer.getPostCode());
        accountPostAdressTextField.setText(customer.getPostAddress());
    }

    private void clearErrorLabels() {
        for (Node node : accountMainGridPane.getChildren()) {
            if (node instanceof AnchorPane) {
                for (Node deeperNode : ((AnchorPane) node).getChildren()) {
                    if (deeperNode instanceof Label && deeperNode.getId().equals("errorLabel")) {
                        deeperNode.setVisible(false);
                    }
                }
            }
            else if (node instanceof Label && node.getId().equals("errorLabel")) {
                node.setVisible(false);
            }
        }
    }

    private void clearTextFieldsBordersErrors() {
        for (Node node : accountMainGridPane.getChildren()) {
            if (node instanceof AnchorPane) {
                for (Node deeperNode : ((AnchorPane) node).getChildren()) {
                    if (deeperNode instanceof TextField) {
                        deeperNode.setStyle(null);
                    }
                }
            }
        }
    }

    private void checkCreditCardErrors() {
        if (!accountCardNumberTextField.getText().matches("[0-9]+")) {
            accountCardNumberErrorLabel.setVisible(true);
            accountCardNumberTextField.setStyle("-fx-text-box-border: red;");
        }
        if (!accountCardNumberTextField1.getText().matches("[0-9]+")) {
            accountCardNumberErrorLabel.setVisible(true);
            accountCardNumberTextField1.setStyle("-fx-text-box-border: red;");
        }
        if (!accountCardNumberTextField2.getText().matches("[0-9]+")) {
            accountCardNumberErrorLabel.setVisible(true);
            accountCardNumberTextField2.setStyle("-fx-text-box-border: red;");
        }
        if (!accountCardNumberTextField3.getText().matches("[0-9]+")) {
            accountCardNumberErrorLabel.setVisible(true);
            accountCardNumberTextField3.setStyle("-fx-text-box-border: red;");
        }
        if (!accountMonthTextField.getText().matches("[0-9]+")) {
            accountMonthErrorLabel.setVisible(true);
            accountMonthTextField.setStyle("-fx-text-box-border: red;");
        }
        if (!accountYearTextField.getText().matches("[0-9]+")) {
            accountYearErrorLabel.setVisible(true);
            accountYearTextField.setStyle("-fx-text-box-border: red;");
        }
    }

    private void checkCustomerDetailErrors() {
        if (accountFirstNameTextField.getText().matches(".*\\d+.*")) {
            accountFirstNameErrorLabel.setVisible(true);
            accountFirstNameTextField.setStyle("-fx-text-box-border: red;");
        }
        if (accountLastNameTextField.getText().matches(".*\\d+.*")) {
            accountLastNameErrorLabel.setVisible(true);
            accountLastNameTextField.setStyle("-fx-text-box-border: red;");
        }
        if (!accountZipCodeTextField.getText().matches("[0-9]+")) {
            accountPostCodeErrorLabel.setVisible(true);
            accountZipCodeTextField.setStyle("-fx-text-box-border: red;");
        }
        if (accountPostAdressTextField.getText().matches(".*\\d+.*")) {
            accountPostAdressErrorLabel.setVisible(true);
            accountPostAdressTextField.setStyle("-fx-text-box-border: red;");
        }
    }

    private boolean isErrors() {
        boolean isError = false;
        for (Node node : accountMainGridPane.getChildren()) {
            if (node instanceof AnchorPane) {
                for (Node deeperNode : ((AnchorPane) node).getChildren()) {
                    if (deeperNode instanceof Label && deeperNode.getId().equals("errorLabel") && deeperNode.isVisible()) {
                        isError = true;
                    }
                }
            }
            else if (node instanceof Label && node.getId().equals("errorLabel") && node.isVisible()) {
                isError = true;
            }
        }
        return isError;
    }

    private void addTextFieldListeners() {
        accountCardNumberTextField.focusedProperty().addListener(new TextFieldListener(accountCardNumberTextField));
        accountCardNumberTextField.focusedProperty().addListener(new CardNumberListener(accountCardNumberTextField));
        accountCardNumberTextField1.focusedProperty().addListener(new TextFieldListener(accountCardNumberTextField1));
        accountCardNumberTextField1.focusedProperty().addListener(new CardNumberListener(accountCardNumberTextField1));
        accountCardNumberTextField2.focusedProperty().addListener(new TextFieldListener(accountCardNumberTextField2));
        accountCardNumberTextField2.focusedProperty().addListener(new CardNumberListener(accountCardNumberTextField2));
        accountCardNumberTextField3.focusedProperty().addListener(new TextFieldListener(accountCardNumberTextField3));
        accountCardNumberTextField3.focusedProperty().addListener(new CardNumberListener(accountCardNumberTextField3));
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
        clearErrorLabels();
        clearTextFieldsBordersErrors();
        checkCreditCardErrors();
        checkCustomerDetailErrors();
        if (isErrors()) {
            return;
        }
        creditCard.setCardNumber(accountCardNumberTextField.getText() + accountCardNumberTextField1.getText() +
                                 accountCardNumberTextField2.getText() + accountCardNumberTextField3.getText());
        creditCard.setValidMonth(Integer.parseInt(accountMonthTextField.getText()));
        creditCard.setValidYear(Integer.parseInt(accountYearTextField.getText()));
        customer.setFirstName(accountFirstNameTextField.getText());
        customer.setLastName(accountLastNameTextField.getText());
        customer.setAddress(accountAdressTextField.getText());
        customer.setPostCode(accountZipCodeTextField.getText());
        customer.setPostAddress(accountPostAdressTextField.getText());
        accountSaveLabel.setVisible(true);
    }

    private void jumpToNext(Node node) {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyCode.TAB.getCode());
        }
        catch (AWTException e) {

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

    private class CardNumberListener implements ChangeListener<Boolean> {
        private int numberOfChar;
        private TextField textField;

        public CardNumberListener(TextField textField) {
            this.numberOfChar = textField.getText().length();
            this.textField = textField;
        }

        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (newValue) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (numberOfChar == 4) {
                            jumpToNext(textField);
                        }
                    }
                });
            }
        }
    }

    @FXML public void navigateGoBack(Event event) {
        navigationHandler.goBack();
    }
}
