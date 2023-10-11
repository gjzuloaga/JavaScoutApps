package files.javascoutapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private TextField searchField;

    @FXML
    private Label searchResult;

    private ObservableList<String> suggestions;

    private DatabaseManager databaseManager;


    public void initialize() {
        // Initialize the suggestions list
        suggestions = FXCollections.observableArrayList();

        // Create an instance of DatabaseManager and connect to the database
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.connect("D:\\DatabaseSoccer\\database.sqlite");

        // Retrieve all player names from the database and populate the suggestions list
        suggestions.addAll(databaseManager.getAllPlayerNames());
    }

    @FXML
    private void handleKeyRelease() {
        String searchTerm = searchField.getText();
        // Perform the autocomplete logic here
        ObservableList<String> matches = FXCollections.observableArrayList();
        for (String suggestion : suggestions) {
            if (suggestion.startsWith(searchTerm)) {
                matches.add(suggestion);
            }
        }
        if (!matches.isEmpty()) {
            searchResult.setText("Suggestions: " + String.join(", ", matches));
        } else {
            searchResult.setText("");
        }
    }

    @FXML
    private void handleSearch() {
        String searchTerm = searchField.getText();
        // Perform your search logic here
        // For demonstration purposes, let's just display the search term in the result label
        searchResult.setText("You searched for: " + searchTerm);
    }

    public void setDatabaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }
}