package matbutik;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.util.ResourceBundle;
import java.util.function.Consumer;

import static javafx.application.Application.launch;

public class IMat extends Application {


    @Override
    public void start(Stage stage) throws Exception {

        // To do: make resource bundle/fxml file and link it

        ResourceBundle bundle = java.util.ResourceBundle.getBundle("matbutik/resources/IMat");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("IMat.fxml"), bundle);
        Parent root = loader.load();//FXMLLoader.load(getClass().getResource("IMat.fxml"), bundle);
        Parent shoppingCart = FXMLLoader.load(getClass().getResource("IMatShoppingCart.fxml"), bundle);
        Parent history = FXMLLoader.load(getClass().getResource("IMatHistory.fxml"), bundle);
        Parent account = FXMLLoader.load(getClass().getResource("IMatAccount.fxml"), bundle);
        Parent confirmation = FXMLLoader.load(getClass().getResource("IMatConfirmationPage.fxml"), bundle);
        Parent delivery = FXMLLoader.load(getClass().getResource("IMatDelivery.fxml"), bundle);
        Parent paymentCard = FXMLLoader.load(getClass().getResource("IMatPaymentCard.fxml"), bundle);
        Parent paymentInvoice = FXMLLoader.load(getClass().getResource("IMatPaymentInvoice.fxml"), bundle);
        Parent paymentOptions = FXMLLoader.load(getClass().getResource("IMatPaymentOptions.fxml"), bundle);
        Parent paymentDeliveryAddress = FXMLLoader.load(getClass().getResource("IMatPaymentDeliveryAddress.fxml"), bundle);



        Parent help = FXMLLoader.load(getClass().getResource("IMatHelp.fxml"), bundle);



        Scene scene = new Scene(root, 1196, 685);

        ScreenController screenController = ScreenController.getInstance();
        screenController.setMain(scene);

        screenController.addScreen("", root);
        screenController.addScreen("ShoppingCart", shoppingCart);
        screenController.addScreen("History", history);
        screenController.addScreen("Account", account);
        screenController.addScreen("Delivery", delivery);
        screenController.addScreen("PaymentCard", paymentCard);
        screenController.addScreen("PaymentInvoice", paymentInvoice);
        screenController.addScreen("PaymentDeliveryAddress", paymentDeliveryAddress);
        screenController.addScreen("PaymentOptions", paymentOptions);
        screenController.addScreen("ConfirmationPage", confirmation);
        screenController.addScreen("Help", help);



        stage.setTitle(bundle.getString("application.name"));
        stage.setScene(scene);
        Consumer<Object> widthListenerConsumer = x->{
            double gap = 0.0;
            IMatController controller = loader.getController();
            double width = controller.getSearchResultScrollPane().getWidth();//-34;
            controller.getSearchResult().setPrefWidth(width);
            controller.getCategories().getTabs().forEach(tab -> {
                FlowPane fp = ((FlowPane) ((ScrollPane) tab.getContent()).getContent());
                fp.setPrefWidth(width);
                fp.setHgap(gap);
                fp.setVgap(gap);
            });
            ((FlowPane)controller.getSearchResultScrollPane().getContent()).setHgap(gap);
            ((FlowPane)controller.getSearchResultScrollPane().getContent()).setVgap(gap);
        };
        scene.widthProperty().addListener(widthListenerConsumer::accept);
        ((IMatController)loader.getController()).setWidthUpdateRunnable(()->widthListenerConsumer.accept(null));
        stage.show();

    }


    public static void main(String[] args) {
        launch(args);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                IMatDataHandler.getInstance().shutDown();
            }
        }));
    }
}
