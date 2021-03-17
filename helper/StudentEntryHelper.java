package helper;

import student.Student;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class will help us utilize the Student.java data class.
 * <br>
 * This class will define helper methods for us to perform basic
 * CRUD operations on the database.
 * <br>
 * We will create a method to:
 * 1. Add a new entry of a Student to the students table in the database.
 * 2. View all the students that are present in the students table in the database.
 * 3. Modify the entry of a particular student in the students table of the database.
 * 4. Delete the entry of a particular student from the students table of the database.
 */
public class StudentEntryHelper {

    /*
     * This is the name of the table in our database.
     * It has four attributes:
     * 1. st_id: to store the id of the student.
     * 2. st_name: to store the name of the student.
     * 3. course: to store the course name of student.
     * 4. branch: to store the branch name of student
     * */
    private static final String TABLE_NAME = "students";
    private static final String ID_COLUMN = "st_roll";

    private static final String NAME_COLUMN = "st_name";
    private static final String COURSE_COLUMN = "course";
    private static final String BRANCH_COLUMN = "branch";

    /*
     * This is the actual SQL query that will send the data from our Java program to the
     * SQL database.
     * The `?` here is called a parameter.
     * This is just a placeholder for the actual value in the SQL query.
     * Using the `?`, we can define the actual value later on when we are ready to execute the query.
     * */
    private static final String CREATE_STUDENT_QUERY =
            "INSERT INTO " + TABLE_NAME + " (st_roll,st_name,course, branch) VALUES (?,?, ?, ?);";

    // This SQL query will fetch all the records (entries) from the cities table.
    private static final String READ_ALL_STUDENTS_QUERY =
            "SELECT * FROM " + TABLE_NAME + ";";

    private static final String UPDATE_STUDENTS_QUERY =
            "UPDATE " + TABLE_NAME + " SET " + COURSE_COLUMN + " = ? WHERE " + NAME_COLUMN + " = ?;";

    private static final String DELETE_CITY_QUERY =
            "DELETE FROM " + TABLE_NAME + " WHERE " + NAME_COLUMN + " = ?;";

    /**
     * This method will create an entry for a new student in the students table of the database;
     *
     * @param connection The reference of the current connection to the database.
     * @param student    The values of the student we want to add, wrapped in a Student object.
     */
    public boolean createANewStudentInTheDatabase(Connection connection, Student student) {
        boolean isSuccess = false;
        try {
            PreparedStatement createStudentStatement = connection.prepareStatement(CREATE_STUDENT_QUERY);
            createStudentStatement.setInt(1, student.getStudentId());
            createStudentStatement.setString(2, student.getStudentName());
            createStudentStatement.setString(3, student.getCourse());
            createStudentStatement.setString(4, student.getBranch());
            createStudentStatement.executeUpdate();
            isSuccess = true;
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(
                    null,
                    "Sorry, we could not add the student \"" + student.getStudentName() + "\" to the database." +
                            "\n" + exception.getMessage(),
                    "ERROR 2",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return isSuccess;
    }

    /**
     * This method will read all the entries from the students table and print it
     * for the user.
     *
     * @param connection The reference of the current connection to the database.
     */
    public void printAllStudentsInDatabase(Connection connection) {
        try {
            PreparedStatement readAllStudentsStatement =
                    connection.prepareStatement(READ_ALL_STUDENTS_QUERY);
            ResultSet resultSet = readAllStudentsStatement.executeQuery();
            StringBuilder builder = new StringBuilder();
            while (resultSet.next()) {
                int studentId = resultSet.getInt(ID_COLUMN);
                String cityName = resultSet.getString(NAME_COLUMN);
                String isTraversed = resultSet.getString(COURSE_COLUMN);
                String kmsRequired = resultSet.getString(BRANCH_COLUMN);
                Student student = new Student(studentId, cityName, isTraversed, kmsRequired);
                builder.append(student.toString()).append("\n").append("-".repeat(150)).append("\n");
            }
            JTextArea textArea = new JTextArea(builder.toString());
            JScrollPane scrollPane = new JScrollPane(textArea);
            textArea.setLineWrap(true);
            scrollPane.setPreferredSize(new Dimension(1024, 400));
            JOptionPane.showMessageDialog(
                    null,
                    scrollPane,
                    "Students In The Database",
                    JOptionPane.PLAIN_MESSAGE
            );
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(
                    null,
                    "Sorry, could not read the values from the database.",
                    "ERROR 3",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void modifyStudentsInDatabase(Connection connection, int studentId, String inputStudentName, String inputCourseName, String inputBranchName) {
        try {
            PreparedStatement modifyStudentStatement =
                    connection.prepareStatement(UPDATE_STUDENTS_QUERY);

            modifyStudentStatement.setString(1, inputCourseName);
            modifyStudentStatement.setString(2, inputBranchName);
            modifyStudentStatement.executeUpdate();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(
                    null,
                    "Sorry, " + inputStudentName + " could not be updated.",
                    "ERROR 4",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void deleteStudentFromDatabase(Connection connection, String studentName) {
        try {
            PreparedStatement deleteCityStatement =
                    connection.prepareStatement(DELETE_CITY_QUERY);
            deleteCityStatement.setString(1, studentName);
            deleteCityStatement.executeUpdate();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(
                    null,
                    studentName + " could not be deleted from the database",
                    "ERROR 5",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
