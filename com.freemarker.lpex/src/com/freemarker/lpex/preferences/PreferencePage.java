package com.freemarker.lpex.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.freemarker.lpex.LPEXFreeMarkerPlugin;
import com.freemarker.lpex.utils.PluginLogger;
import com.freemarker.lpex.utils.TemplateDirectorySyncHelper;
import com.freemarker.lpex.widgets.LogFileFieldEditor;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */

public class PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

    private BooleanFieldEditor tAutoSyncTemplatesEditor = null;

    private BooleanFieldEditor tSmartSyncEditor = null;

    private DirectoryFieldEditor tTeamTemplateEditor = null;

    private BooleanFieldEditor tAutoSyncAssociationsEditor;

    public PreferencePage() {
        super(GRID);
    }

    /**
     * Creates the field editors. Field editors are abstractions of the common
     * GUI blocks needed to manipulate various types of preferences. Each field
     * editor knows how to save and restore itself.
     */
    @Override
    public void createFieldEditors() {

        addField(new DirectoryFieldEditor(Preferences.TEMPLATES_LOCAL_DIRECTORY, "Local templates directory:", getFieldEditorParent()));

        /*
         * The following lines have to be enabled when using the WindowsBuilder,
         * because anonymous class creation is not been supported by
         * WindowsBuilder.
         */
        // tAutoSyncEditor = new
        // BooleanFieldEditor(Preferences.TEMPLATES_AUTO_SYNC,
        // "Synchronize team templates", getFieldEditorParent());
        tAutoSyncTemplatesEditor = new BooleanFieldEditor(Preferences.TEMPLATES_AUTO_SYNC_TEMPLATES, "Synchronize team templates",
            getFieldEditorParent()) {
            @Override
            protected void valueChanged(boolean oldValue, boolean newValue) {
                super.valueChanged(oldValue, newValue);
                setButtonEnablement();
            };
        };
        addField(tAutoSyncTemplatesEditor);

        tAutoSyncAssociationsEditor = new BooleanFieldEditor(Preferences.TEMPLATES_AUTO_SYNC_ASSOCIATIONS, "Synchronize parser associations",
            getFieldEditorParent());
        addField(tAutoSyncAssociationsEditor);

        tSmartSyncEditor = new BooleanFieldEditor(Preferences.TEMPLATES_COMPARE_DATES, "Compare modification dates", BooleanFieldEditor.DEFAULT,
            getFieldEditorParent());
        addField(tSmartSyncEditor);

        tTeamTemplateEditor = new DirectoryFieldEditor(Preferences.TEMPLATES_TEAM_DIRECTORY, "Team templates directory:", getFieldEditorParent()) {
            @Override
            public boolean isValid() {
                if (!getChangeControl(getFieldEditorParent()).getEnabled()) {
                    return true;
                }
                return super.isValid();
            }
        };
        addField(tTeamTemplateEditor);

        /*
         * The following lines have to be enabled when using the WindowsBuilder,
         * because LogFileFieldEditor cannot be parsed by WindowsBuilder.
         */
        // FileFieldEditor tLogFileFieldEditor = new
        // FileFieldEditor(Preferences.LOG_PATH, "Debug log:",
        // getFieldEditorParent());
        LogFileFieldEditor tLogFileFieldEditor = new LogFileFieldEditor(Preferences.LOG_PATH, "Debug log:", getFieldEditorParent());
        tLogFileFieldEditor.setFileExtensions(new String[] { "log", "*" });
        addField(tLogFileFieldEditor);

        addField(new RadioGroupFieldEditor(Preferences.LOG_LEVEL,
            "Choose the logging level", 1, new String[][] { { "All", "all" }, { "Info", PluginLogger.PRIORITY_INFO },
                { "Warning", PluginLogger.PRIORITY_WARNING }, { "Severe", PluginLogger.PRIORITY_SEVERE }, { "Off", PluginLogger.PRIORITY_OFF } },
            getFieldEditorParent()));

        addField(new StringFieldEditor(Preferences.AUTHOR, "Default author:", getFieldEditorParent()));

        addField(new StringFieldEditor(Preferences.DATE_FORMAT, "Date format (java.text.SimpleDateFormat):", getFieldEditorParent()));

        addField(new BooleanFieldEditor(Preferences.SHOW_ECLIPSE_ERROR_LOG_ON_ERROR, "Show Eclipse error log on error", getFieldEditorParent()));
    }

    private void setButtonEnablement() {
        Composite tParent = getFieldEditorParent();
        if (tAutoSyncTemplatesEditor.getBooleanValue()) {
            tSmartSyncEditor.setEnabled(true, tParent);
            tTeamTemplateEditor.setEnabled(true, tParent);
            tAutoSyncAssociationsEditor.setEnabled(true, tParent);
        } else {
            tSmartSyncEditor.setEnabled(false, tParent);
            tTeamTemplateEditor.setEnabled(false, tParent);
            tAutoSyncAssociationsEditor.setEnabled(false, tParent);
        }

        checkState();
    }

    @Override
    public boolean performOk() {
        boolean tIsOK = super.performOk();
        if (tIsOK) {
            TemplateDirectorySyncHelper.getInstance().syncLocalTemplatesImmediately();
        }
        return tIsOK;
    }

    @Override
    protected void initialize() {
        super.initialize();
        setButtonEnablement();
    }

    /*
     * (non-Javadoc)
     * @see
     * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    @Override
    public void init(IWorkbench workbench) {
        setPreferenceStore(LPEXFreeMarkerPlugin.getDefault().getPreferenceStore());
        setDescription("Specify settings for LPEX FreeMarker templates.");
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title + " - v" + LPEXFreeMarkerPlugin.getVersion());
    }
}