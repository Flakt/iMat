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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.util.Callback;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Flow;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class IMatController extends IMatModularCartController implements Initializable {


    private @FXML FlowPane searchResult;
    private @FXML AnchorPane mainPage;
    private @FXML ScrollPane searchResultContainer;
    private @FXML TextField searchBar;
    private @FXML TabPane categories;


    private @FXML FlowPane shoppingCartFlowPane;

    IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    IMatNavigationHandler navigationHandler = IMatNavigationHandler.getInstance();

    private Map<Integer, IMatProductItem> iMatProductItemMap = new HashMap<>();



    public FlowPane getShoppingCartFlowPane(){
        return shoppingCartFlowPane;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateProductItemMap();
        searchResultContainer.toFront();
        navigationHandler.setCustomerPath("");
        deselectTabs();
        mainPage.toFront();
        fillCategoryPages();
        //sizeSearchResultFlowPane();
    }

    private void sizeSearchResultFlowPane() {
        double pWidth = searchResultContainer.getWidth();
        searchResult.setMaxWidth(pWidth);
        searchResult.setMinWidth(searchResult.getMaxWidth());
        searchResult.setPrefWidth(searchResult.getMaxWidth());


        sizeTabFlowPanes();
    }

    private void sizeTabFlowPanes() {
        for (ScrollPane tabSP : categories.getTabs().stream().map(tab -> (ScrollPane)tab.getContent()).collect(Collectors.toList())){
            double pWidth = tabSP.getWidth() - 28-6;
            FlowPane tabFP = (FlowPane) tabSP.getContent();
            tabFP.setMaxWidth(pWidth);
            tabFP.setMinWidth(searchResult.getMaxWidth());
            tabFP.setPrefWidth(searchResult.getMaxWidth());
        }
    }

    @FXML FlowPane dairyFlowPane;
    @FXML FlowPane meatFlowPane;
    private void fillCategoryPages() {sizeSearchResultFlowPane();sizeTabFlowPanes();
        //dairyFlowPane.getChildren().addAll(iMatProductItemMap.values().stream().filter(item -> item.getCategory().contains(Category.Dairy)).collect(Collectors.toList()));
        //meatFlowPane.getChildren().addAll(iMatProductItemMap.values().stream().filter(item -> item.getCategory().contains(Category.Meat)).collect(Collectors.toList()));
        //iMatProductItemMap.values().forEach(item -> {/*to tab index*/item.getCategory().forEach(Enum::ordinal);});
//        iMatProductItemMap.values().forEach(item -> item.getCategory().forEach(category -> ((FlowPane)((ScrollPane)categories.getTabs().get(category.ordinal()).getContent()).getContent()).getChildren().add(new IMatProductItem(item.getProduct(),IMatDataHandler.getInstance(),this))));
        for (IMatProductItem product : iMatProductItemMap.values())
            for (Category category : product.getCategory()) {
                FlowPane correspondingFlowPaneForCategory = ((FlowPane) ((ScrollPane) categories.getTabs().get(category.ordinal()).getContent()).getContent());
                if (!correspondingFlowPaneForCategory.getChildren().contains(product))
                    correspondingFlowPaneForCategory.getChildren().add(product
                    /*new IMatProductItem(product.getProduct(), IMatDataHandler.getInstance(), this)*/);
            }
        //((FlowPane)((ScrollPane)categories.getTabs().get(0).getContent()).getContent()).setCursor(Cursor.CLOSED_HAND);
    } // Try to do this in one "loop" instead

    private void populateProductItemMap() {
        for (Product product : dataHandler.getProducts()) {
            iMatProductItemMap.put(product.getProductId(), new IMatProductItem(product, dataHandler, this));
        }
    }

    @FXML
    private void navigateToHistory(Event event){
        navigationHandler.toHistory();
    }

    @FXML
    private void navigateToAccount(Event event) {
        navigationHandler.toAccount();
    }

    @FXML
    public void search() {
        deselectTabs();

        searchResult.getChildren().clear();
        String query = searchBar.getText().toLowerCase();
        Pattern.compile("[A-z\\u00C0-\\u017F0-9]+").matcher(query).results().forEach((queryWord) -> {
            iMatProductItemMap.values().forEach((product) -> {
                if (Pattern.compile(queryWord.group()).matcher(product.getProduct().getName().toLowerCase()).find())
                    searchResult.getChildren().add(product/*new IMatProductItem(product.getProduct(),IMatDataHandler.getInstance(),this)*/);

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
                        searchResult.getChildren().add(product/*new IMatProductItem(product.getProduct(),IMatDataHandler.getInstance(),this)*/);

                /*if (Pattern.compile(queryWord.group()).matcher(product.getProduct().getName()).find())
                    if (!searchResult.getChildren().contains(product))
                        searchResult.getChildren().add(product);*/
            });
        });


    }

    @FXML public void shoppingCart(Event event){
          navigationHandler.toDestination("ShoppingCart");
    }


    public void deselectTabs() {
        if (!categories.getStyleClass().contains("tabsDeselected"))
            categories.getStyleClass().add("tabsDeselected");
        searchResultContainer.toFront();
    }

    public void reselectTabs() {
        fillCategoryPages();
        categories.getStyleClass().remove("tabsDeselected");
        categories.toFront();
        searchBar.setText("");
        sizeTabFlowPanes();
    }

    @FXML
    public void searchBarKeyPressed(javafx.scene.input.KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER)
            search();
    }
}
