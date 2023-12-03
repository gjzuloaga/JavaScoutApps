package files.javascoutapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
    private Text enteredNameText;

    @FXML
    private Label longPassLabel;

    @FXML
    private ImageView playerImageView;

    @FXML
    private Label ballControlLabel;

    @FXML
    private Label potentialLabel;

    @FXML
    private Label potentialRatingLabel;

    @FXML
    private Label enteredNameLabel;

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

    private StringProperty playerName = new SimpleStringProperty();

    public StringProperty playerNameProperty() {
        return playerName;
    }

    public void setPlayerName(String name) {
        playerName.set(name);
    }
    @FXML
    public void onSearchClick() {
        System.out.println("Retrieved");
        String selectedPlayer = searchField.getText(); // Get the original search input
        setPlayerName(selectedPlayer);
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
        //playerApiIdLabel.setText(playerStats.get("Player API ID"));
        birthdayLabel.setText(playerStats.get("Birthday"));
        heightLabel.setText(playerStats.get("Height"));
        weightLabel.setText(playerStats.get("Weight"));
        potentialLabel.setText(playerStats.get("potential"));

        String dribblingValue = playerStats.get("dribbling") + "%";
        String longPassValue = playerStats.get("long_passing") + "%";
        String shortPassValue = playerStats.get("short_passing") + "%";
        String ballControlValue = playerStats.get("ball_control") + "%";
        //int potentialRating = Integer.parseInt(playerStats.get("potential"));
       // potentialRatingLabel.setText("Potential Rating: " + potentialRating + " (" + calculateLetterGrade(potentialRating) + ")");
        String enteredName = searchField.getText();

        // Update the label text
        setPlayerName(enteredName);

        // Load and set the corresponding image
        String imagePath = "Images/" + enteredName + ".png";

        try {
            Image image = new Image(imagePath);
            playerImageView.setImage(image);
        } catch (IllegalArgumentException e) {
            // Handle invalid image URL, e.g., set a default image
            System.out.println("Image not found: " + imagePath);
        }
        // Set the formatted values for the labels
        dribblingLabel.setText(dribblingValue);
        longPassLabel.setText(longPassValue);
        shortPassLabel.setText(shortPassValue);
        ballControlLabel.setText(ballControlValue);
        potentialLabel.setVisible(true);
        potentialLabel.setStyle("-fx-text-fill: green;");

        // Set the player stats labels to be visible when displaying the stats
        idLabel.setVisible(true);
        //playerApiIdLabel.setVisible(true);
        birthdayLabel.setVisible(true);
        heightLabel.setVisible(true);
        weightLabel.setVisible(true);
        dribblingLabel.setVisible(true);
        longPassLabel.setVisible(true);
        shortPassLabel.setVisible(true);
        ballControlLabel.setVisible(true);



        // Handle the search logic here

        // Set the entered name to the Text element
        //potentialRatingLabel.setVisible(true);
        idLabel.setStyle("-fx-text-fill: white;");
        birthdayLabel.setStyle("-fx-text-fill: white;");
        heightLabel.setStyle("-fx-text-fill: white;");
        weightLabel.setStyle("-fx-text-fill: white;");
        dribblingLabel.setStyle("-fx-text-fill: white;");
        longPassLabel.setStyle("-fx-text-fill: white;");
        shortPassLabel.setStyle("-fx-text-fill: white;");
        ballControlLabel.setStyle("-fx-text-fill: white;");
      potentialLabel.setStyle("-fx-text-fill: green;");
       // potentialRatingLabel.setStyle("-fx-text-fill: white;");
    }
    private void displayErrorMessage(String message) {
        // Handle displaying an error message in your UI
    }

    private String formatPercentage(String rawValue) {
        try {
            double percentage = Double.parseDouble(rawValue) * 100;
            return String.format("%.2f%%", percentage);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return rawValue; // Return the original value if conversion fails
        }
    }

    private String calculateLetterGrade(int potentialRating) {
        if (potentialRating >= 90) {
            return "A";
        } else if (potentialRating >= 80) {
            return "B";
        } else if (potentialRating >= 70) {
            return "C";
        } else if (potentialRating >= 60) {
            return "D";
        } else {
            return "F";
        }
    }
    public void setDatabaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }
}
