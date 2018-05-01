package matbutik;

import javafx.fxml.Initializable;
import se.chalmers.cse.dat216.project.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class IMatBackendController implements Initializable {
    private IMatDataHandler dataHandler;
    private User user;
    private Customer customer;
    private CreditCard creditCard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataHandler = IMatDataHandler.getInstance();
        user = dataHandler.getUser();
        customer = dataHandler.getCustomer();
        creditCard = dataHandler.getCreditCard();
    }

    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        for (ProductCategory category : ProductCategory.values()) {
            categories.add(category.toString());
        }
        return categories;
    }
}
