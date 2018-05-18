package matbutik;

import java.util.ArrayList;
import java.util.List;

public class IMatNavigationHandler {
    private static IMatNavigationHandler instance = null;
    private List<String> customerPath;
    private ScreenController screenController;

    protected IMatNavigationHandler() {

    }

    public static IMatNavigationHandler getInstance() {
        if (instance == null) {
            instance = new IMatNavigationHandler();
            instance.init();
        }
        return instance;
    }

    private void init() {
        this.customerPath = new ArrayList<>();
        this.screenController = ScreenController.getInstance();
    }

    public List<String> getCustomerPath() {
        return this.customerPath;
    }

    public void setCustomerPath(String step) {
        this.customerPath.add(step);
    }

    public void goBack() {
        screenController.activate(this.customerPath.get(customerPath.size() - 2));
        this.customerPath.remove(customerPath.size() - 1);
    }

    public void toAccount() {
        screenController.activate("Account");
        setCustomerPath("Account");
    }
    
    public void toHistory() {
        screenController.activate("History");
        setCustomerPath("History");
    }

    public void toHelp() {
        // Wait until help-pane is done
        setCustomerPath("Help");
    }

    public void toDestination(String dest) {
        screenController.activate(dest);
        setCustomerPath(dest);
    }

}
