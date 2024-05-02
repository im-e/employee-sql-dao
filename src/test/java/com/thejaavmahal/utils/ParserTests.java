package com.thejaavmahal.utils;

import com.thejaavmahal.employees.Employee;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ParserTests {

    @Test
    public void testGetEmployeesFromCSV() {
        ArrayList<String> employees = Parser.getEmployeesFromCSV();
        assertThat(employees, not(empty()));
    }

    @Test
    public void testConvertToDate() {
        Date date = Parser.convertToDate("9/21/1982");
        assertEquals(Date.valueOf("1982-09-21"), date);
    }

    @Test
    public void testParseUncheckedEmployeeData() {
        ArrayList<String> rawData = new ArrayList<>();
        rawData.add("123456,Mr,John,J,Doe,M,Doe@example.com,1/1/1990,1/1/2020,50000");
        List<Employee> employeeList = Parser.parseUncheckedEmployeeData(rawData);
        assertThat(employeeList, hasSize(1));
        Employee employee = employeeList.get(0);
        assertEquals(123456, employee.empId());
        assertEquals("Mr", employee.prefix());
        assertEquals("John", employee.firstName());
         assertEquals('J', employee.initial());
        assertEquals("Doe", employee.lastName());

        assertEquals('M', employee.gender());
        assertEquals("Doe@example.com", employee.email());
        // Add more assertions for other fields
    }

    // Add tests for other methods similarly

    @Test
    public void testParseEmployees() {
        // Create a sample list of employees
        List<Employee> employeeList = new ArrayList<>();
        Employee employee1 = new Employee(123456, "Mr", "John",'J',"Doe", 'M', "Doe@example.com", Date.valueOf("1990-01-01"), Date.valueOf("2020-01-01"), 50000);
        Employee employee2 = new Employee(234567, "Ms", "Jane",'S',"Smith", 'F', "Smith@example.com", Date.valueOf("1995-05-15"), Date.valueOf("2021-02-10"), 60000);
        employeeList.add(employee1);
        employeeList.add(employee2);

        List<Employee> parsedEmployees = Parser.parseEmployees(employeeList);

        // Assert that all employees passed through the validation
        assertThat(parsedEmployees, contains(employee1, employee2));
    }

    // Add tests for other methods similarly
}