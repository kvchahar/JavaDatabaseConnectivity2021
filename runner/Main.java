package my_data.runner;

import databaseconnection.DatabaseConnection;
import student.Student;

import javax.swing.*;

public class Main {

    public static final int CREATE_NEW_STUDENT = 1;
    public static final int PRINT_ALL_STUDENTS = 2;
    public static final int MODIFY_STUDENT = 3;
    public static final int DELETE_STUDENT = 4;
    public static final int EXIT = 5;

    private static helper.StudentEntryHelper helper = null;

    public static void main(String[] args) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        // attempting to connect to a database
        databaseConnection.connectToDatabase();
        helper = new helper.StudentEntryHelper();
        while (true) {
            int choice = Integer.parseInt(JOptionPane.showInputDialog(
                    null,
                    """
                            Enter 1 to add a new student into the database.
                            Enter 2 to view all the student currently in the database.
                            Enter 3 to modify a particular student in the database.
                            Enter 4 to delete a particular student from the database.
                            Enter 5 to exit.""",
                    "GLA JDBC GAME MENU",
                    JOptionPane.PLAIN_MESSAGE
            ));
            switch (choice) {
                case CREATE_NEW_STUDENT -> {
                    helper.createANewStudentInTheDatabase(
                            DatabaseConnection.getConnection(),
                            createNewStudent()
                    );
                    JOptionPane.showMessageDialog(
                            null,
                            "Student Added To The Database",
                            "SUCCESSFULL",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
                case PRINT_ALL_STUDENTS -> printAllStudents();
                case MODIFY_STUDENT -> modifyStudent();
                case DELETE_STUDENT -> deleteStudent();
                case EXIT -> System.exit(0);
            }
        }
    }

    private static void deleteStudent() {
        String cityName = inputStudentName();
        helper.deleteStudentFromDatabase(
                DatabaseConnection.getConnection(),
                cityName
        );
    }

    private static void modifyStudent() {
        helper.modifyStudentsInDatabase(
                DatabaseConnection.getConnection(),
                inputStudentId(),
                inputCourseName(),
                inputStudentName(),
                inputBranchName()
        );
    }

    private static void printAllStudents() {
        helper.printAllStudentsInDatabase(DatabaseConnection.getConnection());
    }

    private static Student createNewStudent() {
        /*
         * To add an entry for a new student into the students table in the database,
         * we need to do the following:
         * 1. Ask the user to input all the values of the attributes of a Student-type.
         * 2. Collect all the values together into a student object.
         * 3. Send the city object to the `createANewStudentInTheDatabase()` method
         * of the `StudentEntryHelper.java` class.
         * */
        int studentId = inputStudentId();
        String studentName = inputStudentName();
        String courseName = inputCourseName();
        String branchName = inputBranchName();
        return new Student(studentId, studentName, courseName, branchName);
    }

    private static int inputStudentId() {
        return Integer.parseInt(JOptionPane.showInputDialog(
                null,
                "Please enter the student id",
                "Enter Student ID",
                JOptionPane.PLAIN_MESSAGE
        ));
    }

    private static String inputBranchName() {
        return JOptionPane.showInputDialog(
                null,
                "Please enter the Branch Name.",
                "Enter Kilometers Required",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    private static String inputCourseName() {
        return JOptionPane.showInputDialog(
                null,
                "Please enter the course name: ",
                "Enter Course Name",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    private static String inputStudentName() {
        return JOptionPane.showInputDialog(
                null,
                "Please enter the name of the student.",
                "Enter Student Name",
                JOptionPane.PLAIN_MESSAGE
        );
    }
}
