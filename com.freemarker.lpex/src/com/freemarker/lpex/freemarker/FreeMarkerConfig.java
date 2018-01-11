package com.freemarker.lpex.freemarker;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.eclipse.rse.services.clientserver.messages.SystemMessageException;

import com.freemarker.lpex.formdialogs.LPEXTemplate;
import com.freemarker.lpex.preferences.Preferences;
import com.freemarker.lpex.preferences.PreferencesChangeListener;
import com.freemarker.lpex.utils.PluginLogger;
import com.ibm.etools.iseries.subsystems.qsys.api.IBMiConnection;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateModelException;

public final class FreeMarkerConfig {

    private static final String USER_DEFINED_DIRECTIVE_MULTILINE = "multiline";

    private static final String USER_DEFINED_DIRECTIVE_KEY_LIST = "keyList";

    private static final String USER_DEFINED_DIRECTIVE_FIELD_LIST = "fieldList";

    private static final String USER_DEFINED_DIRECTIVE_WORDWRAP = "wordwrap";

    private static final String GLOBAL_VARIABLE_DATE = "date";

    private static final String GLOBAL_VARIABLE_AUTHOR = "author";

    /**
     * The instance of this Singleton class.
     */
    private static FreeMarkerConfig instance;

    /**
     * The FreeMarker configuration.
     */
    private Configuration freemarkerConfig = null;

    /**
     * The active IBMiConnection
     */
    private IBMiConnection connection = null;

    /**
     * Private constructor to ensure the Singleton pattern.
     */
    private FreeMarkerConfig() {
    }

    /**
     * Thread-safe method that returns the instance of this Singleton class.
     * 
     * @throws Exception
     */
    public synchronized static FreeMarkerConfig getInstance() throws Exception {
        if (instance == null) {
            instance = new FreeMarkerConfig();
            instance.initialize();
        }
        return instance;
    }

    /**
     * Returns the FreeMarker configuration. Called by
     * {@link LPEXTemplate#merge()} when the template is merged with the data
     * model.
     * 
     * @return FreeMarker configuration settings
     * @throws TemplateModelException
     */
    public Configuration getConfiguration() throws TemplateModelException {
        updateDate();
        return freemarkerConfig;
    }

    /**
     * Sets global FreeMarker variable "author". Called by the
     * {@link PreferencesChangeListener} when the preferences changed.
     * 
     * @param anAuthor default author
     * @throws TemplateModelException
     */
    public void setAuthor(String anAuthor) throws TemplateModelException {
        freemarkerConfig.setSharedVariable(GLOBAL_VARIABLE_AUTHOR, anAuthor);
    }

    /**
     * Initializes FreeMarker configuration.
     * 
     * @throws Exception
     */
    private void initialize() throws Exception {

        // Setup plugin configured constants
        try {
            Calendar tCalendar = Calendar.getInstance();
            SimpleDateFormat tDateFormatter = new SimpleDateFormat(Preferences.getInstance().getDateFormat());
            String tToday = tDateFormatter.format(tCalendar.getTime());

            freemarkerConfig = new Configuration();
            freemarkerConfig.setObjectWrapper(new DefaultObjectWrapper());
            freemarkerConfig.setSharedVariable(GLOBAL_VARIABLE_AUTHOR, Preferences.getInstance().getAuthor());
            freemarkerConfig.setSharedVariable(GLOBAL_VARIABLE_DATE, tToday);
            freemarkerConfig.setSharedVariable(USER_DEFINED_DIRECTIVE_MULTILINE, new com.freemarker.lpex.freemarker.MultilineDirective());
            freemarkerConfig.setSharedVariable(USER_DEFINED_DIRECTIVE_KEY_LIST, new com.freemarker.lpex.freemarker.KeyListDirective());
            freemarkerConfig.setSharedVariable(USER_DEFINED_DIRECTIVE_FIELD_LIST, new com.freemarker.lpex.freemarker.FieldListDirective());
            freemarkerConfig.setSharedVariable(USER_DEFINED_DIRECTIVE_WORDWRAP, new com.freemarker.lpex.freemarker.WordwrapDirective());
        } catch (Exception e) {
            throw new Exception("Failed to create FreeMarker configuration.", e);
        }
    }

    /**
     * Internally used to update global variable "date" whenever {@link
     * this#getConfiguration()} is called.
     * 
     * @throws TemplateModelException
     */
    private void updateDate() throws TemplateModelException {
        SimpleDateFormat tSimpleDateFormat = new SimpleDateFormat(Preferences.getInstance().getDateFormat());
        String tToday = tSimpleDateFormat.format(Calendar.getInstance().getTime());
        freemarkerConfig.setSharedVariable(GLOBAL_VARIABLE_DATE, tToday);
    }

    public void setCurrentConnection(IBMiConnection aConnection) {
        connection = aConnection;
    }

    public IBMiConnection getCurrentConnection() throws SQLException {
        try {
            if (!connection.isConnected()) {
                connection.connect();
                if (!connection.isConnected()) {
                    return null;
                }
            }

            return connection;
        } catch (SystemMessageException e) {
            PluginLogger.logger.severe(e.getLocalizedMessage());
            throw new SQLException(e.getLocalizedMessage(), e);
        }
    }
}
