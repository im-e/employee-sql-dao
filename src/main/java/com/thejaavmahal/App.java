package com.thejaavmahal;


import com.thejaavmahal.employees.DatabasePopulator;
import com.thejaavmahal.employees.Employee;
import com.thejaavmahal.employees.EmployeeDAO;
import com.thejaavmahal.employees.EmployeeList;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class App {

    public static void main(String[] args) {
        // Setup Logger

        Employee employee = new Employee(1, "Mr.", "John", 'D', "Smith", 'M',
                "johndoe@example.com", Date.valueOf("2020-01-20"),
                Date.valueOf("2020-01-20"), 50000);
        EmployeeList.addEmployee(employee);
        Employee employee1 = new Employee(2, "Mr.", "Jimmy", 'E', "Brown", 'M',
                "jimmybrown@example.com", Date.valueOf("2020-01-20"),
                Date.valueOf("1999-10-23"), 30000);
        EmployeeList.addEmployee(employee1);



        DatabasePopulator dbp = new DatabasePopulator(ConnectionManager.getConnection());


        EmployeeDAO employeeDAO = new EmployeeDAO(ConnectionManager.getConnection());
        // Replace with your field name and value
        String fieldName = "employee_id";
        int fieldValue = 1;

        employeeDAO.queryFromField(fieldName, fieldValue);

        //dbp.addEmployeeToDatabase(employee);

        // Setup DB connection


        // Import CSV File


        // Parse CSV file


        // Populate the database (seeding)


        // User requests CRUD operations on DAO

    }
}
