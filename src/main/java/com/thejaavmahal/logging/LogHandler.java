package com.thejaavmahal.logging;

import com.thejaavmahal.App;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogHandler {

    private static Logger LOGGER;

    public static Logger getLogger() {
        if (LOGGER == null) {
            LOGGER = Logger.getLogger(App.class.getName());
            init();
        }
        return LOGGER;
    }

    private static void init() {
        LOGGER.setUseParentHandlers(false);
        LOGGER.addHandler(getConsoleHandler());
        LOGGER.addHandler(getFileHandler());
        LOGGER.setLevel(Level.ALL);
        LOGGER.info("Starting Logger...");
    }

    private static ConsoleHandler getConsoleHandler(){
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.CONFIG);
        consoleHandler.setFormatter(new ConsoleFormatter());
        return consoleHandler;
    }
    private static FileHandler getFileHandler()  {
        try {
            FileHandler fileHandler = new FileHandler("src/main/resources/logfile.log");
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new FileFormatter());
            return fileHandler;
        } catch (IOException e) {
            LOGGER.severe("Unable to create log file: " + e.getMessage());
        }
            return null;
    }

}
