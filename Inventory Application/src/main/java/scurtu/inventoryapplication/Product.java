package scurtu.inventoryapplication;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collection;

/** This is the product class. */
public class Product {
// Declare variables
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    // Constructor
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    /**
     * @param id sets id
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @return the name
     */
    public String getName() {

        return name;
    }
    /**
     * @param name sets the name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }
    /**
     * @param price sets the price
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }
    /**
     * @param stock sets the stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    /**
     * @return the minimum
     */
    public int getMin() {
        return min;
    }
    /**
     * @param min sets the minimum
     */
    public void setMin(int min) {
        this.min = min;
    }
    /**
     * @return the maximum
     */
    public int getMax() {
        return max;
    }
    /**
     * @param max sets the maximum
     */
    public void setMax(int max) {
        this.max = max;
    }

    /** Adds part to Associated Parts list.
     * @param part added part */
    public void addAssociatedPart (Part part) {
        associatedParts.add(part);
    }

    /** Deletes part that is selected on the Associated Parts list.
     * @param selectedAssociatedPart the selected part that will be deleted
     * @return associatedParts returns the list without the selected part */
    public boolean deleteAssociatePart (Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }
    /** Returns the Associated Parts list.
     * @return associatedParts the returned list */
    public  ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

}


