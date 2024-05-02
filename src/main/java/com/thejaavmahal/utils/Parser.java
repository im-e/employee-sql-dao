package com.thejaavmahal.utils;

import com.mysql.cj.log.Log;
import com.thejaavmahal.employees.Employee;
import com.thejaavmahal.logging.LogHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDate;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class Parser {

    private static List<Employee> parsedEmployees;
    private static final Logger LOGGER = LogHandler.getLogger();

    public static void init()
    {
        LOGGER.info("Initialising Parser...");
        ArrayList<String> rawEmployees = getEmployeesFromCSV();
        List<Employee> employeeList = parseUncheckedEmployeeData(rawEmployees);
        parsedEmployees = parseEmployees(employeeList);

        LOGGER.config("Successfully parsed employees for corrupt data.");
        LOGGER.info("Parser Initialised.");

        int count = parsedEmployees.size() - rawEmployees.size();
        LOGGER.info("Number of invalid records removed: " + Math.abs(count));

        List<Integer> s = parsedEmployees.stream().map(Employee::empId).toList();

        LOGGER.info("Employees: " + s);
    }

    public static List<Employee> getEmployees(){
        return parsedEmployees;
    }

    public static void deleteEmployees(){
        LOGGER.config("Deleting parser list as database is populated");
        parsedEmployees.clear();
    }

    public static ArrayList<String> getEmployeesFromCSV() throws IllegalArgumentException {
        LOGGER.info("Getting data from CSV...");
        ArrayList<String> result = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/employees-corrupted.csv"))) {
            bufferedReader.readLine();

            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                result.add(line);
            }
        } catch (IOException e) {
            LOGGER.severe("Could not read data from CSV: " + e.getMessage());
        }

        LOGGER.config("Successfully read data from CSV.");
        return result;
    }

    public static List<Employee> parseUncheckedEmployeeData(ArrayList<String> rawData) {
        LOGGER.info("Parsing data into employee objects...");
        ArrayList<Employee> employeeList = new ArrayList<>();
        for (String rawDatum : rawData) {
            String[] parsedEmployee = rawDatum.split(",");
            if (parsedEmployee[3].length() == 1 && parsedEmployee[5].length() == 1) {
                employeeList.add(new Employee(Integer.parseInt(parsedEmployee[0]), parsedEmployee[1], parsedEmployee[2], parsedEmployee[3].charAt(0), parsedEmployee[4], parsedEmployee[5].charAt(0), parsedEmployee[6], convertToDate(parsedEmployee[7]), convertToDate(parsedEmployee[8]), Integer.parseInt(parsedEmployee[9])));
            }
        }
        LOGGER.config("Successfully parsed data into employee objects.");
        return employeeList;
    }

    public static Date convertToDate(String dateString) {
        LOGGER.finest("Converting date: " + dateString);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");

        LocalDate localDate = LocalDate.parse(dateString, formatter);
        LOGGER.finest("Converted date: " + localDate);
        return java.sql.Date.valueOf(localDate);
    }

    public static List<Employee> parseEmployees(List<Employee> employeeList) {
        LOGGER.info("Parsing employee objects for corrupted data...");
        List<Employee> filtered = employeeList.stream()
                .filter(employee -> checkIfValidId(employee.empId()))
                .filter(employee -> checkIfValidPrefix(employee.prefix()))
                .filter(employee -> checkIfValidName(employee.firstName()))
                .filter(employee -> checkIfValidName(employee.lastName()))
                .filter(employee -> checkIfValidMiddleInitial(employee.initial()))
                .filter(employee -> checkIfValidGender(employee.gender()))
                .filter(employee -> checkIfValidEmail(employee.email()))
                .filter(employee -> checkIfValidDateOfBirth(employee.dateOfBirth()))
                .filter(employee -> checkIfValidDateOfJoining(employee.dateOfJoin(), employee.dateOfBirth()))
                .filter(employee -> checkIfValidSalary(employee.salary()))
                .collect(Collectors.toList());

        return removeDuplicates(filtered);
    }

    public static List<Employee> removeDuplicates(List<Employee> employees) {
        LOGGER.config("Removing duplicates...");
        HashMap<Integer, Integer> idFreqMap = new HashMap<>();
        HashMap<Integer, Employee> idToEmployeeDictionary = new HashMap<>();

        for (Employee employee: employees) {
            int id = employee.empId();
            idFreqMap.put(id, idFreqMap.getOrDefault(id, 0) + 1);
            idToEmployeeDictionary.put(id, employee);
        }

        List<Employee> uniqueEmployees = new ArrayList<>();
        for (Employee employee: employees) {
            int id = employee.empId();
            if (idFreqMap.get(id) == 1) {
                uniqueEmployees.add(idToEmployeeDictionary.get(id));
            }
        }
        return uniqueEmployees;
    }

    private static boolean checkIfValidId(int ID){
        LOGGER.finest("Checking if ID isn't more than 6 numbers: " + ID);
        int numberOfDigits = String.valueOf(ID).length();
        return numberOfDigits == 6;
    }
    private static boolean checkIfValidPrefix(String prefix){
        LOGGER.finest("Checking if the prefix is part of the saved pre-set: " + prefix);
        List<String> myList = new ArrayList<>(Arrays.asList("Mr.", "Mrs.", "Ms.", "Dr.", "Drs.", "Hon.", "Prof."));
        Set<String> mySet = new HashSet<>(myList);
        return mySet.contains(prefix);
    }

    private static boolean checkIfValidName(String name){
        LOGGER.finest("Checking if name contains symbols: " + name);
        String regex = ("^[a-zA-Z]+$");
        return name.matches(regex);
    }

    private static boolean checkIfValidMiddleInitial(char middleInitial){
        LOGGER.finest("Checking if the middle initial is a single letter: " + middleInitial);
        return String.valueOf(middleInitial).matches("[a-zA-Z]");
    }

    private static boolean checkIfValidGender(char gender){
        LOGGER.finest("Checking if the gender is 'M' or 'F': " + gender);
        return gender == 'M' || gender == 'F';
    }

    private static boolean checkIfValidEmail(String email){
        LOGGER.finest("Checking if the email is in valid format: " + email);
        // Regular expression for email validation
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        // Compile the regex pattern
        Pattern pattern = Pattern.compile(regex);
        // Create matcher object
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private static boolean checkIfValidDateOfBirth(Date DoB){
        LOGGER.finest("Checking if the date of birth is in correct format: " + DoB);
        Date currentDate = Date.valueOf(LocalDate.now());
        return DoB.before(currentDate);
    }

    private static boolean checkIfValidDateOfJoining(Date DoJ, Date DoB){
        LOGGER.finest("Checking if the date of joining is in correct format: " + DoB);
        Date currentDate = Date.valueOf(LocalDate.now());
        return DoJ.before(currentDate) && DoJ.after(DoB);
    }
    private static boolean checkIfValidSalary(int salary){
        LOGGER.finest("Checking if the salary isn't negative: " + salary);
        return salary >= 0;
    }


}
