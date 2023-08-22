package scurtu.inventoryapplication;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Random;

/** This is the inventory class. */
public class Inventory {
    /**
     * This list holds all the parts in the inventory.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * This list holds all the products in the inventory.
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds new part to inventory.
     *
     * @param newPart the new part
     */
    public static void addPart(Part newPart) {

        allParts.add(newPart);
    }

    /**
     * Adds new product to inventory.
     *
     * @param newProduct the new product
     */
    public static void addProduct(Product newProduct) {

        allProducts.add(newProduct);
    }

    /**
     * Looks up part from partID.
     *
     * @param partID the part's ID
     * @return Part
     */
    public static Part lookupPart(int partID) {
        for (Part part : allParts) {
            if (part.getId() == partID) {
                return part;
            }
        }
        return null;
    }

    /**
     * Looks up product from product ID.
     *
     * @param productID the product's ID
     * @return Product
     */
    public static Product lookupProduct(int productID) {
        for (Product product : allProducts) {
            if (product.getId() == productID) {
                return product;
            }
        }
        return null;
    }

    /**
     * Looks up part by name.
     *
     * @param partName the part's name
     * @return partsByName
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partsByName = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().contains(partName)) {
                partsByName.add(part);
            }
        }
        return partsByName;
    }

    /**
     * Looks up product by name.
     *
     * @param productName the product's name
     * @return productsByName
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productsByName = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            if (product.getName().contains(productName)) {
                productsByName.add(product);
            }
        }
        return productsByName;
    }

    /**
     * Updates part.
     *
     * @param index the index
     * @param selectedPart the part that is selected
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Updates product.
     *
     * @param index the index
     * @param newProduct the new product that is selected
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * Deletes the part that is selected.
     *
     * @param selectedPart the part that is selected
     * @return list of all parts with removed selectedPart
     */
    public static boolean deletePart(Part selectedPart) {

        return allParts.remove(selectedPart);
    }

    /**
     * Deletes the product that is selected.
     *
     * @param selectedProduct the product that is selected
     * @return list of all products with removed selectedProduct
     */
    public static boolean deleteProduct(Product selectedProduct) {

        return allProducts.remove(selectedProduct);
    }

    /**
     * Shows every part in the list.
     *
     * @return list of all parts
     */
    public static ObservableList<Part> getAllParts() {

        return allParts;
    }

    /**
     * Shows every product in the list.
     *
     * @return list of all products
     */
    public static ObservableList<Product> getAllProducts() {

        return allProducts;
    }

    /**
     * Creates a new generated part id.
     *
     * @return partID
     */
    public static int createPartID() {
        Random ran = new Random();
        int randomID = ran.nextInt();
        return randomID;
    }
}



