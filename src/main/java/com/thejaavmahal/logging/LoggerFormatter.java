package com.thejaavmahal.logging;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LoggerFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        return  "(" + record.getSourceClassName() + ") " +
                "[" + record.getLevel() + "] " +
                "- "+ record.getMessage()
                + "\n";
    }
}
