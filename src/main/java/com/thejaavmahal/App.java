package com.thejaavmahal;

import com.thejaavmahal.employees.DatabasePopulator;
import com.thejaavmahal.logging.LogHandler;
import com.thejaavmahal.utils.Parser;

public class App {
    public static void main(String[] args) {
        Parser.init();
        DatabasePopulator.init();

        // User requests CRUD operations on DAO

    }
}
