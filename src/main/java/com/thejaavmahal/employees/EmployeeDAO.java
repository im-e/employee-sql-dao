package com.thejaavmahal.employees;

import com.thejaavmahal.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// CRUD operations
public class EmployeeDAO {

    public static void main(String[] args) {
        EmployeeDAO employeeDAO = new EmployeeDAO();

        // Replace with your field name and value
        String fieldName = "employee_id";
        int fieldValue = 321432;

        ResultSet resultSet = employeeDAO.queryFromField(fieldName, fieldValue);
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
                    System.out.println("ID: " + id + " Prefix: " + prefix + ", First Name: " + firstName + ", Middle Initial: " + middleInitial + ", Last Name: " + lastName + ", Gender: " + gender);
                }
                // Close the result set
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Close the connection when done
                ConnectionManager.getInstance().closeConnection();
            }
        }
    }


    public ResultSet queryFromField(String fieldName, int fieldValue) {
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Get the connection from the ConnectionManager
            connection = ConnectionManager.getInstance().getConnection();

            // Prepare the SQL statement
            String query = "SELECT * FROM employees WHERE " + fieldName + " = ?";
            preparedStatement = connection.prepareStatement(query);

            // Set parameter for the field value
            preparedStatement.setInt(1, fieldValue); // Corrected here

            // Execute the query
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }
}