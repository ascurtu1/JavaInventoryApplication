module scurtu.inventoryapplication {
    requires javafx.controls;
    requires javafx.fxml;


    opens scurtu.inventoryapplication to javafx.fxml;
    exports scurtu.inventoryapplication;
}