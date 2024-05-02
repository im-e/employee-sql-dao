package com.thejaavmahal;

import com.thejaavmahal.employees.DatabasePopulator;
import com.thejaavmahal.employees.Employee;
import com.thejaavmahal.employees.EmployeeDAO;
import com.thejaavmahal.logging.LogHandler;
import com.thejaavmahal.utils.ConnectionManager;
import com.thejaavmahal.utils.Parser;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        Parser.init();
        DatabasePopulator.init();
        // User requests CRUD operations on DAO
        //EmployeeDAO employeeDAO = new EmployeeDAO();
        EmployeeDAO.queryFromField("employee_id", 991462);
        EmployeeDAO.queryFromField("employee_id", 114577);

        EmployeeDAO.deleteFromEmployees("employee_id", 114577);
        EmployeeDAO.updateEmployee(192501, "middle_initial", "I");
        EmployeeDAO.queryFromField("employee_id", 192501);

        Employee employee = new Employee(123456, "Mr.", "John",'J',
                "Doe", 'M', "Doe@example.com",
                Date.valueOf("1990-01-01"), Date.valueOf("2020-01-01"), 50000);

        EmployeeDAO.createEmployee(employee);
        EmployeeDAO.queryFromField("employee_id", 123456);



        EmployeeDAO.closeConnection();
    }
}
