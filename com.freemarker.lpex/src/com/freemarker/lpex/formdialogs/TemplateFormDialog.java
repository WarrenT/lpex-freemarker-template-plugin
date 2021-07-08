package com.freemarker.lpex.formdialogs;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.freemarker.lpex.preferences.Preferences;
import com.freemarker.lpex.utils.StringUtil;

public class TemplateFormDialog extends AbstractTemplateFormDialog {

    /*
     * Column lables
     */
    private static final String COLUMN_LABEL_REPEAT_INDEX = "Repeat";

    private static final String COLUMN_LABEL_PARAMETER = "Parameter";

    private static final String COLUM_LABEL_VALUE = "Value";

    /*
     * Widget template data
     */
    private static final String DATA_DATE_FORMAT = "template_dateFormat";

    private static final String DATA_DESCRIPTION = "template_description";

    private static final String DATA_HINT = "template_hint";

    private static final String DATA_REPEAT_INDEX = "template_repeatIndex";

    private static final String DATA_PROMPT_NAME = "template_promptName";

    private static final String DATA_PROMPT_GROUP_NAME = "template_promptGroupName";

    private static final String DATA_UNCHECKED_VALUE = "uncheckedValue";

    private static final String DATA_CHECKED_VALUE = "checkedValue";

    private static final String DATA_UPPER_CASE = "upperCase";

    /*
     * Number Widget template data
     */
    private static final String DATA_MAX_VALUE = "maxValue";

    private static final String DATA_MAX_DEC_POS = "maxDecPos";

    /*
     * TabItem controls
     */
    private static final String DATA_THE_DESCRIPTION_CONTROL = "theDescriptionControl";

    private static final String DATA_THE_TABLE_CONTROL = "theTableControl";

    protected int result;

    protected Shell shlLpexFreemarkerTemplate;

