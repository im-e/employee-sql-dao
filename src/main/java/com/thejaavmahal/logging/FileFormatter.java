package com.thejaavmahal.logging;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class FileFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        return  "(" + record.getSourceClassName() + ") " +
                "[" + record.getLevel() + "] " +
                "- "+ record.getMessage()
                + "\n";
    }
}
