package mainApp;

import java.util.Scanner;

public class MainApp {
    private static final Scanner INP = new Scanner(System.in);

    
    public static void main(String[] args) {
        try {
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

            DBConnection.insertContact(nameF, nameL, phone, email);

            DBConnection.displayContacts();
        } finally {
            INP.close(); // Close the Scanner
        }
    }
}
