package mainApp;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainApp {
    public static void main(String[] args) {
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM your_table")) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                // Process other columns as needed
                System.out.println("ID: " + id + ", Name: " + name);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}