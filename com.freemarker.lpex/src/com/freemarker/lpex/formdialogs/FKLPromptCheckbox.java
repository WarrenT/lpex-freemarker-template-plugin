package com.freemarker.lpex.formdialogs;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.freemarker.lpex.utils.XmlUtil;

public class FKLPromptCheckbox extends FKLAbstractPrompt implements ILabel, IMaxLength, IDefaultValue {

    private String label;

    private Integer maxLength;

    private String checkedValue;

    private String uncheckedValue;

    private String defaultValue;

    @Override
    public String getLabel() {
        return label;
    }

    public void setLabel(String aLabel) {
        this.label = aLabel;
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
        if (defaultValue.equals(checkedValue)) {
            return "true";
        } else {
            return "false";
        }
    }

    public void setDefaultValue(String aDefaultValue) {
        if (aDefaultValue == null) {
            this.defaultValue = "";
        } else {
            this.defaultValue = aDefaultValue;
        }
    }

    public String getCheckedValue() {
        return checkedValue;
    }

    public void setCheckedValue(String aCheckedValue) {
        if (aCheckedValue == null) {
            this.checkedValue = "";
        } else {
            this.checkedValue = aCheckedValue;
        }
    }

    public String getUncheckedValue() {
        return uncheckedValue;
    }

    public void setUncheckedValue(String anUncheckedValue) {
        if (anUncheckedValue == null) {
            this.uncheckedValue = "";
        } else {
            this.uncheckedValue = anUncheckedValue;
        }
    }

    public static class Factory {

        public static FKLAbstractPrompt parse(Element anXmlElement) {
            FKLPromptCheckbox tPrompt = new FKLPromptCheckbox();

            parsePromptProperties(anXmlElement, tPrompt);

            return tPrompt;
        }

        protected static void parsePromptProperties(Element anXmlElement, FKLPromptCheckbox tPrompt) {

            NodeList tNodes = anXmlElement.getChildNodes();
            if (tNodes == null) {
                return;
            }

            for (int i = 0; i < tNodes.getLength(); i++) {
                Node tNode = tNodes.item(i);
                if (tNode instanceof Element) {
                    if (FKLTemplate.XML_LABEL.equals(tNode.getNodeName())) {
                        tPrompt.setLabel(tNode.getTextContent());
                    } else if (FKLTemplate.XML_TYPE.equals(tNode.getNodeName())) {
                        Element tElement = (Element)tNode;
                        String tMaxLength = XmlUtil.getOptionalAttribute(tElement, FKLTemplate.XML_MAX_LENGTH, null);
                        try {
                            tPrompt.setMaxLength(Integer.parseInt(tMaxLength));
                        } catch (Exception e) {
                            tPrompt.setMaxLength(null);
                        }

                        tPrompt.setCheckedValue(XmlUtil.getOptionalAttribute(tElement, FKLTemplate.XML_CHECKED_VALUE, ""));
                        tPrompt.setUncheckedValue(XmlUtil.getOptionalAttribute(tElement, FKLTemplate.XML_UNCHECKED_VALUE, ""));
                        tPrompt.setDefaultValue(XmlUtil.getOptionalAttribute(tElement, FKLTemplate.XML_DEFAULT_VALUE, ""));
                    }
                }
            }
        }
    }
}
