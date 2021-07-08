using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Xml;

namespace TemplateBuilder
{
  class PromptMultiline : AbstractPrompt, IHint, ILabel
  {

    // TEXT, MULTILINE, CHECKBOX
    private String _hint;
    private String _maxLength;
    private String _defautlValue;

    // TEXT, MULTILINE, DATE, CHECKBOX
    private String _label;

    #region Constructors

    public PromptMultiline()
      : base() {
      this.Name = "NewMultilinePrompt";
      this.Label = "Label here";
      this.Hint = "Hint here";
      this.Length = "";
    }

    #endregion

    #region FKL attributes

    [Description("Hint of the prompt."), Category("FKL Attributes")]
    public string Hint { get { return _hint; } set { _hint = value; } }

    [Description("Label of the prompt."), Category("FKL Attributes")]
    public string Label { get { return _label; } set { _label = value; } }

    [Description("Maximum length of input value."), Category("FKL Attributes")]
    public String Length { get { return _maxLength; } set { _maxLength = value; } }

    [Description("Default value."), Category("FKL Attributes")]
    public String DefaultValue { get { return _defautlValue; } set { _defautlValue = value; } }

    #endregion

    #region Public methods

    public override int GetIcon() {
      return Icons.TEXT_FIELD_ADD;
    }

    public override AbstractPrompt Clone() {
      PromptMultiline tNewMultiline = (PromptMultiline) AbstractPrompt.Factory.CreateInstance(this.GetType());
      tNewMultiline.Name = "CopyOf" + this.Name;
      tNewMultiline.Description = this.Description;
      tNewMultiline.Label = this.Label;
      tNewMultiline.Hint = this.Hint;
      tNewMultiline.Length = this.Length;
      tNewMultiline.DefaultValue = this.DefaultValue;
      return tNewMultiline;
    }

    public override XmlElement ToXml(XmlDocument aDocument) {

      XmlElement tPromptElement = aDocument.CreateElement("prompt");

      XmlElement tTypeElement = (XmlElement) tPromptElement.AppendChild(aDocument.CreateElement("type"));
      if (this.Length != "") {
        tTypeElement.SetAttribute("maxLength", this.Length.ToString());
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

      XmlElement tHintElement = (XmlElement) tPromptElement.AppendChild(aDocument.CreateElement("hint"));
      tHintElement.InnerText = this.Hint;

      return tPromptElement;
    }

    #endregion

    #region Factory

    public static new class Factory
    {

      public static AbstractPrompt Parse(XmlElement anXmlElement) {

        PromptMultiline tPrompt = (PromptMultiline) AbstractPrompt.Factory.CreateInstance(typeof(PromptMultiline));
        parsePromptChildren(tPrompt, anXmlElement);

        return tPrompt;
      }

      private static void parsePromptChildren(PromptMultiline aPrompt, XmlNode aPromptNode) {
        foreach (XmlNode tPromptNode in aPromptNode.ChildNodes) {
          if (tPromptNode.Name == "label") {
            aPrompt.Label = tPromptNode.InnerText;
          } else if (tPromptNode.Name == "hint") {
            aPrompt.Hint = tPromptNode.InnerText;
          } else if (tPromptNode.Name == "type") {
            int tMaxLength = -1;
            if (Int32.TryParse(getOptionalAttribute(tPromptNode, "maxLength", tMaxLength.ToString()), out tMaxLength)) {
              if (tMaxLength == -1) {
                aPrompt.Length = "";
              } else {
                aPrompt.Length = tMaxLength.ToString();
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
