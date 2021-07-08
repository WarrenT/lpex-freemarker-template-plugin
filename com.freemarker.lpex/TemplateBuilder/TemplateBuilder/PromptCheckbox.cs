using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Xml;

namespace TemplateBuilder
{
  class PromptCheckbox : AbstractPrompt, ILabel
  {

    // CHECKBOX
    private String _checkedValue;
    private String _uncheckedValue;

    // TEXT, MULTILINE, CHECKBOX
    private String _defautlValue;

    // TEXT, MULTILINE, DATE, CHECKBOX
    private String _label;

    #region Constructors

    public PromptCheckbox()
      : base() {
      this.Name = "NewCheckboxPrompt";
      this.Label = "Label here";
      this.UncheckedValue = "";
      this.CheckedValue = "";
      this.DefaultValue = "";
    }

    #endregion

    #region FKL attributes

    [Description("Checked value for CHECKBOX prompts."), Category("FKL Attributes (extended)")]
    public string CheckedValue { get { return _checkedValue; } set { _checkedValue = value; } }

    [Description("Unhecked value for CHECKBOX prompts."), Category("FKL Attributes (extended)")]
    public string UncheckedValue { get { return _uncheckedValue; } set { _uncheckedValue = value; } }

    [Description("Label of the prompt."), Category("FKL Attributes")]
    public string Label { get { return _label; } set { _label = value; } }

    [Description("Default value. If this property matches 'CheckedValue', the checkbox is checked, else unchecked."), Category("FKL Attributes")]
    public String DefaultValue { get { return _defautlValue; } set { _defautlValue = value; } }

    #endregion

    #region Public methods

    public override int GetIcon() {
      return Icons.CHECKBOX;
    }

    public override AbstractPrompt Clone() {
      PromptCheckbox tNewCheckbox = (PromptCheckbox) AbstractPrompt.Factory.CreateInstance(this.GetType());
      tNewCheckbox.Name = "CopyOf" + this.Name;
      tNewCheckbox.Description = this.Description;
      tNewCheckbox.Label = this.Label;
      tNewCheckbox.CheckedValue = this.CheckedValue;
      tNewCheckbox.UncheckedValue = this.UncheckedValue;
      tNewCheckbox.DefaultValue = this.DefaultValue;
      return tNewCheckbox;
    }

    public override XmlElement ToXml(XmlDocument aDocument) {

      XmlElement tPromptElement = aDocument.CreateElement("prompt");

      XmlElement tTypeElement = (XmlElement) tPromptElement.AppendChild(aDocument.CreateElement("type"));
      tTypeElement.SetAttribute("checkedValue", this.CheckedValue);
      tTypeElement.SetAttribute("uncheckedValue", this.UncheckedValue);
      if (this.DefaultValue != "") {
        tTypeElement.SetAttribute("defaultValue", this.DefaultValue);
      }
      tTypeElement.InnerText = GetTypeAsString();

      XmlElement tNameElement = (XmlElement) tPromptElement.AppendChild(aDocument.CreateElement("name"));
      tNameElement.InnerText = this.Name;

      XmlElement tDescriptionElement = (XmlElement) tPromptElement.AppendChild(aDocument.CreateElement("description"));
      tDescriptionElement.InnerText = this.Description;

      XmlElement labelElement = (XmlElement) tPromptElement.AppendChild(aDocument.CreateElement("label"));
      labelElement.InnerText = this.Label;

      return tPromptElement;
    }

    #endregion

    #region Factory

    public static new class Factory
    {

      public static AbstractPrompt Parse(XmlElement anXmlElement) {

        PromptCheckbox tPrompt = (PromptCheckbox) AbstractPrompt.Factory.CreateInstance(typeof(PromptCheckbox));
        parsePromptChildren(tPrompt, anXmlElement);

        return tPrompt;
      }

      private static void parsePromptChildren(PromptCheckbox aPrompt, XmlNode aPromptNode) {
        foreach (XmlNode tPromptNode in aPromptNode.ChildNodes) {

          if (tPromptNode.Name == "label") {
            aPrompt.Label = tPromptNode.InnerText;
          } else if (tPromptNode.Name == "type") {

            aPrompt.CheckedValue = getOptionalAttribute(tPromptNode, "checkedValue", "");
            aPrompt.UncheckedValue = getOptionalAttribute(tPromptNode, "uncheckedValue", "");
            aPrompt.DefaultValue = getOptionalAttribute(tPromptNode, "defaultValue", "");
          }
        }

      }

    }
    #endregion

  }
}
