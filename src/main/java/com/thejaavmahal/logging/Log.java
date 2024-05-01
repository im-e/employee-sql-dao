package com.thejaavmahal.logging;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {

    private static final Logger CONSOLE_LOGGER = Logger.getLogger("ConsoleLogger");
    private static final Logger FILE_LOGGER = Logger.getLogger("FileLogger");

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";

    public static void init() {
        CONSOLE_LOGGER.setUseParentHandlers(false);
        CONSOLE_LOGGER.addHandler(getConsoleHandler());
        CONSOLE_LOGGER.setLevel(Level.INFO);

        FILE_LOGGER.setUseParentHandlers(false);
        FILE_LOGGER.addHandler(getFileHandler());
        FILE_LOGGER.setLevel(Level.ALL);
    }

    private static ConsoleHandler getConsoleHandler(){
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.CONFIG);
        consoleHandler.setFormatter(new LoggerFormatter());
        return consoleHandler;
    }
    private static FileHandler getFileHandler()  {
        try {
            FileHandler fileHandler = new FileHandler("src/main/resources/logfile.log");
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new LoggerFormatter());
            return fileHandler;
        } catch (IOException e) {
             severe("Unable to create log file: " + e.getMessage());
        }
            return null;
    }

    public static void info(String message) {
        CONSOLE_LOGGER.info(ANSI_GREEN + message + ANSI_RESET);
        FILE_LOGGER.info(message);
    }

    public static void warn(String message) {
        CONSOLE_LOGGER.warning(ANSI_YELLOW +  message + ANSI_RESET);
        FILE_LOGGER.warning(message);
    }

    public static void severe(String message) {
        CONSOLE_LOGGER.severe(ANSI_RED +  message + ANSI_RESET);
        FILE_LOGGER.severe(message);
    }

    public static void config(String message) {
        CONSOLE_LOGGER.config(ANSI_BLUE +  message + ANSI_RESET);
        FILE_LOGGER.config(message);
    }

    public static void fine(String message) {
        FILE_LOGGER.fine(message);
    }

    public static void finer(String message) {
        FILE_LOGGER.finer(message);
    }

    public static void finest(String message) {
        FILE_LOGGER.finest(message);
    }


}
