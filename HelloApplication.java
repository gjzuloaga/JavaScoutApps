package files.javascoutapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class HelloApplication extends Application {

    @Override

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("JavaFX Autocomplete Search Bar");

        // Create an instance of DatabaseManager and connect to the database
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.connect("D:\\DatabaseSoccer\\database.sqlite");


        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = loader.load();

        // Get the controller instance and set the databaseManager
        HelloController controller = loader.getController();
        controller.setDatabaseManager(databaseManager);

        // Create a scene and set it on the stage
        Scene scene = new Scene(root, 500, 200);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Disconnect from the database when the application is closed
        primaryStage.setOnCloseRequest(event -> databaseManager.disconnect());
    }

    public static void main(String[] args) {
        launch(args);
    }

    }