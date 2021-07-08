package com.freemarker.lpex.formdialogs;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.freemarker.lpex.preferences.Preferences;
import com.freemarker.lpex.utils.XmlUtil;

public class FKLPromptDate extends FKLAbstractPrompt implements ILabel {

    private String label;

    private String dateFormat;

    @Override
    public String getLabel() {
        return label;
    }

    public void setLabel(String aLabel) {
        this.label = aLabel;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String aDateFormat) {
        this.dateFormat = aDateFormat;
    }

    public static class Factory {

        public static FKLAbstractPrompt parse(Element anXmlElement) {
            FKLPromptDate tPrompt = new FKLPromptDate();

            parsePromptProperties(anXmlElement, tPrompt);

            return tPrompt;
        }

        protected static void parsePromptProperties(Element anXmlElement, FKLPromptDate tPrompt) {

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
                        String tDefaultDateFormat = Preferences.getInstance().getDateFormat();
                        tPrompt.setDateFormat(XmlUtil.getOptionalAttribute((Element)tNode, FKLTemplate.XML_DATE_FORMAT, tDefaultDateFormat));
                    }
                }
            }
        }
    }

}
