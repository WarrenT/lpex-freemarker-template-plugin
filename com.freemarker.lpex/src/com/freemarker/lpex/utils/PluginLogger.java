package com.freemarker.lpex.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.freemarker.lpex.preferences.Preferences;

public final class PluginLogger {
    public final static Logger logger = Logger.getLogger(PluginLogger.class.getName());

    static private FileHandler fileTxt;

    static private Formatter formatterTxt;

    public static final String PRIORITY_ALL = "all";

    public static final String PRIORITY_INFO = "info";

    public static final String PRIORITY_WARNING = "warning";

    public static final String PRIORITY_SEVERE = "severe";

    public static final String PRIORITY_OFF = "off";

    private PluginLogger() {
    }

    static public void setLevel(String aLevel) {
        // Parse and set the level
        if (PRIORITY_ALL.compareToIgnoreCase(aLevel) == 0) {
            logger.setLevel(Level.ALL);
        } else if (PRIORITY_INFO.compareToIgnoreCase(aLevel) == 0) {
            logger.setLevel(Level.INFO);
        } else if (PRIORITY_WARNING.compareToIgnoreCase(aLevel) == 0) {
            logger.setLevel(Level.WARNING);
        } else if (PRIORITY_SEVERE.compareToIgnoreCase(aLevel) == 0) {
            logger.setLevel(Level.SEVERE);
        } else if (PRIORITY_OFF.compareToIgnoreCase(aLevel) == 0) {
            logger.setLevel(Level.OFF);
        } else {
            logger.setLevel(Level.OFF);
        }
    }

    static public void setPath(String aPath) throws IOException {
        try {
            logger.removeHandler(fileTxt);
        } catch (Exception e) {
            // ignore exceptions
            PluginLogger.doNothing();
        }

        if (aPath == null) {
            aPath = Preferences.getInstance().getDefaultLogPath();
        }

        try {
            fileTxt = new FileHandler(aPath, true);
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            return;
        }

        // Create text Formatter
        formatterTxt = new TextFormatter();
        fileTxt.setFormatter(formatterTxt);

        logger.addHandler(fileTxt);
    }

    /**
     * Dummy method used inside empty catch() statements to satisfy Checkstyle.
     */
    public static void doNothing() {
        return;
    }

    static public void setup(String aPath, String aLevel) throws IOException {
        setLevel(aLevel);
        setPath(aPath);
    }
}

class TextFormatter extends Formatter {
    private static final String LOGGER_DATE_FORMAT = "yyyy-MM-dd HH:mm";

    // This method is called for every log records
    @Override
    public String format(LogRecord rec) {
        StringBuffer tBuffer = new StringBuffer(1000);
        tBuffer.append(rec.getLevel());
        tBuffer.append(' ');
        tBuffer.append(produceOutputDate(rec.getMillis()));
        tBuffer.append(' ');
        tBuffer.append(formatMessage(rec));
        tBuffer.append('\n');
        return tBuffer.toString();
    }

    // This method is called just after the handler using this
    // formatter is created
    @Override
    public String getHead(Handler aHandler) {
        SimpleDateFormat tDateFormat = getDateFormat();
        Date tResultDate = new Date();
        return "New session " + tDateFormat.format(tResultDate) + "\n=================================\n";
    }

    // This method is called just after the handler using this
    // formatter is closed
    @Override
    public String getTail(Handler aHandler) {
        return "=================================\n";
    }

    private String produceOutputDate(long aMilliSecs) {
        SimpleDateFormat tDateFormat = getDateFormat();
        Date tLogDate = new Date(aMilliSecs);
        return tDateFormat.format(tLogDate);
    }

    private SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat(LOGGER_DATE_FORMAT);
    }
}