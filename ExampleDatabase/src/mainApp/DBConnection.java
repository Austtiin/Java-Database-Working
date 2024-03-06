// This file is used to connect to the database and retrieve data from it.


package mainApp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBConnection {
    public static void main(String[] args) {
    	// Establish a connection to the database
        try (Connection connection = MainApp.getConnection();
        		// Create a statement object to execute SQL queries
             Statement statement = connection.createStatement();
        		// Execute the query and store the results in a ResultSet object
             ResultSet resultSet = statement.executeQuery("SELECT * FROM your_table")) {

        	
        		// Iterate through the result set and print the data
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                // Process other columns as needed
                System.out.println("ID: " + id + ", Name: " + name);
            }
            
            // Close the connection
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}