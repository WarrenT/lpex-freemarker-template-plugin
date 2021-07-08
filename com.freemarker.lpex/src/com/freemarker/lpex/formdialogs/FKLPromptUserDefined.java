package com.freemarker.lpex.formdialogs;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.freemarker.lpex.utils.XmlUtil;

public class FKLPromptUserDefined extends FKLAbstractPrompt {

    private String className;

    public String getClassName() {
        return className;
    }

    public void setClassName(String aClassName) {
        this.className = aClassName;
    }

    public static class Factory {

        public static FKLAbstractPrompt parse(Element anXmlElement) {
            FKLPromptUserDefined tPrompt = new FKLPromptUserDefined();

            parsePromptProperties(anXmlElement, tPrompt);

            return tPrompt;
        }

        protected static void parsePromptProperties(Element anXmlElement, FKLPromptUserDefined tPrompt) {

            NodeList tNodes = anXmlElement.getChildNodes();
            if (tNodes == null) {
                return;
            }

            for (int i = 0; i < tNodes.getLength(); i++) {
                Node tNode = tNodes.item(i);
                if (tNode instanceof Element) {
                    if (FKLTemplate.XML_TYPE.equals(tNode.getNodeName())) {
                        tPrompt.setClassName(XmlUtil.getOptionalAttribute((Element)tNode, FKLTemplate.XML_CLASS, ""));
                    }
                }
            }
        }
    }

}
