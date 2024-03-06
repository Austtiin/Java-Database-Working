
// This is the main class that connects to the MySQL database using the JDBC driver.
// The getConnection method is used to establish a connection to the database.
package mainApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MainApp {
    private static final Scanner INP = new Scanner(System.in);

    // Database connection details
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://myservice-stockx-rasmussen-stockx-ajs.a.aivencloud.com:3306/contact_database?useSSL=true&requireSSL=true";
    private static final String DB_USER = "avnadmin";
    private static final String DB_PASSWORD = "AVNS_uYYq-8I32N-sLAwgIO0";
    private static final String SSL_CA_PATH = "C:\\Users\\Austin\\Downloads\\ca.pem";

    public static void main(String[] args) {
        try {
            createTableIfNotExists(); // Create the table if it does not exist

            System.out.println("Welcome to the contact database! Please enter the following details to add a new contact, they will be displayed after:");

            System.out.println("First Name: ");
            String nameF = INP.nextLine();

            System.out.println("Last Name:");
            String nameL = INP.nextLine();

            System.out.println("Phone Number:");
            String phone = INP.nextLine();

            System.out.println("Email Address:");
            String email = INP.nextLine();

            insertContact(nameF, nameL, phone, email);

            displayContacts();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            INP.close(); // Close the Scanner
        }
    }

    // This method is used to establish a connection to the database
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
    }

    // This method is used to create the contacts table if it does not exist
    private static void createTableIfNotExists() {
        try (Connection connection = getConnection();
             // Create a prepared statement to execute SQL queries
             PreparedStatement statement = connection.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS contacts (id INT AUTO_INCREMENT PRIMARY KEY, " +
                             "first_name VARCHAR(50) NOT NULL, last_name VARCHAR(50) NOT NULL, " +
                             "phone_number VARCHAR(15) NOT NULL, email_address VARCHAR(50) NOT NULL)")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // This method is used to insert a new contact into the contacts table
    private static void insertContact(String firstName, String lastName, String phoneNumber, String emailAddress) {
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
    private static void displayContacts() {
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