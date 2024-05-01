package com.thejaavmahal.employees;

public interface PopulatorStatements {
    String INSERT = "INSERT INTO employees (employee_id, prefix, first_name, " +
            "middle_initial, last_name, gender, email, date_of_birth, date_of_joining, " +
            "salary) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    String DELETE = "DELETE FROM employees WHERE employee_id > 0";

}

