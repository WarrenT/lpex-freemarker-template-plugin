package com.freemarker.lpex.utils;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

public final class StringUtil {

    private static String newLineChar = null;

    private StringUtil() {
    }

    public static String newLineChar() {
        if (newLineChar == null) {
            newLineChar = System.getProperty("line.separator");
        }
        return newLineChar;
    }

    public static String removeTrailingWhiteSpaces(String aText) {
        int i = aText.length() - 1;
        while (i >= 0 && isWhitespace(aText.substring(i, i + 1))) {
            i--;
        }
        if (i >= 0) {
            return aText.substring(0, i + 1);
        }
        return "";
    }

    private static boolean isWhitespace(String aChar) {
        if ("\n".equals(aChar) || "\r".equals(aChar) || " ".equals(aChar)) {
            return true;
        }
        return false;
    }

    /**
     * Reformats a string where lines that are longer than <tt>width</tt> are
     * split apart at the earliest wordbreak or at maxLength, whichever is
     * sooner. If the width specified is less than 5 or greater than the input
     * Strings length the string will be returned as is.
     * <p/>
     * Please note that this method can be lossy - trailing spaces on wrapped
     * lines may be trimmed.
     * 
     * @param input the String to reformat.
     * @param width the maximum length of any one line.
     * @return a new String with reformatted as needed.
     */
    public static Collection<String> wordWrap(String anInput, int width, Locale locale) {

        ArrayList<String> tLines = new ArrayList<String>();

        final String tNewLineChar = newLineChar();
        String tInput = rtrim(anInput);

        // protect ourselves
        if (tInput == null) {
            return tLines;
        } else if (width < 10) {
            tLines.add(tInput);
            return tLines;
        } else if (width >= tInput.length() && tInput.indexOf(tNewLineChar) == -1) {
            tLines.add(tInput);
            return tLines;
        }

        StringBuilder tBuffer = new StringBuilder(tInput.replace(tNewLineChar, "\n"));
        int tLineStart = 0;
        int i;

        for (i = 0; i < tBuffer.length(); i++) {
            if (tBuffer.charAt(i) == '\n') {
                tLines.add(ltrim(tBuffer.substring(tLineStart, i)));
                tLineStart = i + 1;
            }

            // handle splitting at width character
            if (i > tLineStart + width - 1) {
                int tLimit = i - tLineStart - 1;
                BreakIterator tBreaks = BreakIterator.getLineInstance(locale);
                tBreaks.setText(tBuffer.substring(tLineStart, i));
                int tEnd = tBreaks.last();

                // if the last character in the search string isn't a space,
                // we can't split on it (looks bad). Search for a previous
                // break character
                if (tEnd == tLimit + 1) {
                    if (!Character.isWhitespace(tBuffer.charAt(tLineStart + tEnd))) {
                        tEnd = tBreaks.preceding(tEnd - 1);
                    }
                }

                // if the last character is a space, replace it with a \n
                if (tEnd != BreakIterator.DONE && tEnd == tLimit + 1) {
                    tLines.add(ltrim(tBuffer.substring(tLineStart, tLineStart + tEnd)));
                    tLineStart = tLineStart + tEnd;
                } else if (tEnd != BreakIterator.DONE && tEnd != 0) {
                    tLines.add(ltrim(tBuffer.substring(tLineStart, tLineStart + tEnd)));
                    tLineStart = tLineStart + tEnd;
                } else {
                    tLines.add(ltrim(tBuffer.substring(tLineStart, i)));
                    tLineStart = i + 1;
                }
            }
        }

        if (i - tLineStart > 0) {
            tLines.add(ltrim(tBuffer.substring(tLineStart)));
        }

        return tLines;
    }

    public static String ltrim(String aValue) {
        if (aValue == null) {
            return "";
        }
        if (aValue.trim().length() == 0) {
            return "";
        }

        int i = 0;
        while (aValue.charAt(i) == ' ') {
            i++;
        }
        return aValue.substring(i);
    }

    public static String rtrim(String aValue) {
        if (aValue == null) {
            return "";
        }
        if (aValue.trim().length() == 0) {
            return "";
        }

        int i = aValue.length() - 1;
        while (i >= 0 && aValue.charAt(i) == ' ') {
            i--;
        }
        return aValue.substring(0, i + 1);
    }

}
