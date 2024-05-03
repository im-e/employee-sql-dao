package com.thejaavmahal.employees;

import java.sql.Date;

// Employee Data Transfer Object
public record Employee(
                 int empId,
                 String prefix,
                 String firstName,
                 char initial,
                 String lastName,
                 char gender,
                 String email,
                 Date dateOfBirth,
                 Date dateOfJoin,
                 int salary
                 )
{
}

