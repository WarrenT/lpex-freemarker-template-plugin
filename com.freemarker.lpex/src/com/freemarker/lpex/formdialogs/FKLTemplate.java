package com.freemarker.lpex.formdialogs;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.swt.SWT;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Representation off a FreeMArker template as produced by the TemplateBuilder
 * application.
 * <p>
 * Prompt group attributes:
 * <ul>
 * <li><code>Name</code>: Name of the prompt group.</li>
 * <li><code>Repeatable</code>: Specifies whether or not the prompt group is
 * repeatable.</li>
 * <li><code>MaxRepeates</code>: Specifies the maximum number of repeats of a
 * repeatable prompt group.</li>
 * </ul>
 * <p>
 * Prompt type attributes:
 * <ul>
 * <li><code>Name</code>: Prompt name. Used as the variable name.</li>
 * <li><code>Type</code>: Prompt type. Type of the prompt: TEXT, MULTILINE,
 * DATE, CHECKBOX, USERDEFINED.</li>
 * <li><code>Label</code>: Prompt label. Displayed in the prompt dialog next to
 * the input field.</li>
 * <li><code>Hint</code>: Prompt input hint. Displayed in the input field of the
 * prompt dialog.</li>
 * <li><code>Description</code>: Description of the prompt. Displayed at the top
 * of the prompt dialog.</li>
 * </ul>
 * <p>
 * Prompt types and attributes:
 * <ul>
 * <li><code>TEXT</code>: Text input field.</li>
 * <ul>
 * <li><code>UpperCase</code>: Text input field.</li>
 * </ul>
 * </ul>
 * <ul>
 * <li><code>DATE</code>: Text input field.</li>
 * <ul>
 * <li><code>DateFormat</code>: pattern used to format the date.</li>
 * <li><code>Hint</code>: <b>Not</b> allowed for DATE input fields.</li>
 * </ul>
 * </ul>
 * <ul>
 * <li><code>CHECKBOX</code>: Text input field.</li>
 * <ul>
 * <li><code>CheckedValue</code>: Value used when the checkbox is checked.</li>
 * <li><code>UncheckedValue</code>: Value used when the checkbox is <b>not</b>
 * checked.</li>
 * </ul>
 * </ul>
 * <ul>
 * <li><code>USERDEFINED</code>: User defined type. User defined types are not
 * rendered on the prompt dialog window but processed under the cover.</li>
 * <ul>
 * <li><code>class</code>: Java class that implements the type.
 * <p>
 * See example:
 * <code>com.freemarker.lpex.userdefined.FileFormatDescriptionUserType</code></li>
 * </ul>
 * </ul>
 * 
 * @author Thomas Raddatz
 */
public class FKLTemplate {

    /**
     * XML element name of: prompt.type
     */
    public static final String XML_PROMPT_TEXT = "text";

    public static final String XML_PROMPT_MULTILINE = "multiline";

    public static final String XML_PROMPT_NUMBER = "number";

    public static final String XML_PROMPT_DATE = "date";

    public static final String XML_PROMPT_CHECKBOX = "checkbox";

    public static final String XML_PROMPT_USER_DEFINED = "userdefined";

    /**
     * XML element name of: template.name
     */
    private static final String XML_PROMPT_GROUP = "promptgroup";

    public static final String XML_PROMPT = "prompt";

    public static final String XML_NAME = "name";

    public static final String XML_DESCRIPTION = "description";

    public static final String XML_REPEATABLE = "repeatable";

    public static final String XML_MAX_REPEATS = "maxRepeats";

    public static final String XML_TYPE = "type";

    public static final String XML_LABEL = "label";

    public static final String XML_HINT = "hint";

    public static final String XML_UPPER_CASE = "uppercase";

    public static final String XML_CHECKED_VALUE = "checkedValue";

    public static final String XML_UNCHECKED_VALUE = "uncheckedValue";

    public static final String XML_DATE_FORMAT = "dateFormat";

    public static final String XML_CLASS = "class";

    public static final String XML_MAX_LENGTH = "maxLength";

    public static final String XML_DECIMAL_POSITIONS = "decimalPositions";

    public static final String XML_DEFAULT_VALUE = "defaultValue";

    private String name;

    private String description;

    private ArrayList<FKLPromptGroup> promptGroups;

    private LPEXTemplate lpexTemplate;

    public FKLTemplate(LPEXTemplate aLpexTemplate) throws Exception {
        lpexTemplate = aLpexTemplate;
        loadFromXML(aLpexTemplate.getDialogXML());
    }

    public int open() {
        TemplateFormDialog tDialog = new TemplateFormDialog(this);
        int tResult = tDialog.open();
        if (tResult == 0) {
            tResult = SWT.CANCEL;
        }
        return tResult;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public FKLPromptGroup getPromptGroup(String aName) {
        for (FKLPromptGroup tPromptGroup : promptGroups) {
            if (tPromptGroup.getName().equals(aName)) {
                return tPromptGroup;
            }
        }
        return null;
    }

    public ArrayList<FKLPromptGroup> getPromptGroups() {
        return promptGroups;
    }

    public LPEXTemplate getLPEXTemplate() {
        return lpexTemplate;
    }

    private void loadFromXML(String aXmlString) throws Exception {
        Document tXmlDocument = parseXml(aXmlString);
        name = tXmlDocument.getElementsByTagName(XML_NAME).item(0).getTextContent();
        description = tXmlDocument.getElementsByTagName(XML_DESCRIPTION).item(0).getTextContent();
        promptGroups = new ArrayList<FKLPromptGroup>();

        // Loop through each prompt group
        NodeList tPromptGroupNodes = tXmlDocument.getElementsByTagName(XML_PROMPT_GROUP);
        int tNumPromptGroupNodes = tPromptGroupNodes.getLength();

        for (int i = 0; i < tNumPromptGroupNodes; i++) {
            if (tPromptGroupNodes.item(i) instanceof Element) {
                FKLPromptGroup tPromptGroup = FKLPromptGroup.Factory.parse((Element)tPromptGroupNodes.item(i));
                promptGroups.add(tPromptGroup);
            }
        }
    }

    private Document parseXml(String xml) throws Exception {
        DocumentBuilderFactory tFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder tBuilder = tFactory.newDocumentBuilder();
        InputSource tInputSource = new InputSource(new StringReader(xml));
        return tBuilder.parse(tInputSource);
    }

}
