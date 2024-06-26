package com.thejaavmahal;

import com.thejaavmahal.employees.DatabasePopulator;
import com.thejaavmahal.employees.Employee;
import com.thejaavmahal.employees.EmployeeDAO;
import com.thejaavmahal.utils.Parser;

import java.sql.Date;

public class App {
    public static void main(String[] args) {
        Parser.init();
        DatabasePopulator.init();

        EmployeeDAO.queryFromField("employee_id", 991462);

        EmployeeDAO.queryFromField("employee_id", 114577);
        EmployeeDAO.deleteEmployeeFromFieldWithValue("employee_id", 114577);

        EmployeeDAO.queryFromField("first_name", "Jason");

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
