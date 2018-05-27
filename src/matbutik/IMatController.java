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
import java.util.stream.Collectors;

public class IMatController extends IMatModularCartController implements Initializable {
    IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    IMatNavigationHandler navigationHandler = IMatNavigationHandler.getInstance();
    private Map<Integer, IMatProductItem> iMatProductItemMap = IMatProductItemMapController.getInstance();
    @FXML FlowPane dairyFlowPane;
    @FXML FlowPane meatFlowPane;
    private @FXML FlowPane searchResult;
    private @FXML AnchorPane mainPage;
    private @FXML AnchorPane searchResultContainer;
    private @FXML TextField searchBar;
    private @FXML FlowPane shoppingCartFlowPane;
    private @FXML TabPane categories;
    private @FXML AnchorPane categoriesContainer;
    private @FXML Label numberOfProductsLabel, totalCostLabel;
    private @FXML ScrollPane searchResultScrollPane;
    private Runnable widthUpdateRunnable;

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
        productItem();
    }

    protected void productItem(){
        dataHandler.getShoppingCart().getItems().forEach(x->{
            iMatProductItemMap.get(x.getProduct().getProductId()).updateShoppingCart();
        });
    }

    public void setWidthUpdateRunnable(Runnable runnable) {
        widthUpdateRunnable = runnable;
    }

    private void updateWidthForTabAndResultFlowPanes() {
        if (widthUpdateRunnable != null)
            widthUpdateRunnable.run();
    }

    private void fillCategoryPages() {
        updateWidthForTabAndResultFlowPanes();
        for (IMatProductItem product : iMatProductItemMap.values()) {
            for (Category category : product.getCategory()) {
                FlowPane correspondingFlowPaneForCategory = ((FlowPane) ((ScrollPane) categories.getTabs().get(category.ordinal()).getContent()).getContent());
                if (!correspondingFlowPaneForCategory.getChildren().contains(product))
                    correspondingFlowPaneForCategory.getChildren().add(product);
            }
            if (shoppingCart.getItems().stream().filter(x->x.getProduct()==product.getProduct()).anyMatch(x->x.getAmount()>0.000001)) {
                product.shoppingItem = shoppingCart.getItems().stream().filter(x->x.getProduct()==product.getProduct()).collect(Collectors.toList()).get(0);
                product.addItemToCartButtonContainerToBack();
            }
        }
        //((FlowPane)((ScrollPane)categories.getTabs().get(0).getContent()).getContent()).setCursor(Cursor.CLOSED_HAND);
        if (widthUpdateRunnable!=null) widthUpdateRunnable.run();
    } // Try to do this in one "loop" instead

    private void populateProductItemMap() {
        long t0 = System.nanoTime();
        if (iMatProductItemMap.isEmpty())
            for (Product product : dataHandler.getProducts()) {
                iMatProductItemMap.put(product.getProductId(), new IMatProductItem(product, dataHandler, this));
            }
        else {
            updateProductsList();
        }
        long t1 = System.nanoTime();
        System.out.println((t1-t0) / 1.0E09 + " s");
    }

    @FXML public void shoppingCart(Event event){
        navigationHandler.toDestination("ShoppingCart");
    }
    @FXML private void navigateToHistory(Event event){
        navigationHandler.toHistory();
    }
    @FXML private void navigateToAccount(Event event) {
        navigationHandler.toAccount();
    }
    @FXML private void navigateToHelp(Event event) {navigationHandler.toHelp();}
    @FXML public void toMainPage(Event event){ navigationHandler.toMainPage(); }
    @FXML public void navigateGoBack(Event event) { navigationHandler.goBack(); }


    @FXML public void search() {
        deselectTabs();
        searchResult.getChildren().clear();
        String query = searchBar.getText().toLowerCase();
        Pattern.compile("[A-z\\u00C0-\\u017F0-9]+").matcher(query).results().forEach((queryWord) -> {
            iMatProductItemMap.values().forEach((product) -> {
                if (Pattern.compile(queryWord.group()).matcher(product.getProduct().getName().toLowerCase()).find())
                    searchResult.getChildren().add(product);

            });
            iMatProductItemMap.values().forEach((product) -> {
                if (product.getTags().stream().anyMatch(tag -> Pattern.compile(queryWord.group()).matcher(tag.toLowerCase()).find()))
                    if (searchResult.getChildren().stream().noneMatch(item -> ((IMatProductItem)item).getProduct() == product.getProduct()))
                        searchResult.getChildren().add(product);
                });
        });
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

    @FXML public void searchBarKeyPressed(javafx.scene.input.KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER)
            search();
    }

    public void shoppingItems(){
        shoppingCartFlowPane.getChildren().clear();
        dataHandler.getShoppingCart().getItems().forEach(x->shoppingCartFlowPane.getChildren().add(
                new IMatMiniShoppingCartItem(x,this,iMatProductItemMap.get(x.getProduct().getProductId())::updateShoppingCart)));
    }

    public void setNumberLabels() {
        numberOfProductsLabel.setText("Antal Varor: " + String.valueOf((int)dataHandler.getShoppingCart().getItems().stream().mapToDouble(item -> item.getProduct().getUnit().substring(item.getProduct().getUnit().length() - 2).equals("st") ?item.getAmount():1).sum()));
        totalCostLabel.setText("Summa: " + String.format("%.2f",dataHandler.getShoppingCart().getTotal()) + " kr");
    }

    public String getCreditNumberSplit(int part) {
        String number = "";
        switch (part) {
            case 0:
                number += dataHandler.getCreditCard().getCardNumber().substring(0,4);
                break;
            case 1:
                number += dataHandler.getCreditCard().getCardNumber().substring(4,8);
                break;
            case 2:
                number += dataHandler.getCreditCard().getCardNumber().substring(8,12);
                break;
            case 3:
                number += dataHandler.getCreditCard().getCardNumber().substring(12,16);
                break;
        }
        return number;
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

class IMatProductItemMapController {
    private static Map<Integer, IMatProductItem> instance;
    public static Map<Integer, IMatProductItem> getInstance() {
        return instance != null ? instance : (instance = new HashMap<>());
    }
}