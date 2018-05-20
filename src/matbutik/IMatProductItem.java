package matbutik;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.*;
import java.util.*;

public class IMatProductItem extends AnchorPane {

    private IMatController controller;
    private IMatDataHandler dataHandler;
    private Product product;
    public ShoppingItem shoppingItem;
    private EnumSet<Category> category;
    private Set<String> tags;





    // SPINNER
    @FXML
    private AnchorPane spinner;
    @FXML
    private Button decrementButton;

    @FXML
    private Button incrementButton;

    @FXML
    private TextField numberOfProducts;

    private SpinnerValueFactory valueFactory;
    //

    @FXML
    private ImageView productImage;
    @FXML
    private Label name;
    @FXML
    private Label price;
    @FXML
    private Label eco;
    @FXML Label productTotalPrice;

    @FXML
    private Label unitLabel;

    @FXML
    AnchorPane addItemToCartButtonContainer;

    public EnumSet<Category> getCategory() {
        return category;
    }


    public Set<String> getTags() {
        return tags;
    }

    IMatProductItem(Product p, IMatDataHandler h, IMatController controller){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("IMatProduct.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);




        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.controller = controller;
        this.product = p;
        this.dataHandler = h;

        //shoppingItem = new ShoppingItem(product);
        setImage();
        setName();
        setPrice();
        setEco();

        category = EnumSet.noneOf(Category.class);
        tags = new HashSet<>();
        //acquireCategoryAndTags(p);
        tags = TagLoader.getInstance().tagMap.get(product.getProductId());
        category = TagLoader.getInstance().categoryMap.get(product.getProductId());

        if (isAPiece())
            valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
                    0, 99);
        else
            valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0,99,0,0.1);

        productTotalPrice.setText(String.format( "%.2f",((product.getPrice()))) + " kr");

        unitLabel.setText(product.getUnit().substring(product.getUnit().indexOf('/')+1));

        addItemToCartButtonContainer.toFront();
    }


    private void setName(){
        name.setText(product.getName());

    }
    private void setEco(){
        if(product.isEcological()){
            eco.setVisible(true);
        }
        else{
            eco.setVisible(false);
        }
    }
    private void setPrice(){
        price.setText("Jfr: " + product.getPrice() +  " " + product.getUnit());
    }

    @FXML
    private void onIncrement(Event event) {
        valueFactory.increment(1);
        if (shoppingItem != null)
            if (isAPiece())
                controller.incrementProductAmount(shoppingItem);
            else
                controller.incrementProductAmount(shoppingItem,0.1);
        else {
            shoppingItem = new ShoppingItem(product);
            shoppingItem.setAmount(isAPiece() ? (Integer)valueFactory.getValue() : (Double)valueFactory.getValue());
        }
        if(!controller.shoppingCart.getItems().contains(shoppingItem)){
            controller.shoppingCart.addItem(shoppingItem);
        }

        updateShoppingCart();
    }

    @FXML
        private void onDecrement(Event event){
        valueFactory.decrement(1);


        if (shoppingItem.getAmount() - (isAPiece()?1:0.1) < 0.00001) {
            controller.shoppingCart.removeItem(shoppingItem);
            shoppingItem = null;
            addItemToCartButtonContainer.toFront();
        } else
            controller.decrementProductAmount(shoppingItem, isAPiece()?1:0.1);


        updateShoppingCart();
    }

    @FXML
    public void onTextFieldInput(){
        String amountText= numberOfProducts.getText();
        System.out.println(numberOfProducts.getText());
        double amount = Double.parseDouble(amountText);
        if (isAPiece()) valueFactory.setValue((int)amount);
        else valueFactory.setValue(amount);
        controller.setProductAmount(shoppingItem, amount);
        updateShoppingCart();
    }

    @FXML
    private void addItemToCart(Event e) {
        spinner.toFront();
        onIncrement(e);
    }

    private void update(){
        String amountFormat = isAPiece() ? "%.0f" : "%.1f";
        String spacing = "", unit = product.getUnit().substring(product.getUnit().indexOf('/')+1);
        if (unit.equals("st") || unit.equals("kg")) spacing = " "; else if (unit.equals("förp") || unit.equals("burk")) spacing = "   ";
        numberOfProducts.setText(String.format(amountFormat,(Double)(shoppingItem!=null ? shoppingItem.getAmount() : 0))+spacing);
        productTotalPrice.setText(String.format("%.2f",(((shoppingItem!=null && shoppingItem.getAmount() != 0 ? shoppingItem.getAmount() : 1) * product.getPrice()))) + " kr");
        if (shoppingItem==null || shoppingItem.getAmount()==0)
            addItemToCartButtonContainer.toFront();
    }

    private boolean isAPiece() {
        return Arrays.stream(new String[]{"st", "rp", "se", "rk"}).anyMatch(x->x.equals(product.getUnit().substring(product.getUnit().length()-2)));
    }

    private void setImage(){
        productImage.setImage(dataHandler.getFXImage(product));

    }

    public void updateShoppingCart(){
        update();
        controller.getShoppingCartFlowPane().getChildren().clear();
        for (ShoppingItem si: controller.shoppingCart.getItems()){
            if (si.getAmount() != 0) {
                IMatMiniShoppingCartItem cartItem = new IMatMiniShoppingCartItem(si, controller, this::updateShoppingCart);
                controller.getShoppingCartFlowPane().getChildren().add(cartItem);
            }
        }
        controller.getTotalCostLabel().setText("Summa: " + String.format("%.2f",dataHandler.getShoppingCart().getTotal()) + " kr");
        controller.getNumberOfProductsLabel().setText("Antal Varor: " + String.valueOf(dataHandler.getShoppingCart().getItems().size()));

    }


    public Product getProduct() {
        return this.product;
    }

}
