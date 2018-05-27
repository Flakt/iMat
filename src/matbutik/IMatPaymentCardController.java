package matbutik;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.net.URL;
import java.util.ResourceBundle;

public class IMatPaymentCardController extends IMatController implements Initializable {
    private CreditCard creditCard;
    private IMatDataHandler dataHandler;
    @FXML private AnchorPane creditcardMainAnchorPane;
    @FXML private Label creditCardNameErrorLabel;
    @FXML private Label creditCardNumberErrorLabel;
    @FXML private Label creditMonthErrorLabel;
    @FXML private Label creditYearErrorLabel;
    @FXML private Label creditVerifErrorLabel;
    @FXML private Label creditErrorLabel;
    @FXML private TextField nameTextField;
    @FXML private TextField cardNumberTextField;
    @FXML private TextField cardNumberTextField1;
    @FXML private TextField cardNumberTextField2;
    @FXML private TextField cardNumberTextField3;
    @FXML private TextField yearTextField;
    @FXML private TextField monthTextField;
    @FXML private TextField verificationCodeTextField;
    @FXML private CheckBox creditSaveDetailsCheckBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataHandler = IMatDataHandler.getInstance();
        creditCard = dataHandler.getCreditCard();
        clearCreditTextFieldsError();
        creditInit();
        creditPopulateTextField();
        setNumberLabels();
    }

    private boolean validateCardNumberLength() {
        if (cardNumberTextField.getText().length() == 4 && cardNumberTextField1.getText().length() == 4 &&
            cardNumberTextField2.getText().length() == 4 && cardNumberTextField3.getText().length() == 4) {
            return true;
        }
        else {
            return false;
        }
    }

    private void setCardNumberLengthErrors() {
        if (cardNumberTextField.getText().length() != 4) {
            cardNumberTextField.setStyle("-fx-text-box-border: red;");
        }
        if (cardNumberTextField1.getText().length() != 4) {
            cardNumberTextField1.setStyle("-fx-text-box-border: red;");
        }
        if (cardNumberTextField2.getText().length() != 4) {
            cardNumberTextField2.setStyle("-fx-text-box-border: red;");
        }
        if (cardNumberTextField3.getText().length() != 4) {
            cardNumberTextField3.setStyle("-fx-text-box-border: red;");
        }
    }

    private void setEmptyTextFieldsErrors() {
        for (Node node : creditcardMainAnchorPane.getChildren()) {
            if (node instanceof TextField) {
                if (((TextField) node).getText().equals("")) {
                    ((TextField) node).setStyle("-fx-text-box-border: red;");
                }
            }
        }
    }

    private void clearCreditTextFieldsError() {
        creditCardNameErrorLabel.setVisible(false);
        creditCardNumberErrorLabel.setVisible(false);
        creditMonthErrorLabel.setVisible(false);
        creditYearErrorLabel.setVisible(false);
        creditVerifErrorLabel.setVisible(false);
        creditErrorLabel.setVisible(false);
        for (Node node : creditcardMainAnchorPane.getChildren()) {
            if (node instanceof TextField) {
                ((TextField) node).setStyle(null);
            }
        }
    }

    private void creditPopulateTextField() {
        nameTextField.setText(creditCard.getHoldersName());
        if (creditCard.getCardNumber().toCharArray().length == 16) {
            cardNumberTextField.setText(getCreditNumberSplit(0));
            cardNumberTextField1.setText(getCreditNumberSplit(1));
            cardNumberTextField2.setText(getCreditNumberSplit(2));
            cardNumberTextField3.setText(getCreditNumberSplit(3));
        }
        monthTextField.setText(String.valueOf(creditCard.getValidMonth()));
        yearTextField.setText(String.valueOf(creditCard.getValidYear()));
        verificationCodeTextField.setText(String.valueOf(creditCard.getVerificationCode()));
    }

    private void creditInit() {
        nameTextField.focusedProperty().addListener(new TextFieldListener(nameTextField));
        cardNumberTextField.focusedProperty().addListener(new TextFieldListener(cardNumberTextField));
        monthTextField.focusedProperty().addListener(new TextFieldListener(monthTextField));
        yearTextField.focusedProperty().addListener(new TextFieldListener(yearTextField));
        verificationCodeTextField.focusedProperty().addListener(new TextFieldListener(verificationCodeTextField));
        creditPopulateTextField();
    }

    private void setCardNumberTextFieldErrors() {
        if (!cardNumberTextField.getText().matches("[0-9]+")) {
            cardNumberTextField.setStyle("-fx-text-box-border: red;");
            creditCardNumberErrorLabel.setText("Bara siffror är tillåtna");
            creditCardNumberErrorLabel.setVisible(true);
        }
        if (!cardNumberTextField1.getText().matches("[0-9]+")) {
            cardNumberTextField1.setStyle("-fx-text-box-border: red;");
            creditCardNumberErrorLabel.setText("Bara siffror är tillåtna");
            creditCardNumberErrorLabel.setVisible(true);
        }
        if (!cardNumberTextField2.getText().matches("[0-9]+")) {
            cardNumberTextField2.setStyle("-fx-text-box-border: red;");
            creditCardNumberErrorLabel.setText("Bara siffror är tillåtna");
            creditCardNumberErrorLabel.setVisible(true);
        }
        if (!cardNumberTextField3.getText().matches("[0-9]+")) {
            cardNumberTextField3.setStyle("-fx-text-box-border: red;");
            creditCardNumberErrorLabel.setText("Bara siffror är tillåtna");
            creditCardNumberErrorLabel.setVisible(true);
        }
    }

    private void showCreditTextFieldsError() {
        if (nameTextField.getText().matches(".*\\d+.*")) {
            creditCardNameErrorLabel.setVisible(true);
            nameTextField.setStyle("-fx-text-box-border: red;");
        }
        setCardNumberTextFieldErrors();
        if (!validateCardNumberLength()) {
            setCardNumberLengthErrors();
            creditCardNumberErrorLabel.setText("Kortnummret är inte i korrekt format");
            creditCardNumberErrorLabel.setVisible(true);
        }
        if (!yearTextField.getText().matches("[0-9]+")) {
            creditYearErrorLabel.setVisible(true);
        }
        if (!monthTextField.getText().matches("[0-9]+")) {
            creditMonthErrorLabel.setVisible(true);
        }
        if (!verificationCodeTextField.getText().matches("[0-9]+")) {
            creditVerifErrorLabel.setVisible(true);
        }
    }

    @FXML
    protected void creditPayAction() {
        clearCreditTextFieldsError();
        if (nameTextField.getText().equals("") || cardNumberTextField.getText().equals("") ||
                yearTextField.getText().equals("") || monthTextField.getText().equals("") ||
                verificationCodeTextField.getText().equals("")) {
            setEmptyTextFieldsErrors();
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
            creditCard.setCardNumber(cardNumberTextField.getText() + cardNumberTextField1.getText() + cardNumberTextField2.getText() + cardNumberTextField3.getText());
            creditCard.setValidMonth(Integer.parseInt(monthTextField.getText()));
            creditCard.setValidYear(Integer.parseInt(yearTextField.getText()));
            creditCard.setVerificationCode(Integer.parseInt(verificationCodeTextField.getText()));
        }
        dataHandler.placeOrder(true);
        navigationHandler.toConfirmationPage();

    }


}
