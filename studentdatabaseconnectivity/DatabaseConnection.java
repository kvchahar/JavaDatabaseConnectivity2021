package databaseconnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class will create a new connection to the database.
 * <br>
 * This class is extremely important for JDBC implementation.
 * <br>
 */
public class DatabaseConnection {

    /**
     * This String constant contains the URL to the database on our localhost server
     * provided by XAMPP.
     * <br>
     * I would just like to take a minute to point out that using XAMPP is not at all
     * extremely mandatory, nor is it the only server emulator out there. We have
     * XAMPP, WAMP, MAMP, and many more.
     * <br>
     * XAMPP is just the only I believe is most easy-to-install and configure for a
     * beginner.
     * <br>
     * Also, when working in a real-world environment, or as we developers like to call it
     * 'a production environment', your actual database might be hosted on a live-server.
     * You can still use the same code to connect to it, by just changing the URL and other
     * parameters for the database accordingly.
     */
    private static final String URL = "jdbc:mysql://localhost:3306/my_data";

    /**
     * This is the username of the admin of the database.
     * Usually, on server emulators like XAMPP, it is defaulted to 'root'.
     */
    private static final String USERNAME = "root";

    /**
     * This is the password of the admin of the database.
     * If you are using XAMPP like me, then the default password is just going to be blank.
     * IF you are using another emulator like WAMP or MAMP, then you need to consult your
     * configuration or the documentation provided by the software vendor for the password.
     */
    private static final String PASSWORD = "";

    /**
     * This connection variable is required to maintain
     * the reference of a connection to the actual database.
     */
    private static Connection connection = null;

    public static Connection getConnection() {
        return connection;
    }

    /**
     * This method will create the actual connection to our database.
     * We just need to initialize the connection variable inside of it.
     *
     * @return whether the connection was successfully created.
     */
    public boolean connectToDatabase() {
        boolean wasConnectionSuccessful = false;
        try {
            /* We are using the static getConnection method of the
             * DriverManager class to initialize the connection variable
             * because the connection variable of the `java.sql.Connection` interface,
             * and we cannot initialize the reference variables of any interface
             * by using the Constructor.
             * If you check the return type of the getConnection method here,
             * you will find that it `Connection` only.
             * */
            connection = DriverManager.getConnection(
                    URL,
                    USERNAME,
                    PASSWORD);
            wasConnectionSuccessful = true;
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(
                    null,
                    "There was an error connecting to the database." +
                            "\nPlease check your username, password and URL.",
                    "ERROR 1",
                    JOptionPane.ERROR_MESSAGE
            );
            exception.printStackTrace();
        } finally {
            printConnectionStatus();
        }
        return wasConnectionSuccessful;
    }

    /**
     * This method will print the current status of the connection to the database.
     */
    private void printConnectionStatus() {
        if (getConnection() != null) {
            printConnectionActive();
        } else {
            printConnectionInactive();
        }
    }

    private void printConnectionInactive() {
        try {
            String userName = getConnection().getMetaData().getUserName();
            JOptionPane.showMessageDialog(
                    null,
                    "Hi, " + userName + " sorry, we could not connect to the database!" +
                            "Please check your username, password and url.",
                    "Failed To Connected To The Database",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (SQLException ignored) {

        }

    }


    private void printConnectionActive() {
        try {
            DatabaseMetaData metaData = getConnection().getMetaData();
            String databaseProductName = metaData.getDatabaseProductName();
            String userName = metaData.getUserName().split("@")[0];
            JOptionPane.showMessageDialog(
                    null,
                    "Hi " + userName + ", the connection to the " + databaseProductName + " database is now active.",
                    "Successfully Connected To The Database",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (SQLException ignored) {

        }
    }

}
