package files.javascoutapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL; // Add this import statement

import java.util.Objects;

public class HelloApplication extends Application {
    @Override

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("JavaFX Autocomplete Search Bar");

        // Create an instance of DatabaseManager and connect to the database
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.connect("D:\\DatabaseSoccer\\database.sqlite");


        // Load the FXML file
        URL fxmlUrl = getClass().getResource("/files/javascoutapp/hello-view.fxml");
        if (fxmlUrl == null) {
            System.err.println("FXML file not found. Please check the path.");
            System.exit(1); // Exit the application if the FXML file is not found
        }

        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();

        // Get the controller instance and set the databaseManager
        HelloController controller = loader.getController();
        controller.setDatabaseManager(databaseManager);

        // Create a scene and set it on the stage
        // Create a scene and set it on the stage
        Scene scene = new Scene(root, 500, 200); // Set your preferred width and height
        primaryStage.setScene(scene);

        // Set the minimum and maximum dimensions to make the window fixed
        primaryStage.setMinWidth(500);
        primaryStage.setMaxWidth(500);
        primaryStage.setMinHeight(300);
        primaryStage.setMaxHeight(300);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Disconnect from the database when the application is closed
        primaryStage.setOnCloseRequest(event -> databaseManager.disconnect());
    }

    public static void main(String[] args) {
        launch(args);
    }

    }