package com.freemarker.lpex.utils;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class XmlUtil {

    private XmlUtil() {
    }

    public static String getOptionalAttribute(Element anXmlElement, String anAttribute, String aDefault) {
        String tValue = anXmlElement.getAttribute(anAttribute);
        if (tValue == null) {
            return aDefault;
        }
        return tValue;
    }

    public static String getRequiredAttribute(Element anXmlElement, String anAttribute) throws Exception {
        String tValue = anXmlElement.getAttribute(anAttribute);
        if (tValue == null) {
            throw new Exception("Missing required attribute: " + anAttribute);
        }
        return tValue;
    }

    public static String getElementText(Node aNode) {
        return StringUtil.removeTrailingWhiteSpaces(aNode.getTextContent());
    }

    public static boolean parseBoolean(String aValue) {
        if ("yes".equalsIgnoreCase(aValue)) {
            return true;
        } else if ("no".equalsIgnoreCase(aValue)) {
            return false;
        }
        return Boolean.parseBoolean(aValue);
    }

}
