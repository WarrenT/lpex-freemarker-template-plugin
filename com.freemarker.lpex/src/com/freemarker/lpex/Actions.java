package com.freemarker.lpex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.Arrays;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.freemarker.lpex.formdialogs.LPEXTemplate;
import com.freemarker.lpex.freemarker.FreeMarkerConfig;
import com.freemarker.lpex.preferences.Preferences;
import com.freemarker.lpex.utils.PluginLogger;
import com.freemarker.lpex.utils.StackTraceUtil;
import com.ibm.etools.iseries.dds.tui.editor.SdEditor;
import com.ibm.etools.iseries.edit.IBMiEditPlugin;
import com.ibm.etools.iseries.edit.ISeriesEditorUtilities;
import com.ibm.etools.iseries.subsystems.qsys.api.IBMiConnection;
import com.ibm.lpex.alef.LpexTextEditor;
import com.ibm.lpex.core.LpexAction;
import com.ibm.lpex.core.LpexView;

import freemarker.template.TemplateException;

public class Actions {

    public static class InsertTemplate implements LpexAction {

        public static final String ID = "LPEXFreeMarkerInsertTemplate";

        @Override
        public void doAction(LpexView aView) {
            LPEXTemplate tLpexTemplate = null;
            try {
                String tBaseTemplateFolder = "";
                try {
                    tBaseTemplateFolder = Preferences.getInstance().getTemplatesDirectory();
                    if (!new File(tBaseTemplateFolder).exists()) {
                        throw new FileNotFoundException("You must have a valid template directory set.");
                    }
                } catch (Exception e) {
                    PluginLogger.logger.warning(StackTraceUtil.getStackTrace(e));
                    tBaseTemplateFolder = "c:/templates";
                }
                if (tBaseTemplateFolder == "") {
                    PluginLogger.logger.warning("No template directory set");
                    aView.doDefaultCommand("set messageText You must first set the templates directory in the settings");
                    return;
                }

                PluginLogger.logger.info("Load template files");

                LPEXManipulator tLpexManipulator = new LPEXManipulator(aView);
                final String tTemplateHint = tLpexManipulator.getCursorWord();
                String tTemplateFolderName = tLpexManipulator.getTemplateFolderFromParser();

                File tTemplateDirectory;
                if (tTemplateFolderName != null) {
                    tTemplateDirectory = new File(tBaseTemplateFolder + "/" + tTemplateFolderName);
                    PluginLogger.logger.info("Using template folder: " + tTemplateFolderName);
                } else {
                    tTemplateDirectory = new File(tBaseTemplateFolder);
                    PluginLogger.logger.warning("Using template folder: " + tTemplateFolderName);
                }

                FilenameFilter tTemplateFilter = new FilenameFilter() {
                    @Override
                    public boolean accept(File aDirectory, String aFilename) {
                        File tFile = new File(aDirectory + "/" + aFilename);
                        if (tFile.isFile()) {
                            String tExtension = tFile.toString().substring(tFile.toString().lastIndexOf("."));
                            if (tExtension.compareToIgnoreCase(".ftl") == 0) {
                                return aFilename.toUpperCase().startsWith(tTemplateHint.toUpperCase());
                            }
                        }
                        return false;
                    }
                };

                String[] tTemplateFiles = tTemplateDirectory.list(tTemplateFilter);
                if (tTemplateFiles == null || tTemplateFiles.length == 0) {
                    String messageText = "set messageText No template files found: " + tTemplateDirectory.getAbsolutePath();
                    aView.doDefaultCommand(messageText);
                    PluginLogger.logger.warning(messageText);
                    return;
                }

                Arrays.sort(tTemplateFiles);

                PluginLogger.logger.info("Present popup list of templates");

                String tSelectedTemplate = "";
                tLpexManipulator.promptTemplateChooser(tTemplateFiles);
                tSelectedTemplate = tLpexManipulator.getSelectedTemplateNameNoExt();
                if (tSelectedTemplate == "") {
                    // no matches found
                    return;
                }

                PluginLogger.logger.info("Selected: " + tSelectedTemplate);

                IBMiConnection tConnection = getEditorForPrimaryView(aView);
                if (tConnection == null) {
                    handleError(aView, "Could not get iSeries connection of the editor.");
                    return;
                }

                FreeMarkerConfig.getInstance().setCurrentConnection(tConnection);

                File tTemplateFile = new File(tTemplateFolderName + "/" + tSelectedTemplate + ".ftl");
                File tTemplateBaseFolder = new File(tBaseTemplateFolder);
                tLpexTemplate = new LPEXTemplate(tTemplateBaseFolder, tTemplateFile);

                int tRC;
                if (tLpexTemplate.getDialog().getPromptGroups().size() == 0) {
                    tRC = SWT.OK;
                } else {
                    tRC = tLpexTemplate.getDialog().open();
                }

                // Present the dialogs for the user to fill out
                if (tRC == SWT.OK) {
                    PluginLogger.logger.info("Forms filled out completely");
                    logTemplateData(tLpexTemplate);

                    // Merge the collected data with the template
                    tLpexTemplate.merge();

                    // Insert the merged template into the cursor position of
                    // the current LPEX document
                    tLpexManipulator.addBlockTextAtCursorPosition(tLpexTemplate.getResult());
                } else {
                    PluginLogger.logger.info("Forms exited early");
                }

            } catch (TemplateException e) {
                handleError(aView, e.getLocalizedMessage(), e.getFTLInstructionStack());
                logTemplateData(tLpexTemplate);
            } catch (Exception e) {
                handleError(aView, e.getLocalizedMessage(), StackTraceUtil.getStackTrace(e));
                logTemplateData(tLpexTemplate);
            }

            return;
        }

        private void handleError(LpexView view, String msgText) {
            handleError(view, msgText, msgText);
        }

        private void handleError(LpexView view, String msgText, String logText) {
            MessageDialog.openError(getShell(), "Error", msgText);
            PluginLogger.logger.severe(logText);
            view.doDefaultCommand("set messageText " + msgText);
        }

        public static IBMiConnection getEditorForPrimaryView(LpexView view) {
            IWorkbenchWindow window = IBMiEditPlugin.getActiveWorkbenchWindow();
            if (window != null) {
                IWorkbenchPage activePage = window.getActivePage();
                if (activePage != null) {
                    IEditorReference[] references = activePage.getEditorReferences();
                    for (int i = 0; i < references.length; i++) {
                        IWorkbenchPart part = references[i].getPart(false);
                        if (((part instanceof LpexTextEditor)) && (((LpexTextEditor)part).getFirstLpexView() == view)) {
                            return ISeriesEditorUtilities.getISeriesConnection((LpexTextEditor)part);
                        } else if (part instanceof SdEditor) {
                            String viewFile = new File(view.query("name")).getAbsolutePath();
                            String editorFile = new File(((SdEditor)part).getFile().getLocation().toString()).getAbsolutePath();
                            if (viewFile.equals(editorFile)) {
                                return ((SdEditor)part).getISeriesConnection(true);
                            }
                        }
                    }
                }
            }
            return null;
        }

        private Shell getShell() {
            return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
        }

        private void logTemplateData(LPEXTemplate aLpexTemplate) {
            if (aLpexTemplate == null) {
                return;
            }
            PluginLogger.logger.info(aLpexTemplate.getFormDataAsString());

        }

        @Override
        public boolean available(LpexView aView) {
            return true;
        }
    }
}