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

/** This class controls the logic to modify products to the application */
public class ModifyProductController implements Initializable {

    Stage stage;
    Parent scene;

    public Label idLblModify;
    public Label NameLblModify;
    public Label InvLblModify;
    public Label PriceLblModify;
    public Label MaxLblModify;
    public Label MinLblModify;
    public TextField idFieldModify;
    public TextField NameFieldModify;
    public TextField InvFieldModify;
    public TextField PriceFieldModify;
    public TextField MaxFieldModify;
    public TextField MinFieldModify;
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
    private static Product ModifyProduct;
    private static int productIndex;


    /**Method used to receive product information from Main screen
     * @param product the selected product */
    public static void passModifyProduct(Product product) {
        ModifyProduct = product;
    }

    /**Method used to receive product index from Main screen
     * @param index the selected product's index */
    public static void passModifyProductIn(int index) {
        productIndex = index;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//Populates information into the top TableView containing all the part data to choose from.

        associatedParts = ModifyProduct.getAllAssociatedParts();

        allPartsTableview.setItems(Inventory.getAllParts());
        IdAllPartsCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        NameAllPartsCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        InvAllPartsCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PriceAllPartsCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Populates information into the bottom table view of associated parts.

        associatedPartsTableview.setItems(associatedParts);
        IdAssociatedPartsCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        NameAssociatedPartsCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        InvAssociatedPartsCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PriceAssociatedPartsCol.setCellValueFactory(new PropertyValueFactory<>("price"));


        // Sets data from selected product from Main screen to fields in the new form.

        idFieldModify.setText(String.valueOf(ModifyProduct.getId()));
        NameFieldModify.setText(ModifyProduct.getName());
        PriceFieldModify.setText(String.valueOf(ModifyProduct.getPrice()));
        InvFieldModify.setText(String.valueOf(ModifyProduct.getStock()));
        MaxFieldModify.setText(String.valueOf(ModifyProduct.getMax()));
        MinFieldModify.setText(String.valueOf(ModifyProduct.getMin()));
    }

    /**
     * Searches for inputted product information.
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

    /**
     * Adds selected part from the top table to the bottom associated parts table.
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
     * Cancels any changes to associated parts and returns user to Main screen.
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
     * Saves changes to parts table on Main & current location.
     *
     * @param actionEvent mouse click
     */
    public void OnActionSaveAssociatedParts(ActionEvent actionEvent) throws IOException {
        try {

            int id = ModifyProduct.getId();
            String name = NameFieldModify.getText();
            double price = Double.parseDouble(PriceFieldModify.getText());
            int stock = Integer.parseInt(InvFieldModify.getText());
            int min = Integer.parseInt(MinFieldModify.getText());
            int max = Integer.parseInt(MaxFieldModify.getText());

            if (name.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Name value is blank");
                alert.show();
            } else if (min >= max) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Min must be less than max");
                alert.show();
            } else if (stock < min || stock > max) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Inventory must be a value between the min and max");
                alert.show();
            } else {
                Product newProductAddModify = new Product(id, name, price, stock, min, max);
                Inventory.updateProduct(productIndex, newProductAddModify);
                for (Part part : associatedParts) {
                    newProductAddModify.addAssociatedPart(part);
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
     * Removes product information from associated parts table.
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
                ModifyProduct.deleteAssociatePart(DeleteAssociatedPart);
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setContentText("You have successfully deleted the part");
                alert2.show();

            }
        }
    }
}