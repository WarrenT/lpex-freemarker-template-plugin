using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Xml;

namespace TemplateBuilder
{
  public class PromptGroup : ParsedObject
  {
    private const bool REPEATABLE = true;
    private const bool NOT_REPEATABLE = false;

    private string _name;
    private bool _repeatable;
    private int _maxRepeats;

    private Template _parent;
    private int _orderKey;
    private List<AbstractPrompt> _prompts;

    #region Constructors

    public PromptGroup() {
      Name = "NewPromptGroup";
      Parent = null;
      Repeatable = false;
      MaxRepeats = 0;
      _prompts = new List<AbstractPrompt>();
    }
    #endregion

    #region FKL attributes

    [Description("Name of the prompt group."), Category("FKL Attributes")]
    public string Name {
      set {
        if (value.Contains(' ')) {
          throw (new ArgumentException("Name cannot contain spaces."));
        } else {
          this._name = value;
        }
      }
      get { return this._name; }
    }

    [Description("Specifies whether or not this prompt group is repeatable."), Category("FKL Attributes")]
    public bool Repeatable {
      get {
        return _repeatable;
      }
      set {
        _repeatable = value;
      }
    }

    [Description("Number of repeates of a repeatable prompt group."), Category("FKL Attributes")]
    public int MaxRepeats {
      set { this._maxRepeats = value; }
      get { return this._maxRepeats; }
    }

    [ReadOnlyAttribute(true)]
    [Description("Sequence number of this prompt group."), Category("Other")]
    public int OrderKey {
      get {
        return _orderKey;
      }
      set {
        _orderKey = value;
      }
    }

    #endregion

    #region Methods of List()

    public bool MoveUpPrompt(AbstractPrompt prompt) {
      bool isChanged = false;
      bool foundLower = false;
      _prompts.ForEach(delegate(AbstractPrompt p) { if (p.OrderKey == prompt.OrderKey - 1) { p.OrderKey = prompt.OrderKey; foundLower = true; } });
      if (foundLower) {
        prompt.OrderKey = prompt.OrderKey - 1;
        isChanged = true;
      }
      return isChanged;
    }

    public bool MoveDownPrompt(AbstractPrompt prompt) {
      bool isChanged = false;
      bool foundHigher = false;
      _prompts.ForEach(delegate(AbstractPrompt p) { if (p.OrderKey == prompt.OrderKey + 1) { p.OrderKey = prompt.OrderKey; foundHigher = true; } });
      if (foundHigher) {
        prompt.OrderKey = prompt.OrderKey + 1;
        isChanged = true;
      }
      return isChanged;
    }

    public List<AbstractPrompt>.Enumerator GetEnumerator() {
      return _prompts.GetEnumerator();
    }

    [Browsable(false)]
    public int Count {
      get {
        return _prompts.Count;
      }
    }

    public void AddPrompt(AbstractPrompt aPrompt) {
      aPrompt.Parent = this;
      aPrompt.OrderKey = _prompts.Count + 1;
      _prompts.Add(aPrompt);
    }

    public void AddPromptAfter(AbstractPrompt aNewPrompt, AbstractPrompt aPrompt) {
      aNewPrompt.Parent = this;
      aNewPrompt.OrderKey = aPrompt.OrderKey;

      int count = _prompts.Count;
      foreach (AbstractPrompt p in _prompts) {
        p.OrderKey = p.OrderKey - count;
        if (p == aPrompt) {
          break;
        }
      }

      _prompts.Add(aNewPrompt);
      renumberPrompts();
    }

    public void RemovePrompt(AbstractPrompt aPrompt) {
      _prompts.Remove(aPrompt);
      renumberPrompts();
    }

    public void SortPrompts() {
      _prompts.Sort(delegate(AbstractPrompt p1, AbstractPrompt p2) { return p1.OrderKey.CompareTo(p2.OrderKey); });
    }

    #endregion

    #region Public methods
    [Browsable(false)]
    public Template Parent {
      get {
        return _parent;
      }
      set {
        _parent = value;
      }
    }

    public void SetRepeatableFromString(string repeatable) {

      if ((string.Equals(repeatable, "yes", StringComparison.CurrentCultureIgnoreCase)) ||
          (string.Equals(repeatable, "true", StringComparison.CurrentCultureIgnoreCase))) {
        Repeatable = true;
      } else {
        Repeatable = false;
      }
    }

    public void SetMaxRepeatsFromString(string maxRepeats) {
      int.TryParse(maxRepeats, out _maxRepeats);
    }

    public XmlElement ToXml(XmlDocument aDocument) {

      XmlElement tPromptGroupElement = aDocument.CreateElement("promptgroup");

      tPromptGroupElement.SetAttribute("name", this.Name);
      if (this.Repeatable) {
        tPromptGroupElement.SetAttribute("repeatable", "yes");
        tPromptGroupElement.SetAttribute("maxRepeats", this.MaxRepeats.ToString());
      } else {
        tPromptGroupElement.SetAttribute("repeatable", "no");
      }

      foreach (AbstractPrompt tPrompt in this) {
        XmlElement tPromptElement = tPrompt.ToXml(aDocument);
        tPromptGroupElement.AppendChild(tPromptElement);
      }

      return tPromptGroupElement;
    }

    #endregion

    #region Private methods

    private void renumberPrompts() {
      SortPrompts();
      int count = 0;
      foreach (AbstractPrompt p in _prompts) {
        count++;
        p.OrderKey = count;
      }
    }

    #endregion

    #region Factory

    public static class Factory
    {

      public static PromptGroup CreateInstance() {
        return new PromptGroup();
      }

      public static PromptGroup Parse(Object aParent, XmlElement anXmlElement) {

        if (!(aParent is Template)) {
          throw new IllegalParentException("Illegal type! Expected instance of type: Template");
        }

        Template tParent = aParent as Template;

        String tName = getRequiredAttribute(anXmlElement, "name");
        String tRepeatable = getRequiredAttribute(anXmlElement, "repeatable");
        String tMaxRepeats = getOptionalAttribute(anXmlElement, "maxRepeats", "");

        PromptGroup tPromptGroup = new PromptGroup();
        tPromptGroup.Name = tName;
        tPromptGroup.SetRepeatableFromString(tRepeatable);
        tPromptGroup.SetMaxRepeatsFromString(tMaxRepeats);

        //Process prompts
        foreach (XmlNode tNode in anXmlElement.ChildNodes) {
          if (tNode is XmlElement) {
            XmlElement tElement = tNode as XmlElement;
            AbstractPrompt tPrompt = AbstractPrompt.Factory.Parse(tPromptGroup, tElement);
            tPromptGroup.AddPrompt(tPrompt);
          }
        }

        return tPromptGroup;
      }

    }
    #endregion
  }
}
