package com.freemarker.lpex.formdialogs;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.freemarker.lpex.utils.XmlUtil;

public class FKLPromptText extends FKLPromptMultiline implements IUppercase {

    private static final long serialVersionUID = -6387647827720969819L;

    private boolean isUppercase = false;

    @Override
    public boolean isUpperCase() {
        return isUppercase;
    }

    public void setUppercase(boolean anIsUppercase) {
        this.isUppercase = anIsUppercase;
    }

    public static class Factory {

        public static FKLAbstractPrompt parse(Element anXmlElement) {
            FKLPromptText tPrompt = new FKLPromptText();

            FKLPromptMultiline.Factory.parsePromptProperties(anXmlElement, tPrompt);
            parsePromptProperties(anXmlElement, tPrompt);

            return tPrompt;
        }

        protected static void parsePromptProperties(Element anXmlElement, FKLPromptText tPrompt) {

            NodeList tNodes = anXmlElement.getChildNodes();
            if (tNodes == null) {
                return;
            }

            for (int i = 0; i < tNodes.getLength(); i++) {
                Node tNode = tNodes.item(i);
                if (tNode instanceof Element) {
                    if (FKLTemplate.XML_TYPE.equals(tNode.getNodeName())) {
                        String tUppercase = XmlUtil.getOptionalAttribute((Element)tNode, FKLTemplate.XML_UPPER_CASE, null);
                        tPrompt.setUppercase(XmlUtil.parseBoolean(tUppercase));
                    }
                }
            }
        }
    }
}
