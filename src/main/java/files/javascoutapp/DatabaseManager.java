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
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, player_api_id, birthday, height, weight FROM Player WHERE player_name = ?");
            preparedStatement.setString(1, playerName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                playerStats.put("ID", resultSet.getString("id"));
                playerStats.put("Player API ID", resultSet.getString("player_api_id"));
                playerStats.put("Birthday", resultSet.getString("birthday"));
                playerStats.put("Height", resultSet.getString("height"));
                playerStats.put("Weight", resultSet.getString("weight"));

                resultSet.close();
                preparedStatement.close();
            }
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
