package matbutik;

import javafx.scene.Parent;
import javafx.scene.Scene;

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

    protected void activate(String name, Parent currentRoot){

        previousRoot = currentRoot;
        Parent root = screenMap.get(name);
        main.setRoot(root);
    }

    public void navigateToPrevious(){
        main.setRoot(previousRoot);
    }
}