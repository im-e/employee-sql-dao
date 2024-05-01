package com.thejaavmahal.employees;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Logger;
import com.thejaavmahal.utils.ConnectionManager;
import com.thejaavmahal.utils.Parser;

public class DatabasePopulator implements PopulatorStatements {

    private static final Connection connection = ConnectionManager.getConnection();
    private static final Logger LOGGER = Logger.getLogger("Logger");

    public static void init(){
        LOGGER.info("Initializing Database Populator...");
        deleteEmployeesFromDatabase();
        populateEmployees(Parser.getEmployees());
        LOGGER.config("Deleting parser list as database is populated");
        Parser.deleteEmployees();
        LOGGER.info("Database Populator Initialized.");
    }

    private static void populateEmployees(List<Employee> employees){
        LOGGER.info("Populating employees from EmployeeList...");
        for(Employee employee : employees){
            addEmployeeToDatabase(employee);
        }
        LOGGER.info("Employees populated successfully.");
    }

    private static void addEmployeeToDatabase(Employee employee) {
        //String sql = "INSERT INTO employees (employee_id, prefix, first_name, middle_initial, last_name, gender, email, date_of_birth, date_of_joining, salary) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement populateStatement = connection.prepareStatement(INSERT)) {

            populateStatement.setInt(1, employee.empId());  // employee_id
            populateStatement.setString(2, employee.prefix());  // prefix
            populateStatement.setString(3, employee.firstName());  // first_name
            populateStatement.setString(4, String.valueOf(employee.initial()));  // middle_initial
            populateStatement.setString(5, employee.lastName());  // last_name
            populateStatement.setString(6, String.valueOf(employee.gender()));  // gender
            populateStatement.setString(7, employee.email());  // email
            populateStatement.setDate(8, employee.dateOfBirth());  // date_of_birth
            populateStatement.setDate(9, employee.dateOfJoin());  // date_of_joining
            populateStatement.setInt(10, employee.salary());  // salary

            populateStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.warning("Error when populating the current employee: " + e.getMessage());
        }
    }

    public static void deleteEmployeesFromDatabase() {
        LOGGER.info("Clearing the database...");
        //String sql = "DELETE FROM employees WHERE employee_id > 0";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            LOGGER.severe("Error when deleting employees from table: " + e.getMessage());
        }
    }


}
