package com.thejaavmahal.logging;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class ResultFileFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
        if (record.getLevel().getName().equals("CONFIG")) {
            return "------------------------------------------------------------" + "\n" +
                    record.getMessage() + "\n";
        }
        return record.getMessage() + "\n";
    }
}