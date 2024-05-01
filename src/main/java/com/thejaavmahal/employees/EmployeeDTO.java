package com.thejaavmahal.employees;

import java.sql.Date;

// Employee Data Model
public record EmployeeDTO(
        int empId,
        String prefix,
        String firstName,
        char initials,
        String lastName,
        char gender,
        String email,
        Date dateOfBirth,
        Date dateOfJoin,
        int salary
) {





}
