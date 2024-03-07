package mainApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBConnection {
    // This static block is used to load the MySQL driver
    // This method is used to establish a connection to the database
	// The connection details are obtained from the Aiven Console
	
    public static Connection getConnection() throws SQLException {
    	String host, port, databaseName, userName, password;
        host = "myservice-stockx-rasmussen-stockx-ajs.a.aivencloud.com";
        port = "17817";
        userName = "avnadmin";
        password = "AVNS_uYYq-8I32N-sLAwgIO0";
        databaseName = "defaultdb";
        
        // Load the MySQL driver minimize errors in the code by allowing jdbc to handle the driver and input the connection details
        return DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + databaseName + "?sslmode=require", userName, password);
    }

    // This method is used to create the contacts table if it does not exist
    public static void createTableIfNotExists() {
        try (Connection connection = getConnection();
             // Create a prepared statement to execute SQL queries
             PreparedStatement statement = connection.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS contactlists (id INT AUTO_INCREMENT PRIMARY KEY, " +
                             "first_name VARCHAR(50) NOT NULL, last_name VARCHAR(50) NOT NULL, " +
                             "phone_number VARCHAR(15) NOT NULL, email_address VARCHAR(50) NOT NULL)")) {
        	// Execute the SQL query
        	// The table will be created if it does not exist
            statement.executeUpdate();
            //Print out the success message
        } catch (SQLException e) {
        	//Print out the error message
            e.printStackTrace();
        }
    }

    // This method is used to insert a new contact into the contacts table
    public static void insertContact(String firstName, String lastName, String phoneNumber, String emailAddress) {
    	// Try with resources to automatically close the connection
        try (Connection connection = getConnection();
             // Create a prepared statement to execute SQL queries
             PreparedStatement statement = connection.prepareStatement(
            		 // Insert the contact details into the contacts table
                     "INSERT INTO contactlists (first_name, last_name, phone_number, email_address) VALUES (?, ?, ?, ?)")) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, phoneNumber);
            statement.setString(4, emailAddress);

            // Execute the SQL query
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // This method is used to display all the contacts in the contacts table
    public static void displayContacts() {
    	// Try with resources to automatically close the connection
        try (Connection connection = getConnection();
        		// Create a prepared statement to execute SQL queries
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM contactlists");
        		// Execute the SQL query
             ResultSet resultSet = statement.executeQuery()) {

        	// Print out the contacts
            System.out.println("Contacts:");

            while (resultSet.next()) {
            	// Get the contact details from the contacts table
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String phoneNumber = resultSet.getString("phone_number");
                String emailAddress = resultSet.getString("email_address");
                
                // Print out the contact details
                System.out.println("ID: " + id +
                        ", First Name: " + firstName +
                        ", Last Name: " + lastName +
                        ", Phone Number: " + phoneNumber +
                        ", Email Address: " + emailAddress);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
