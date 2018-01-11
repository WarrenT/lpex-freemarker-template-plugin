using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;

namespace TemplateBuilder
{
  public class ParsedObject
  {
    public static String getOptionalAttribute(XmlNode aXmlNode, String anAttribute, String aDefaultValue) {
      XmlNode tAttrNode = aXmlNode.Attributes.GetNamedItem(anAttribute);
      if (tAttrNode == null) {
        return aDefaultValue;
      }
      return tAttrNode.Value;
    }

    public static String getRequiredAttribute(XmlNode aXmlNode, String anAttribute) {
      XmlNode tAttrNode = aXmlNode.Attributes.GetNamedItem(anAttribute);
      if (tAttrNode == null) {
        return null;
      }
      return tAttrNode.Value;
    }
  }
}
