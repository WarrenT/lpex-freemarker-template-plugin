using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Xml;

namespace TemplateBuilder
{
  class PromptDate : AbstractPrompt, ILabel
  {

    // DATE
    private String _dateFormat;

    // TEXT, MULTILINE, DATE, CHECKBOX
    private String _label;

    #region Constructors

    public PromptDate()
      : base() {
      this.Name = "NewDatePrompt";
      this.Label = "Label here";
      this.DateFormat = "yyyy-MM-dd";
    }

    #endregion

    #region FKL attributes

    [Description("Date format for DATE prompts."), Category("FKL Attributes (extended)")]
    public string DateFormat { get { return _dateFormat; } set { _dateFormat = value; } }

    [Description("Label of the prompt."), Category("FKL Attributes")]
    public string Label { get { return _label; } set { _label = value; } }

    #endregion

    #region Public methods

    public override int GetIcon() {
      return Icons.DATE;
    }

    public override AbstractPrompt Clone() {
      PromptDate tNewCheckbox = (PromptDate) AbstractPrompt.Factory.CreateInstance(this.GetType());
      tNewCheckbox.Name = "CopyOf" + this.Name;
      tNewCheckbox.Description = this.Description;
      tNewCheckbox.Label = this.Label;
      tNewCheckbox.DateFormat = this.DateFormat;
      return tNewCheckbox;
    }

    public override XmlElement ToXml(XmlDocument aDocument) {

      XmlElement tPromptElement = aDocument.CreateElement("prompt");

      XmlElement typeElement = (XmlElement) tPromptElement.AppendChild(aDocument.CreateElement("type"));
      typeElement.SetAttribute("dateFormat", this.DateFormat);
      typeElement.InnerText = GetTypeAsString();

      XmlElement tNameElement = (XmlElement) tPromptElement.AppendChild(aDocument.CreateElement("name"));
      tNameElement.InnerText = this.Name;

      XmlElement tDescriptionElement = (XmlElement) tPromptElement.AppendChild(aDocument.CreateElement("description"));
      tDescriptionElement.InnerText = this.Description;

      XmlElement tLabelElement = (XmlElement) tPromptElement.AppendChild(aDocument.CreateElement("label"));
      tLabelElement.InnerText = this.Label;

      return tPromptElement;
    }

    #endregion

    #region Factory

    public static new class Factory
    {

      public static AbstractPrompt Parse(XmlElement anXmlElement) {

        PromptDate tPrompt = (PromptDate) AbstractPrompt.Factory.CreateInstance(typeof(PromptDate));
        parsePromptChildren(tPrompt, anXmlElement);

        return tPrompt;
      }

      public static void parsePromptAttributes(PromptDate aPrompt, XmlNode aPromptNode) {
        {
          aPrompt.DateFormat = getOptionalAttribute(aPromptNode, "dateFormat", "yyyy-MM-dd");
        }
      }

      private static void parsePromptChildren(PromptDate aPrompt, XmlNode aPromptNode) {
        foreach (XmlNode tPromptNode in aPromptNode.ChildNodes) {

          if (tPromptNode.Name == "label") {
            aPrompt.Label = tPromptNode.InnerText;
          } else if (tPromptNode.Name == "type") {
            aPrompt.DateFormat = getOptionalAttribute(tPromptNode, "dateFormat", "yyyy-MM-dd");
          }
        }

      }

    }
    #endregion

  }
}
