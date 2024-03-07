package mainApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBConnection {
    // Database connection details
	

    // This method is used to establish a connection to the database
    public static Connection getConnection() throws SQLException {
    	String host, port, databaseName, userName, password;
        host = "myservice-stockx-rasmussen-stockx-ajs.a.aivencloud.com";
        port = "17817";
        userName = "avnadmin";
        password = "AVNS_uYYq-8I32N-sLAwgIO0";
        databaseName = "defaultdb";
        
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
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // This method is used to insert a new contact into the contacts table
    public static void insertContact(String firstName, String lastName, String phoneNumber, String emailAddress) {
        try (Connection connection = getConnection();
             // Create a prepared statement to execute SQL queries
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO contacts (first_name, last_name, phone_number, email_address) VALUES (?, ?, ?, ?)")) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, phoneNumber);
            statement.setString(4, emailAddress);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // This method is used to display all the contacts in the contacts table
    public static void displayContacts() {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM contacts");
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("Contacts:");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String phoneNumber = resultSet.getString("phone_number");
                String emailAddress = resultSet.getString("email_address");

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
