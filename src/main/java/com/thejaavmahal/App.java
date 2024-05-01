package com.thejaavmahal;

import com.thejaavmahal.employees.DatabasePopulator;
import com.thejaavmahal.utils.ConnectionManager;
import com.thejaavmahal.utils.Parser;

public class App {
    public static void main(String[] args) {
        Parser.init();
        DatabasePopulator databasePopulator = new DatabasePopulator(ConnectionManager.getConnection());

        // User requests CRUD operations on DAO

    }
}
