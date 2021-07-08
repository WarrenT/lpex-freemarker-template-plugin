using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Xml;

namespace TemplateBuilder
{
  class PromptText : PromptMultiline
  {

    // TEXT
    private Boolean _upperCase;

    #region Constructors

    public PromptText()
      : base() {
      this.Name = "NewTextPrompt";
    }

    #endregion

    #region FKL attributes

    [Description("Upper-case attribute of TEXT input fields."), Category("FKL Attributes (extended)")]
    public Boolean UpperCase { get { return _upperCase; } set { _upperCase = value; } }

    #endregion

    #region Public methods

    public override int GetIcon() {
      return Icons.TEXT_FIELD;
    }

    public override AbstractPrompt Clone() {
      PromptText tNewPromptText = (PromptText) AbstractPrompt.Factory.CreateInstance(this.GetType());
      tNewPromptText.Name = "CopyOf" + this.Name;
      tNewPromptText.Description = this.Description;
      tNewPromptText.Label = this.Label;
      tNewPromptText.Hint = this.Hint;
      tNewPromptText.UpperCase = this.UpperCase;
      tNewPromptText.Length = this.Length;
      tNewPromptText.DefaultValue = this.DefaultValue;
      return tNewPromptText;
    }


    public override XmlElement ToXml(XmlDocument aDocument) {
      XmlElement tPromptElement = base.ToXml(aDocument);

      XmlNodeList tChildNodes = tPromptElement.GetElementsByTagName("type");
      XmlElement tTypeElement = (XmlElement) tChildNodes.Item(0);
      tTypeElement.SetAttribute("uppercase", this.UpperCase.ToString().ToLower());
      return tPromptElement;
    }

    #endregion

    #region Factory

    public static new class Factory
    {

      public static AbstractPrompt Parse(PromptGroup aParent, XmlElement anXmlElement) {

        PromptText tPrompt = (PromptText) AbstractPrompt.Factory.CreateInstance(typeof(PromptText));
        parsePromptChildren(tPrompt, anXmlElement);

        return tPrompt;
      }

      private static void parsePromptChildren(PromptText aPrompt, XmlNode aPromptNode) {

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
            aPrompt.UpperCase = Boolean.Parse(getOptionalAttribute(tPromptNode, "uppercase", "false"));
          }
        }

      }

    }
    #endregion

  }
}
