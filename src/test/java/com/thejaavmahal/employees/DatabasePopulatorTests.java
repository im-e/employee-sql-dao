package com.thejaavmahal.employees;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class DatabasePopulatorTests {

    private static List<Employee> employees;

    @BeforeAll
    static void setUpBeforeClass() {
        Employee employee1 = new Employee(401535,"Ms.","Theodora",
                'G',"Hung",'F',"theodora.hung@aol.com",
                Date.valueOf("1989-3-17"), Date.valueOf("2015-9-11"),40113);
        Employee employee2 = new Employee(405212,"Mr.","Theo",
                'G',"Brooke",'M',"theo.hung@aol.com",
                Date.valueOf("1959-3-17"), Date.valueOf("2017-9-11"),70113);
        employees = new ArrayList<>();

        employees.add(employee1);
        employees.add(employee2);
    }


  //@Test
  //@DisplayName("Given populate employees is called, employees are added to the database")
  //void givenPopulateEmployeesIsCalledEmployeesAreAddedToTheDatabase() {
  //    boolean result = DatabasePopulator.populateEmployees(employees);
  //    Assertions.assertTrue(result);

  //}

  //@Test
  //@DisplayName("Given employee to addEmployeeToDatabase, employee is added to database")
  //void givenEmployeeToAddEmployeeToDatabaseEmployeeIsAddedToDatabase() {
  //    boolean result = DatabasePopulator.addEmployeeToDatabase(employees.getFirst());
  //    Assertions.assertTrue(result);
  //
  //}

  //@Test
  //@DisplayName("Given deleteEmployeesFromDatabase is called, employees are deleted from the database")
  //void givenDeleteEmployeesFromDatabaseIsCalledEmployeesAreDeletedFromTheDatabase() {
  //    boolean result = DatabasePopulator.deleteEmployeesFromDatabase();
  //    Assertions.assertTrue(result);

  //}
}
