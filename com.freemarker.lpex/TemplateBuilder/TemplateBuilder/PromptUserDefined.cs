using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Xml;

namespace TemplateBuilder
{
  class PromptUserDefined : AbstractPrompt
  {

    // USERDEFINED
    private String _className;

    #region Constructors

    public PromptUserDefined()
      : base() {
      this.Name = "NewUserDefinedPrompt";
    }

    #endregion

    #region FKL attributes

    [Description("Class name for DATE prompts."), Category("FKL Attributes (extended)")]
    public string ClassName { get { return _className; } set { _className = value; } }

    #endregion

    #region Public methods

    public override int GetIcon() {
      return Icons.USERDEFINED;
    }

    public override AbstractPrompt Clone() {
      PromptUserDefined tNewCheckbox = (PromptUserDefined) AbstractPrompt.Factory.CreateInstance(this.GetType());
      tNewCheckbox.Name = "CopyOf" + this.Name;
      tNewCheckbox.Description = this.Description;
      tNewCheckbox.ClassName = this.ClassName;
      return tNewCheckbox;
    }


    public override XmlElement ToXml(XmlDocument aDocument) {

      XmlElement tPromptElement = aDocument.CreateElement("prompt");

      XmlElement tTypeElement = (XmlElement) tPromptElement.AppendChild(aDocument.CreateElement("type"));
      tTypeElement.SetAttribute("class", this.ClassName);
      tTypeElement.InnerText = GetTypeAsString();

      XmlElement tNameElement = (XmlElement) tPromptElement.AppendChild(aDocument.CreateElement("name"));
      tNameElement.InnerText = this.Name;

      XmlElement tDescriptionElement = (XmlElement) tPromptElement.AppendChild(aDocument.CreateElement("description"));
      tDescriptionElement.InnerText = this.Description;

      return tPromptElement;
    }

    #endregion

    #region Factory

    public static new class Factory
    {

      public static AbstractPrompt Parse(XmlElement anXmlElement) {

        PromptUserDefined tPrompt = (PromptUserDefined) AbstractPrompt.Factory.CreateInstance(typeof(PromptUserDefined));
        parsePromptChildren(tPrompt, anXmlElement);

        return tPrompt;
      }

      private static void parsePromptChildren(PromptUserDefined aPrompt, XmlNode aPromptNode) {
        foreach (XmlNode tPromptNode in aPromptNode.ChildNodes) {

          if (tPromptNode.Name == "type") {
            aPrompt.ClassName = getRequiredAttribute(tPromptNode, "class");
          }
        }

      }
    }

    #endregion

  }
}
