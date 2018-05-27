package matbutik;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

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

    public void addItemToCartButtonContainerToBack() {
        addItemToCartButtonContainer.toBack();
    }

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
        if (shoppingItem == null && controller.shoppingCart.getItems().stream().anyMatch(x->x.getProduct()==this.product && x.getAmount()>0.000001)){
            List<ShoppingItem> sil = controller.shoppingCart.getItems().stream().filter(x->x.getProduct()==this.product).collect(Collectors.toList());
            if (sil.size() > 0)
                shoppingItem = sil.get(0);
        }
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
            controller.iMatShoppingItemMap.put(shoppingItem.getProduct().getProductId(),new IMatMiniShoppingCartItem(shoppingItem,controller,()->{}));
            controller.getShoppingCartFlowPane().getChildren().add(controller.iMatShoppingItemMap.get(shoppingItem.getProduct().getProductId()));
        }

        updateShoppingCart();
    }

    @FXML
        private void onDecrement(Event event){
        valueFactory.decrement(1);

        controller.decrementProductAmount(shoppingItem, isAPiece()?1:0.1);
        if (!controller.shoppingCart.getItems().contains(shoppingItem)/*shoppingItem.getAmount() - (isAPiece()?1:0.1) < 0.00001*/) {
            controller.shoppingCart.removeItem(shoppingItem);
            shoppingItem = null;
            addItemToCartButtonContainer.toFront();
        } //else



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

    public void updateShoppingCart(){ long t0 = System.nanoTime();
        update();
        /*controller.getShoppingCartFlowPane().getChildren().clear();
        for (ShoppingItem si: controller.shoppingCart.getItems()){
            if (si.getAmount() != 0) {
                IMatMiniShoppingCartItem cartItem = new IMatMiniShoppingCartItem(si, controller, this::updateShoppingCart);
                controller.getShoppingCartFlowPane().getChildren().add(cartItem);
            } else {
                controller.shoppingCart.getItems().remove(si); // Maybe not useful at all
            }
        }*/
        // Replacement
        if (this.shoppingItem == null) {
            //IMatShoppingItem removee = controller.iMatShoppingItemMap.get(product.getProductId());
            //controller.getShoppingCartFlowPane().getChildren().remove(removee);
            ObservableList<Node> fpChildren = controller.getShoppingCartFlowPane().getChildren();
            for (int i = 0; i < fpChildren.size(); i++) {
                IMatShoppingItem x = (IMatShoppingItem) fpChildren.get(i);
                if (x.shoppingItem.getProduct() == this.product)
                    fpChildren.remove(fpChildren.get(i));
            }
        } else
        for (Node item : controller.getShoppingCartFlowPane().getChildren()) {
            if (((IMatMiniShoppingCartItem) item).shoppingItem == this.shoppingItem) {
                ((IMatMiniShoppingCartItem) item).updateCartItemAmountTextField();
                //((IMatMiniShoppingCartItem) item).cartItemAmountTextField.setText(String.format("%.1f",((IMatMiniShoppingCartItem) item).shoppingItem.getAmount()));
                /*controller.getShoppingCartFlowPane().getChildren().remove(item);
                item = new IMatMiniShoppingCartItem(this.shoppingItem,controller,((IMatMiniShoppingCartItem) item).cartUpdater);
                controller.getShoppingCartFlowPane().getChildren().add(0,item);*/
                break;
            }
        }
        // END Replacement
        controller.getTotalCostLabel().setText("Summa: " + String.format("%.2f",dataHandler.getShoppingCart().getTotal()) + " kr");
        controller.getNumberOfProductsLabel().setText("Antal Varor: " + String.valueOf(dataHandler.getShoppingCart().getItems().size()));

    }

    public void updateAmountText() {
        numberOfProducts.setText(amountToString(shoppingItem.getAmount()));
    }

    private String amountToString(double amount) {
        return (isAPiece() ? ((Integer)(int)amount).toString() : String.format("%.1f",(Double)amount));
    }


    public Product getProduct() {
        return this.product;
    }

}
