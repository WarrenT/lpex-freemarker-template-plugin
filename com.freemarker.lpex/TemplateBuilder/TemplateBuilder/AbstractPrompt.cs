using System.Windows.Forms;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Xml;
using System.IO;

using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;

namespace TemplateBuilder
{
  public abstract class AbstractPrompt : ParsedObject
  {
    public enum PromptType
    {
      TEXT,
      MULTILINE,
      DATE,
      CHECKBOX,
      USERDEFINED
    }

    private const String FIELD_TEXT = "text";
    private const String FIELD_MULTILINE = "multiline";
    private const String FIELD_NUMBER = "number";
    private const String FIELD_DATE = "date";
    private const String FIELD_CHECKBOX = "checkbox";
    private const String FIELD_USERDEFINED = "userdefined";

    // Common attributes
    private string _name;
    private String _description;

    // All types internally
    private PromptGroup _parent;
    private int _orderKey;

    [Browsable(false)]
    public PromptGroup Parent { get { return _parent; } set { _parent = value; } }

    #region Constructors

    public AbstractPrompt() {
      this.Name = "PromptName";
      this.Description = "Description here";
      this.Parent = null;
      this.OrderKey = 0;
    }

    #endregion

    #region FKL attributes

    [Description("Description of the prompt."), Category("FKL Attributes")]
    public string Name {
      set {
        if (value.Contains(' ')) {
          throw (new ArgumentException("Name cannot contain spaces."));
        } else {
          callRenameHandler(value);
          this._name = value;
        }
      }
      get { return this._name; }
    }

    [Description("Description of the prompt."), Category("FKL Attributes")]
    public string Description {
      get { return _description; }
      set { _description = value; }
    }

    [ReadOnlyAttribute(true)]
    [Description("Sequence number of this prompt group."), Category("Other")]
    public int OrderKey { get { return _orderKey; } set { _orderKey = value; } }

    [Description("Name of the template variable."), Category("Other")]
    public string VariableName { get { return GetVariableName(this.Parent, this.Name); } }

    [Description("Type."), Category("Other")]
    public string Type { get { return GetTypeAsString(); } }

    #endregion

    #region Public methods

    public String GetTypeAsString() {
      if (this is PromptText) {
        return FIELD_TEXT;
      } else if (this is PromptMultiline) {
        return FIELD_MULTILINE;
      } else if (this is PromptNumber) {
        return FIELD_NUMBER;
      } else if (this is PromptDate) {
        return FIELD_DATE;
      } else if (this is PromptCheckbox) {
        return FIELD_CHECKBOX;
      } else if (this is PromptUserDefined) {
        return FIELD_USERDEFINED;
      } else {
        return FIELD_TEXT;
      }
    }

    public string GetVariableName(PromptGroup promptGroup, string promtName) {
      string variable = "";
      if (promptGroup.Repeatable) {
        variable = "\r\n<#list " + promptGroup.Name + ".repeats as var>\r\n";
        variable += "${var." + promtName + "}\r\n";
        variable += "</#list>";
      } else {
        variable = "${" + promptGroup.Name + "." + promtName + "}";
      }
      return variable;
    }

    public void Remove() {
      callDeleteHandler();
      this.Parent.RemovePrompt(this);
    }

    public abstract XmlElement ToXml(XmlDocument aDocument);

    public abstract AbstractPrompt Clone();

    public abstract int GetIcon();

    #endregion

    #region Private methods

    private static Stream Serialize(object source) {
      IFormatter formatter = new BinaryFormatter();
      Stream stream = new MemoryStream();
      formatter.Serialize(stream, source);
      return stream;
    }

    private static T Deserialize<T>(Stream stream) {
      IFormatter formatter = new BinaryFormatter();
      stream.Position = 0;
      return (T) formatter.Deserialize(stream);
    }

    private void callRenameHandler(string newName) {
      try {
        if (Parent.Parent.promptRenameHandler != null)
          Parent.Parent.promptRenameHandler(this, newName);
      } catch { }
    }

    private void callDeleteHandler() {
      if (Parent.Parent.beforePromptDeleteHandler != null)
        Parent.Parent.beforePromptDeleteHandler(this);
    }
    #endregion

    #region Factory

    public static class Factory
    {

      public static AbstractPrompt CreateInstance(Type aType) {
        return (AbstractPrompt) Activator.CreateInstance(aType);
      }

      public static AbstractPrompt Parse(PromptGroup aParent, XmlElement anXmlElement) {

        AbstractPrompt tPrompt = producePrompt(aParent, anXmlElement);

        return tPrompt;
      }

      private static AbstractPrompt producePrompt(PromptGroup aParent, XmlElement anXmlElement) {
        AbstractPrompt tPrompt = null;
        String tName = null;
        String tDescription = null;

        foreach (XmlNode tPromptNode in anXmlElement.ChildNodes) {
          String tType = null;

          if (tPromptNode.Name == "type") {
            tType = tPromptNode.InnerText;
          } else if (tPromptNode.Name == "name") {
            tName = tPromptNode.InnerText;
          } else if (tPromptNode.Name == "description") {
            tDescription = tPromptNode.InnerText;
          }

          if (tType == AbstractPrompt.FIELD_TEXT) {
            tPrompt = PromptText.Factory.Parse(aParent, anXmlElement);
          } else if (tType == AbstractPrompt.FIELD_MULTILINE) {
            tPrompt = PromptMultiline.Factory.Parse(anXmlElement);
          } else if (tType == AbstractPrompt.FIELD_NUMBER) {
            tPrompt = PromptNumber.Factory.Parse(anXmlElement);
          } else if (tType == AbstractPrompt.FIELD_DATE) {
            tPrompt = PromptDate.Factory.Parse(anXmlElement);
          } else if (tType == AbstractPrompt.FIELD_CHECKBOX) {
            tPrompt = PromptCheckbox.Factory.Parse(anXmlElement);
          } else if (tType == AbstractPrompt.FIELD_USERDEFINED) {
            tPrompt = PromptUserDefined.Factory.Parse(anXmlElement);
          }

        }

        if (tPrompt != null) {
          tPrompt.Name = tName;
          tPrompt.Description = tDescription;
        }

        return tPrompt;
      }
    }
    #endregion
  }
}

