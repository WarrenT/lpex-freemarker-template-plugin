package com.freemarker.lpex.preferences;

import java.net.URL;
import java.util.Arrays;
import java.util.StringTokenizer;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

public class LinksPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

    public LinksPreferencePage() {
        super();
    }

    /*
     * @see PreferencePage#createContents(Composite)
     */
    @Override
    protected Control createContents(Composite parent) {

        Composite tContainer = new Composite(parent, SWT.NULL);

        // Create a data that takes up the extra space in the dialog .
        GridData tGridData = new GridData(GridData.FILL_HORIZONTAL);
        tGridData.grabExcessHorizontalSpace = true;
        tContainer.setLayoutData(tGridData);

        GridLayout tLayout = new GridLayout(2, false);
        tContainer.setLayout(tLayout);

        // Add in a dummy label for spacing
        // new Label(tContainer, SWT.NONE);

        createLink(tContainer, "Project page", "http://code.google.com/p/lpex-freemarker-template-plugin");
        createLink(tContainer, "GitHub", "https://github.com/mariuslr/lpex-freemarker-template-plugin");
        createLink(tContainer, "Apache FreeMarker", "http://freemarker.org");
        createLink(tContainer, "Apache FreeMarker Documentation", "http://freemarker.org/docs/dgui.html");
        createLink(tContainer, "Tools/400", "http://www.tools400.de");

        return tContainer;
    }

    private Link createLink(Composite parent, String text, String url) {

        final Label label = new Label(parent, SWT.NONE);
        label.setText(text);
        label.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false, 2, 1));

        Label spacer = new Label(parent, SWT.NONE);
        GridData gridData = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false, 1, 1);
        gridData.widthHint = 5;
        spacer.setLayoutData(gridData);

        final Link link = new Link(parent, SWT.NONE);
        link.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, true, false));
        link.setText("<a href=\"" + url + "\">" + url + "</a>");
        link.addSelectionListener(new LinkSelectionListener());

        return link;
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

        // setDescription("Setup the parser template folder associations.");
    }

    private class LinkSelectionListener extends SelectionAdapter {
        @Override
        public void widgetSelected(SelectionEvent event) {
            try {
                // Open default external browser
                PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser().openURL(new URL("http://" + event.text));
            } catch (Exception e) {
                // ignore all errors
            }
        }
    }
}