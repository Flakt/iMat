package matbutik;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IMatProductItem extends AnchorPane {

    private IMatController controller;
    private IMatDataHandler dataHandler;
    private Product product;
    public ShoppingItem shoppingItem;
    private EnumSet<Category> category;
    private Set<String> tags;





    // SPINNER
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
        acquireCategoryAndTags(p);

        if (isAPiece())
            valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
                    0, 99);
        else
            valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0,99,0,0.1);

        productTotalPrice.setText(String.format( "%.2f",((product.getPrice()))) + " kr");
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
        } else
            controller.decrementProductAmount(shoppingItem, isAPiece()?1:0.1);


        updateShoppingCart();
    }

    @FXML
    public void onTextFieldInput(){
        String amountText= numberOfProducts.getText();
        System.out.println(numberOfProducts.getText());
        int amount = Integer.parseInt(amountText);
        valueFactory.setValue(amount);
        controller.setProductAmount(shoppingItem, amount);
        updateShoppingCart();
    }

    private void update(){
        String amountFormat = isAPiece() ? "%.0f" : "%.1f";
        numberOfProducts.setText(String.format(amountFormat,(Double)(shoppingItem!=null ? shoppingItem.getAmount() : 0)));
        productTotalPrice.setText(String.format("%.2f",(((shoppingItem!=null ? shoppingItem.getAmount() : 1) * product.getPrice()))) + " kr");
        }

    private boolean isAPiece() {
        return Arrays.stream(new String[]{"st", "rp"}).anyMatch(x->x.equals(product.getUnit().substring(product.getUnit().length()-2)));
    }

    private void setImage(){
        productImage.setImage(dataHandler.getFXImage(product));

    }

    public void updateShoppingCart(){
        update();
        controller.getShoppingCartFlowPane().getChildren().clear();
        for (ShoppingItem si: controller.shoppingCart.getItems()){
            controller.getShoppingCartFlowPane().getChildren().add(new IMatMiniShoppingCartItem(si, controller));
        }

    }

    private void acquireCategoryAndTags(Product p) {
        String filePath = System.getProperty("user.home") + "/.dat215/imat/tags.txt";
        try {
            InputStream is;
            try { is = new FileInputStream(new File(filePath)); } catch (FileNotFoundException e) {
                Path dest = new File(filePath).toPath();
                Files.copy(Paths.get(System.getProperty("user.dir") + "/src/matbutik/resources/tags.txt"), dest, StandardCopyOption.REPLACE_EXISTING);
            }
            is = new FileInputStream(new File(filePath));
            while (true) {
                String packet = readPacket(is);
                if (!Pattern.compile("\\D").matcher(packet).matches()) { // If the packet only contains numeric characters
                    int parsed = Integer.parseInt(packet);
                    if (p.getProductId() == parsed) { // If the id if this product is found
                        String subPacket = "";
                        do {
                            subPacket = readSubPacket(is);
                            if (!subPacket.equals(""))
                                addCategory(subPacket);
                        } while (!subPacket.equals("")); // End of packet

                        do {
                            subPacket = readSubPacket(is);
                            if (!subPacket.equals(""))
                                tags.add(subPacket);
                        } while (!subPacket.equals("")); // End of packet
                        break;
                    }
                    else {
                        waitForEnd(is);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("tags.txt seems to be lost!");
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Did not contain ID!");
        }
        addCategoriesAsTags();
    }

    private void addCategoriesAsTags() {
        List<String> list = new ArrayList<>();
        tags.addAll(category.stream().map(en -> {switch(en){
            case Fish:
                return new ArrayList<String>(){{add("fisk");}};
            case Meat:
                return new ArrayList<String>(){{add("kött");}};
            case Bread:
                return new ArrayList<String>(){{add("bröd");}};
            case Dairy:
                return new ArrayList<String>(){{add("mejeri");}};
            case Drink:
                return new ArrayList<String>(){{add("dricka");add("dryck");}};
            case Frozen:
                return new ArrayList<String>(){{add("frys");}};
            case Pantry:
                return new ArrayList<String>(){{add("skafferi");}};
            case FruitVeg:
                return new ArrayList<String>();
            case Pastries:
                return new ArrayList<String>(){{add("kaffebröd");add("sött");add("sötsaker");}};
            case Condiments:
                return new ArrayList<String>(){{add("smaksättare");add("kryddor");add("krydda");}};
            default:
                return new ArrayList<String>();
        }}).flatMap(List::stream).collect(Collectors.toList()));
    }

    private void waitForEnd(InputStream is) throws IOException {
        while (true) {
            while (is.read() != ';') ;
            if (is.read() == 'e')
                if (is.read() == 'n')
                    if (is.read() == 'd') {
                        int temp = is.read();
                        if (temp == '\r' || temp == '\n')
                            return;
                        else if (is.read() == -1)
                            throw new IOException("EOF reached!");
                    }
        }
    }

    private void addCategory(String category) {
        switch (category) {
            case "dairy":
                this.category.add(Category.Dairy);
                break;
            case "meat":
                this.category.add(Category.Meat);
                break;
            case "fish":
                this.category.add(Category.Fish);
                break;
            case "fruitveg":
                this.category.add(Category.FruitVeg);
                break;
            case "bread":
                this.category.add(Category.Bread);
                break;
            case "pantry":
                this.category.add(Category.Pantry);
                break;
            case "drink":
                this.category.add(Category.Drink);
                break;
            case "condiments":
                this.category.add(Category.Condiments);
                break;
            case "frozen":
                this.category.add(Category.Frozen);
                break;
            case "pastries":
                this.category.add(Category.Pastries);
                break;
        }
    }

    private String readPacket(InputStream is) throws IOException {
        String fileContentBuffer = "";
        for (char c = 0; true; c = (char)is.read()) {
            if (c == ';') {
                return fileContentBuffer;
            } else if (fileContentBuffer.equals("end")) {
                return "";
            }
            // If c contains special characters ( ascii code < 32: ignore)
            fileContentBuffer += c < 32 ? "" : c;
        }
    }

    private boolean readSubPacketEnd = false;
    private String readSubPacket(InputStream is) throws IOException {
        if (readSubPacketEnd) {
            readSubPacketEnd = false;
            return "";
        }

        String fileContentBuffer = "";
        for (char c = 0; true; c = (char)is.read()) {
            if (c == ',') {
                return fileContentBuffer;
            } else if (c == ';') {
                readSubPacketEnd = true;
                return fileContentBuffer;
            } else if (fileContentBuffer.equals("end")) {
                return "";
            }
            fileContentBuffer += c < 32 ? "" : c;
        }
    }



    public Product getProduct() {
        return this.product;
    }

}
