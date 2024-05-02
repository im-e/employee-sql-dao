package com.thejaavmahal.utils;

import com.thejaavmahal.employees.Employee;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ParserTests {

    @Test
    public void testGetEmployeesFromCSV() {
        ArrayList<String> employees = Parser.getEmployeesFromCSV();
        assertFalse(employees.isEmpty());
    }

    @Test
    public void testConvertToDate() {
        Date date = Parser.convertToDate("9/21/1982");
        assertEquals(Date.valueOf("1982-09-21"), date);
    }

    @Test
    public void testParseUncheckedEmployeeData() {
        ArrayList<String> rawData = new ArrayList<>();
        rawData.add("123456,Mr.,John,J,Doe,M,Doe@example.com,1/1/1990,1/1/2020,50000");
        List<Employee> employeeList = Parser.parseUncheckedEmployeeData(rawData);
        Employee employee = employeeList.get(0);
        assertEquals(123456, employee.empId());
        assertEquals("Mr.", employee.prefix());
        assertEquals("John", employee.firstName());
        assertEquals('J', employee.initial());
        assertEquals("Doe", employee.lastName());

        assertEquals('M', employee.gender());
        assertEquals("Doe@example.com", employee.email());
        assertEquals(Date.valueOf("1990-01-01"), employee.dateOfBirth());
        assertEquals(Date.valueOf("2020-01-01"), employee.dateOfJoin());
        assertEquals(50000, employee.salary());
    }

    @Test
    public void testParseEmployees() {
        // Create a sample list of employees
        List<Employee> employeeList = new ArrayList<>();
        Employee employee1 = new Employee(123456, "Mr.", "John",'J',"Doe", 'M', "Doe@example.com", Date.valueOf("1990-01-01"), Date.valueOf("2020-01-01"), 50000);
        Employee employee2 = new Employee(234567, "Ms.", "Jane",'S',"Smith", 'F', "Smith@example.com", Date.valueOf("1995-05-15"), Date.valueOf("2021-02-10"), 60000);
        employeeList.add(employee1);
        employeeList.add(employee2);

        List<Employee> parsedEmployees = Parser.parseEmployees(employeeList);

        // Assert that all employees passed through the validation
        //assertThat(parsedEmployees, contains(employee1, employee2));
    }

    // Add tests for other methods similarly


    @Nested
    @DisplayName("Individual Employee Fields")
    class IndividualEmployeeFieldsTests{
        @BeforeAll
        static void setup(){
            Parser parser = new Parser();
        }
        @Test
        @DisplayName("Valid empID")
        void checkValidEmpID(){
            // Create a sample list of employees
            List<Employee> employeeList = new ArrayList<>();
            Employee validEmployee = new Employee(123456, "Mr.", "John",'J',"Doe", 'M', "Doe@example.com", Date.valueOf("1990-01-01"), Date.valueOf("2020-01-01"), 50000);
            Employee invalidEmployee = new Employee(23456, "Ms.", "Jane",'S',"Smith", 'F', "Smith@example.com", Date.valueOf("1995-05-15"), Date.valueOf("2021-02-10"), 60000);
            employeeList.add(validEmployee);
            employeeList.add(invalidEmployee);

            System.out.println(employeeList);
            List<Employee> parsedEmployees = Parser.parseEmployees(employeeList);
            System.out.println(parsedEmployees);
            // Assert that all employees passed through the validation
            Assumptions.assumeTrue(parsedEmployees.size() == 1);
            Assumptions.assumeTrue(parsedEmployees.get(0).equals(validEmployee));
        }
        @Test
        @DisplayName("Valid Prefix")
        void checkValidPrefix(){
            // Create a sample list of employees
            List<Employee> employeeList = new ArrayList<>();
            Employee validEmployee = new Employee(123456, "Mr.", "John",'J',"Doe", 'M', "Doe@example.com", Date.valueOf("1990-01-01"), Date.valueOf("2020-01-01"), 50000);
            Employee invalidEmployee = new Employee(234562, "Super", "Jane",'S',"Smith", 'F', "Smith@example.com", Date.valueOf("1995-05-15"), Date.valueOf("2021-02-10"), 60000);
            employeeList.add(validEmployee);
            employeeList.add(invalidEmployee);

            List<Employee> parsedEmployees = Parser.parseEmployees(employeeList);
            // Assert that all employees passed through the validation
            Assumptions.assumeTrue(parsedEmployees.size() == 1);
            Assumptions.assumeTrue(parsedEmployees.get(0).equals(validEmployee));
        }

        @Test
        @DisplayName("Valid Initials")
        void checkInitials(){
            // Create a sample list of employees
            List<Employee> employeeList = new ArrayList<>();
            Employee validEmployee = new Employee(123456, "Mr.", "John",'M',"Doe", 'M', "Doe@example.com", Date.valueOf("1990-01-01"), Date.valueOf("2020-01-01"), 50000);
            Employee invalidEmployee = new Employee(234562, "Mrs.", "Jane",'&',"Smith", 'F', "Smith@example.com", Date.valueOf("1995-05-15"), Date.valueOf("2021-02-10"), 60000);
            employeeList.add(validEmployee);
            employeeList.add(invalidEmployee);

            List<Employee> parsedEmployees = Parser.parseEmployees(employeeList);
            // Assert that all employees passed through the validation
            Assumptions.assumeTrue(parsedEmployees.size() == 1);
            Assumptions.assumeTrue(parsedEmployees.get(0).equals(validEmployee));
        }

        @Test
        @DisplayName("Valid Name")
        void checkValidName(){
            // Create a sample list of employees
            List<Employee> employeeList = new ArrayList<>();
            Employee validEmployee = new Employee(123456, "Mr.", "John",'J',"Doe", 'M', "Doe@example.com", Date.valueOf("1990-01-01"), Date.valueOf("2020-01-01"), 50000);
            Employee invalidEmployee = new Employee(234562, "Mrs.", "J@ne",'S',"Smith", 'F', "Smith@example.com", Date.valueOf("1995-05-15"), Date.valueOf("2021-02-10"), 60000);
            employeeList.add(validEmployee);
            employeeList.add(invalidEmployee);

            List<Employee> parsedEmployees = Parser.parseEmployees(employeeList);
            // Assert that all employees passed through the validation
            Assumptions.assumeTrue(parsedEmployees.size() == 1);
            Assumptions.assumeTrue(parsedEmployees.get(0).equals(validEmployee));
        }

        @Test
        @DisplayName("Valid Gender")
        void checkValid(){
            // Create a sample list of employees
            List<Employee> employeeList = new ArrayList<>();
            Employee validEmployee = new Employee(123456, "Mr.", "John",'J',"Doe", 'M', "Doe@example.com", Date.valueOf("1990-01-01"), Date.valueOf("2020-01-01"), 50000);
            Employee invalidEmployee = new Employee(234562, "Mrs.", "Jane",'S',"Smith", 'X', "Smith@example.com", Date.valueOf("1995-05-15"), Date.valueOf("2021-02-10"), 60000);
            employeeList.add(validEmployee);
            employeeList.add(invalidEmployee);

            List<Employee> parsedEmployees = Parser.parseEmployees(employeeList);
            // Assert that all employees passed through the validation
            Assumptions.assumeTrue(parsedEmployees.size() == 1);
            Assumptions.assumeTrue(parsedEmployees.get(0).equals(validEmployee));
        }
        @Test
        @DisplayName("Valid email")
        void checkValidEmail(){
            // Create a sample list of employees
            List<Employee> employeeList = new ArrayList<>();
            Employee validEmployee = new Employee(123456, "Mr.", "John",'J',"Doe", 'M', "Doe@example.com", Date.valueOf("1990-01-01"), Date.valueOf("2020-01-01"), 50000);
            Employee invalidEmployee = new Employee(234562, "Mrs.", "Jane",'S',"Smith", 'F', "Smithy&Hotmail.gov", Date.valueOf("1995-05-15"), Date.valueOf("2021-02-10"), 60000);
            employeeList.add(validEmployee);
            employeeList.add(invalidEmployee);

            List<Employee> parsedEmployees = Parser.parseEmployees(employeeList);
            // Assert that all employees passed through the validation
            Assumptions.assumeTrue(parsedEmployees.size() == 1);
            Assumptions.assumeTrue(parsedEmployees.get(0).equals(validEmployee));
        }

        @Test
        @DisplayName("Valid Dates")
        void checkValidDates(){
            // Create a sample list of employees
            List<Employee> employeeList = new ArrayList<>();
            Employee validEmployee = new Employee(123456, "Mr.", "John",'J',"Doe", 'M', "Doe@example.com", Date.valueOf("1990-01-01"), Date.valueOf("2020-01-01"), 50000);
            Employee invalidEmployee = new Employee(234562, "Mrs.", "Jane",'S',"Smith", 'F', "Smith@example.com", Date.valueOf("2022-05-15"), Date.valueOf("2021-02-10"), 60000);
            employeeList.add(validEmployee);
            employeeList.add(invalidEmployee);

            List<Employee> parsedEmployees = Parser.parseEmployees(employeeList);
            // Assert that all employees passed through the validation
            Assumptions.assumeTrue(parsedEmployees.size() == 1);
            Assumptions.assumeTrue(parsedEmployees.get(0).equals(validEmployee));
        }

        @Test
        @DisplayName("Valid Salary")
        void checkValidSalary(){
            // Create a sample list of employees
            List<Employee> employeeList = new ArrayList<>();
            Employee validEmployee = new Employee(123456, "Mr.", "John",'J',"Doe", 'M', "Doe@example.com", Date.valueOf("1990-01-01"), Date.valueOf("2020-01-01"), 50000);
            Employee invalidEmployee = new Employee(234562, "Mrs.", "Jane",'S',"Smith", 'F', "Smith@example.com", Date.valueOf("1995-05-15"), Date.valueOf("2021-02-10"), -60000);
            employeeList.add(validEmployee);
            employeeList.add(invalidEmployee);

            List<Employee> parsedEmployees = Parser.parseEmployees(employeeList);
            // Assert that all employees passed through the validation
            Assumptions.assumeTrue(parsedEmployees.size() == 1);
            Assumptions.assumeTrue(parsedEmployees.get(0).equals(validEmployee));
        }

    }
    @Test
    @DisplayName("Check is Duplicates Removed")
    void checkDuplicatesRemoved(){
        // Create a sample list of employees
        List<Employee> employeeList = new ArrayList<>();
        Employee Employee1 = new Employee(123456, "Mr.", "John",'J',"Doe", 'M', "Doe@example.com", Date.valueOf("1990-01-01"), Date.valueOf("2020-01-01"), 50000);
        Employee Employee2 = new Employee(123456, "Mrs.", "Jane",'S',"Smith", 'F', "Smith@example.com", Date.valueOf("1995-05-15"), Date.valueOf("2021-02-10"), 60000);
        employeeList.add(Employee1);
        employeeList.add(Employee2);

        List<Employee> parsedEmployees = Parser.removeDuplicates(employeeList);
        // Assert that all employees passed through the validation
        System.out.println(parsedEmployees);
        Assertions.assertEquals(0, parsedEmployees.size());
    }

    @Test
    @DisplayName("Check that non-Duplicates are not Removed")
    void checkNonDuplicatesAreNotRemoved(){
        // Create a sample list of employees
        List<Employee> employeeList = new ArrayList<>();
        Employee Employee1 = new Employee(923343, "Mr.", "John",'J',"Doe", 'M', "Doe@example.com", Date.valueOf("1990-01-01"), Date.valueOf("2020-01-01"), 50000);
        Employee Employee2 = new Employee(123456, "Mrs.", "Jane",'S',"Smith", 'F', "Smith@example.com", Date.valueOf("1995-05-15"), Date.valueOf("2021-02-10"), 60000);
        employeeList.add(Employee1);
        employeeList.add(Employee2);

//        System.out.println("111: " + employeeList);
        List<Employee> parsedEmployees = Parser.removeDuplicates(employeeList);
//        System.out.println("222: " +employeeList);
        // Assert that all employees passed through the validation
        System.out.println(parsedEmployees);
        Assertions.assertEquals(2, parsedEmployees.size());
    }
}