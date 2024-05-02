package com.thejaavmahal.logging;

import com.thejaavmahal.App;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResultHandler {

    private static Logger LOGGER;

    public static Logger getResultLogger() {
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
        LOGGER.info("Starting Search Results...");
    }

    private static ConsoleHandler getConsoleHandler(){
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.CONFIG);
        consoleHandler.setFormatter(new ResultConsoleFormatter());
        return consoleHandler;
    }
    private static FileHandler getFileHandler()  {
        try {
            String currentDay = LocalDate.now().toString();
            String currentHour = String.valueOf(LocalTime.now().getHour());
            String currentMin = String.valueOf(LocalTime.now().getMinute());
            String currentSec = String.valueOf(LocalTime.now().getSecond());
            String currentTime = currentHour + "-" + currentMin + "-" + currentSec;
            FileHandler fileHandler = new FileHandler("src/main/resources/SearchResult-" + currentDay + "---" + currentTime + ".txt");
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new ResultFileFormatter());

            return fileHandler;
        } catch (IOException e) {
            LOGGER.severe("Unable to create log file: " + e.getMessage());
        }
            return null;
    }

}
