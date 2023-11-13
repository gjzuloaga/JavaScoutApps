package files.javascoutapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.Map;

public class HelloController {
    @FXML
    private TextField searchField;

    @FXML
    private Label searchResult;

    @FXML
    private Label idLabel;

    @FXML
    private Label playerApiIdLabel;

    @FXML
    private Label birthdayLabel;

    @FXML
    private Label heightLabel;

    @FXML
    private Label weightLabel;

    @FXML
    private Label dribblingLabel;

    @FXML
    private Label shortPassLabel;

    @FXML
    private Label longPassLabel;

    @FXML
    private ImageView playerImageView;


    private ObservableList<String> suggestions;

    private DatabaseManager databaseManager;

    public void initialize() {
        // Initialize the suggestions list
        suggestions = FXCollections.observableArrayList();

        // Create an instance of DatabaseManager and connect to the database
        databaseManager = new DatabaseManager();
        databaseManager.connect("D:\\DatabaseSoccer\\database.sqlite");

        // Retrieve all player names from the database and populate the suggestions list
        suggestions.addAll(databaseManager.getAllPlayerNames());

        // Initially, set the player stats labels to be invisible

    }

    @FXML
    public void onSearchClick() {
        System.out.println("Retrieved");
        String selectedPlayer = searchField.getText(); // Get the original search input
        if (!selectedPlayer.isEmpty()) {
            Map<String, String> playerStats = databaseManager.getPlayerStats(selectedPlayer);
            if (!playerStats.isEmpty()) {
                displayPlayerStats(playerStats);
            } else {
                // Convert player names from the database to lowercase for comparison
                List<String> allPlayerNames = databaseManager.getAllPlayerNames();
                String lowerCaseSelectedPlayer = selectedPlayer.toLowerCase();

                if (allPlayerNames.stream().map(String::toLowerCase).anyMatch(lowerCaseSelectedPlayer::equals)) {
                    displayErrorMessage("Player stats not found for " + selectedPlayer);
                } else {
                    displayErrorMessage("Player not found: " + selectedPlayer);
                }
            }
        }
    }



    private void displayPlayerStats(Map<String, String> playerStats) {
        idLabel.setText(playerStats.get("ID"));
       // playerApiIdLabel.setText(playerStats.get("Player API ID"));
        birthdayLabel.setText(playerStats.get("Birthday"));
        heightLabel.setText(playerStats.get("Height"));
        weightLabel.setText(playerStats.get("Weight"));
        // Print the retrieved values to the console for debugging
        System.out.println("Dribbling: " + playerStats.get("dribbling"));
        System.out.println("Long Pass: " + playerStats.get("long_passing"));
        System.out.println("Short Pass: " + playerStats.get("short_passing"));
        System.out.println("Birthday: " + playerStats.get("Birthday"));

        // Set the values for the new labels
        dribblingLabel.setText(playerStats.get("dribbling"));
        longPassLabel.setText(playerStats.get("long_passing"));
        shortPassLabel.setText(playerStats.get("short_passing"));
        
        // Set the player stats labels to be visible when displaying the stats
        idLabel.setVisible(true);
       // playerApiIdLabel.setVisible(true);
        birthdayLabel.setVisible(true);
        heightLabel.setVisible(true);
        weightLabel.setVisible(true);
        dribblingLabel.setVisible(true);
        longPassLabel.setVisible(true);
        shortPassLabel.setVisible(true);

    }
    private void displayErrorMessage(String message) {
        // Handle displaying an error message in your UI
    }

    public void setDatabaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }
}
