package scurtu.inventoryapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class controls the logic to modify parts to the application. */
public class ModifyPartController implements Initializable {

    Stage stage;
    Parent scene;

    public Label IdLblModify;
    public Label NameLblModify;
    public Label InvLblModify;
    public Label PriceLblModify;
    public Label MaxLblModify;
    public Label ChangeLblModify;
    public Label MinLblModify;
    public TextField IdFieldModify;
    public TextField NameFieldModify;
    public TextField InvFieldModify;
    public TextField PriceFieldModify;
    public TextField MaxFieldModify;
    public TextField ChangeFieldModify;
    public RadioButton IHRadioModify;
    public TextField MinFieldModify;
    public RadioButton ORModify;
    public Button SaveBtnModify;
    public Button CancelBtnModify;

    private static Part ModifyPart;
    private static int partIndex;



    /**Method used to receive part information from Main screen
     * @param part the selected part */
    public static void passModifyPart(Part part) {
        ModifyPart = part;
    }

    /**Method used to receive index of part information from Main screen
     * @param index the index of the part selected to modify from the list */
    public static void passModifyPartIn(int index){
        partIndex = index;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /**
         * Sets data from Main Screen selected part to fields in the new form.
         */
        IdFieldModify.setText(String.valueOf(ModifyPart.getId()));
        NameFieldModify.setText(ModifyPart.getName());
        PriceFieldModify.setText(String.valueOf(ModifyPart.getPrice()));
        InvFieldModify.setText(String.valueOf(ModifyPart.getStock()));
        MaxFieldModify.setText(String.valueOf(ModifyPart.getMax()));
        MinFieldModify.setText(String.valueOf(ModifyPart.getMin()));

        /**
         *checks if InHouse or Outsourced part is selected and changes the form.
         */
        if(ModifyPart instanceof InHouse){
            IHRadioModify.setSelected(true);
            ChangeLblModify.setText("Machine ID");
            ChangeFieldModify.setText(String.valueOf(((InHouse) ModifyPart).getMachineId()));


        }
        else if(ModifyPart instanceof Outsourced){
            ORModify.setSelected(true);
            ChangeLblModify.setText("Company Name");
            ChangeFieldModify.setText(((Outsourced) ModifyPart).getCompanyName());
        }
    }

    /**Changes the radio button text to "Machine ID" when clicked.
     * @param actionEvent mouse click */
    public void OnActionIHModify(ActionEvent actionEvent) {
        ChangeLblModify.setText("MachineID");
    }

    /**
     * Changes the radio button text to "Company Name" when clicked.
     * @param actionEvent mouse click
     */
    public void OnActionORModify(ActionEvent actionEvent) {
        ChangeLblModify.setText("Company Name");
    }

    /**
     * Saves the modified data and provides logical checks and error notifications as necessary.
     * @param actionEvent mouse click
     */
    public void OnActionSaveModify(ActionEvent actionEvent) throws IOException {
    try {
        int id = ModifyPart.getId();
        String name = NameFieldModify.getText();
        double price = Double.parseDouble(PriceFieldModify.getText());
        int stock = Integer.parseInt(InvFieldModify.getText());
        int min = Integer.parseInt(MinFieldModify.getText());
        int max = Integer.parseInt(MaxFieldModify.getText());
        String changeField = ChangeFieldModify.getText();

        if (name.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Name value is blank");
            alert.show();
        } else if (changeField.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Input value is blank");
            alert.show();
        } else if (min >= max) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Min must be less than max");
            alert.show();
        } else if (stock < min || stock > max) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Inventory must be a value between the min and max");
            alert.show();
        } else {
            if (IHRadioModify.isSelected()) {
                InHouse updatedInHousePart = new InHouse(id, name, price, stock, min, max, Integer.parseInt(changeField));
                Inventory.updatePart(partIndex, updatedInHousePart);
            } else if (ORModify.isSelected()) {
                Outsourced updatedOutsourcedPart = new Outsourced(id, name, price, stock, min, max, changeField);
                Inventory.updatePart(partIndex, updatedOutsourcedPart);

            }

            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/scurtu/inventoryapplication/Main.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
            stage.setTitle("Inventory Management Application");
            stage.setScene(scene);
            stage.show();
        }
    } catch(NumberFormatException e) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "ERROR: Price, inventory, machine ID, min and max must be input as numbers");
        alert.show();
    }

}

    /** Cancels any changes and returns to Main screen.
     * @param actionEvent mouse click */
    public void OnActionCancelModify(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will cancel any changes. Please confirm to proceed.");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
}
