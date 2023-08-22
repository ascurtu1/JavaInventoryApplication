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

/** This class controls the logic to add products to the application. */
public class AddProductController implements Initializable {

    Stage stage;
    Parent scene;

    public Label idLblAddProduct;
    public Label NameLblAddProduct;
    public Label InvLblAddProduct;
    public Label PriceLblAddProduct;
    public Label MaxLblAddProduct;
    public Label MinLblAddProduct;
    public TextField idFieldAddProduct;
    public TextField NameFieldAddProduct;
    public TextField InvFieldAddProduct;
    public TextField PriceFieldAddProduct;
    public TextField MaxFieldAddProduct;
    public TextField MinFieldAddProduct;
    public TableView allPartsTableview;
    public TableColumn IdAllPartsCol;
    public TableColumn NameAllPartsCol;
    public TableColumn InvAllPartsCol;
    public TableColumn PriceAllPartsCol;
    public TableView associatedPartsTableview;
    public TableColumn IdAssociatedPartsCol;
    public TableColumn NameAssociatedPartsCol;
    public TableColumn InvAssociatedPartsCol;
    public TableColumn PriceAssociatedPartsCol;
    public TextField SearchFieldProduct;
    public Button AddBtnAllParts;
    public Button CancelBtnAssociatedParts;
    public Button SaveBtnAssociatedParts;
    public Button RemoveBtnAssociatedParts;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Populates information into the top TableView containing all the part data to choose from.

        allPartsTableview.setItems(Inventory.getAllParts());
        IdAllPartsCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        NameAllPartsCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        InvAllPartsCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PriceAllPartsCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsTableview.setItems(associatedParts);
        IdAssociatedPartsCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        NameAssociatedPartsCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        InvAssociatedPartsCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PriceAssociatedPartsCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * Adds the selected part data from the top table view to the bottom table view with associated part data.
     *
     * @param actionEvent mouse click
     */
    public void OnActionAddAllParts(ActionEvent actionEvent) {
        Part selectedPart = (Part) allPartsTableview.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error: No part selected");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            associatedParts.add(selectedPart);
        }
    }


    /**
     * Cancels any changes and prompts user back to Main screen.
     *
     * @param actionEvent mouse click
     */
    public void OnActionCancelAssociatedParts(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will cancel any changes. Please confirm to proceed.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Saves the added product data and then returns to Main screen.
     *
     * @param actionEvent mouse click
     */
    public void OnActionSaveAssociatedParts(ActionEvent actionEvent) throws IOException {
        try {

            int id = Inventory.createPartID();
            String name = NameFieldAddProduct.getText();
            double price = Double.parseDouble(PriceFieldAddProduct.getText());
            int stock = Integer.parseInt(InvFieldAddProduct.getText());
            int min = Integer.parseInt(MinFieldAddProduct.getText());
            int max = Integer.parseInt(MaxFieldAddProduct.getText());

            if (name.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Name value is blank");
                alert.show();
            } else if (min >= max) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Min must be less than max");
                alert.show();
            } else if (stock < min|| stock > max) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Inventory must be a value between the min and max");
                alert.show();
            } else {
                Product newProductAdd = new Product(id, name, price, stock, min, max);
                Inventory.addProduct(newProductAdd);
                for (Part part : associatedParts) {
                    newProductAdd.addAssociatedPart(part);
                }

                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/scurtu/inventoryapplication/Main.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
                stage.setTitle("Inventory Management Application");
                stage.setScene(scene);
                stage.show();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "ERROR: Price, inventory, machine ID, min and max must be input as numbers");
            alert.show();
        }

    }

    /**
     * Removes the selected associated part data.
     *
     * @param actionEvent mouse click
     */
    public void OnActionRemoveAssociatedParts(ActionEvent actionEvent) {
        Part DeleteAssociatedPart = (Part) associatedPartsTableview.getSelectionModel().getSelectedItem();
        if (DeleteAssociatedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("ERROR: No Part Selected");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("WARNING: Please confirm you would like to delete");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                associatedParts.remove(DeleteAssociatedPart);
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setContentText("You have successfully deleted the part");
                alert2.show();
            }
        }
    }

    /**
     * Searches the top all parts table for inputted part data.
     *
     * @param actionEvent mouse click
     */
    public void OnActionSearchProduct(ActionEvent actionEvent) {
        String PartSearch = SearchFieldProduct.getText();
        ObservableList<Part> partSearchResult = FXCollections.observableArrayList();
        Part searchPart;
        try {
            int num = Integer.parseInt(PartSearch);
            searchPart = Inventory.lookupPart(num);
            if (searchPart == null) {

            } else {
                partSearchResult.add(searchPart);
            }

        } catch (Exception e) {
            partSearchResult = Inventory.lookupPart(PartSearch);
        }
        allPartsTableview.setItems(partSearchResult);
        if (partSearchResult.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("ERROR: No Part Selected");
            alert.show();
        }

    }
}