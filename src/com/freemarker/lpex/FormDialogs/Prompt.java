package com.freemarker.lpex.FormDialogs;

import java.io.Serializable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Prompt implements Serializable {

	private static final long serialVersionUID = 3L;

	public enum InputType implements Serializable {
		TEXT, DATE, MULTILINE, CHECKBOX
	}

	private Object shell = null;
	private Boolean isGrouped = false;
	private InputType type = InputType.TEXT;
	private String name = "";
	private String label = "";
	private String description = "";
	private String hint = "";
	private Object defaultValue = "";

	public Prompt() {
	}

	public Prompt(InputType type, String name, String label, String description, String hint) {
		setType(type);
		setName(name);
		setLabel(label);
		setDescription(description);
		setHint(hint);
	}

	public Object getShell() {
		return shell;
	}

	public void setShell(Object shell) {
		this.shell = shell;
	}

	public InputType getType() {
		return type;
	}

	public void setType(InputType type) {
		this.type = type;
	}
	
	public void setType(String type) {
		if (type.equalsIgnoreCase("text")) setType(InputType.TEXT);
		else if (type.equalsIgnoreCase("multiline")) setType(InputType.MULTILINE);
		else if (type.equalsIgnoreCase("checkbox")) setType(InputType.CHECKBOX);
		else if (type.equalsIgnoreCase("date")) setType(InputType.DATE);
		else setType(InputType.TEXT);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null) {
			this.name = "";
		}else{
			this.name = name;
		}
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		if (label == null) {
			this.label = "";
		}else{
			this.label = label;
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (description == null) {
			this.description = "";
		}else{
			this.description = description;
		}
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		if (hint == null) {
			this.hint = "";
		}else{
			this.hint = hint;
		}
	}
	
	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		if (defaultValue == null) {
			this.defaultValue = new String("");
		}else{
			this.defaultValue = defaultValue;
		}
	}
	
	public void render(Group group) {
		isGrouped = true;
		_render(group);
	}
	
	public void render(Shell shell) {
		isGrouped = false;
		_render(shell);
	}
	
	private void _render(Object shell) {
		setShell(shell);
		renderPromptLabel();
		renderPromptDescription();
		
		//Choose which style input control to render based on the type
		if (this.type == InputType.TEXT) renderTextInput();
		else if (this.type == InputType.MULTILINE) renderMultilineTextInput();
		else if (this.type == InputType.CHECKBOX) renderCheckboxInput();
		else if (this.type == InputType.DATE) renderDateInput();
	}
	
	private void renderPromptLabel() {
		Label label = null;
		Label filler = null;
		if (isGrouped) {
			label = new Label((Group) shell, SWT.NONE);
			filler = new Label((Group) shell, SWT.NONE);
		}else{
			label = new Label((Shell) shell, SWT.NONE);
			filler = new Label((Shell) shell, SWT.NONE);
		}
		
		label.setText(this.label);
		label.setFont(new Font(Display.getDefault(), "Tahoma", 10, SWT.BOLD));
		GridData gridData = new GridData();
		gridData.verticalAlignment = SWT.TOP;
		label.setLayoutData(gridData);
		filler.setLayoutData(gridData);
	}
	
	private void renderPromptDescription() {
		Label description = null;
		if (isGrouped) {
			description = new Label((Group) shell, SWT.WRAP | SWT.NONE);
		}else{
			description = new Label((Shell) shell, SWT.WRAP | SWT.NONE);
		}
		
		description.setText(this.description);
		description.setSize(150, 40);
		description.setLocation(0, 0);  
		//GridData gridData = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		GridData gridData = new GridData();
		gridData.widthHint = 10;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.heightHint = 50;
		gridData.verticalAlignment = SWT.FILL;
		description.setLayoutData(gridData);
	}
	
	private void renderTextInput() {
		Text text = null;
		if (isGrouped) {
			text = new Text((Group) shell, SWT.BORDER);
		}else{
			text = new Text((Shell) shell, SWT.BORDER);
		}
		
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = SWT.TOP;
		text.setLayoutData(gridData);
		text.setToolTipText(hint);

		//Choose to show the hint or the default value
		try {
			String defaultText = (String) defaultValue;
			if ((defaultText == "") || (defaultText == null)) {
				if (hint != "") {
					addAutoClearingHint(text, hint);
				}
			}else{
				text.setText(defaultText);
			}
		} catch (Exception e) {}
	}
	
	private void renderMultilineTextInput() {
		Text text = null;
		if (isGrouped) {
			text = new Text((Group) shell, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		}else{
			text = new Text((Shell) shell, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		}
		
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		text.setLayoutData(gridData);
		text.setToolTipText(hint);
		
		//Choose to show the hint or the default value
		try {
			String defaultText = (String) defaultValue;
			if ((defaultText == "") || (defaultText == null)) {
				if (hint != "") {
					addAutoClearingHint(text, hint);
				}
			}else{
				text.setText(defaultText);
			}
		} catch (Exception e) {}
		
		//Prevent the multiline control from stealing the tab key for \t characters
		text.addTraverseListener(new TraverseListener() {
		    public void keyTraversed(TraverseEvent e) {
		        if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS) {
		            e.doit = true;
		        }
		    }
		});
	}
	
	private void renderCheckboxInput() {
		Button checkbox = null;
		if (isGrouped) {
			checkbox = new Button((Group) shell, SWT.CHECK);
		}else{
			checkbox = new Button((Shell) shell, SWT.CHECK);
		}

		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = SWT.TOP;
		checkbox.setLayoutData(gridData);
		checkbox.setText(name);
		try {
			Boolean checked = (Boolean) defaultValue;
			checkbox.setSelection(checked);
		} catch (Exception e) {}
	    checkbox.setToolTipText(hint);
	}
	
	private void renderDateInput() {
		DateTime date = null;
		if (isGrouped) {
			date = new DateTime((Group) shell, SWT.DATE | SWT.SHORT | SWT.BORDER | SWT.DROP_DOWN);
		}else{
			date = new DateTime((Shell) shell, SWT.DATE | SWT.SHORT | SWT.BORDER | SWT.DROP_DOWN);
		}

		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = SWT.TOP;
		date.setLayoutData(gridData);
		date.setToolTipText(hint);
		date.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				System.out.println("time changed");
			}
		});
	}
	
    private void addAutoClearingHint(final Text text, final String defaultText) {
    	if(text.getText().equals("")) {
            text.setText(defaultText);
            text.setForeground(text.getDisplay().getSystemColor(SWT.COLOR_GRAY));
        }
        text.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                if(text.getText().equals(defaultText)) {
                    text.setText("");
                    text.setForeground(null);
                }
            }

            public void focusLost(FocusEvent e) {
                if(text.getText().equals("")) {
                    text.setText(defaultText);
                    text.setForeground(text.getDisplay().getSystemColor(SWT.COLOR_GRAY));
                }
            }
        });
    }
}