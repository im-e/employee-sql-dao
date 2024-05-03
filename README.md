## employee-sql-dao
Employee-sql-dao is a group project by 'The Jaav Mahal' consisting of Imogen, Murad, Oliver, Patrick, Patryk and Phoenix 👋.
This proejct creates a Java application that takes a CSV file which contains a List of Employees and their relevant personal details
and stores the data in a MYSQL database. Additioanlly, the application has built in functionality that can identify and remove 
corrupted data in the file automatically, as well as the capability to allow for the user to create, update and remove specific employees.

## Dependencies

JDK 21, Junit


## How to Use the Project 

Setup: Ensure you have Java installed on your system. 

    Fork this repository
    Clone the forked repository and import it into yout preferred Java IDE
    Add your contributions (code or documentation)
    Commit and push
    Wait for pull request to be merged

## How to use the Program 

Open the project directory: "TheJaavMahal" and open the class "App". Ensure that the init function has been called on the Parser and DatabasePopulator, this should come before use of the CRUD methods.

```
    Parser.init();
    DatabasePopulator.init();
```

Within the main method you can access the data with the following queries.
```
    EmployeeDAO.queryFromField("employee_id", 114577);
    EmployeeDAO.deleteFromEmployees("employee_id", 114577);
    EmployeeDAO.queryFromField("first_name", "Jason");
    EmployeeDAO.updateEmployee(192501, "middle_initial", "I");
    EmployeeDAO.queryFromField("employee_id", 192501);
    EmployeeDAO.createEmployee(employee);
    EmployeeDAO.queryFromField("employee_id", 123456);
```
You can use any combination or frequency of these methods, and by running the program the results of each search will be shown in the console.

For each run of App, there will be a recorded file of the results named SearchResult-YYYY-MM-DD---hh-mm-ss.txt. These can be found in the resources (src/main/resources) folder.

To enhance maintainability we created logging functionality using java.util.logging. Our colour-coded logger allows you to easily track the flow of the program, record the state when an important event happens and capture errors or exceptions that occur during runtime. This can be used through the Log class and it's static methods.

##  

## Acceptance Criteria
- Read the csv file using File I/O and parse the data into a suitable data structure.
- Parse the data correctly, ignoring the records that have corrupted data
- Have a DAO class that performs crud operations on the data
- Store data to database
- Inform the user of how many employee records are corrupted using a suitable logging mechanism.

##  

📫 If you encounter any bugs, please open up an issue to let us know.
Alternatively, we welcome suggestions for any updates or improvements you would like to see! 
