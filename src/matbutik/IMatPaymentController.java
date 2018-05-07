package matbutik;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.net.URL;
import java.util.ResourceBundle;

public class IMatPaymentController implements Initializable {

    private IMatDataHandler dataHandler;

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
    private CheckBox saveDetailsCheckBox;
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
    private TextField cvvcvcTextField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataHandler = IMatDataHandler.getInstance();
    }
}
