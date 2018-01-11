package com.freemarker.lpex.widgets;

import java.io.File;

import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.StringButtonFieldEditor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;

import com.freemarker.lpex.preferences.PreferencesResources;

public class LogFileFieldEditor extends FileFieldEditor {

    /**
     * List of legal file extension suffixes, or <code>null</code> for system
     * defaults.
     */
    private String[] extensions = null;

    /**
     * Initial path for the Browse dialog.
     */
    private File filterPath = null;

    /**
     * Indicates whether the path must be absolute; <code>false</code> by
     * default.
     */
    private boolean enforceAbsolute = false;

    /**
     * Creates a new file field editor
     */
    protected LogFileFieldEditor() {
    }

    /**
     * Creates a file field editor.
     * 
     * @param name the name of the preference this field editor works on
     * @param labelText the label text of the field editor
     * @param parent the parent of the field editor's control
     */
    public LogFileFieldEditor(String name, String labelText, Composite parent) {
        this(name, labelText, false, parent);
    }

    /**
     * Creates a file field editor.
     * 
     * @param aName the name of the preference this field editor works on
     * @param aLabelText the label text of the field editor
     * @param anEnforceAbsolute <code>true</code> if the file path must be
     *        absolute, and <code>false</code> otherwise
     * @param aParent the parent of the field editor's control
     */
    public LogFileFieldEditor(String aName, String aLabelText, boolean anEnforceAbsolute, Composite aParent) {
        this(aName, aLabelText, anEnforceAbsolute, VALIDATE_ON_FOCUS_LOST, aParent);
    }

    /**
     * Creates a file field editor.
     * 
     * @param aName the name of the preference this field editor works on
     * @param aLabelText the label text of the field editor
     * @param anEnforceAbsolute <code>true</code> if the file path must be
     *        absolute, and <code>false</code> otherwise
     * @param aValidationStrategy either
     *        {@link StringButtonFieldEditor#VALIDATE_ON_KEY_STROKE} to perform
     *        on the fly checking, or
     *        {@link StringButtonFieldEditor#VALIDATE_ON_FOCUS_LOST} (the
     *        default) to perform validation only after the text has been typed
     *        in
     * @param aParent the parent of the field editor's control.
     * @since 3.4
     * @see StringButtonFieldEditor#VALIDATE_ON_KEY_STROKE
     * @see StringButtonFieldEditor#VALIDATE_ON_FOCUS_LOST
     */
    public LogFileFieldEditor(String aName, String aLabelText, boolean anEnforceAbsolute, int aValidationStrategy, Composite aParent) {
        init(aName, aLabelText);
        this.enforceAbsolute = anEnforceAbsolute;
        setErrorMessage(PreferencesResources.getString("LogFileFieldEditor.errorMessage"));
        setChangeButtonText(JFaceResources.getString("openBrowse"));
        setValidateStrategy(aValidationStrategy);
        createControl(aParent);
    }

    /*
     * (non-Javadoc) Method declared on StringButtonFieldEditor. Opens the file
     * chooser dialog and returns the selected file.
     */
    @Override
    protected String changePressed() {
        File f = new File(getTextControl().getText());
        File tDirectory = f.getParentFile();
        if (!f.exists() && (tDirectory != null && !tDirectory.exists())) {
            f = null;
        }
        File d = getFile(f);
        if (d == null) {
            return null;
        }

        return d.getAbsolutePath();
    }

    /*
     * (non-Javadoc) Method declared on StringFieldEditor. Checks whether the
     * text input field specifies an existing file.
     */
    @Override
    protected boolean checkState() {

        String tMsg = null;

        String tPath = getTextControl().getText();
        if (tPath != null) {
            tPath = tPath.trim();
        } else {
            tPath = "";
        }
        if (tPath.length() == 0) {
            if (!isEmptyStringAllowed()) {
                tMsg = getErrorMessage();
            }
        } else {
            File tFile = new File(tPath);
            File tParent = tFile.getParentFile();
            if (tFile.isFile()) {
                if (enforceAbsolute && !tFile.isAbsolute()) {
                    tMsg = JFaceResources.getString("LogFileFieldEditor.errorMessage2");
                }
            } else if (tParent != null && tParent.isDirectory()) {
                if (enforceAbsolute && !tParent.isAbsolute()) {
                    tMsg = JFaceResources.getString("LogFileFieldEditor.errorMessage2");
                }
            } else {
                tMsg = getErrorMessage();
            }
        }

        if (tMsg != null) { // error
            showErrorMessage(tMsg);
            return false;
        }

        if (doCheckState()) { // OK!
            clearErrorMessage();
            return true;
        }
        tMsg = getErrorMessage(); // subclass might have changed it in the
                                  // #doCheckState()
        if (tMsg != null) {
            showErrorMessage(tMsg);
        }
        return false;
    }

    /**
     * Helper to open the file chooser dialog.
     * 
     * @param startingDirectory the directory to open the dialog on.
     * @return File The File the user selected or <code>null</code> if they do
     *         not.
     */
    private File getFile(File startingDirectory) {

        FileDialog tDialog = new FileDialog(getShell(), SWT.OPEN | SWT.SHEET);
        if (startingDirectory != null) {
            tDialog.setFileName(startingDirectory.getPath());
        } else if (filterPath != null) {
            tDialog.setFilterPath(filterPath.getPath());
        }
        if (extensions != null) {
            tDialog.setFilterExtensions(extensions);
        }
        String tFile = tDialog.open();
        if (tFile != null) {
            tFile = tFile.trim();
            if (tFile.length() > 0) {
                return new File(tFile);
            }
        }

        return null;
    }

    /**
     * Sets this file field editor's file extension filter.
     * 
     * @param anExtensions a list of file extension, or <code>null</code> to set
     *        the filter to the system's default value
     */
    @Override
    public void setFileExtensions(String[] anExtensions) {
        this.extensions = anExtensions;
    }

    /**
     * Sets the initial path for the Browse dialog.
     * 
     * @param path initial path for the Browse dialog
     * @since 3.6
     */
    @Override
    public void setFilterPath(File path) {
        filterPath = path;
    }

}
