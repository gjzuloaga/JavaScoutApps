package files.javascoutapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.ResultSetMetaData;

public class DatabaseManager {
    private Connection connection;

    public void connect(String databaseFilePath) {
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            // Connect to the database
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFilePath);
            System.out.println("Connected to the database");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getAllPlayerNames() {
        List<String> playerNames = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT player_name FROM Player");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String playerName = resultSet.getString("player_name");
                playerNames.add(playerName);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return playerNames;
    }

    public Map<String, String> getPlayerStats(String playerName) {
        Map<String, String> playerStats = new HashMap<>();

        try {
            // First, get the player details from the Player table
            PreparedStatement playerDetailsStatement = connection.prepareStatement("SELECT id, player_api_id, birthday, height, weight FROM Player WHERE player_name = ?");
            playerDetailsStatement.setString(1, playerName);
            ResultSet playerDetailsResultSet = playerDetailsStatement.executeQuery();

            if (playerDetailsResultSet.next()) {
                playerStats.put("ID", playerDetailsResultSet.getString("id"));
                playerStats.put("Birthday", playerDetailsResultSet.getString("birthday"));
                playerStats.put("Height", playerDetailsResultSet.getString("height"));
                playerStats.put("Weight", playerDetailsResultSet.getString("weight"));

                // Now, get all attributes from the Player_Attributes table for the specific player_api_id
                PreparedStatement playerAttributesStatement = connection.prepareStatement("SELECT * FROM Player_Attributes WHERE player_api_id = ?");
                playerAttributesStatement.setString(1, playerDetailsResultSet.getString("player_api_id"));
                ResultSet playerAttributesResultSet = playerAttributesStatement.executeQuery();

                // Check if any attributes were found
                if (playerAttributesResultSet.next()) {
                    ResultSetMetaData metaData = playerAttributesResultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    // Iterate through all columns and add them to the playerStats map
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        String columnValue = playerAttributesResultSet.getString(i);
                        playerStats.put(columnName, columnValue);
                    }
                }

                playerAttributesResultSet.close();
                playerAttributesStatement.close();
            }

            playerDetailsResultSet.close();
            playerDetailsStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return playerStats;
    }

    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Disconnected from the database");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
