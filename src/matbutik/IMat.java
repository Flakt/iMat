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

import static javafx.application.Application.launch;

public class IMat extends Application {

    @FXML
        private ListView categories;

    @Override
    public void start(Stage stage) throws Exception {

        // To do: make resource bundle/fxml file and link it

        ResourceBundle bundle = java.util.ResourceBundle.getBundle("matbutik/resources/IMat");

        Parent root = FXMLLoader.load(getClass().getResource("IMat.fxml"), bundle);

        Scene scene = new Scene(root, 1024, 800);

        stage.setTitle(bundle.getString("application.name"));
        stage.setScene(scene);
        stage.show();

    }


    public static void main(String[] args) {
        launch(args);

        /*categories.setItems(FXCollections.observableArrayList(
                "Erbjudanden", "Mejeri", "Chark & Pålägg", "Fisk", "Frukt & Grönt", "Bröd", "Kryddhyllan",
                "Dryck", "Frys", "Konfektyr & Kaffebröd"));
        */
    }
}
