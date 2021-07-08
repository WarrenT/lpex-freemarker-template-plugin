package com.freemarker.lpex;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.freemarker.lpex.preferences.ParserAssociationPreferencePage;
import com.freemarker.lpex.preferences.Preferences;
import com.freemarker.lpex.utils.PluginLogger;
import com.freemarker.lpex.utils.StackTraceUtil;
import com.ibm.lpex.core.LpexView;

public class LPEXManipulator {

    private LpexView view = null;

    private String selectedTemplateName = "";

    private String templateHintWord = "";

    public LPEXManipulator(LpexView aView) {
        view = aView;
    }

    /**
     * Inserts a text block at the current cursor position.
     * 
     * @param aTextBlock text to insert
     */
    public void addBlockTextAtCursorPosition(String aTextBlock) {
        if (templateHintWord != "") {
            view.doAction(view.actionId("deletePrevWord"));
        }

        view.doCommand("delete");
        int tPrevIndex = 0;
        String tTextBlock = aTextBlock.replaceAll("[\\r]", "");
        for (int i = tTextBlock.indexOf('\n'); i >= 0; i = tTextBlock.indexOf('\n', i + 1)) {
            String tLine = tTextBlock.substring(tPrevIndex, i);
            view.doCommand("insert " + tLine);
            tPrevIndex = i + 1;
        }
        if (tPrevIndex < tTextBlock.length()) {
            String tLine = tTextBlock.substring(tPrevIndex);
            view.doCommand("insert " + tLine);
        }
        return;
    }

