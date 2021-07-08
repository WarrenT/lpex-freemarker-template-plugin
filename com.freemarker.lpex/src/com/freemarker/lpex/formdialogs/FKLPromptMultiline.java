package com.freemarker.lpex.formdialogs;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.freemarker.lpex.utils.XmlUtil;

public class FKLPromptMultiline extends FKLAbstractPrompt implements ILabel, IHint, IMaxLength, IDefaultValue {

    private String label;

    private String hint;

    private Integer maxLength;

    private String defaultValue;

    @Override
    public String getLabel() {
        return label;
    }

    public void setLabel(String aLabel) {
        this.label = aLabel;
    }

    @Override
    public String getHint() {
        if (maxLength == null || hint.length() <= maxLength) {
            return hint;
        } else {
            return hint.substring(0, maxLength);
        }
    }

    public void setHint(String aHint) {
        this.hint = aHint;
    }

    @Override
    public boolean hasMaxLength() {
        if (maxLength != null) {
            return true;
        }
        return false;
    }

    @Override
    public int getMaxLength() {
        return maxLength.intValue();
    }

    public void setMaxLength(Integer aMaxLength) {
        this.maxLength = aMaxLength;
    }

    @Override
    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String aDefaultValue) {
        if (aDefaultValue == null) {
            this.defaultValue = "";
        } else {
            this.defaultValue = aDefaultValue;
        }
    }

    public static class Factory {

        public static FKLAbstractPrompt parse(Element anXmlElement) {
            FKLPromptMultiline tPrompt = new FKLPromptMultiline();

            parsePromptProperties(anXmlElement, tPrompt);

            return tPrompt;
        }

        protected static void parsePromptProperties(Element anXmlElement, FKLPromptMultiline tPrompt) {

            NodeList tNodes = anXmlElement.getChildNodes();
            if (tNodes == null) {
                return;
            }

            for (int i = 0; i < tNodes.getLength(); i++) {
                Node tNode = tNodes.item(i);
                if (tNode instanceof Element) {
                    if (FKLTemplate.XML_LABEL.equals(tNode.getNodeName())) {
                        tPrompt.setLabel(tNode.getTextContent());
                    } else if (FKLTemplate.XML_HINT.equals(tNode.getNodeName())) {
                        tPrompt.setHint(XmlUtil.getElementText(tNode));
                    } else if (FKLTemplate.XML_TYPE.equals(tNode.getNodeName())) {
                        Element tElement = (Element)tNode;
                        String tMaxLength = XmlUtil.getOptionalAttribute(tElement, FKLTemplate.XML_MAX_LENGTH, null);
                        try {
                            tPrompt.setMaxLength(Integer.parseInt(tMaxLength));
                        } catch (Exception e) {
                            tPrompt.setMaxLength(null);
                        }

                        tPrompt.setDefaultValue(XmlUtil.getOptionalAttribute(tElement, FKLTemplate.XML_DEFAULT_VALUE, ""));
                    }
                }
            }
        }
    }
}