    private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());

    private Button btnOK;

    private Button btnCancel;

    private Button btnPrevious;

    private Button btnNext;

    private static final int TABLE_COLUMN_DESCRIPTION = 0;

    /**
     * Create the dialog. Default application constructor.
     * 
     * @param aFKLTemplate
     */
    public TemplateFormDialog(FKLTemplate aFKLTemplate) {
        super(aFKLTemplate);
    }

    /**
     * Open the dialog.
     * 
     * @return the result
     */
    public int open() {
        createContents();
        shlLpexFreemarkerTemplate.open();
        shlLpexFreemarkerTemplate.layout();
        Display tDisplay = getParent().getDisplay();
        while (!shlLpexFreemarkerTemplate.isDisposed()) {
            if (!tDisplay.readAndDispatch()) {
                tDisplay.sleep();
            }
        }
        return result;
    }

    /**
     * Create contents of the dialog.
     */
    private void createContents() {
        shlLpexFreemarkerTemplate = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.RESIZE | SWT.APPLICATION_MODAL);
        shlLpexFreemarkerTemplate.setSize(670, 560);
        shlLpexFreemarkerTemplate.setText("LPEX FreeMarker Template: " + getFKLTemplate().getName());
        shlLpexFreemarkerTemplate.setLayout(new FormLayout());

        btnOK = new Button(shlLpexFreemarkerTemplate, SWT.NONE);
        FormData tBtnOK = new FormData();
        tBtnOK.top = new FormAttachment(100, -33);
        tBtnOK.bottom = new FormAttachment(100, -10);
        tBtnOK.left = new FormAttachment(100, -91);
        tBtnOK.right = new FormAttachment(100, -10);
        btnOK.setLayoutData(tBtnOK);
        btnOK.setText("&OK");
        btnOK.addListener(SWT.Selection, new ButtonOKClickedListener());

        btnCancel = new Button(shlLpexFreemarkerTemplate, SWT.NONE);
        FormData tBtnCancel = new FormData();
        tBtnCancel.top = new FormAttachment(100, -33);
        tBtnCancel.bottom = new FormAttachment(100, -10);
        tBtnCancel.left = new FormAttachment(btnOK, -87, SWT.LEFT);
        tBtnCancel.right = new FormAttachment(btnOK, -6);
        btnCancel.setLayoutData(tBtnCancel);
        formToolkit.adapt(btnCancel, true, true);
        btnCancel.setText("Cancel");
        btnCancel.addListener(SWT.Selection, new ButtonCancelClickedListener());

        Label tLblButtonSeparator = new Label(shlLpexFreemarkerTemplate, SWT.SEPARATOR | SWT.HORIZONTAL);
        FormData tLblButtonSeparatorFormData = new FormData();
        tLblButtonSeparatorFormData.top = new FormAttachment(100, -46);
        tLblButtonSeparatorFormData.bottom = new FormAttachment(100, -44);
        tLblButtonSeparatorFormData.left = new FormAttachment(0, 10);
        tLblButtonSeparatorFormData.right = new FormAttachment(100, -10);
        tLblButtonSeparator.setLayoutData(tLblButtonSeparatorFormData);
        formToolkit.adapt(tLblButtonSeparator, true, true);

        final TabFolder tabParameters = new TabFolder(shlLpexFreemarkerTemplate, SWT.NONE);
        FormData tTabParametersFormData = new FormData();
        tTabParametersFormData.bottom = new FormAttachment(tLblButtonSeparator, -16);
        tTabParametersFormData.top = new FormAttachment(0, 10);
        tTabParametersFormData.left = new FormAttachment(0, 10);
        tTabParametersFormData.right = new FormAttachment(100, -10);
        tabParameters.setLayoutData(tTabParametersFormData);
        formToolkit.adapt(tabParameters);
        formToolkit.paintBordersFor(tabParameters);
        tabParameters.addSelectionListener(new TabSelectedListener(tabParameters));

        btnPrevious = new Button(shlLpexFreemarkerTemplate, SWT.NONE);
        btnPrevious.addListener(SWT.Selection, new ButtonPreviousClickedListener(tabParameters));
        FormData tBtnPreviousFormData = new FormData();
        tBtnPreviousFormData.top = new FormAttachment(btnOK, 0, SWT.TOP);
        btnPrevious.setLayoutData(tBtnPreviousFormData);
        formToolkit.adapt(btnPrevious, true, true);
        btnPrevious.setText("&Previous");

        btnNext = new Button(shlLpexFreemarkerTemplate, SWT.NONE);
        tBtnPreviousFormData.left = new FormAttachment(btnNext, -87, SWT.LEFT);
        tBtnPreviousFormData.right = new FormAttachment(btnNext, -6);
        btnNext.addListener(SWT.Selection, new ButtonNextClickedListener(tabParameters));
        FormData tBtnNextFormData = new FormData();
        tBtnNextFormData.left = new FormAttachment(btnCancel, -121, SWT.LEFT);
        tBtnNextFormData.right = new FormAttachment(btnCancel, -40);
        tBtnNextFormData.bottom = new FormAttachment(btnOK, 0, SWT.BOTTOM);
        tBtnNextFormData.top = new FormAttachment(btnOK, 0, SWT.TOP);
        btnNext.setLayoutData(tBtnNextFormData);
        formToolkit.adapt(btnNext, true, true);
        btnNext.setText("&Next");

        createParameterTabs(tabParameters);
    }

    private void createParameterTabs(TabFolder aTabFolder) {
        boolean tHasFocus = false;
        ArrayList<FKLPromptGroup> tPromptGoups = getFKLTemplate().getPromptGroups();
        for (FKLPromptGroup tPromptGroup : tPromptGoups) {
            tHasFocus = createParameterTab(aTabFolder, tPromptGroup, tHasFocus);
        }

        if (tPromptGoups.size() > 1) {
            btnPrevious.setEnabled(true);
            btnPrevious.setVisible(true);
            btnNext.setEnabled(true);
            btnNext.setVisible(true);
        } else {
            btnPrevious.setEnabled(false);
            btnPrevious.setVisible(false);
            btnNext.setEnabled(false);
            btnNext.setVisible(false);
        }

        enableDisableTabButtons(aTabFolder);
    }

    private boolean createParameterTab(TabFolder aTabFolder, FKLPromptGroup aPromptGroup, boolean aHasFocus) {

        Composite tPanel = new Composite(aTabFolder, SWT.NONE);
        tPanel.setLayout(new GridLayout(1, false));

        Label tDescription = new Label(tPanel, SWT.NONE);
        GridData tGridData = new GridData();
        tGridData.horizontalAlignment = SWT.FILL;
        tGridData.grabExcessHorizontalSpace = true;
        tGridData.grabExcessVerticalSpace = false;
        tDescription.setLayoutData(tGridData);

        if (aPromptGroup.isRepeatable()) {
            aHasFocus = createRepeatablePromptGroup(tPanel, aTabFolder, aPromptGroup, tDescription, aHasFocus);
        } else {
            aHasFocus = createNonRepeatablePromptGroup(aTabFolder, aPromptGroup, tDescription, aHasFocus);
        }
        return aHasFocus;
    }

    private boolean createNonRepeatablePromptGroup(TabFolder aTabFolder, FKLPromptGroup aPromptGroup, Control aDescriptionControl, boolean aHasFocus) {

        ScrolledComposite tScrolledComposite = new ScrolledComposite(aTabFolder, SWT.H_SCROLL | SWT.V_SCROLL);
        tScrolledComposite.setExpandHorizontal(true);
        tScrolledComposite.setExpandVertical(true);

        TabItem tParameterTab = new TabItem(aTabFolder, SWT.NONE);
        tParameterTab.setText(aPromptGroup.getName());
        tParameterTab.setControl(tScrolledComposite);
        tParameterTab.setData(DATA_THE_TABLE_CONTROL, tScrolledComposite);

        Composite tComposite = new Composite(tScrolledComposite, SWT.NONE);
        tComposite.setLayout(new GridLayout(2, false));
        tScrolledComposite.setContent(tComposite);

        aDescriptionControl.setParent(tComposite);
        GridData tGridData = new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1);
        tGridData.widthHint = 400;
        aDescriptionControl.setLayoutData(tGridData);

        ArrayList<FKLAbstractPrompt> tPrompts = aPromptGroup.getPrompts();

        int tPromptCount = 0;
        for (FKLAbstractPrompt tPrompt : tPrompts) {
            if (!(tPrompt instanceof FKLPromptUserDefined)) {
                tPromptCount++;
                Control tControl = createNonRepeatablePrompt(aTabFolder, tComposite, aPromptGroup, null, tPromptCount, tPrompt, aDescriptionControl);
                if (tControl != null && !aHasFocus) {
                    tControl.setFocus();
                    aHasFocus = true;
                }
            }
        }
        return aHasFocus;
    }

    private Control createNonRepeatablePrompt(TabFolder aTabFolder, Composite aParent, FKLPromptGroup aPromptGroup, Integer aRepeatIndex,
        int aPromptCount, FKLAbstractPrompt aPrompt, Control aDescriptionControl) {

        // Create label
        Label tLabel = new Label(aParent, SWT.NONE);
        GridData tGridData = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
        tGridData.widthHint = 180;
        tLabel.setLayoutData(tGridData);
        if (aPrompt instanceof ILabel) {
            ILabel tLabelPrompt = (ILabel)aPrompt;
            tLabel.setText(tLabelPrompt.getLabel());
        }

        // Render the control
        GridData tControlGridData = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
        Control tControl = null;
        if (aPrompt instanceof FKLPromptText) {
            FKLPromptText tPromptText = (FKLPromptText)aPrompt;
            tControl = renderTextField(aParent, aPromptGroup.getName(), aRepeatIndex, tPromptText);
        } else if (aPrompt instanceof FKLPromptMultiline) {
            FKLPromptMultiline tPromptMultiline = (FKLPromptMultiline)aPrompt;
            tControl = renderMultilineField(aParent, aPromptGroup.getName(), aRepeatIndex, tPromptMultiline);
            tControlGridData.heightHint = 66;
        } else if (aPrompt instanceof FKLPromptNumber) {
            FKLPromptNumber tPromptNumber = (FKLPromptNumber)aPrompt;
            tControl = renderNumberField(aParent, aPromptGroup.getName(), aRepeatIndex, tPromptNumber);
        } else if (aPrompt instanceof FKLPromptCheckbox) {
            FKLPromptCheckbox tPromptCheckbox = (FKLPromptCheckbox)aPrompt;
            tControl = renderCheckboxField(aParent, aPromptGroup.getName(), aRepeatIndex, tPromptCheckbox);
        } else if (aPrompt instanceof FKLPromptDate) {
            FKLPromptDate tPromptDate = (FKLPromptDate)aPrompt;
            tControl = renderDateField(aParent, aPromptGroup.getName(), aRepeatIndex, tPromptDate);
        }

        tControl.setLayoutData(tControlGridData);

        inputChanged(tControl);

        // Add a OK key listener to the last control of the prompt group
        // but do n ot add it to a multiline text control
        if (aPromptCount == aPromptGroup.getPrompts().size()) {
            if (!(aPrompt instanceof FKLPromptMultiline)) {
                tControl.addKeyListener(new TabLastInputFieldKeyListener(aTabFolder));
            }
        }

        tControl.addFocusListener(new InputFieldFocusListener());
        tControl.setData(DATA_THE_DESCRIPTION_CONTROL, aDescriptionControl);

        return tControl;
    }

    private boolean createRepeatablePromptGroup(Composite aPanel, TabFolder aTabFolder, FKLPromptGroup aPromptGroup, Control aDescriptionControl,
        boolean aHasFocus) {

        Table tParameterTable = new Table(aPanel, SWT.BORDER | SWT.MULTI);
        GridData tGridData = new GridData();
        tGridData.horizontalAlignment = SWT.FILL;
        tGridData.grabExcessHorizontalSpace = true;
        tGridData.verticalAlignment = SWT.FILL;
        tGridData.grabExcessVerticalSpace = true;
        tParameterTable.setLayoutData(tGridData);
        tParameterTable.setHeaderVisible(true);
        tParameterTable.setLinesVisible(true);

        TabItem tParameterTab = new TabItem(aTabFolder, SWT.NONE);
        tParameterTab.setText(aPromptGroup.getName());
        tParameterTab.setControl(aPanel);
        tParameterTab.setData(DATA_THE_TABLE_CONTROL, tParameterTable);

        TableColumn tTTableColumnParameter = new TableColumn(tParameterTable, SWT.NONE);
        if (aPromptGroup.isRepeatable()) {
            tTTableColumnParameter.setText(COLUMN_LABEL_REPEAT_INDEX);
            tTTableColumnParameter.setWidth(50);
        } else {
            tTTableColumnParameter.setText(COLUMN_LABEL_PARAMETER);
            tTTableColumnParameter.setWidth(200);
        }

        for (int i = 0; i < aPromptGroup.getMaxRepeats(); i++) {
            int tPromptCount = 0;
            for (FKLAbstractPrompt tPrompt : aPromptGroup.getPrompts()) {
                if (!(tPrompt instanceof FKLPromptUserDefined)) {
                    tPromptCount++;
                    Integer tRepeatIndex;
                    if (aPromptGroup.isRepeatable()) {
                        tRepeatIndex = new Integer(i);
                    } else {
                        tRepeatIndex = null;
                    }

                    Control tControl = createRepeatablePrompt(aTabFolder, tParameterTable, aPromptGroup, tRepeatIndex, tPromptCount, tPrompt,
                        aDescriptionControl);

                    if (tControl != null && !aHasFocus) {
                        tControl.setFocus();
                        aHasFocus = true;
                    }
                }
            }
        }
        return aHasFocus;
    }

    private Control createRepeatablePrompt(TabFolder aTabFolder, Table aParameterTable, FKLPromptGroup aPromptGroup, Integer aRepeatIndex,
        int aPromptCount, FKLAbstractPrompt aPrompt, Control aDescriptionControl) {

        // Add a new table column
        if (aRepeatIndex.intValue() == 0) {
            TableColumn tTableColumnValue = new TableColumn(aParameterTable, SWT.NONE);
            tTableColumnValue.setWidth(180);
            if (aPrompt instanceof ILabel) {
                ILabel tLabelPrompt = (ILabel)aPrompt;
                tTableColumnValue.setText(tLabelPrompt.getLabel());
            }
        }

        // Add a new table row or get an existing row for repeatable prompt
        // groups
        TableItem tRow = null;
        if (aPromptCount == 1) {
            tRow = new TableItem(aParameterTable, SWT.NONE);
            tRow.setText(TABLE_COLUMN_DESCRIPTION, (aRepeatIndex.intValue() + 1) + ".");
        } else {
            int tLastRowIndex = aParameterTable.getItems().length - 1;
            tRow = aParameterTable.getItem(tLastRowIndex);
        }

        // Determine the column index where to add the editor
        int tColumnIndex = aPromptCount;

        // Render the control
        Control tControl = null;
        if (aPrompt instanceof FKLPromptText) {
            FKLPromptText tPromptText = (FKLPromptText)aPrompt;
            tControl = renderTextFieldForTable(aParameterTable, aPromptGroup.getName(), aRepeatIndex, tPromptText, tRow, tColumnIndex);
        } else if (aPrompt instanceof FKLPromptMultiline) {
            FKLPromptMultiline tPromptMultiline = (FKLPromptMultiline)aPrompt;
            tControl = renderMultilineFieldForTable(aParameterTable, aPromptGroup.getName(), aRepeatIndex, tPromptMultiline, tRow, tColumnIndex);
        } else if (aPrompt instanceof FKLPromptCheckbox) {
            FKLPromptCheckbox tPromptCheckbox = (FKLPromptCheckbox)aPrompt;
            tControl = renderCheckboxFieldForTable(aParameterTable, aPromptGroup.getName(), aRepeatIndex, tPromptCheckbox, tRow, tColumnIndex);
        } else if (aPrompt instanceof FKLPromptDate) {
            FKLPromptDate tPromptDate = (FKLPromptDate)aPrompt;
            tControl = renderDateFieldForTable(aParameterTable, aPromptGroup.getName(), aRepeatIndex, tPromptDate, tRow, tColumnIndex);
        }

        inputChanged(tControl);

        // Add a OK key listener to the last control of the prompt group
        if (aPromptGroup.isRepeatable()) {
            if (aRepeatIndex.intValue() == aPromptGroup.getMaxRepeats() - 1) {
                if (aPromptCount == aPromptGroup.getPrompts().size()) {
                    tControl.addKeyListener(new TabLastInputFieldKeyListener(aTabFolder));
                }
            }
        } else {
            if (aPromptCount == aPromptGroup.getPrompts().size()) {
                tControl.addKeyListener(new TabLastInputFieldKeyListener(aTabFolder));
            }
        }

        tControl.addFocusListener(new InputFieldFocusListener());
        tControl.setData(DATA_THE_DESCRIPTION_CONTROL, aDescriptionControl);

        return tControl;
    }

    private Control renderTextFieldForTable(Table aParameterTable, String aPromptGroupName, Integer aRepeatIndex, FKLPromptText aPrompt,
        TableItem aRow, int aColumn) {

        Control tText = renderTextField(aParameterTable, aPromptGroupName, aRepeatIndex, aPrompt);

        TableEditor tEditor = new TableEditor(aParameterTable);
        tEditor.horizontalAlignment = SWT.LEFT;
        tEditor.grabHorizontal = true;
        tEditor.setEditor(tText, aRow, aColumn);

        return tText;
    }

    private Control renderTextField(Composite aParent, String aPromptGroupName, Integer aRepeatIndex, FKLPromptText aPrompt) {

        Text tText = new Text(aParent, getBorderStyle(aParent));
        if (aPrompt.hasMaxLength()) {
            tText.setTextLimit(aPrompt.getMaxLength());
        }

        addUppercaseListener(aPrompt, tText);

        setCommonDataValues(aPromptGroupName, aRepeatIndex, aPrompt, tText);

        // Choose to show the hint or the default value
        setDefaultOrHint(aPrompt, tText);

        tText.addModifyListener(new TextInputChangedListener());
        tText.addFocusListener(new TextSetSelectionListener());

        return tText;
    }

    private Control renderNumberField(Composite aParent, String aPromptGroupName, Integer aRepeatIndex, FKLPromptNumber aPrompt) {

        Text tText = new Text(aParent, getBorderStyle(aParent));

        if (aPrompt.hasMaxLength()) {
            tText.setTextLimit(aPrompt.getMaxLength());
        }

        addNumberListener(aPrompt, tText);

        setCommonDataValues(aPromptGroupName, aRepeatIndex, aPrompt, tText);

        // Choose to show the hint or the default value
        setDefaultOrHint(aPrompt, tText);

        tText.addModifyListener(new TextInputChangedListener());
        tText.addFocusListener(new TextSetSelectionListener());

        return tText;
    }

    private Control renderMultilineFieldForTable(Table aParameterTable, String aPromptGroupName, Integer aRepeatIndex, FKLPromptMultiline aPrompt,
        TableItem aRow, int aColumn) {

        Control tText = renderMultilineField(aParameterTable, aPromptGroupName, aRepeatIndex, aPrompt);

        TableEditor tEditor = new TableEditor(aParameterTable);
        tEditor.horizontalAlignment = SWT.LEFT;
        tEditor.grabHorizontal = true;
        tEditor.setEditor(tText, aRow, aColumn);

        return tText;
    }

    private Control renderMultilineField(Composite aParent, String aPromptGroupName, Integer aRepeatIndex, FKLPromptMultiline aPrompt) {

        StyledText tText = new StyledText(aParent, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | getBorderStyle(aParent));
        if (aPrompt.hasMaxLength()) {
            tText.setTextLimit(aPrompt.getMaxLength());
        }

        tText.setWordWrap(true);

        // Prevent the multiline control from stealing the tab key for \t
        // characters
        tText.addTraverseListener(new TraverseListener() {
            @Override
            public void keyTraversed(TraverseEvent e) {
                if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS) {
                    e.doit = true;
                }
            }
        });

        setCommonDataValues(aPromptGroupName, aRepeatIndex, aPrompt, tText);

        // Choose to show the hint or the default value
        setDefaultOrHint(aPrompt, tText);

        tText.addModifyListener(new TextInputChangedListener());
        tText.addFocusListener(new TextSetSelectionListener());

        return tText;
    }

    private Control renderCheckboxFieldForTable(Table aParameterTable, String aPromptGroupName, Integer aRepeatIndex, FKLPromptCheckbox aPrompt,
        TableItem aRow, int aColumn) {

        Control tCheckbox = renderCheckboxField(aParameterTable, aPromptGroupName, aRepeatIndex, aPrompt);
        tCheckbox.setBackground(aRow.getBackground());

        TableEditor tEditor = new TableEditor(aParameterTable);
        tEditor.minimumWidth = tCheckbox.getSize().x;
        tEditor.horizontalAlignment = SWT.LEFT;
        tEditor.grabHorizontal = true;
        tEditor.setEditor(tCheckbox, aRow, aColumn);

        return tCheckbox;
    }

    private Control renderCheckboxField(Composite aParent, String aPromptGroupName, Integer aRepeatIndex, FKLPromptCheckbox aPrompt) {
        Button tCheckbox = new Button(aParent, SWT.CHECK);
        tCheckbox.pack();

        setCommonDataValues(aPromptGroupName, aRepeatIndex, aPrompt, tCheckbox);

        tCheckbox.setData("checkedValue", aPrompt.getCheckedValue());
        tCheckbox.setData("uncheckedValue", aPrompt.getUncheckedValue());

        tCheckbox.setSelection(Boolean.parseBoolean(aPrompt.getDefaultValue()));
        tCheckbox.addSelectionListener(new CheckboxInputChangedListener());

        return tCheckbox;
    }

    private Control renderDateFieldForTable(Table aParameterTable, String aPromptGroupName, Integer aRepeatIndex, FKLPromptDate aPrompt,
        TableItem aRow, int aColumn) {

        Control tDateTime = renderDateField(aParameterTable, aPromptGroupName, aRepeatIndex, aPrompt);

        TableEditor tEditor = new TableEditor(aParameterTable);
        tEditor.minimumWidth = tDateTime.getSize().x;
        tEditor.horizontalAlignment = SWT.LEFT;
        tEditor.grabHorizontal = false;
        tEditor.setEditor(tDateTime, aRow, aColumn);

        return tDateTime;
    }

    private Control renderDateField(Composite aParent, String aPromptGroupName, Integer aRepeatIndex, FKLPromptDate aPrompt) {
        DateTime tDateTime = new DateTime(aParent, SWT.DATE | SWT.MEDIUM | SWT.DROP_DOWN);
        tDateTime.pack();

        setCommonDataValues(aPromptGroupName, aRepeatIndex, aPrompt, tDateTime);

        tDateTime.setData(DATA_DATE_FORMAT, aPrompt.getDateFormat());

        tDateTime.addSelectionListener(new DateInputChangedListener());

        return tDateTime;
    }

    private int getBorderStyle(Composite aParent) {
        if (aParent instanceof Table) {
            return SWT.NONE;
        }
        return SWT.BORDER;
    }

    private void setCommonDataValues(String aPromptGroupName, Integer aRepeatIndex, FKLAbstractPrompt aPrompt, Control tControl) {
        tControl.setData(DATA_PROMPT_GROUP_NAME, aPromptGroupName);
        tControl.setData(DATA_REPEAT_INDEX, aRepeatIndex);

        tControl.setData(DATA_PROMPT_NAME, aPrompt.getName());
        tControl.setData(DATA_DESCRIPTION, aPrompt.getDescription());

        if (aPrompt instanceof IHint) {
            tControl.setToolTipText(((IHint)aPrompt).getHint());
            tControl.setData(DATA_HINT, ((IHint)aPrompt).getHint());
        }

        if (aPrompt instanceof IUppercase) {
            tControl.setData(DATA_UPPER_CASE, ((IUppercase)aPrompt).isUpperCase());
        }
    }

    private void addUppercaseListener(FKLAbstractPrompt aPrompt, Control tControl) {
        if (!(aPrompt instanceof IUppercase)) {
            return;
        }

        IUppercase tUppercasePrompt = (IUppercase)aPrompt;
        if (tUppercasePrompt.isUpperCase()) {
            VerifyListener tVerifyListener = new VerifyListener() {
                @Override
                public void verifyText(VerifyEvent anEvent) {
                    String tHint = (String)anEvent.widget.getData(DATA_HINT);
                    if (!anEvent.text.equalsIgnoreCase(tHint)) {
                        anEvent.text = anEvent.text.toUpperCase();
                    }
                }
            };
            addControlVerifyListener(tControl, tVerifyListener);
        }
    }

    private void addNumberListener(FKLPromptNumber aPrompt, Text tControl) {
        if (!(aPrompt instanceof FKLPromptNumber)) {
            return;
        }
        tControl.setData(DATA_MAX_VALUE, aPrompt.getMaxValue());
        if (aPrompt.hasDecimalPositions()) {
            tControl.setData(DATA_MAX_DEC_POS, aPrompt.getDecimalPositions());
        } else {
            tControl.setData(DATA_MAX_DEC_POS, new Integer("-1"));
        }

        VerifyListener tVerifyListener = new VerifyListener() {

            @Override
            public void verifyText(VerifyEvent anEvent) {
                if (anEvent.text.length() == 0) {
                    return;
                }

                String tText = anEvent.text;
                char tComma = Preferences.getInstance().getCommaSymbol();
                char[] tChars = new char[tText.length()];
                tText.getChars(0, tChars.length, tChars, 0);
                for (int i = 0; i < tChars.length; i++) {
                    if ((!('0' <= tChars[i] && tChars[i] <= '9')) && !(tChars[i] == tComma) && !(tChars[i] == '-')) {
                        anEvent.doit = false;
                        return;
                    }
                }

                String tTextValue = ((Text)anEvent.widget).getText() + tText;
                try {
                    if (!"-".equals(tTextValue)) {
                        Number tCurrentValue = DecimalFormat.getInstance().parse(tTextValue);
                        Number tMaxValue = (Number)anEvent.widget.getData(DATA_MAX_VALUE);
                        if (tMaxValue.doubleValue() > 0 && Math.abs(tCurrentValue.doubleValue()) > tMaxValue.doubleValue()) {
                            anEvent.doit = false;
                            return;
                        }
                    }
                } catch (Exception e) {
                    anEvent.doit = false;
                    return;
                }

                int tPosComma = tTextValue.indexOf(tComma) + 1;
                if (tPosComma >= 1) {
                    Integer tMaxDecPos = (Integer)anEvent.widget.getData(DATA_MAX_DEC_POS);
                    int tLenDecPos = tTextValue.length() - tPosComma;
                    if (tMaxDecPos.intValue() >= 0 && tLenDecPos > tMaxDecPos.intValue()) {
                        anEvent.doit = false;
                        return;
                    }
                }
            }
        };
        tControl.addVerifyListener(tVerifyListener);
    }

    private void setDefaultOrHint(FKLAbstractPrompt aPrompt, Control tControl) {
        String tText = getControlText(tControl);

        if (tText.length() == 0 && aPrompt instanceof IDefaultValue) {
            IDefaultValue tDefaultValuePrompt = (IDefaultValue)aPrompt;
            if (tDefaultValuePrompt.getDefaultValue().length() > 0) {
                String tText2 = tDefaultValuePrompt.getDefaultValue();
                setControlText(tControl, tText2);
            }
        }

        if (tText.length() == 0 && aPrompt instanceof IHint) {
            IHint tHintPrompt = (IHint)aPrompt;
            if (tHintPrompt.getHint().length() > 0) {
                addAutoClearingHint(tControl, tHintPrompt.getHint());
            }
        }
    }

    private void addAutoClearingHint(final Control aControl, final String aHint) {
        String tControlText = getControlText(aControl);

        if (tControlText.equals("")) {
            setControlText(aControl, aHint);
            aControl.setForeground(aControl.getDisplay().getSystemColor(SWT.COLOR_GRAY));
        } else {
            aControl.setForeground(aControl.getDisplay().getSystemColor(SWT.COLOR_BLACK));
        }

        aControl.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                String tText = getControlText(aControl);
                if (tText.equalsIgnoreCase(aHint)) {
                    setControlText(aControl, "");
                    aControl.setForeground(null);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String tText = getControlText(aControl);
                if (tText.equals("") || tText.equalsIgnoreCase(aHint)) {
                    setControlText(aControl, aHint);
                    aControl.setForeground(aControl.getDisplay().getSystemColor(SWT.COLOR_GRAY));
                } else {
                    aControl.setForeground(aControl.getDisplay().getSystemColor(SWT.COLOR_BLACK));
                }
            }

        });
    }

    private void addControlVerifyListener(Widget tControl, VerifyListener aVerifyListener) {
        if (tControl instanceof Text) {
            ((Text)tControl).addVerifyListener(aVerifyListener);
        } else if (tControl instanceof StyledText) {
            ((StyledText)tControl).addVerifyListener(aVerifyListener);
        }
    }

    /**
     * removes the trailing 'newLine' character that is automatically added by a
     * multiline Text-control.
     * 
     * @param aText multiline text control that contains the text value
     * @return text value without the last trailing newLine charcater
     */
    private String getControlMultilineText(Widget aText) {
        String tValue = getControlText(aText);
        if (tValue.endsWith(StringUtil.newLineChar())) {
            int tEndIndex = tValue.lastIndexOf(StringUtil.newLineChar());
            if (tEndIndex >= 0) {
                return tValue.substring(0, tEndIndex);
            }
        }
        return tValue;
    }

    private String getControlText(Widget aControl) {
        if (aControl instanceof Text) {
            return ((Text)aControl).getText();
        } else if (aControl instanceof StyledText) {
            return ((StyledText)aControl).getText();
        } else {
            return "";
        }
    }

    private void setControlText(Widget aControl, String aText) {
        if (aControl instanceof Text) {
            ((Text)aControl).setText(aText);
        } else {
            ((StyledText)aControl).setText(aText);
        }
    }

    private class InputFieldFocusListener implements FocusListener {
        private Color backgroundColor = null;

        @Override
        public void focusGained(FocusEvent e) {
            Control tControl = (Control)e.widget;
            backgroundColor = tControl.getBackground();
            tControl.setBackground(new Color(getParent().getDisplay(), new RGB(204, 255, 204)));
            String tDescription = getStringData(tControl, DATA_DESCRIPTION);
            Control tTheDescriptionControl = (Control)tControl.getData(DATA_THE_DESCRIPTION_CONTROL);
            if (tTheDescriptionControl instanceof Label) {
                ((Label)tTheDescriptionControl).setText(tDescription);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            Control tControl = (Control)e.widget;
            if (backgroundColor != null) {
                tControl.setBackground(backgroundColor);
            }
            Control tTheDescriptionControl = (Control)tControl.getData(DATA_THE_DESCRIPTION_CONTROL);
            if (tTheDescriptionControl instanceof Label) {
                ((Label)tTheDescriptionControl).setText("");
            }
        }

        private String getStringData(Widget aWidget, String aKey) {
            Object tValue = aWidget.getData(aKey);
            if (tValue == null) {
                return null;
            }
            return (String)tValue;
        }
    };

    private void inputChanged(Widget aWidget) {
        if ((aWidget instanceof Text) || (aWidget instanceof StyledText)) {
            Control tControl = (Control)aWidget;
            String tText = getControlText(aWidget);
            String tHint = getStringData(tControl, DATA_HINT);

            String tValue;
            if ((tText.equalsIgnoreCase(tHint)) || (tText == "")) {
                tValue = "";
            } else if ((aWidget.getStyle() & SWT.MULTI) == SWT.MULTI) {
                tValue = getControlMultilineText(aWidget);
            } else {
                tValue = getControlText(aWidget);
            }
            updateTemplateData(tControl, tValue);

        } else if (aWidget instanceof DateTime) {
            DateTime tDateTime = (DateTime)aWidget;
            updateTemplateData(tDateTime, parseAndConvertDate(tDateTime));

        } else if (aWidget instanceof Button) {
            Button tButton = (Button)aWidget;
            updateTemplateData(tButton, getCheckboxValue(tButton));

        }
    }

    private void updateTemplateData(Control aControl, String aValue) {
        String tPromptGroupName = getStringData(aControl, DATA_PROMPT_GROUP_NAME);
        String tPromptName = getStringData(aControl, DATA_PROMPT_NAME);
        Integer tRepeatIndex = getIntegerData(aControl, DATA_REPEAT_INDEX);
        Boolean tIsUpperCase = getBooleanData(aControl, DATA_UPPER_CASE);
        if (tIsUpperCase != null && tIsUpperCase) {
            getFKLTemplate().getLPEXTemplate().updateData(tPromptGroupName, tPromptName, tRepeatIndex, aValue.toUpperCase());
        } else {
            getFKLTemplate().getLPEXTemplate().updateData(tPromptGroupName, tPromptName, tRepeatIndex, aValue);
        }
    }

    private String getStringData(Control aControl, String aKey) {
        Object tValue = aControl.getData(aKey);
        if (tValue == null) {
            return null;
        }
        return (String)tValue;
    }

    private Integer getIntegerData(Control aControl, String aKey) {
        Object tValue = aControl.getData(aKey);
        if (tValue == null) {
            return null;
        }
        return (Integer)tValue;
    }

    private Boolean getBooleanData(Control aControl, String aKey) {
        Object tValue = aControl.getData(aKey);
        if (tValue == null) {
            return null;
        }
        return (Boolean)tValue;
    }

    private String getCheckboxValue(Button aButton) {
        if (aButton.getSelection()) {
            return getStringData(aButton, DATA_CHECKED_VALUE);
        } else {
            return getStringData(aButton, DATA_UNCHECKED_VALUE);
        }
    }

    private String parseAndConvertDate(DateTime aDateTime) {
        String tDateFormatted = "";

        Calendar tCalendar = GregorianCalendar.getInstance();
        tCalendar.set(aDateTime.getYear(), aDateTime.getMonth(), aDateTime.getDay());
        String tDateFormat = getStringData(aDateTime, DATA_DATE_FORMAT);
        SimpleDateFormat tFormatter = new SimpleDateFormat(tDateFormat);
        tDateFormatted = tFormatter.format(tCalendar.getTime());

        return tDateFormatted;
    }

    /**
     * Action that is performed when the "OK" button is clicked. Sets
     * <code>result</code> to <code>SWT.OK</code> and closes the dialog.
     */
    private void performActionOK() {
        result = SWT.OK;
        shlLpexFreemarkerTemplate.close();
    }

    /**
     * Action that is performed when the "OK" button is clicked. Sets
     * <code>result</code> to <code>SWT.CANCEL</code> and closes the dialog.
     */
    private void performActionCancel() {
        result = SWT.CANCEL;
        shlLpexFreemarkerTemplate.close();
    }

    /**
     * Action that is performed when the "Previous" button is clicked. Proceeds
     * to the previous tab or stays on the current tab when the first tab is the
     * current tab.
     * 
     * @param aTabFolder tab folder containing the tab items
     */
    public void performActionPreviousTab(TabFolder aTabFolder) {
        int tTabIndex = aTabFolder.getSelectionIndex();
        if (tTabIndex > 0) {
            aTabFolder.setSelection(tTabIndex - 1);
            setFocusOnFirstInputField(aTabFolder);
            enableDisableTabButtons(aTabFolder);
        }
    }

    /**
     * Action that is performed when the "Next" button is clicked. Proceeds to
     * the next tab or stays on the current tab when the last tab is the current
     * tab.
     * 
     * @param aTabFolder tab folder containing the tab items
     */
    public void performActionNextTab(TabFolder aTabFolder) {
        int tTabIndex = aTabFolder.getSelectionIndex();

        if (tTabIndex < aTabFolder.getItemCount() - 1) {
            aTabFolder.setSelection(tTabIndex + 1);
            enableDisableTabButtons(aTabFolder);
            setFocusOnFirstInputField(aTabFolder);
        } else {
            performActionOK();
        }
    }

    /**
     * Sets the focus on the first input field of a the current tab item.
     * 
     * @param aTabFolder tab folder containing the current tab item
     */
    private void setFocusOnFirstInputField(TabFolder aTabFolder) {
        TabItem tTabItem = aTabFolder.getItem(aTabFolder.getSelectionIndex());
        Control tControl = (Control)tTabItem.getData(DATA_THE_TABLE_CONTROL);
        tControl.setFocus();
    }

    private void enableDisableTabButtons(TabFolder aTabFolder) {
        int tIndex = aTabFolder.getSelectionIndex();

        // Enable/disable "Previous" button
        if (tIndex == 0) {
            btnPrevious.setEnabled(false);
        } else {
            btnPrevious.setEnabled(btnPrevious.getVisible());
        }

        // Enable/disable "Next" button
        if (tIndex == aTabFolder.getItemCount() - 1) {
            btnNext.setEnabled(false);
        } else {
            btnNext.setEnabled(btnNext.getVisible());
        }
    }

    /**
     * Listener that fires whenever a text input field is changed.
     * 
     * @author Thomas Raddatz
     */
    private class TextInputChangedListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent anEvent) {
            inputChanged(anEvent.widget);
        }
    }

    /**
     * Listener that fires whenever a text control gets the focus.
     * 
     * @author Thomas Raddatz
     */
    private class TextSetSelectionListener extends FocusAdapter {
        @Override
        public void focusGained(FocusEvent event) {

            Object source = event.getSource();
            if (source instanceof StyledText) {
                StyledText text = (StyledText)source;
                text.setSelection(0, text.getText().length());
            } else if (source instanceof Text) {
                Text text = (Text)event.getSource();
                text.setSelection(0, text.getText().length());
            }
        }
    }

    /**
     * Listener that fires whenever a checkbox input field is changed.
     * 
     * @author Thomas Raddatz
     */
    private class CheckboxInputChangedListener implements SelectionListener {

        @Override
        public void widgetDefaultSelected(SelectionEvent anEvent) {
        }

        @Override
        public void widgetSelected(SelectionEvent anEvent) {
            inputChanged(anEvent.widget);
        }
    }

    /**
     * Listener that fires whenever a date input field is changed.
     * 
     * @author Thomas Raddatz
     */
    private class DateInputChangedListener extends SelectionAdapter {

        @Override
        public void widgetSelected(SelectionEvent anEvent) {
            inputChanged(anEvent.widget);
        }

    }

    /**
     * Listener that fires when the "OK" button is clicked.
     * 
     * @author Thomas Raddatz
     */
    private class ButtonOKClickedListener implements Listener {
        @Override
        public void handleEvent(Event anEvent) {
            performActionOK();
        }
    }

    /**
     * Listener that fires when the "Cancel" button is clicked.
     * 
     * @author Thomas Raddatz
     */
    private class ButtonCancelClickedListener implements Listener {
        @Override
        public void handleEvent(Event anEvent) {
            performActionCancel();
        }
    }

    /**
     * Listener that fires when the "Previous" button is clicked.
     * 
     * @author Thomas Raddatz
     */
    private class ButtonPreviousClickedListener implements Listener {
        private TabFolder tabFolder;

        public ButtonPreviousClickedListener(TabFolder aTabFolder) {
            tabFolder = aTabFolder;
        }

        @Override
        public void handleEvent(Event anEvent) {
            performActionPreviousTab(tabFolder);
        }
    }

    /**
     * Listener that fires when the "Next" button is clicked.
     * 
     * @author Thomas Raddatz
     */
    private class ButtonNextClickedListener implements Listener {
        private TabFolder tabFolder;

        public ButtonNextClickedListener(TabFolder aTabFolder) {
            tabFolder = aTabFolder;
        }

        @Override
        public void handleEvent(Event anEvent) {
            performActionNextTab(tabFolder);
        }
    }

    /**
     * Listener, added to the last control of a tab. This listener waits for the
     * ENTER key pressed and simulates a click on the "Next" or "OK" button.
     * 
     * @author Thomas Raddatz
     */
    private class TabLastInputFieldKeyListener implements KeyListener {
        private TabFolder tabFolder;

        public TabLastInputFieldKeyListener(TabFolder aTabFolder) {
            tabFolder = aTabFolder;
        }

        @Override
        public void keyPressed(KeyEvent anEvent) {
            if (anEvent.keyCode == SWT.CR || anEvent.keyCode == SWT.KEYPAD_CR) {
                performActionNextTab(tabFolder);
            }
        }

        @Override
        public void keyReleased(KeyEvent anEvent) {
        }
    }

    private class TabSelectedListener extends SelectionAdapter {

        private TabFolder tabFolder;

        public TabSelectedListener(TabFolder aTabFolder) {
            tabFolder = aTabFolder;
        }

        @Override
        public void widgetDefaultSelected(SelectionEvent e) {
            enableDisableTabButtons(tabFolder);
        }

        @Override
        public void widgetSelected(SelectionEvent e) {
            enableDisableTabButtons(tabFolder);
        }
    }
}
