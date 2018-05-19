package matbutik;

import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.regex.Pattern;

public class TagLoader {
    Map<Integer,Set<String>> tagMap = new HashMap<>();
    Map<Integer,EnumSet<Category>> categoryMap = new HashMap<>();
    private boolean loaded = false;

    private static TagLoader instance;
    private TagLoader() { }
    public static TagLoader getInstance() {
        if (instance == null) instance = new TagLoader();
        return instance;
    }

    public void loadTags() {
        if (!loaded) {
            loaded = true;
            for (Product p : IMatDataHandler.getInstance().getProducts())
                acquireCategoryAndTags(p);
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
                                addCategory(subPacket,p);
                        } while (!subPacket.equals("")); // End of packet

                        do {
                            subPacket = readSubPacket(is);
                            if (!subPacket.equals("")) {
                                Set<String> tagSet = new HashSet<>();
                                tagSet.add(subPacket);
                                tagMap.put(p.getProductId(), tagSet);
                            }
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
//        addCategoriesAsTags();
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

    private void addCategory(String category, Product product) {
        EnumSet<Category> set = EnumSet.noneOf(Category.class);
        this.categoryMap.put(product.getProductId(),set);
        switch (category) {
            case "dairy":
                set.add(Category.Dairy);
                break;
            case "meat":
                set.add(Category.Meat);
                break;
            case "fish":
                set.add(Category.Fish);
                break;
            case "fruitveg":
                set.add(Category.FruitVeg);
                break;
            case "bread":
                set.add(Category.Bread);
                break;
            case "pantry":
                set.add(Category.Pantry);
                break;
            case "drink":
                set.add(Category.Drink);
                break;
            case "condiments":
                set.add(Category.Condiments);
                break;
            case "frozen":
                set.add(Category.Frozen);
                break;
            case "pastries":
                set.add(Category.Pastries);
                break;
        }
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
}
