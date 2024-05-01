package com.thejaavmahal.employees;

import java.util.ArrayList;
import java.util.List;

public class EmployeeList {

    private static List<Employee> employeeList;

    public static List<Employee> getEmployeeList() {
        return employeeList;
    }

    public static void setEmployeeList(List<Employee> employeeList) {
        EmployeeList.employeeList = employeeList;
    }

    public static void addEmployee(Employee employee) {
        employeeList.add(employee);
    }

    public static void deleteEmployees() {
        employeeList.clear();
    }

}
