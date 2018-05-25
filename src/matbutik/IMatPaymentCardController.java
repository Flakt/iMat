package matbutik;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.net.URL;
import java.util.ResourceBundle;

public class IMatPaymentCardController extends IMatController implements Initializable {
    private CreditCard creditCard;
    private IMatDataHandler dataHandler;
    @FXML private Label creditCardNameErrorLabel;
    @FXML private Label creditCardNumberErrorLabel;
    @FXML private Label creditMonthErrorLabel;
    @FXML private Label creditYearErrorLabel;
    @FXML private Label creditVerifErrorLabel;
    @FXML private Label creditErrorLabel;
    @FXML private TextField nameTextField;
    @FXML private TextField cardNumberTextField;
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
    private void clearCreditTextFieldsError() {
        creditCardNameErrorLabel.setVisible(false);
        creditCardNumberErrorLabel.setVisible(false);
        creditMonthErrorLabel.setVisible(false);
        creditYearErrorLabel.setVisible(false);
        creditVerifErrorLabel.setVisible(false);
        creditErrorLabel.setVisible(false);
    }

    private void creditPopulateTextField() {
        nameTextField.setText(creditCard.getHoldersName());
        cardNumberTextField.setText(creditCard.getCardNumber());
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

    private void showCreditTextFieldsError() {
        if (nameTextField.getText().matches(".*\\d+.*")) {
            creditCardNameErrorLabel.setVisible(true);
        }
        if (!cardNumberTextField.getText().matches("[0-9]+")) {
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
        dataHandler.placeOrder(true);
        navigationHandler.toDelivery();

    }


}
