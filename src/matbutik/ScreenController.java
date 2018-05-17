package matbutik;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.HashMap;

public class ScreenController {
    private HashMap<String, Parent> screenMap = new HashMap<>();
    private Scene main;

    private Parent previousRoot;

    private ScreenController() {
    }

    private static ScreenController instance;
    public static ScreenController getInstance() {
        if (instance == null)
            instance = new ScreenController();
        return instance;
    }

    public void setMain(Scene main){
        instance.main = main;
    }

    protected void addScreen(String name, Parent pane){
        instance.screenMap.put(name, pane);
    }

    protected void removeScreen(String name){
        instance.screenMap.remove(name);
    }

    protected void activate(String name, Parent currentRoot) {

        previousRoot = currentRoot;
        Parent root = screenMap.get(name);
        try {
            root = FXMLLoader.load(getClass().getResource("IMat"+name+".fxml"), java.util.ResourceBundle.getBundle("matbutik/resources/IMat"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        main.setRoot(root);
    }

    public void navigateToPrevious(){
        if (previousRoot == screenMap.get("Main")){
            try {
                ((TabPane) ((StackPane) ((AnchorPane) ((GridPane) ((AnchorPane) previousRoot).getChildren().get(0)).getChildren().get(1)).getChildren().get(0)).getChildren().get(2/**/)).getTabs().forEach(tab -> ((FlowPane) ((ScrollPane) tab.getContent()).getContent()).getChildren().forEach(item -> {
                    if (((IMatProductItem) item).shoppingItem != null) ((IMatProductItem) item).updateShoppingCart();
                }));
            } catch (Exception ex) {

                ((TabPane)((StackPane)((AnchorPane)((GridPane)((AnchorPane)previousRoot).getChildren().get(0)).getChildren().get(1)).getChildren().get(0)).getChildren().get(0/**/)).getTabs().forEach(tab -> ((FlowPane)((ScrollPane)tab.getContent()).getContent()).getChildren().forEach(item -> {if (((IMatProductItem)item).shoppingItem != null) ((IMatProductItem)item).updateShoppingCart();}));
            }
        }
        main.setRoot(previousRoot);
    }
}