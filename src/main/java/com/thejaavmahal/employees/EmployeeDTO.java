package com.thejaavmahal.employees;

import java.util.Date;

// Employee Data Model
public record EmployeeDTO(
        int empId,
        String name,
        String prefix,
        String firstName,
        String lastName,
        String initials,
        String gender,
        String email,
        Date dateOfBirth,
        Date dateOfJoin,
        int salary
) {





}
