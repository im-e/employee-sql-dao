package com.thejaavmahal.employees;

import com.thejaavmahal.logging.LogHandler;
import com.thejaavmahal.utils.ConnectionManager;

import java.sql.*;
import java.util.Objects;
import java.util.logging.Logger;

// CRUD operations
public class EmployeeDAO {


    private final static Logger LOGGER = LogHandler.getLogger();
    private final static Connection CONNECTION = ConnectionManager.getConnection();

    public EmployeeDAO() {
        LOGGER.info("Starting Employee DAO");
    }

    public static void queryFromField(String fieldName, Object fieldValue) {
        LOGGER.config("Querying field: " + fieldName + " from " + fieldValue);
        ResultSet resultSet = null;
        try { // if u have it here
            // Prepare the SQL statement
            final String query = "SELECT * FROM employees WHERE " + fieldName + " = ?";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);

            // Set parameter for the field value
            if (fieldValue instanceof Integer) {
                preparedStatement.setInt(1, (Integer) fieldValue);
            } else if (fieldValue instanceof String) {
                preparedStatement.setString(1, (String) fieldValue);
            } // Add more conditions for other data types if needed
            else if (fieldValue instanceof Character) {
                preparedStatement.setString(1, String.valueOf(fieldValue));
            }// Add more conditions for other data types if needed
            else if (fieldValue instanceof Date) {
                preparedStatement.setDate(1, (Date) fieldValue);
            }


            // Execute the query
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            LOGGER.info("Error while executing query: " + e.getMessage());
        }
        printResultSet(resultSet);
    }

    public static void deleteFromEmployees(String fieldName, Object fieldValue) {
        LOGGER.config("Deleting employee from field: " + fieldName + " from " + fieldValue);
        final String selectQuery = "SELECT * FROM employees WHERE " + fieldName + " = ?";
        try (PreparedStatement selectStatement = CONNECTION.prepareStatement(selectQuery)) {
            // Set parameter for the field value
            if (fieldValue instanceof Integer) {
                selectStatement.setInt(1, (Integer) fieldValue);
            } else if (fieldValue instanceof String) {
                selectStatement.setString(1, (String) fieldValue);
            } else if (fieldValue instanceof Character) {
                selectStatement.setString(1, String.valueOf(fieldValue));
            } else if (fieldValue instanceof Date) {
                selectStatement.setDate(1, (Date) fieldValue);
            }
            // Execute the SELECT query
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                // Print details of the selected row (if found)
                if (resultSet.next()) {
                    int id = resultSet.getInt("employee_id");
                    String prefix = resultSet.getString("prefix");
                    String firstName = resultSet.getString("first_name");
                    String middleInitial = resultSet.getString("middle_initial");
                    String lastName = resultSet.getString("last_name");
                    String gender = resultSet.getString("gender");
                    // Print details of the row before deleting
                    LOGGER.info("Row to be deleted: ID=" + id + ", Prefix=" + prefix + ", First Name=" + firstName + ", Middle Initial=" + middleInitial + ", Last Name=" + lastName + ", Gender=" + gender);
                } else {
                    LOGGER.info("No matching row found for deletion.");
                    return; // No need to proceed with deletion if no row found
                }
            }
        }
        catch (SQLException e) {
            LOGGER.info("Error while executing query: " + e.getMessage());
        }
        // Prepare the SQL statement for DELETE
        final String deleteQuery = "DELETE FROM employees WHERE " + fieldName + " = ?";
        try (PreparedStatement deleteStatement = CONNECTION.prepareStatement(deleteQuery)) {
            // Set parameter for the field value (same as in SELECT)
            if (fieldValue instanceof Integer) {
                deleteStatement.setInt(1, (Integer) fieldValue);
            } else if (fieldValue instanceof String) {
                deleteStatement.setString(1, (String) fieldValue);
            } else if (fieldValue instanceof Character) {
                deleteStatement.setString(1, String.valueOf(fieldValue));
            } else if (fieldValue instanceof Date) {
                deleteStatement.setDate(1, (Date) fieldValue);
            }
            // Execute the DELETE statement
            int rowsDeleted = deleteStatement.executeUpdate();
            LOGGER.info(rowsDeleted + " row(s) deleted");
        } catch (SQLException e) {
            LOGGER.severe("Error while executing delete query: " + e.getMessage());
        }

    }

    private static void printResultSet(ResultSet resultSet) {
        LOGGER.fine("Printing result set");
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
                    LOGGER.info("ID: " + id + " Prefix: " + prefix + ", First Name: " + firstName + ", Middle Initial: " + middleInitial + ", Last Name: " + lastName + ", Gender: " + gender);
                }
                // Close the result set
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.severe("Error while executing query: " + e.getMessage());
            }
        }
    }

    public static void createEmployee(Employee employee) {
        LOGGER.config("Creating employee: " + employee.empId());
        DatabasePopulator.addEmployeeToDatabase(employee);
    }

    public static void updateEmployee(int idToUpdate, String fieldNameToUpdate, Object fieldValueToUpdate) {
        LOGGER.config("Updating employee: " + idToUpdate +" - " + fieldNameToUpdate + " to " + fieldValueToUpdate);
        try{
            // Prepare the SQL statement
            final String query = "UPDATE employees SET " + fieldNameToUpdate + " = ? " +
                    " WHERE employee_id = " + idToUpdate + ";";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);
            // Set parameter for the field value
            if (fieldValueToUpdate instanceof Integer) {
                preparedStatement.setInt(1, (Integer) fieldValueToUpdate);
            } else if (fieldValueToUpdate instanceof String) {
                preparedStatement.setString(1, (String) fieldValueToUpdate);
            } // Add more conditions for other data types if needed
            else if (fieldValueToUpdate instanceof Character) {
                preparedStatement.setString(1, String.valueOf(fieldValueToUpdate));
            }// Add more conditions for other data types if needed
            else if (fieldValueToUpdate instanceof Date) {
                preparedStatement.setDate(1, (Date) fieldValueToUpdate);
            }
            // Execute the query
            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            LOGGER.warning("Error while executing query in updateEmployee: " + e.getMessage());
        }


    }

    public static void closeConnection()
    {
        ConnectionManager.closeConnection();
    }

}
