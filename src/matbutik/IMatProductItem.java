package matbutik;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.io.*;
import java.util.EnumSet;
import java.util.Set;
import java.util.regex.Pattern;

public class IMatProductItem extends AnchorPane {


    private IMatDataHandler iMatDataHandler;
    private Product product;
    private EnumSet<Category> category;
    private Set<String> tags;

    public EnumSet<Category> getCategory() {
        return category;
    }

    public Set<String> getTags() {
        return tags;
    }

    public IMatProductItem(Product p, IMatDataHandler h){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("IMatProduct.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.product = p;
        this.iMatDataHandler = h;
/*
        category = EnumSet.noneOf(Category.class);
        tags = Set.of();
        acquireCategoryAndTags(p);*/
    }

    private void acquireCategoryAndTags(Product p) {
        try {
            InputStream is = new FileInputStream(new File(System.getProperty("user.home") + "/.dat215/tags.txt"));
            while (true) {
                String packet = readPacket(is, ';', "end");
                if (Pattern.compile("\\D").matcher(packet).matches()) { // If the packet doesn't contain numeric characters
                    if (p.getProductId() == Integer.getInteger(packet)) { // If the id if this product is found
                        String subPacket = "";
                        do {
                            subPacket = readPacket(is, ',', ";");
                            addCategory(subPacket);
                        } while (!subPacket.equals("")); // End of packet

                        do {
                            subPacket = readPacket(is, ',', ";");
                            tags.add(subPacket);
                        } while (!subPacket.equals("")); // End of packet
                        break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Did not contain ID!");
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

    private String readPacket(InputStream is, char separator, String ender) throws IOException {
        String fileContentBuffer = "";
        for (char c = 0; true; c = (char)is.read()) {
            if (c == separator) {
                return fileContentBuffer;
            } else if (fileContentBuffer.equals(ender)) {
                return "";
            }
            fileContentBuffer += c;
        }
    }



    public Product getProduct() {
        return this.product;
    }

}
