package com.freemarker.lpex.preferences;

import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.freemarker.lpex.LPEXFreeMarkerPlugin;
import com.freemarker.lpex.utils.PluginLogger;

public class ParserAssociationPreferencePage extends PreferencePage implements IWorkbenchPreferencePage, IPropertyChangeListener {

    // The list that displays the current parsers
    private List parserList;

    // The newEntryText is the text where new bad words are specified
    private Text newEntryText;

    // Button, that adds a new entry to the list of parsers
    private Button addButton;

    private Button removeButton;

    private static Pattern pattern = null;

    public ParserAssociationPreferencePage() {
        super();
    }

    /*
     * @see PreferencePage#createContents(Composite)
     */
    @Override
    protected Control createContents(Composite parent) {

        IPreferenceStore tStore = LPEXFreeMarkerPlugin.getDefault().getPreferenceStore();

        Composite tEntryTable = new Composite(parent, SWT.NULL);

        // Create a data that takes up the extra space in the dialog .
        GridData tGridData = new GridData(GridData.FILL_HORIZONTAL);
        tGridData.grabExcessHorizontalSpace = true;
        tEntryTable.setLayoutData(tGridData);

        GridLayout tLayout = new GridLayout();
        tEntryTable.setLayout(tLayout);

        // Add in a dummy label for spacing
        new Label(tEntryTable, SWT.NONE);

        // Create list for parser associations
        parserList = new List(tEntryTable, SWT.BORDER | SWT.V_SCROLL);
        parserList.setItems(parseToArray(tStore.getString(Preferences.PARSER_MAPPINGS)));

        // Create a data that takes up the extra space in the dialog and spans
        // both columns.
        tGridData = new GridData(GridData.FILL_BOTH);
        parserList.setLayoutData(tGridData);
        parserList.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent paramSelectionEvent) {
                setRemoveButtonEnablement();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent paramSelectionEvent) {
            }
        });

        Composite tButtonComposite = new Composite(tEntryTable, SWT.NULL);

        GridLayout tButtonLayout = new GridLayout();
        tButtonLayout.numColumns = 2;
        tButtonComposite.setLayout(tButtonLayout);

        // Create a data that takes up the extra space in the dialog and spans
        // both columns.
        tGridData = new GridData(GridData.FILL_BOTH | GridData.VERTICAL_ALIGN_BEGINNING);
        tButtonComposite.setLayoutData(tGridData);

        addButton = new Button(tButtonComposite, SWT.PUSH | SWT.CENTER);

        addButton.setText("Add to List");
        addButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                parserList.add(newEntryText.getText(), parserList.getItemCount());
                newEntryText.setText("");
            }
        });

        newEntryText = new Text(tButtonComposite, SWT.BORDER);
        // Create a data that takes up the extra space in the dialog .
        tGridData = new GridData(GridData.FILL_HORIZONTAL);
        tGridData.grabExcessHorizontalSpace = true;
        newEntryText.setLayoutData(tGridData);
        newEntryText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent anEvent) {
                setNewEntryButtonEnablement();
            }
        });

        removeButton = new Button(tButtonComposite, SWT.PUSH | SWT.CENTER);

        removeButton.setText("Remove Selection");
        removeButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                parserList.remove(parserList.getSelectionIndex());
            }
        });

        tGridData = new GridData();
        tGridData.horizontalSpan = 2;
        removeButton.setLayoutData(tGridData);

        setNewEntryButtonEnablement();
        setRemoveButtonEnablement();

        return tEntryTable;
    }

    public static String[] parseToArray(String aPreferenceValue) {
        StringTokenizer tTokenizer = new StringTokenizer(aPreferenceValue, ";");
        int tTokenCount = tTokenizer.countTokens();
        String[] tElements = new String[tTokenCount];

        for (int i = 0; i < tTokenCount; i++) {
            tElements[i] = tTokenizer.nextToken();
        }

        Arrays.sort(tElements);

        return tElements;
    }

    /*
     * @see IWorkbenchPreferencePage#init(IWorkbench)
     */
    @Override
    public void init(IWorkbench aWorkbench) {
        // Initialize the preference store we wish to use
        setPreferenceStore(LPEXFreeMarkerPlugin.getDefault().getPreferenceStore());
        setDescription("Setup the parser template folder associations.");

        getPreferenceStore().addPropertyChangeListener(this);
    }

    /**
     * Performs special processing when this page's Restore Defaults button has
     * been pressed. Sets the contents of the nameEntry field to be the default
     */
    @Override
    protected void performDefaults() {
        IPreferenceStore tStore = LPEXFreeMarkerPlugin.getDefault().getPreferenceStore();
        parserList.setItems(parseToArray(tStore.getDefaultString(Preferences.PARSER_MAPPINGS)));
    }

    /**
     * Method declared on IPreferencePage. Save the author name to the
     * preference store.
     */
    @Override
    public boolean performOk() {
        IPreferenceStore tStore = LPEXFreeMarkerPlugin.getDefault().getPreferenceStore();
        StringBuffer tBuffer = new StringBuffer();
        for (int i = 0; i < parserList.getItems().length; i++) {
            tBuffer.append(parserList.getItems()[i]);
            tBuffer.append(";");
        }
        tStore.setValue(Preferences.PARSER_MAPPINGS, tBuffer.toString());
        return super.performOk();
    }

    private void setNewEntryButtonEnablement() {
        if (newEntryText.getText() == null || newEntryText.getText().trim().length() == 0) {
            addButton.setEnabled(false);
        } else {
            addButton.setEnabled(checkParserAssociation());
        }
    }

    private void setRemoveButtonEnablement() {
        if (parserList == null || parserList.getSelectionCount() == 0) {
            removeButton.setEnabled(false);
        } else {
            removeButton.setEnabled(true);
        }
    }

    private boolean checkParserAssociation() {
        String tText = newEntryText.getText();
        if (tText == null || tText.trim().length() == 0) {
            return false;
        }
        if (!isValidAssociation(tText)) {
            return false;
        }
        String tFolder = tText.substring(tText.indexOf("=") + 1);
        return isValidName(tFolder);
    }

    private boolean isValidAssociation(String aText) {
        return aText.matches(".+=.+");
    }

    public static boolean isValidName(String text) {
        if (pattern == null) {
            pattern = Pattern.compile("# Match a valid Windows filename (unspecified file system).          \n"
                + "^                                # Anchor to start of string.        \n"
                + "(?!                              # Assert filename is not: CON, PRN, \n"
                + "  (?:                            # AUX, NUL, COM1, COM2, COM3, COM4, \n"
                + "    CON|PRN|AUX|NUL|             # COM5, COM6, COM7, COM8, COM9,     \n"
                + "    COM[1-9]|LPT[1-9]            # LPT1, LPT2, LPT3, LPT4, LPT5,     \n"
                + "  )                              # LPT6, LPT7, LPT8, and LPT9...     \n"
                + "  (?:\\.[^.]*)?                  # followed by optional extension    \n"
                + "  $                              # and end of string                 \n"
                + ")                                # End negative lookahead assertion. \n"
                + "[^<>:\"/\\\\|?*\\x00-\\x1F]*     # Zero or more valid filename chars.\n"
                + "[^<>:\"/\\\\|?*\\x00-\\x1F\\ .]  # Last char is not a space or dot.  \n"
                + "$                                # Anchor to end of string.            ", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE
                | Pattern.COMMENTS);
        }
        Matcher matcher = pattern.matcher(text);
        boolean isMatch = matcher.matches();
        return isMatch;
    }

    @Override
    public void propertyChange(PropertyChangeEvent anEvent) {
        if (anEvent.getProperty() == Preferences.PARSER_MAPPINGS) {
            try {
                IPreferenceStore tStore = LPEXFreeMarkerPlugin.getDefault().getPreferenceStore();
                parserList.setItems(parseToArray(tStore.getString(Preferences.PARSER_MAPPINGS)));
            } catch (Exception e) {
                // ignore exception
                PluginLogger.doNothing();
            }
        }
    }
}