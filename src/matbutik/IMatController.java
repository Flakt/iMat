package matbutik;

import javafx.collections.FXCollections;
import javafx.collections.ObservableListBase;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.util.Callback;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Flow;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class IMatController extends IMatModularCartController implements Initializable {



    @FXML FlowPane searchResult;
    @FXML AnchorPane mainPage;
    @FXML ScrollPane searchResultContainer;
    @FXML TextField searchBar;
    @FXML TabPane categories;

    IMatDataHandler dataHandler = IMatDataHandler.getInstance();


    private Map<Integer, IMatProductItem> iMatProductItemMap = new HashMap<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listItems();
        searchResultContainer.toFront();

        deselectTabs();
        mainPage.toFront();
        fillCategoryPages();
    }

    @FXML FlowPane dairyFlowPane;
    @FXML FlowPane meatFlowPane;
    private void fillCategoryPages() {
        dairyFlowPane.getChildren().addAll(iMatProductItemMap.values().stream().filter(item -> item.getCategory().contains(Category.Dairy)).collect(Collectors.toList()));
        meatFlowPane.getChildren().addAll(iMatProductItemMap.values().stream().filter(item -> item.getCategory().contains(Category.Meat)).collect(Collectors.toList()));
        //iMatProductItemMap.values().forEach(item -> {item.getCategory().forEach();});
    } // Try to do this in one "loop" instead

    private void listItems() {

        for (Product product : dataHandler.getProducts()) {
            iMatProductItemMap.put(product.getProductId(), new IMatProductItem(product, dataHandler, this));
            searchResult.getChildren().add(iMatProductItemMap.get(product.getProductId()));
            //System.out.println(product.getProductId() + " — " + product.getName());
        }
    }

    @FXML
    private void navigateToHistory(Event event){
        ScreenController.getInstance().activate("History", categories.getScene().getRoot());
    }

    @FXML
    public void search() {
        deselectTabs();

        searchResult.getChildren().clear();
        String query = searchBar.getText().toLowerCase();
        Pattern.compile("[A-z\\u00C0-\\u017F]+").matcher(query).results().forEach((queryWord) -> {
            iMatProductItemMap.values().forEach((product) -> {
                if (Pattern.compile(queryWord.group()).matcher(product.getProduct().getName().toLowerCase()).find())
                    searchResult.getChildren().add(product);

            });
            iMatProductItemMap.values().forEach((product) -> {/*(new Function<EnumSet<Category>,Set<String>>(){
                @Override
                public Set<String> apply(EnumSet<Category> enumSet) {
                    return enumSet.stream().map(Enum::name).collect(Collectors.toSet());
                }
            }).apply(product.getCategory());*/
                /*
                product.getTags().forEach((tag) -> {
                    if (Pattern.compile(queryWord.group()).matcher(tag).find()) {
                        if (!searchResult.getChildren().contains(product))
                            searchResult.getChildren().add(product);
                    }
                });*/
                if (product.getTags().stream().anyMatch(tag -> Pattern.compile(queryWord.group()).matcher(tag.toLowerCase()).find()))
                    if (!searchResult.getChildren().contains(product))
                        searchResult.getChildren().add(product);

                /*if (Pattern.compile(queryWord.group()).matcher(product.getProduct().getName()).find())
                    if (!searchResult.getChildren().contains(product))
                        searchResult.getChildren().add(product);*/
            });
        });
    }

    @FXML public void shoppingCart(Event event){
          ScreenController.getInstance().activate("ShoppingCart", categories.getScene().getRoot());
    }


    public void deselectTabs() {
        if (!categories.getStyleClass().contains("tabsDeselected"))
            categories.getStyleClass().add("tabsDeselected");
        searchResult.toFront();
    }

    public void reselectTabs() {
        categories.getStyleClass().remove("tabsDeselected");
        categories.toFront();
    }
}
