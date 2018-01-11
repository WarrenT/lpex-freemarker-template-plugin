package com.freemarker.lpex.formdialogs;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class FKLAbstractPrompt {

    // Common attributes of all prompts
    private String name = "";

    private String description = "";

    public FKLAbstractPrompt() {
    }

    public String getName() {
        return name;
    }

    public void setName(String aName) {
        if (aName == null) {
            this.name = "";
        } else {
            this.name = aName;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String aDescription) {
        if (aDescription == null) {
            this.description = "";
        } else {
            this.description = aDescription;
        }
    }

    public static class Factory {

        public static FKLAbstractPrompt parse(Element anXmlElement) throws Exception {

            FKLAbstractPrompt tPrompt = producePrompt(anXmlElement);
            return tPrompt;
        }

        private static FKLAbstractPrompt producePrompt(Element anXmlElement) {
            FKLAbstractPrompt tPrompt = null;

            String tType = null;
            String tName = null;
            String tDescription = null;

            // Process prompt properties
            NodeList tNodes = anXmlElement.getChildNodes();
            if (tNodes == null) {
                return tPrompt;
            }

            for (int i = 0; i < tNodes.getLength(); i++) {
                Node tPromptNode = tNodes.item(i);
                if (FKLTemplate.XML_TYPE.equals(tPromptNode.getNodeName())) {
                    tType = tPromptNode.getTextContent();
                } else if (FKLTemplate.XML_NAME.equals(tPromptNode.getNodeName())) {
                    tName = tPromptNode.getTextContent();
                } else if (FKLTemplate.XML_DESCRIPTION.equals(tPromptNode.getNodeName())) {
                    tDescription = tPromptNode.getTextContent();
                }
            }

            if (FKLTemplate.XML_PROMPT_TEXT.equals(tType)) {
                tPrompt = FKLPromptText.Factory.parse(anXmlElement);
            } else if (FKLTemplate.XML_PROMPT_MULTILINE.equals(tType)) {
                tPrompt = FKLPromptMultiline.Factory.parse(anXmlElement);
            } else if (FKLTemplate.XML_PROMPT_NUMBER.equals(tType)) {
                tPrompt = FKLPromptNumber.Factory.parse(anXmlElement);
            } else if (FKLTemplate.XML_PROMPT_DATE.equals(tType)) {
                tPrompt = FKLPromptDate.Factory.parse(anXmlElement);
            } else if (FKLTemplate.XML_PROMPT_CHECKBOX.equals(tType)) {
                tPrompt = FKLPromptCheckbox.Factory.parse(anXmlElement);
            } else if (FKLTemplate.XML_PROMPT_USER_DEFINED.equals(tType)) {
                tPrompt = FKLPromptUserDefined.Factory.parse(anXmlElement);
            }

            if (tPrompt != null) {
                tPrompt.setName(tName);
                tPrompt.setDescription(tDescription);
            }

            return tPrompt;
        }
    }
}