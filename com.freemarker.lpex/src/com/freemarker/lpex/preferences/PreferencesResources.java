package com.freemarker.lpex.preferences;

import java.util.ResourceBundle;

public final class PreferencesResources {

    /**
     * Private constructor to ensure the Singleton pattern.
     */
    private PreferencesResources() {
    }

    /**
     * The messages resource bundle.
     */
    private static final ResourceBundle bundle = ResourceBundle.getBundle("com.freemarker.lpex.preferences.messages");

    public static String getString(String aKey) {
        String tValue = bundle.getString(aKey);
        if (tValue != null) {
            return tValue;
        }
        return "";
    }

}