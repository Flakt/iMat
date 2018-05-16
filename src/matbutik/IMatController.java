package matbutik;

import javafx.collections.FXCollections;
import javafx.collections.ObservableListBase;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
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

public class IMatController implements Initializable {



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
        //dairyFlowPane.getChildren().addAll(iMatProductItemMap.values().stream().filter(item -> item.getCategory().contains(Category.Dairy)).collect(Collectors.toList()));
        //meatFlowPane.getChildren().addAll(iMatProductItemMap.values().stream().filter(item -> item.getCategory().contains(Category.Meat)).collect(Collectors.toList()));
        //iMatProductItemMap.values().forEach(item -> {/*to tab index*/item.getCategory().forEach(Enum::ordinal);});
        iMatProductItemMap.values().forEach(item -> item.getCategory().forEach(category -> ((FlowPane)((ScrollPane)categories.getTabs().get(category.ordinal()).getContent()).getContent()).getChildren().add(new IMatProductItem(item.getProduct(),IMatDataHandler.getInstance()))));
        //((FlowPane)((ScrollPane)categories.getTabs().get(0).getContent()).getContent()).setCursor(Cursor.CLOSED_HAND);
    } // Try to do this in one "loop" instead

    private void listItems() {

        for (Product product : dataHandler.getProducts()) {
            iMatProductItemMap.put(product.getProductId(), new IMatProductItem(product, dataHandler));
            //searchResult.getChildren().add(iMatProductItemMap.get(product.getProductId()));
            //System.out.println(product.getProductId() + " — " + product.getName());
        }
    }

    @FXML
    public void search() {
        deselectTabs();

        searchResult.getChildren().clear();
        String query = searchBar.getText().toLowerCase();
        Pattern.compile("[A-z\\u00C0-\\u017F]+").matcher(query).results().forEach((queryWord) -> {
            iMatProductItemMap.values().forEach((product) -> {
                if (Pattern.compile(queryWord.group()).matcher(product.getProduct().getName().toLowerCase()).find())
                    searchResult.getChildren().add(/*product*/new IMatProductItem(product.getProduct(),IMatDataHandler.getInstance()));

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
                    if (searchResult.getChildren().stream().noneMatch(item -> ((IMatProductItem)item).getProduct() == product.getProduct()))
                        searchResult.getChildren().add(/*product*/new IMatProductItem(product.getProduct(),IMatDataHandler.getInstance()));

                /*if (Pattern.compile(queryWord.group()).matcher(product.getProduct().getName()).find())
                    if (!searchResult.getChildren().contains(product))
                        searchResult.getChildren().add(product);*/
            });
        });


    }

    @FXML public void ShoppingCart(Event event){
          ScreenController.getInstance().activate("ShoppingCart");
    }


    public void deselectTabs() {
        if (!categories.getStyleClass().contains("tabsDeselected"))
            categories.getStyleClass().add("tabsDeselected");
        searchResultContainer.toFront();
    }

    public void reselectTabs() {
        categories.getStyleClass().remove("tabsDeselected");
        categories.toFront();
    }
}
