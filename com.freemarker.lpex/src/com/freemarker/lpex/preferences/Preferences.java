package com.freemarker.lpex.preferences;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.eclipse.jface.preference.IPreferenceStore;

import com.freemarker.lpex.LPEXFreeMarkerPlugin;

public final class Preferences {

    private static final String DATE_FORMAT_ISO = "yyyy-MM-dd-hh.mm.ss";

    /**
     * The instance of this Singleton class.
     */
    private static Preferences instance;

    /**
     * Global preferences of the LPEX FreeMarker Plugins.
     */
    private static IPreferenceStore preferenceStore;

    /**
     * Attribute names of the properties.
     */
    public static final String LOG_PATH = "logPathPreference";

    public static final String LOG_LEVEL = "logLevelPreference";

    public static final String AUTHOR = "authorPreference";

    public static final String DATE_FORMAT = "dateFormatPreference";

    public static final String PARSER_MAPPINGS = "parserMappings";

    public static final String TEMPLATES_AUTO_SYNC_TEMPLATES = "templatesSyncPreference";

    public static final String TEMPLATES_AUTO_SYNC_ASSOCIATIONS = "associationsSyncPreference";

    public static final String TEMPLATES_COMPARE_DATES = "templatesCompareDatesPreference";

    public static final String TEMPLATES_LOCAL_DIRECTORY = "templatesDirectoryPreference";

    public static final String TEMPLATES_TEAM_DIRECTORY = "templatesSyncDirectoryPreference";

    public static final String TEMPLATES_LAST_SYNC_DATE = "templatesLastSyncDate";

    /**
     * Private constructor to ensure the Singleton pattern.
     */
    private Preferences() {
    }

    /**
     * Thread-safe method that returns the instance of this Singleton class.
     */
    public synchronized static Preferences getInstance() {
        if (instance == null) {
            instance = new Preferences();
            preferenceStore = LPEXFreeMarkerPlugin.getDefault().getPreferenceStore();
            instance.initialize();
        }
        return instance;
    }

    /**
     * Return <code>true</code> if the local templates directory is
     * automatically synchronized with the shared templates, else
     * <code>false</code>.
     * 
     * @return <code>true</code> when auto-sync is enabled.
     */
    public boolean isAutoSyncTemplatesEnabled() {
        return preferenceStore.getBoolean(Preferences.TEMPLATES_AUTO_SYNC_TEMPLATES);
    }

    /**
     * Return <code>true</code> if the parser associations are automatically
     * synchronized with the shared parser associations, else <code>false</code>
     * .
     * 
     * @return <code>true</code> when auto-sync is enabled.
     */
    public boolean isAutoSyncParserAssociations() {
        if (!isAutoSyncTemplatesEnabled()) {
            return false;
        }
        return preferenceStore.getBoolean(Preferences.TEMPLATES_AUTO_SYNC_ASSOCIATIONS);
    }

    /**
     * Return <code>true</code> if smart-sync is enabled, else
     * <code>false</code>. When smart-sync is enabled, the synchronize helper
     * copies modified files only.
     * 
     * @return <code>true</code> when smart-sync is enabled.
     */
    public boolean isSmartSyncEnabled() {
        return preferenceStore.getBoolean(Preferences.TEMPLATES_COMPARE_DATES);
    }

    /**
     * Returns the shared-templates directory name.
     * 
     * @return name of directory that contains the shared FreeMarker templates.
     */
    public String getTeamTemplatesDirectory() {
        return preferenceStore.getString(Preferences.TEMPLATES_TEAM_DIRECTORY);
    }

    /**
     * Returns the name of the local templates directory.
     * 
     * @return name of directory that contains the FreeMarker templates.
     */
    public String getTemplatesDirectory() {
        return preferenceStore.getString(Preferences.TEMPLATES_LOCAL_DIRECTORY);
    }

    /**
     * Returns the name of the parser associations property file.
     * 
     * @return property file name
     */
    public String getParserAssociations() {
        return getTemplatesDirectory() + "/parserAssociations.properties";
    }

    /**
     * Returns the log level of the plugin logger.
     * 
     * @return log level.
     */
    public String getLogLevel() {
        return preferenceStore.getString(Preferences.LOG_LEVEL);
    }

    /**
     * Returns the path of the log file.
     * 
     * @return log file path.
     */
    public String getLogPath() {
        return preferenceStore.getString(Preferences.LOG_PATH);
    }

    /**
     * Returns the default path of the log file.
     * 
     * @return default log file path.
     */
    public String getDefaultLogPath() {
        return getUserHome() + "\\Documents\\Programmierung\\Tools\\LPEX FreeMarker Templates\\logs\\com.freemaker.lpex.log";
    }

    /**
     * Returns the default date format.
     * 
     * @return date format.
     */
    public String getDateFormat() {
        return preferenceStore.getString(Preferences.DATE_FORMAT);
    }

