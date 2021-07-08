package com.freemarker.lpex.preferences;

import java.io.IOException;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

import com.freemarker.lpex.freemarker.FreeMarkerConfig;
import com.freemarker.lpex.utils.PluginLogger;
import com.freemarker.lpex.utils.TemplateDirectorySyncHelper;

public class PreferencesChangeListener implements IPropertyChangeListener {

    @Override
    public void propertyChange(PropertyChangeEvent anEvent) {
        Preferences tPreferences = Preferences.getInstance();
        if (anEvent.getProperty() == Preferences.LOG_PATH) {
            try {
                PluginLogger.setPath(tPreferences.getLogPath());
            } catch (IOException e) {
                // ignore exception
                PluginLogger.doNothing();
            }
        } else if (anEvent.getProperty() == Preferences.LOG_LEVEL) {
            try {
                PluginLogger.setLevel(tPreferences.getLogLevel());
            } catch (Exception e) {
                // ignore exception
                PluginLogger.doNothing();
            }
        } else if (anEvent.getProperty() == Preferences.TEMPLATES_TEAM_DIRECTORY) {
            try {
                TemplateDirectorySyncHelper.getInstance().setSyncDirectoryPath(tPreferences.getTeamTemplatesDirectory());
            } catch (Exception e) {
                // ignore exception
                PluginLogger.doNothing();
            }
        } else if (anEvent.getProperty() == Preferences.TEMPLATES_AUTO_SYNC_TEMPLATES) {
            try {
                if (tPreferences.isAutoSyncTemplatesEnabled()) {
                    TemplateDirectorySyncHelper.getInstance().enableSyncTemplates();
                    TemplateDirectorySyncHelper.getInstance().enableSyncParserAssociations();
                } else {
                    TemplateDirectorySyncHelper.getInstance().disableSyncTemplates();
                    TemplateDirectorySyncHelper.getInstance().disableSyncParserAssociations();
                }
            } catch (Exception e) {
                // ignore exception
                PluginLogger.doNothing();
            }
        } else if (anEvent.getProperty() == Preferences.TEMPLATES_AUTO_SYNC_ASSOCIATIONS) {
            try {
                if (tPreferences.isAutoSyncParserAssociations()) {
                    TemplateDirectorySyncHelper.getInstance().enableSyncParserAssociations();
                } else {
                    TemplateDirectorySyncHelper.getInstance().disableSyncParserAssociations();
                }
            } catch (Exception e) {
                // ignore exception
                PluginLogger.doNothing();
            }
        } else if (anEvent.getProperty() == Preferences.AUTHOR) {
            try {
                FreeMarkerConfig.getInstance().setAuthor(tPreferences.getAuthor());
            } catch (Exception e) {
                // ignore exception
                PluginLogger.doNothing();
            }
        }
    }

}
