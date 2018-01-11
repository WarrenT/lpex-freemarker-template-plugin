using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Xml;

namespace TemplateBuilder
{
  class PromptNumber : AbstractPrompt, ILabel
  {

    // TEXT, MULTILINE, CHECKBOX
    private String _length;
    private String _decimalPositions;
    private String _defautlValue;

    // TEXT, MULTILINE, DATE, CHECKBOX
    private String _label;

    #region Constructors

    public PromptNumber()
      : base() {
      this.Name = "NewNumberPrompt";
      this.Label = "Label here";
      this.Length = "";
      this._decimalPositions = "";
    }

    #endregion

    #region FKL attributes

    [Description("Label of the prompt."), Category("FKL Attributes")]
    public string Label { get { return _label; } set { _label = value; } }

    [Description("Maximum length of input value."), Category("FKL Attributes")]
    public String Length { get { return _length; } set { _length = value; } }

    [Description("Decimal positions of input value."), Category("FKL Attributes")]
    public String DecimalPositions { get { return _decimalPositions; } set { _decimalPositions = value; } }

    [Description("Default value."), Category("FKL Attributes")]
    public String DefaultValue { get { return _defautlValue; } set { _defautlValue = value; } }

    #endregion

    #region Public methods

    public override int GetIcon() {
      return Icons.TEXT_NUMBER;
    }

    public override AbstractPrompt Clone() {
      PromptNumber tNewNumberPrompt = (PromptNumber) AbstractPrompt.Factory.CreateInstance(this.GetType());
      tNewNumberPrompt.Name = "CopyOf" + this.Name;
      tNewNumberPrompt.Description = this.Description;
      tNewNumberPrompt.Label = this.Label;
      tNewNumberPrompt.Length = this.Length;
      tNewNumberPrompt.DecimalPositions = this.DecimalPositions;
      tNewNumberPrompt.DefaultValue = this.DefaultValue;
      return tNewNumberPrompt;
    }

    public override XmlElement ToXml(XmlDocument aDocument) {

      XmlElement tPromptElement = aDocument.CreateElement("prompt");

      XmlElement tTypeElement = (XmlElement) tPromptElement.AppendChild(aDocument.CreateElement("type"));
      if (this.Length != "") {
        tTypeElement.SetAttribute("maxLength", this.Length.ToString());
      }
      if (this.DecimalPositions != "") {
        tTypeElement.SetAttribute("decimalPositions", this.DecimalPositions.ToString());
      }
      if (this.DefaultValue != "") {
        tTypeElement.SetAttribute("defaultValue", this.DefaultValue);
      }
      tTypeElement.InnerText = GetTypeAsString();

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

        PromptNumber tPrompt = (PromptNumber) AbstractPrompt.Factory.CreateInstance(typeof(PromptNumber));
        parsePromptChildren(tPrompt, anXmlElement);

        return tPrompt;
      }

      private static void parsePromptChildren(PromptNumber aPrompt, XmlNode aPromptNode) {
        foreach (XmlNode tPromptNode in aPromptNode.ChildNodes) {
          if (tPromptNode.Name == "label") {
            aPrompt.Label = tPromptNode.InnerText;
          } else if (tPromptNode.Name == "type") {
            int tLength = -1;
            if (Int32.TryParse(getOptionalAttribute(tPromptNode, "maxLength", tLength.ToString()), out tLength)) {
              if (tLength == -1) {
                aPrompt.Length = "";
              } else {
                aPrompt.Length = tLength.ToString();
              }
            };

            int tDecimalPositions = -1;
            if (Int32.TryParse(getOptionalAttribute(tPromptNode, "decimalPositions", tDecimalPositions.ToString()), out tDecimalPositions)) {
              if (tDecimalPositions == -1) {
                aPrompt.DecimalPositions = "";
              } else {
                aPrompt.DecimalPositions = tDecimalPositions.ToString();
              }
            };

            aPrompt.DefaultValue = getOptionalAttribute(tPromptNode, "defaultValue", "");
          }
        }

      }

    }
    #endregion

  }
}
