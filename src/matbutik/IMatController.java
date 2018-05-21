package matbutik;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

public class IMatController extends IMatModularCartController implements Initializable {


    private @FXML FlowPane searchResult;
    private @FXML AnchorPane mainPage;
    private @FXML AnchorPane searchResultContainer;
    private @FXML TextField searchBar;
    private @FXML TabPane categories;


    private @FXML FlowPane shoppingCartFlowPane;

    private @FXML AnchorPane categoriesContainer;

    private @FXML Label numberOfProductsLabel, totalCostLabel;

    private @FXML ScrollPane searchResultScrollPane;

    IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    IMatNavigationHandler navigationHandler = IMatNavigationHandler.getInstance();

    private Map<Integer, IMatProductItem> iMatProductItemMap = new HashMap<>();


    public FlowPane getSearchResult(){
        return this.searchResult;
    }

    public FlowPane getShoppingCartFlowPane(){
        return shoppingCartFlowPane;
    }
    public TabPane getCategories() {
        return categories;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TagLoader.getInstance().loadTags();
        populateProductItemMap();
        searchResultContainer.toFront();
        navigationHandler.setCustomerPath("");
        deselectTabs();
        mainPage.toFront();
        fillCategoryPages();
        //sizeSearchResultFlowPane();
        dataHandler.getShoppingCart().getItems().forEach(x->{
            iMatProductItemMap.get(x.getProduct().getProductId()).updateShoppingCart();
        });
    }

    private Runnable widthUpdateRunnable;
    public void setWidthUpdateRunnable(Runnable runnable) {
        widthUpdateRunnable = runnable;
    }

    private void updateWidthForTabAndResultFlowPanes() {
        /*for (ScrollPane tabSP : categories.getTabs().stream().map(tab -> (ScrollPane)tab.getContent()).collect(Collectors.toList())){
            double pWidth = tabSP.getWidth() - 28-6;
            FlowPane tabFP = (FlowPane) tabSP.getContent();
            tabFP.setMaxWidth(pWidth);
            tabFP.setMinWidth(searchResult.getMaxWidth());
            tabFP.setPrefWidth(searchResult.getMaxWidth());
        }*/
        if (widthUpdateRunnable != null)
            widthUpdateRunnable.run();
    }

    @FXML FlowPane dairyFlowPane;
    @FXML FlowPane meatFlowPane;
    private void fillCategoryPages() {
        updateWidthForTabAndResultFlowPanes();
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
    private void navigateToHelp(Event event) {navigationHandler.toHelp();}

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
        categoriesContainer.toFront();
        searchBar.setText("");
        updateWidthForTabAndResultFlowPanes();
    }

    @FXML
    public void searchBarKeyPressed(javafx.scene.input.KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER)
            search();
    }

    public Label getNumberOfProductsLabel() {
        return numberOfProductsLabel;
    }

    public Label getTotalCostLabel() {
        return totalCostLabel;
    }

    public ScrollPane getSearchResultScrollPane() {
        return searchResultScrollPane;
    }
}
