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

/** This class controls the logic to add parts to the application */
public class AddPartController implements Initializable {

    Stage stage;
    Parent scene;

    public Label IdLblAddPart;
    public Label NameLblAddPart;
    public Label InvLblAddPart;
    public Label PartLblAddPart;
    public Label MaxLblAddPart;
    public Label ChangeLblAddPart;
    public Label MinLblAddPart;
    public TextField idFieldAddPart;
    public TextField NameFieldAddPart;
    public TextField InvFieldAddPart;
    public TextField PriceFieldAddPart;
    public TextField MaxFieldAddPart;
    public TextField ChangeFieldAddPart;
    public RadioButton InHouseRadio;
    public TextField MinFieldAddPart;
    public RadioButton OutsourcedRadio;
    public Button SaveBtnAddPart;
    public Button CancelBtnAddPart;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    /** Changes the radio button text to "Machine ID" when clicked.
     * @param actionEvent mouse click */
    public void OnActionInHouse(ActionEvent actionEvent) {

        ChangeLblAddPart.setText("MachineID");
    }
    /** Changes the radio button text to "Company Name" when clicked.
     * @param actionEvent mouse click */
    public void OnActionOutsourced(ActionEvent actionEvent) {

        ChangeLblAddPart.setText("Company Name");
    }

    /** Saves added part when clicked.
     * @param actionEvent mouse click */
    public void OnActionSaveAddPart(ActionEvent actionEvent) throws IOException {
    try {
            int id = Inventory.createPartID();
            String name = NameFieldAddPart.getText();
            double price = Double.parseDouble(PriceFieldAddPart.getText());
            int stock = Integer.parseInt(InvFieldAddPart.getText());
            int min = Integer.parseInt(MinFieldAddPart.getText());
            int max = Integer.parseInt(MaxFieldAddPart.getText());
            String changeField = ChangeFieldAddPart.getText();

            if (PriceFieldAddPart.getText() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Price value is blank");
                alert.show();
            } else if (name.isBlank()) {
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
                if (InHouseRadio.isSelected()) {
                    InHouse newInHousePart = new InHouse(id, name, price, stock, min, max, Integer.parseInt(changeField));
                    Inventory.addPart(newInHousePart);
                } else {
                    Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max, changeField);
                    Inventory.addPart(newOutsourcedPart);
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
    /** Cancels adding a part when clicked.
     * @param actionEvent mouse click */
    public void OnActionCancelAddPart(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will cancel any changes. Please confirm to proceed.");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
}
