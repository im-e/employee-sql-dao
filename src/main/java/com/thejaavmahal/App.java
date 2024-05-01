package com.thejaavmahal;

import com.thejaavmahal.employees.DatabasePopulator;
import com.thejaavmahal.utils.ConnectionManager;
import com.thejaavmahal.utils.Parser;

public class App {
    public static void main(String[] args) {
        // Setup Logger


        Parser.init();
        DatabasePopulator databasePopulator = new DatabasePopulator(ConnectionManager.getConnection());



        // Setup DB connection


        // Import CSV File


        // Parse CSV file


        // Populate the database (seeding)


        // User requests CRUD operations on DAO

    }
}
