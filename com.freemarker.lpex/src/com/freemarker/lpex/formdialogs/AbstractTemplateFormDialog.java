package com.freemarker.lpex.formdialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class AbstractTemplateFormDialog extends Dialog {

    private FKLTemplate fklTemplate = null;

    /**
     * Create the dialog. Default constructor for WindowsBuilder.
     * 
     * @wbp.parser.constructor
     */
    protected AbstractTemplateFormDialog() {
        this(null);
    }

    /**
     * Create the dialog. Default application constructor.
     * 
     * @param aFKLTemplate
     */
    protected AbstractTemplateFormDialog(FKLTemplate aFKLTemplate) {
        this(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.TITLE | SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL, aFKLTemplate);
    }

    /**
     * Create the dialog.
     * 
     * @param aParent
     * @param aStyle
     * @param aFKLTemplate
     */
    private AbstractTemplateFormDialog(Shell aParent, int aStyle, FKLTemplate aFKLTemplate) {
        super(aParent, SWT.NO_TRIM);
        setText("SWT Dialog");
        fklTemplate = aFKLTemplate;
    }

    protected FKLTemplate getFKLTemplate() {
        return fklTemplate;
    }

}
