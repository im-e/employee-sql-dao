package com.thejaavmahal.employees;

import com.thejaavmahal.logging.LogHandler;
import com.thejaavmahal.utils.ConnectionManager;

import java.sql.*;
import java.util.Objects;
import java.util.logging.Logger;

// CRUD operations
public class EmployeeDAO {


    private final static Logger LOGGER = LogHandler.getLogger();

    public EmployeeDAO() {
        LOGGER.info("Starting Employee DAO");
    }

    public ResultSet queryFromField(String fieldName, Object fieldValue) {
        final Connection connection = ConnectionManager.getConnection();
        ResultSet resultSet = null;
        try { // if u have it here
            // Prepare the SQL statement
            final String query = "SELECT * FROM employees WHERE " + fieldName + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);


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
            } catch(SQLException e){
                LOGGER.info("Error while executing query: " + e.getMessage());
            }
            printResultSet(resultSet);
            return resultSet;
        }

       public void deleteFromEmployees(String fieldName, Object fieldValue) {
           try (Connection connection = ConnectionManager.getConnection()) {
               // Prepare the SQL statement for SELECT
               final String selectQuery = "SELECT * FROM employees WHERE " + fieldName + " = ?";
               try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
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
                           System.out.println("Row to be deleted: ID=" + id + ", Prefix=" + prefix + ", First Name=" + firstName + ", Middle Initial=" + middleInitial + ", Last Name=" + lastName + ", Gender=" + gender);
                       } else {
                           System.out.println("No matching row found for deletion.");
                           return; // No need to proceed with deletion if no row found
                       }
                   }
               }
               // Prepare the SQL statement for DELETE
               final String deleteQuery = "DELETE FROM employees WHERE " + fieldName + " = ?";
               try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
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
                   System.out.println(rowsDeleted + " row(s) deleted");
               }
           } catch (SQLException e) {
               LOGGER.severe("Error while executing delete query: " + e.getMessage());
           }
       }


           private void printResultSet (ResultSet resultSet){
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
                } finally {
                    // Close the connection when done
                    try {
                        ConnectionManager.closeConnection();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void createEmployee(Employee employee) {
            DatabasePopulator.addEmployeeToDatabase(employee);
        }

        public void updateEmployee(int idToUpdate, String fieldNameToUpdate, Object fieldValueToUpdate) {
            final Connection connection = ConnectionManager.getConnection();
            ResultSet resultSet = null;
            try { // if u have it here
                // Prepare the SQL statement
                final String query = "UPDATE employees SET " + fieldNameToUpdate + " = ? " +
                        " WHERE employee_id = " + idToUpdate + ";";
                PreparedStatement preparedStatement = connection.prepareStatement(query);


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
            } catch(SQLException e){
                LOGGER.info("Error while executing query: " + e.getMessage());
            }
        }

    }
