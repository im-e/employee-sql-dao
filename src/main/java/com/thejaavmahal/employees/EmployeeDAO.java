package com.thejaavmahal.employees;

import com.thejaavmahal.ConnectionManager;
import com.thejaavmahal.logging.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// CRUD operations
public class EmployeeDAO {

    Connection connection;

    public EmployeeDAO(Connection connection) {
        Log.info("Starting Employee DAO");
        this.connection = connection;
    }

    public ResultSet queryFromField(String fieldName, int fieldValue) {
        ResultSet resultSet = null;
        try {
            // Prepare the SQL statement
            final String query = "SELECT * FROM employees WHERE " + fieldName + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set parameter for the field value
            preparedStatement.setInt(1, fieldValue); // Corrected here

            // Execute the query
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            Log.info("Error while executing query: " + e.getMessage());
        }
        printResultSet(resultSet);
        return resultSet;
    }

    private void printResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                // Iterate through the result set
                while (resultSet.next()) {
                    // Retrieve data from the result set
                    int id = resultSet.getInt("employee_id");
                    String prefix = resultSet.getString("prefix");
                    String firstName = resultSet.getString("first_name");
                    String middleInitial = resultSet.getString("middle_initial");
                    String lastName = resultSet.getString("last_name");
                    String gender = resultSet.getString("gender");
                    // Process retrieved data here
                    Log.info("ID: " + id + " Prefix: " + prefix + ", First Name: " + firstName + ", Middle Initial: " + middleInitial + ", Last Name: " + lastName + ", Gender: " + gender);
                }
                // Close the result set
                resultSet.close();
            } catch (SQLException e) {
                Log.severe("Error while executing query: " + e.getMessage());
            } finally {
                // Close the connection when done
                ConnectionManager.closeConnection();
            }
        }
    }
}