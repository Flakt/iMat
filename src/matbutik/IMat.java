package matbutik;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static javafx.application.Application.launch;

public class IMat extends Application {


    @Override
    public void start(Stage stage) throws Exception {

        // To do: make resource bundle/fxml file and link it

        ResourceBundle bundle = java.util.ResourceBundle.getBundle("matbutik/resources/IMat");

        Parent root = FXMLLoader.load(getClass().getResource("IMat.fxml"), bundle);
        Parent shoppingCart = FXMLLoader.load(getClass().getResource("IMatShoppingCart.fxml"), bundle);
        Parent history = FXMLLoader.load(getClass().getResource("IMatHistory.fxml"), bundle);
        Parent account = FXMLLoader.load(getClass().getResource("IMatAccount.fxml"), bundle);



        Scene scene = new Scene(root, 1024, 685);

        ScreenController screenController = ScreenController.getInstance();
        screenController.setMain(scene);

        screenController.addScreen("Main", root);
        screenController.addScreen("ShoppingCart", shoppingCart);
        screenController.addScreen("History", history);
        screenController.addScreen("Account", account);


        stage.setTitle(bundle.getString("application.name"));
        stage.setScene(scene);
        stage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
