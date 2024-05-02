package com.thejaavmahal.logging;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class ResultConsoleFormatter extends Formatter {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";

    @Override
    public String format(LogRecord record) {
        return switch (record.getLevel().getName()) {
            case "SEVERE" ->
                    ANSI_RED +
                    record.getMessage()
                    + ANSI_RESET
                    + "\n";
            case "WARNING" ->
                    ANSI_YELLOW +
                    record.getMessage()
                    + ANSI_RESET
                    + "\n";
            case "INFO" ->
                    ANSI_GREEN +
                    record.getMessage()
                    + ANSI_RESET
                    + "\n";
            case "CONFIG" ->
                    ANSI_BLUE + "------------------------------------------------------------" + "\n" +
                     record.getMessage()  + ANSI_RESET + "\n";
            default -> record.getMessage() + "\n";
        };
    }
}