package com.freemarker.lpex.formdialogs;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.freemarker.lpex.utils.XmlUtil;

public class FKLPromptNumber extends FKLAbstractPrompt implements ILabel, IMaxLength, IDecimalPositions, IDefaultValue {

    private String label;

    private Integer maxLength;

    private Integer decimalPositions;

    private String defaultValue;

    private Number maxValue = -1;

    @Override
    public String getLabel() {
        return label;
    }

    public void setLabel(String aLabel) {
        this.label = aLabel;
    }

    @Override
    public boolean hasMaxLength() {
        if (maxLength != null || decimalPositions != null) {
            return true;
        }
        return false;
    }

    @Override
    public int getMaxLength() {
        int tMaxLength = maxLength.intValue() + 1; // length + minus sign
        if (hasDecimalPositions()) {
            tMaxLength++; // + comma
        }
        return tMaxLength;
    }

    public void setMaxLength(Integer aLength) {
        this.maxLength = aLength;
        updateMaxValue();
    }

    @Override
    public boolean hasDecimalPositions() {
        if (decimalPositions != null) {
            return true;
        }
        return false;
    }

    @Override
    public int getDecimalPositions() {
        return decimalPositions.intValue();
    }

    public void setDecimalPositions(Integer aDecimalPositions) {
        this.decimalPositions = aDecimalPositions;
        updateMaxValue();
    }

    @Override
    public String getDefaultValue() {
        return defaultValue;
    }

    public Number getMaxValue() {
        return maxValue;
    }

    public void setDefaultValue(String aDefaultValue) {
        if (aDefaultValue == null) {
            this.defaultValue = "";
        } else {
            this.defaultValue = aDefaultValue;
        }
    }

    private void updateMaxValue() {

        DecimalFormat tFormat = (DecimalFormat)DecimalFormat.getInstance();
        DecimalFormatSymbols tSymbols = tFormat.getDecimalFormatSymbols();
        char tComma = tSymbols.getDecimalSeparator();

        StringBuffer tBuffer = new StringBuffer();
        if (maxLength != null) {
            int tMaxLength = maxLength.intValue();
            if (decimalPositions != null) {
                tMaxLength = tMaxLength - decimalPositions.intValue();
            }

            for (int i = 0; i < tMaxLength; i++) {
                tBuffer.append("9");
            }
        }
        if (decimalPositions != null) {
            tBuffer.append(tComma);
            for (int i = 0; i < decimalPositions; i++) {
                tBuffer.append("9");
            }
        }

        try {
            maxValue = tFormat.parse(tBuffer.toString());
        } catch (ParseException e) {
            maxValue = -1;
        }
    }

    public static class Factory {

        public static FKLAbstractPrompt parse(Element anXmlElement) {
            FKLPromptNumber tPrompt = new FKLPromptNumber();

            parsePromptProperties(anXmlElement, tPrompt);

            return tPrompt;
        }

        protected static void parsePromptProperties(Element anXmlElement, FKLPromptNumber tPrompt) {

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

                        String tDecimalPositions = XmlUtil.getOptionalAttribute(tElement, FKLTemplate.XML_DECIMAL_POSITIONS, null);
                        try {
                            tPrompt.setDecimalPositions(Integer.parseInt(tDecimalPositions));
                        } catch (Exception e) {
                            tPrompt.setDecimalPositions(null);
                        }

                        tPrompt.setDefaultValue(XmlUtil.getOptionalAttribute(tElement, FKLTemplate.XML_DEFAULT_VALUE, ""));
                    }
                }
            }
        }
    }
}