    /**
     * Returns the default author name.
     * 
     * @return author name
     */
    public String getAuthor() {
        return preferenceStore.getString(Preferences.AUTHOR);
    }

    /**
     * Returns the parser mappings.
     * 
     * @return parser mappings.
     */
    public String getParserMappings() {
        return preferenceStore.getString(Preferences.PARSER_MAPPINGS);
    }

    /**
     * Returns the date when the templates have been synchronized.
     * 
     * @return last date of template synchronisation
     */
    public Date getLastSyncDate() {
        String tValue = preferenceStore.getString(Preferences.TEMPLATES_LAST_SYNC_DATE);
        DateFormat tFormat = new SimpleDateFormat(DATE_FORMAT_ISO);
        try {
            return tFormat.parse(tValue);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Set the date at which the templates have been synchronized.
     * 
     * @param aDate last synchronization date
     */
    public void setLastSyncDate(Date aDate) {
        DateFormat tFormat = new SimpleDateFormat(DATE_FORMAT_ISO);
        String tValue = tFormat.format(aDate);
        preferenceStore.setValue(Preferences.TEMPLATES_LAST_SYNC_DATE, tValue);
    }

    public void setParserAssociations(Properties aProperties) {
        StringBuilder tBuffer = new StringBuilder();
        for (String tKey : aProperties.stringPropertyNames()) {
            tBuffer.append(tKey);
            tBuffer.append("=");
            tBuffer.append(aProperties.getProperty(tKey));
            tBuffer.append(";");
        }
        if (tBuffer.length() > 0) {
            preferenceStore.setValue(Preferences.PARSER_MAPPINGS, tBuffer.toString());
        }
    }

    /**
     * Return the comma of the number format of the current locale.
     * 
     * @return comma symbol
     */
    public char getCommaSymbol() {
        DecimalFormat tFormat = (DecimalFormat)DecimalFormat.getInstance();
        DecimalFormatSymbols tSymbols = tFormat.getDecimalFormatSymbols();
        char tComma = tSymbols.getDecimalSeparator();
        return tComma;
    }

    /**
     * Is called by {@link PreferenceInitializer#initializeDefaultPreferences()}
     * in order to initialize the preferences default values.
     * <p>
     * This method must <b>never</b> be called from outside the
     * PreferenceInitializer class.
     */
    public void initializeDefaultPreferences() {
        preferenceStore.setDefault(Preferences.TEMPLATES_LOCAL_DIRECTORY, getUserHome()
            + "\\Documents\\Programmierung\\Tools\\LPEX FreeMarker Templates\\");
        preferenceStore.setDefault(Preferences.LOG_PATH, getDefaultLogPath());
        preferenceStore.setDefault(Preferences.LOG_LEVEL, "severe");
        preferenceStore.setDefault(Preferences.AUTHOR, "");
        preferenceStore.setDefault(Preferences.DATE_FORMAT, getDefaultDateFormat());
        preferenceStore
            .setDefault(
                Preferences.PARSER_MAPPINGS,
                "c++=cpp;cbl=cobol;cl=cl;cle=cl;clle=cl;clp=cl;dds=dds;dspf=dds;h=cpp;ilerpg=rpg;ilerpgsql=rpg;lf=dds;pf=dds;pftbl=sql;prtf=dds;rpg=rpg;rpg36=rpg;rpg38=rpg;rpgle=rpg;rpgleinc=rpg;sql=sql;sqlc=sql;sqlrpg=rpg;sqlrpgle=rpg;sqlrple=rpg;");
        preferenceStore.setDefault(Preferences.TEMPLATES_AUTO_SYNC_TEMPLATES, false);
        preferenceStore.setDefault(Preferences.TEMPLATES_AUTO_SYNC_ASSOCIATIONS, false);
        preferenceStore.setDefault(Preferences.TEMPLATES_COMPARE_DATES, true);
        preferenceStore.setDefault(Preferences.TEMPLATES_TEAM_DIRECTORY,
            "\\\\localhost\\AS400_Entwicklung\\Entwicklung\\Programmierung\\Tools\\LPEX FreeMarker Templates\\");
        preferenceStore.setDefault(Preferences.TEMPLATES_LAST_SYNC_DATE, "");
    }

    private String getUserHome() {
        return System.getProperty("user.home");
    }

    private String getDefaultDateFormat() {
        DateFormat tDateFormatter = DateFormat.getDateInstance();
        if (tDateFormatter instanceof SimpleDateFormat) {
            SimpleDateFormat tSimpleFormatter = (SimpleDateFormat)tDateFormatter;
            return tSimpleFormatter.toPattern();
        }
        return "yyyy-MM-dd";
    }

    /**
     * Initializes the preferences. Adds a property change listener to the
     * Eclipse preferences store to keep track of changes.
     */
    private void initialize() {

        // Add change listener to the preference store so that we are notified
        // in case of changes
        preferenceStore.addPropertyChangeListener(new PreferencesChangeListener());
    }

}