// This is the main class of the application. It will prompt the user to enter contact details, then display all contacts in the database. If the table does not exist, it will be created.

// The database connection details are obtained from the Aiven Console. The connection details are used to establish a connection to the database.
package mainApp;

import java.util.Scanner;

public class MainApp {
	//Scanner to get user input
    private static final Scanner INP = new Scanner(System.in);

    
    public static void main(String[] args) {
        try {
        	//Print out the classpath to verify installation of the JDBC driver
            System.out.println("Classpath: " + System.getProperty("java.class.path"));
            DBConnection.createTableIfNotExists(); // Create the table if it does not exist

            System.out.println("Welcome to the contact database! Please enter the following details to add a new contact, they will be displayed after:");

            System.out.println("First Name: ");
            String nameF = INP.nextLine();

            System.out.println("Last Name:");
            String nameL = INP.nextLine();

            System.out.println("Phone Number:");
            String phone = INP.nextLine();

            System.out.println("Email Address:");
            String email = INP.nextLine();

            	//Insert the contact details into the database
            DBConnection.insertContact(nameF, nameL, phone, email);

            	//Display all the contacts in the database
            DBConnection.displayContacts();
        } finally {
            INP.close(); // Close the Scanner
        }
    }
}
