package com.thejaavmahal.employees;

import java.util.ArrayList;

public class EmployeeList {

    private static final ArrayList<Employee> employees = new ArrayList<>();

    public static ArrayList<Employee> getEmployees() {
        return employees;
    }

    public static void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public static void deleteEmployees() {
        employees.clear();
    }

}
