package scurtu.inventoryapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/** JAVADOC FOLDER LOCATION: IdeaProjects\Inventory Application\javadoc */

/**FUTURE ENHANCEMENT
 * Add a search feature to the associated parts table to enable the user to easily search through the associated parts
 * that were added to the table from the all parts table. This is especially helpful in keeping the user organized and
 * enabling them to search through the associated parts quickly if there are many listed in the table.
*/

/** This class creates an app that displays and allows editing of inventory. */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/scurtu/inventoryapplication/Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1050, 800);
        stage.setTitle("Inventory Management Application");
        stage.setScene(scene);
        stage.show();
    }
/** This is the main method. This is the first method that gets called when the program is run. */
    public static void main(String[] args) {

        // Creating demo parts to add to Inventory list.
        InHouse p1 = new InHouse(220,"SamplePart1",150.00,200,50,1000,23);
        InHouse p2 = new InHouse(87,"SamplePart2",250.00,158,100,500,45);
        InHouse p3 = new InHouse(345,"SamplePart3",349.99,452,100,1000,23);

        Outsourced p4 = new Outsourced(37,"SamplePart4",510.99,452,50,500,"Company1");
        Outsourced p5 = new Outsourced(256,"SamplePart5",380.00,568,100,1000,"Company2");
        Outsourced p6 = new Outsourced(289,"SamplePart6",367.00,230,100,1000,"Company3");

        // Adding the demo parts to the list.
        Inventory.addPart(p1);
        Inventory.addPart(p2);
        Inventory.addPart(p3);
        Inventory.addPart(p4);
        Inventory.addPart(p5);
        Inventory.addPart(p6);

        // Creating demo products to add to Inventory list.
        Product pr1 = new Product(245, "SampleProduct1", 299.00, 350,100, 1000);
        Product pr2 = new Product(250, "SampleProduct2", 500.00, 500,100, 500);
        Product pr3 = new Product(489, "SampleProduct3", 799.99, 300,50, 500);

        // Adding the demo products to the list.
        Inventory.addProduct(pr1);
        Inventory.addProduct(pr2);
        Inventory.addProduct(pr3);


        launch();
    }
}