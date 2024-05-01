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

        @Override
        public String toString() {
                return
                        "Employee: " + empId + " " + firstName + " " + lastName + '\n' +
                                ". Employee Id => " + empId + '\n' +
                                ". Employee Title => " + prefix + '\n' +
                                ". Employee First Name => " + firstName + '\n' +
                                ". Employee Middle Initial => " + initial + '\n' +
                                ". Employee Last Name => " + lastName + '\n' +
                                ". Employee Gender => " + gender + '\n' +
                                ". Employee Email Address => " + email + '\n' +
                                ". Employee Date of Birth (YYYY-MM-DD) => " + dateOfBirth + '\n' +
                                ". Employee Hire Date (YYYY-MM-DD) => " + dateOfJoin + '\n' +
                                ". Employee Salary => " + "£" + salary
                        ;
        }


}

