
// This is the main class that connects to the MySQL database using the JDBC driver.
// The getConnection method is used to establish a connection to the database.


package mainApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainApp {
	
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://myservice-stockx-rasmussen-stockx-ajs.a.aivencloud.com:3306/your_database_name?useSSL=true&requireSSL=true";
    private static final String DB_USER = "avnadmin";
    private static final String DB_PASSWORD = "AVNS_uYYq-8I32N-sLAwgIO0";
    private static final String SSL_CA_PATH = "C:\\Users\\Austin\\Downloads\\ca.pem";

    public static Connection getConnection() throws SQLException {
        try {
        	// Load the MySQL JDBC driver
            Class.forName(JDBC_DRIVER);
            // Set the SSL certificate path
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            
        } catch (ClassNotFoundException e) {
            // Throw an exception if the driver is not found
        	throw new SQLException("MySQL JDBC Driver failed to load", e);
        }
    }
}