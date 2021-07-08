package com.freemarker.lpex.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Simple utilities to return the stack trace of an exception as a String.
 */
public final class StackTraceUtil {

    private StackTraceUtil() {
    }

    public static String getStackTrace(Throwable aThrowable) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        aThrowable.printStackTrace(printWriter);
        return result.toString();
    }

    /**
     * Defines a custom format for the stack trace as String.
     */
    public static String getCustomStackTrace(Throwable aThrowable) {
        // add the class name and any message passed to constructor
        final StringBuilder result = new StringBuilder("BOO-BOO: ");
        result.append(aThrowable.toString());
        final String tNewLineChar = StringUtil.newLineChar();
        result.append(tNewLineChar);

        // add each element of the stack trace
        for (StackTraceElement tElement : aThrowable.getStackTrace()) {
            result.append(tElement);
            result.append(tNewLineChar);
        }
        return result.toString();
    }
}