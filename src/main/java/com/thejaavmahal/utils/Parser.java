package com.thejaavmahal.utils;

import com.thejaavmahal.employees.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
;


public class Parser {

    private static List<Employee> parsedEmployees;

    public static void init()
    {
        ArrayList<String> rawEmployees = getEmployeesFromCSV();
        List<Employee> employeeList = parseUncheckedEmployeeData(rawEmployees);
        parsedEmployees = parseEmployees(employeeList);
    }

    public static List<Employee> getEmployees(){
        return parsedEmployees;
    }

    public static void deleteEmployees(){
        parsedEmployees.clear();
    }

    public static ArrayList<String> getEmployeesFromCSV() throws IllegalArgumentException {
        ArrayList<String> result = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/employees-corrupted.csv"))) {
            bufferedReader.readLine();

            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                result.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<Employee> parseUncheckedEmployeeData(ArrayList<String> rawData) {
        ArrayList<Employee> employeeList = new ArrayList<>();
        for (int i = 0; i < rawData.size(); i++) {
            String[] parsedEmployee = rawData.get(i).split(",");
            employeeList.add(new Employee(Integer.parseInt(parsedEmployee[0]), parsedEmployee[1], parsedEmployee[2],  parsedEmployee[3].charAt(0), parsedEmployee[4],  parsedEmployee[5].charAt(0),  parsedEmployee[6],  convertToDate(parsedEmployee[7]), convertToDate(parsedEmployee[8]),  Integer.parseInt(parsedEmployee[9])));
        }
//        LOGGER.info("employee list: " + employeeList);
        return employeeList;
    }

    public static Date convertToDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");

        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return java.sql.Date.valueOf(localDate);
    }

    public static List<Employee> parseEmployees(List<Employee> employeeList) {
        return employeeList.stream()
                .filter(employee -> checkIfValidId(employee.empId()))
                .filter(employee -> checkIfValidName(employee.firstName()))
                .filter(employee -> checkIfValidName(employee.lastName()))
                .filter(employee -> checkIfValidMiddleInitial(employee.initial()))
                .filter(employee -> checkIfValidGender(employee.gender()))
                .filter(employee -> checkIfValidEmail(employee.email()))
                .filter(employee -> checkIfValidDateOfBirth(employee.dateOfBirth()))
                .filter(employee -> checkIfValidDateOfJoining(employee.dateOfJoin(), employee.dateOfBirth()))
                .filter(employee -> checkIfValidSalary(employee.salary()))
                .collect(Collectors.toList());
    }

    private static boolean checkIfValidId(int ID){
        int numberOfDigits = String.valueOf(ID).length();
        return numberOfDigits == 6;
    }

    private static boolean checkIfValidName(String name){  //use for first and last name
        //name can only consist of letters
        String regex = ("^[a-zA-Z]+$");
        return name.matches(regex);
    }

    private static boolean checkIfValidMiddleInitial(char middleInitial){
        //Not symbol & Length 1
        return String.valueOf(middleInitial).matches("[a-zA-Z]");
    }

    private static boolean checkIfValidGender(char gender){
        //has to be M or F
        return gender == 'M' || gender == 'F';
    }

    private static boolean checkIfValidEmail(String email){
        // Regular expression for email validation
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        // Compile the regex pattern
        Pattern pattern = Pattern.compile(regex);
        // Create matcher object
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private static boolean checkIfValidDateOfBirth(Date DoB){
        //check in the past
        //Do we want to add minimum age??
        Date currentDate = Date.valueOf(LocalDate.now());
        return DoB.before(currentDate);
    }

    private static boolean checkIfValidDateOfJoining(Date DoJ, Date DoB){
        //has to be in format YYYY-MM-DD & can't be before date of birth
        Date currentDate = Date.valueOf(LocalDate.now());

        return DoJ.before(currentDate) && DoJ.after(DoB);
    }
    private static boolean checkIfValidSalary(int salary){
        return salary >= 0;
    }


}
