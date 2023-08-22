package scurtu.inventoryapplication;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class controls the logic for the Main screen. */
public class MainController implements Initializable {

    Stage stage;
    Parent scene;


    public TableView PartsTableView;
    public TableColumn IdPartsCol;
    public TableColumn NamePartsCol;
    public TableColumn InventoryPartsCol;
    public TableColumn PricePartsCol;
    public TextField searchFieldParts;
    public Button addBtnParts;
    public Button modifyBtnParts;
    public Button deleteBtnParts;
    public Button exitBtn;
    public TableView ProductsTableView;
    public TableColumn IdProductsCol;
    public TableColumn NameProductsCol;
    public TableColumn InventoryProductsCol;
    public TableColumn PriceProductsCol;
    public TextField searchFieldProducts;
    public Button addBtnProducts;
    public Button modifyBtnProducts;
    public Button deleteBtnProducts;
    private static Part ModifyPart;
    private static Product ModifyProduct;
    private static int modifyPartIndex;
    private static int modifyProductIndex;
    private static Part DeletePart;
    private static Product DeleteProduct;

    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Insert parts into Part table.
        PartsTableView.setItems(Inventory.getAllParts());
        IdPartsCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        NamePartsCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        InventoryPartsCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PricePartsCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Insert products into Product table.
        ProductsTableView.setItems(Inventory.getAllProducts());
        IdProductsCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        NameProductsCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        InventoryProductsCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PriceProductsCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /** Searches the Parts table for inputted part data.
     * @param actionEvent mouse click */
    public void OnActionSearchParts(ActionEvent actionEvent) {
        String PartSearch = searchFieldParts.getText();
        ObservableList<Part> partSearchResult = FXCollections.observableArrayList();
        Part searchPart;
        try {
            int num = Integer.parseInt(PartSearch);
            searchPart = Inventory.lookupPart(num);
            if(searchPart == null){
            } else {
                partSearchResult.add(searchPart);
            }
        } catch (Exception e){
            partSearchResult = Inventory.lookupPart(PartSearch);
        }
        PartsTableView.setItems(partSearchResult);
        if(partSearchResult.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("ERROR: No Part Selected");
            alert.show();
        }
    }

    /** Adds part data to the Parts table. Button click leads to Add Part Form.
     * @param actionEvent mouse click */
        public void OnActionAddParts(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /** Modifies part data on the Parts table. Button click leads to Modify Part Form.
     * @param actionEvent mouse click */
    public void OnActionModifyParts(ActionEvent actionEvent) throws IOException {
        Part ModifyPart = (Part) PartsTableView.getSelectionModel().getSelectedItem();
        modifyPartIndex = Inventory.getAllParts().indexOf(ModifyPart);
        ModifyPartController.passModifyPart(ModifyPart);
        ModifyPartController.passModifyPartIn(modifyPartIndex);
        if (ModifyPart == null) {
            Alert alert4 = new Alert(Alert.AlertType.WARNING);
            alert4.setContentText("There is no part selected");
            alert4.show();
        }
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /** Deletes part data from the Parts table.
     * @param actionEvent mouse click */
    public void OnActionDeleteParts(ActionEvent actionEvent) {
        Part DeletePart = (Part) (PartsTableView.getSelectionModel().getSelectedItem());
        if(DeletePart == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("ERROR: No Part Selected");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("WARNING: Please confirm you would like to delete");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get()== ButtonType.OK) {
                Inventory.deletePart(DeletePart);
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setContentText("You have successfully deleted the part");
                alert2.show();

            }
        }
    }
    /** Exits the application.
     * @param actionEvent mouse click */
    public void OnActionExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    /** Searches the Products table for inputted product data.
     * @param actionEvent mouse click */
    public void OnActionSearchProducts(ActionEvent actionEvent) {
        String ProductSearch = searchFieldProducts.getText();
        ObservableList<Product> productSearchResult = FXCollections.observableArrayList();
        Product searchProduct;
        try {
            int num = Integer.parseInt(ProductSearch);
            searchProduct = Inventory.lookupProduct(num);
            if(searchProduct == null){

            } else {
                productSearchResult.add(searchProduct);
            }

        } catch (Exception e){
            productSearchResult = Inventory.lookupProduct(ProductSearch);
        }
        ProductsTableView.setItems(productSearchResult);
        if(productSearchResult.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("ERROR: No Product Selected");
            alert.show();
        }
    }
    /** Adds product data to the Products table. Button click leads to Add Products Form.
     * @param actionEvent mouse click */
    public void OnActionAddProducts(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /** Modifies product data on the Products table. Button click leads to Modify Product Form.
     * @param actionEvent mouse click */
    public void OnActionModifyProducts(ActionEvent actionEvent) throws IOException {
        Product ModifyProduct = (Product) ProductsTableView.getSelectionModel().getSelectedItem();
        modifyProductIndex = Inventory.getAllProducts().indexOf(ModifyProduct);
        ModifyProductController.passModifyProduct(ModifyProduct);
        ModifyProductController.passModifyProductIn(modifyProductIndex);

        if (ModifyProduct == null) {
            Alert alert3 = new Alert(Alert.AlertType.WARNING);
            alert3.setContentText("There is no product selected");
            alert3.show();
        }
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /** RUNTIME ERROR
     * I originally created the OnActionDeleteProducts method in a way in which it allowed the deletion of a product
     * that had an associated part. After testing and realizing the error, I corrected the code by adding in an else if
     * statement that stopped the user from deleting a product with an associated part. It did so by checking if
     * the selected product has an associated part with it, and only allowing the code to move forward to completing
     * the deletion if there is no associated part, otherwise preventing the deletion and issuing an ERROR alert.
     */

    /** Deletes product data from the Products table.
     * @param actionEvent mouse click */
    public void OnActionDeleteProducts(ActionEvent actionEvent) {
        Product DeleteProduct = (Product) ProductsTableView.getSelectionModel().getSelectedItem();
        if(DeleteProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("ERROR: No Product Selected");
            alert.show();
        } else if (!DeleteProduct.getAllAssociatedParts().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("ERROR: Unable to delete product that has an associated part");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("WARNING: Please confirm you would like to delete");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get()== ButtonType.OK) {
                Inventory.deleteProduct(DeleteProduct);
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setContentText("You have successfully deleted the product");
                alert2.show();

            }
        }
    }
}