    /**
     * Prompts the template chooser to let the user select the template he
     * wants.
     * 
     * @param aListOfTemplateFiles available template paths
     */
    public void promptTemplateChooser(String[] aListOfTemplateFiles) {

        if (aListOfTemplateFiles.length == 0) {
            selectedTemplateName = "";
            return;
        } else if (aListOfTemplateFiles.length == 1) {
            selectedTemplateName = aListOfTemplateFiles[0];
            return;
        }

        Display tDisplay = PlatformUI.getWorkbench().getDisplay();
        Shell tWorkbenchShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
        int tWindowX = tWorkbenchShell.getLocation().x;
        int tWindowY = tWorkbenchShell.getLocation().y;

        final Shell tChooserDialog = new Shell(tWorkbenchShell, SWT.APPLICATION_MODAL | SWT.NO_TRIM);
        tChooserDialog.setText("Select template to use");
        int tWidth = 300;
        int tHeight = 140;
        tChooserDialog.setSize(tWidth, tHeight);
        tChooserDialog.setLocation(tWindowX + tWidth, tWindowY + tHeight);

        final List tListTemplates = new List(tChooserDialog, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        Color tYellow = new Color(tDisplay, 255, 255, 225);
        tListTemplates.setBackground(tYellow);
        for (int i = 0; i < aListOfTemplateFiles.length; i++) {
            // Add the entry without the file extension - case insensitive
            // replace all (?i)
            String tTemplateEntry = aListOfTemplateFiles[i].replaceAll("(?i).ftl", "");
            tListTemplates.add(tTemplateEntry);
        }
        tListTemplates.select(0);
        tListTemplates.setBounds(0, 0, tWidth, tHeight);

        final Button tButtonOK = new Button(tChooserDialog, SWT.PUSH);
        tButtonOK.setText("OK");
        tButtonOK.setBounds(20, 0, 80, 25);
        tChooserDialog.setDefaultButton(tButtonOK);

        Listener tKeyListener = new Listener() {
            @Override
            public void handleEvent(Event anEvent) {
                if (anEvent.widget == tButtonOK) {
                    String tSelectedTemplate[] = tListTemplates.getSelection();
                    if (tSelectedTemplate.length > 0) {
                        selectedTemplateName = tSelectedTemplate[0];
                    }
                }
                tChooserDialog.close();
            }
        };

        MouseListener tMouseListener = new MouseListener() {
            @Override
            public void mouseUp(MouseEvent arg0) {
            }

            @Override
            public void mouseDown(MouseEvent arg0) {
            }

            @Override
            public void mouseDoubleClick(MouseEvent anEvent) {
                String tSelected[] = tListTemplates.getSelection();
                if (tSelected.length > 0) {
                    selectedTemplateName = tSelected[0];
                }
                tChooserDialog.close();
            }
        };

        tButtonOK.addListener(SWT.Selection, tKeyListener);
        tListTemplates.addMouseListener(tMouseListener);

        tChooserDialog.open();

        while (!tChooserDialog.isDisposed()) {
            if (!tDisplay.readAndDispatch()) {
                tDisplay.sleep();
            }
        }

        tYellow.dispose(); // free color resource
    }

    /**
     * Returns the template folder that is associated to the parser of the Lpex
     * editor view.
     * 
     * @return template folder name
     */
    public String getTemplateFolderFromParser() {

        String tTemplateFolderName = getTemplateFolder(getMemberType());
        if (tTemplateFolderName == null) {
            PluginLogger.logger.info("Member type returned null so we used the IBM parser instead of file extension: " + getMemberType());
            tTemplateFolderName = getTemplateFolder(getIBMParser());
            if (tTemplateFolderName == null) {
                PluginLogger.logger.info("LPEX parser returned null so do not have a sub-directory.");
            }
        }

        return tTemplateFolderName;
    }

    private String getIBMParser() {
        return view.query("parser");
    }

    private String getFileName() {
        return view.query("name");
    }

    private String getMemberType() {
        String tFilename = getFileName();
        int tDot = tFilename.lastIndexOf(".");
        return tFilename.substring(tDot + 1);
    }

    private String getTemplateFolder(String tParser) {
        PluginLogger.logger.info("Parser: " + tParser);

        String tTemplateFolderName = null;
        String[] tParserMappingEntries = ParserAssociationPreferencePage.parseToArray(Preferences.getInstance().getParserMappings());
        for (String tParserMappingEntry : tParserMappingEntries) {
            try {
                ParserMapping tMappedParser = new ParserMapping(tParserMappingEntry);
                if (tMappedParser.getParser().toLowerCase().equals(tParser.toLowerCase())) {
                    tTemplateFolderName = tMappedParser.getFolderName();
                }
            } catch (Exception e) {
                PluginLogger.logger.warning("Failed to load a parser mapping");
                PluginLogger.logger.warning(StackTraceUtil.getStackTrace(e));
            }
        }
        PluginLogger.logger.info("Template folder: " + tTemplateFolderName);
        return tTemplateFolderName;
    }

    /**
     * Returns the word at the current cursor position.
     * 
     * @return word at cursor position
     */
    public String getCursorWord() {
        int tEndColumn = view.queryInt("displayPosition");
        String tWholeLine = view.query("text");
        String tWord = "";
        int tStartColumn = tEndColumn - 1;
        while ((tStartColumn >= 1) && (tStartColumn <= tWholeLine.length()) && (isNameChar(tWholeLine.charAt(tStartColumn - 1)))) {
            tStartColumn -= 1;
        }
        while ((tEndColumn <= tWholeLine.length()) && (isNameChar(tWholeLine.charAt(tEndColumn - 1)))) {
            tEndColumn += 1;
        }
        if (tEndColumn - tStartColumn > 1) {
            tWord = tWholeLine.substring(tStartColumn, tEndColumn - 1);
        }
        templateHintWord = tWord;
        return tWord;
    }

    /**
     * Returns <code>true</code> if a given character is part of the template
     * hint word.
     * 
     * @param charToCheck
     * @return <code>true</code>, if part of the word, else <code>false</code>.
     */
    private static boolean isNameChar(char charToCheck) {
        if (((charToCheck >= 'A') && (charToCheck <= 'Z')) || ((charToCheck >= 'a') && (charToCheck <= 'z'))
            || ((charToCheck >= '0') && (charToCheck <= '9')) || (charToCheck == '#') || (charToCheck == '@') || (charToCheck == '$')
            || (charToCheck == '_')) {
            return true;
        }
        return false;
    }

    /**
     * Returns the name of the selected template without the file extension.
     * 
     * @return template name without file extension.
     */
    public String getSelectedTemplateNameNoExt() {
        String tTest = selectedTemplateName;
        try {
            int tExtIndex = selectedTemplateName.lastIndexOf(".");
            tTest = selectedTemplateName.substring(0, tExtIndex);
        } catch (Exception e) {
            // ignore exception
            PluginLogger.doNothing();
        }
        return tTest;
    }

    public class ParserMapping {
        private String folderName;

        private String parser;

        public ParserMapping(String aParserMapping) {
            String[] tParserMapping = aParserMapping.split("=");
            parser = tParserMapping[0];
            folderName = tParserMapping[1];
        }

        public String getFolderName() {
            return folderName;
        }

        public String getParser() {
            return parser;
        }
    }

}