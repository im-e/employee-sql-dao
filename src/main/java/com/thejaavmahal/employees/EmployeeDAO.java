package com.thejaavmahal.employees;

import com.thejaavmahal.logging.LogHandler;
import com.thejaavmahal.logging.ResultHandler;
import com.thejaavmahal.utils.ConnectionManager;

import java.sql.*;
import java.util.logging.Logger;

// CRUD operations
public class EmployeeDAO implements DAOStatements{


    private final static Logger LOGGER = LogHandler.getLogger();
    private final static Logger RESULT = ResultHandler.getResultLogger();
    private final static Connection CONNECTION = ConnectionManager.getConnection();

    public EmployeeDAO() {
        LOGGER.info("Starting Employee DAO");
    }


    public static PreparedStatement setStatementFromValue(String query, Object fieldValue) {
        try {
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);
            if (fieldValue instanceof Integer) {
                preparedStatement.setInt(1, (Integer) fieldValue);
            } else if (fieldValue instanceof String) {
                preparedStatement.setString(1, (String) fieldValue);
            }
            else if (fieldValue instanceof Character) {
                preparedStatement.setString(1, String.valueOf(fieldValue));
            }
            else if (fieldValue instanceof Date) {
                preparedStatement.setDate(1, (Date) fieldValue);
            }
            return preparedStatement;
        } catch (SQLException e) {
            LOGGER.warning("Exception when setting statement from value: " + e.getMessage());
            return null;
        }
    }

    public static ResultSet executeSelectQuery(String fieldName, Object fieldValue) {
        ResultSet resultSet = null;
        try {
            final String query = "SELECT * FROM employees WHERE " + fieldName + " = ?";
            PreparedStatement preparedStatement = setStatementFromValue(query,fieldValue);
            assert preparedStatement != null;
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            LOGGER.info("Error while executing query: " + e.getMessage());
            return null;
        }
    }


    public static void queryFromField(String fieldName, Object fieldValue) {
        RESULT.config("Searching employees with: " + fieldName + " -> " + fieldValue + "\n");
        ResultSet resultSet = executeSelectQuery(fieldName, fieldValue);
        try{
            if (resultSet != null) {
                int counter = 0;
                while (resultSet.next()) {
                    counter++;
                    String result = resultSetToString(resultSet);
                    RESULT.info(result);
                }
                if(counter != 0) RESULT.warning(counter + " Employee(s) found.");
                else RESULT.severe("No employees found.");
                resultSet.close();
            }
        }catch (SQLException e) {
            LOGGER.info("Error while printing result set: " + e.getMessage());
        }
    }

    public static void deleteEmployeeFromFieldWithValue(String fieldName, Object fieldValue) {
        RESULT.config("Deleting employee: " + fieldName + " from " + fieldValue + "\n");
        ResultSet resultSet = executeSelectQuery(fieldName, fieldValue);

        try{
            assert resultSet != null;
            if (resultSet.next()) {
                String deletedEmployee = resultSetToString(resultSet);
                // Print details of the row before deleting
                RESULT.warning("Record to be deleted: " + deletedEmployee);
            } else {
                LOGGER.info("No matching row found for deletion.");
            }

            final String deleteQuery = "DELETE FROM employees WHERE " + fieldName + " = ?";
            PreparedStatement deleteStatement = setStatementFromValue(deleteQuery, fieldValue);
            assert deleteStatement != null;
            int rowsDeleted = deleteStatement.executeUpdate();
            RESULT.severe(rowsDeleted + " record(s) deleted");
        }
        catch (SQLException e) {
            LOGGER.info("Error while deleting record: " + e.getMessage());
        }

    }


    public static void createEmployee(Employee employee) {
        RESULT.config("Creating employee: " + employee.empId()+ "\n");
        DatabasePopulator.addEmployeeToDatabase(employee);
    }

    public static void updateEmployee(int idToUpdate, String fieldNameToUpdate, Object fieldValueToUpdate) {
        RESULT.config("Updating employee of id: " + idToUpdate +" | updating " + fieldNameToUpdate + " to " + fieldValueToUpdate + "\n");
        try{
            // Prepare the SQL statement
            final String query = "UPDATE employees SET " + fieldNameToUpdate + " = ? " +
                    " WHERE employee_id = " + idToUpdate + ";";

            PreparedStatement preparedStatement = setStatementFromValue(query,fieldValueToUpdate);
            assert preparedStatement != null;
            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            LOGGER.warning("Error while executing query in updateEmployee: " + e.getMessage());
        }
    }


    public static String resultSetToString(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("employee_id");
            String prefix = resultSet.getString("prefix");
            String firstName = resultSet.getString("first_name");
            String middleInitial = resultSet.getString("middle_initial");
            String lastName = resultSet.getString("last_name");
            String gender = resultSet.getString("gender");
            String email = resultSet.getString("email");
            String dateOfBirth = resultSet.getString("date_of_birth");
            String dateOfJoin = resultSet.getString("date_of_joining");
            int salary = resultSet.getInt("salary");
            return  "Employee: " + prefix + " " + firstName + " " + lastName + '\n' +
                    ". Employee Id => " + id + '\n' +
                    ". Employee Title => " + prefix + '\n' +
                    ". Employee First Name => " + firstName + '\n' +
                    ". Employee Middle Initial => " + middleInitial + '\n' +
                    ". Employee Last Name => " + lastName + '\n' +
                    ". Employee Gender => " + gender + '\n' +
                    ". Employee Email Address => " + email + '\n' +
                    ". Employee Date of Birth (YYYY-MM-DD) => " + dateOfBirth + '\n' +
                    ". Employee Hire Date (YYYY-MM-DD) => " + dateOfJoin  + '\n' +
                    ". Employee Salary => " + "£" + salary + "\n";
        }
        catch (SQLException e)
        {
            LOGGER.warning("Error while retrieving data from result set: " + e.getMessage());
            return "";
        }

    }

    public static void closeConnection()
    {
        ConnectionManager.closeConnection();
    }

}
