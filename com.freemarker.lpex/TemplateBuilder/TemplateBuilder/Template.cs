using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Collections;
using System.Windows.Forms;
using System.Text.RegularExpressions;
using System.Globalization;
using System.IO;
using System.Xml.Serialization;
using System.Xml;

namespace TemplateBuilder
{
  [Browsable(false)]
  public delegate void PromptRenameHandler(AbstractPrompt prompt, string newName);
  [Browsable(false)]
  public delegate void BeforePromptDeleteHandler(AbstractPrompt prompt);
  [Browsable(false)]
  public delegate void UpdateProgress(int percentage, string action);

  public class Template
  {

    // RZ: 5.6.2012
    private const String FTL_START_TAG = "<#ftl";
    private const String FTL_END_TAG = ">";
    private const String FTL_STRIP_WHITESPACE = "strip_whitespace";
    private const String DOUBLE_QUOTES = "\"";
    private const Boolean DEFAULT_FTL_STRIP_WHITESPACES = true;

    private List<PromptGroup> _promptGroups;

    public PromptRenameHandler promptRenameHandler { get; set; }
    public BeforePromptDeleteHandler beforePromptDeleteHandler { get; set; }
    public UpdateProgress updateProgress { get; set; }

    private String _name;
    private String _description;

    #region Constructors

    public Template() {
      _RawText = "";
      Name = "NewTemplate";
      Description = "Description here";
      promptRenameHandler = null;
      beforePromptDeleteHandler = null;
      _promptGroups = new List<PromptGroup>();
    }

    #endregion

    #region FKL attributes

    // RZ: 6.6.2012
    [Description("Name of the template."), Category("FKL Attributes")]
    public string Name { get { return _name; } set { _name = value; } }

    // RZ: 6.6.2012
    [Description("Description of the template."), Category("FKL Attributes")]
    public string Description { get { return _description; } set { _description = value; } }

    // RZ: 5.6.2012
    /// <summary>
    /// Property as part of the 'ftl' directive. Controls whether or not 
    /// to strip white-spaces.
    /// </summary>
    private Boolean _StriptWhiteSpaces = DEFAULT_FTL_STRIP_WHITESPACES;
    [Description("Specifies whether or not white-spaces are removed when merging the template."), Category("FKL Attributes")]
    public Boolean StripWhiteSpaces {
      set {
        this._StriptWhiteSpaces = value;
      }
      get {
        return _StriptWhiteSpaces;
      }
    }

    #endregion

    #region Methods of List()

    public bool MoveUpPromptGroup(PromptGroup promptGroup) {
      bool isChanged = false;
      bool foundLower = false;
      _promptGroups.ForEach(delegate(PromptGroup p) { if (p.OrderKey == promptGroup.OrderKey - 1) { p.OrderKey = promptGroup.OrderKey; foundLower = true; } });
      if (foundLower) {
        promptGroup.OrderKey = promptGroup.OrderKey - 1;
        isChanged = true;
      }
      return isChanged;
    }

    public bool MoveDownPromptGroup(PromptGroup promptGroup) {
      bool isChanged = false;
      bool foundHigher = false;
      _promptGroups.ForEach(delegate(PromptGroup p) { if (p.OrderKey == promptGroup.OrderKey + 1) { p.OrderKey = promptGroup.OrderKey; foundHigher = true; } });
      if (foundHigher) {
        promptGroup.OrderKey = promptGroup.OrderKey + 1;
        isChanged = true;
      }
      return isChanged;
    }

    public List<PromptGroup>.Enumerator GetEnumerator() {
      return _promptGroups.GetEnumerator();
    }

    [Browsable(false)]
    public int Count {
      get {
        return _promptGroups.Count;
      }
    }

    public void AddPromptGroup(PromptGroup aPromptGroup) {
      aPromptGroup.Parent = this;
      aPromptGroup.OrderKey = _promptGroups.Count + 1;
      _promptGroups.Add(aPromptGroup);
    }

    public Boolean RemovePromptGroup(PromptGroup aPromptGroup) {
      Boolean tRemoved = _promptGroups.Remove(aPromptGroup);
      renumberPromptGroups();
      return tRemoved;
    }

    public void SortPromptGroups() {
      _promptGroups.Sort(delegate(PromptGroup pg1, PromptGroup pg2) { return pg1.OrderKey.CompareTo(pg2.OrderKey); });
    }

    #endregion

    #region Private methods

    private void renumberPromptGroups() {
      SortPromptGroups();
      int count = 0;
      foreach (PromptGroup pg in _promptGroups) {
        count++;
        pg.OrderKey = count;
      }
    }

    #endregion

    private string _RawText;
    [Browsable(false)]
    public string RawText {
      set {
        this._RawText = value;
        try {
          loadTemplateProperties(); // RZ: 5.6.2012
          loadFormConfig();
          loadTemplateText();
          loadFromXML();
        } catch (Exception e) {
          return;
        }
      }
      get {
        return this._RawText;
      }
    }

    private string _FormConfigXML;
    [Browsable(false)]
    public string FormConfigXML {
      set { this._FormConfigXML = value; }
      get {
        if ((this._FormConfigXML == string.Empty) ||
            (this._FormConfigXML == null))
          try {
            loadFormConfig();
          } catch { }
        return this._FormConfigXML;
      }
    }

    private string _TemplateText;
    [Browsable(false)]
    public string TemplateText {
      set { this._TemplateText = value; }
      get {
        if ((this._TemplateText == string.Empty) ||
            (this._TemplateText == null))
          loadTemplateText();
        return this._TemplateText;
      }
    }

    [Browsable(false)]
    public string Output {
      get {
        // RZ: 5.6.2012
        // Changed to add the FreeMarker 'ftl' directive.
        String xmlValue = this.ToXml();
        return templatePropertiesToXml() + Environment.NewLine + "<#--" + xmlValue + "-->" + this._TemplateText;
      }
    }

    // RZ: 5.6.2012
    /// <summary>
    /// Produces the Xml string of the FreeMarker 'ftl' directive.
    /// </summary>
    /// <returns>ftl directive as Xml string.</returns>
    private string templatePropertiesToXml() {
      return FTL_START_TAG + produceXmlAttribute(FTL_STRIP_WHITESPACE, _StriptWhiteSpaces) + FTL_END_TAG;
    }

    // RZ: 5.6.2012
    /// <summary>
    /// Produces a Xml attribute. The attribute value is not 
    /// automatically enclosed in quotes.
    /// </summary>
    /// <param name="name">Name of the Attribute</param>
    /// <param name="value">Value of the attribute</param>
    /// <returns>Xml attribute</returns>
    private string produceXmlAttribute(String name, Boolean value) {
      return " " + name + "=" + value.ToString().ToLower();
    }

    // RZ: 5.6.2012
    /// <summary>
    /// Removes leading and trailing double qoutes from a given string.
    /// </summary>
    /// <param name="value">Quoted string value.</param>
    /// <returns>String value without quotes.</returns>
    private String removeQuotes(String value) {
      if (value.StartsWith(DOUBLE_QUOTES)) {
        value = value.Substring(1);
      }
      if (value.EndsWith(DOUBLE_QUOTES)) {
        value = value.Substring(0, value.Length - 2);
      }
      return value;
    }

    // RZ: 5.6.2012
    /// <summary>
    ///  Loads the properties of the FreeMarker 'ftl' directive.
    /// </summary>
    private void loadTemplateProperties() {
      _StriptWhiteSpaces = false;
      try {
        if (RawText != string.Empty) {
          String startingTag = FTL_START_TAG;
          String endingTag = FTL_END_TAG;
          int startingPosition = 0;
          int endingPosition = 0;
          startingPosition = RawText.IndexOf(startingTag) + startingTag.Length;
          endingPosition = RawText.IndexOf(endingTag);
          String templateProperties = RawText.Substring(startingPosition, endingPosition - startingPosition);
          if (!String.IsNullOrEmpty(templateProperties)) {
            string[] pairs = templateProperties.Trim().Split(' ');
            foreach (String pair in pairs) {
              String[] value = pair.Split('=');
              value[0] = value[0].Trim();
              if (value.Length == 2) {
                if (value[0].ToLower() == FTL_STRIP_WHITESPACE) {
                  Boolean result = false;
                  Boolean.TryParse(removeQuotes(value[1].Trim()), out result);
                  _StriptWhiteSpaces = result;
                }
              }
            }
          }
        }
      } catch (Exception e) {
      }
    }

    private void loadFormConfig() {
      try {
        if (RawText != string.Empty) {
          String startingTag = "<#--";
          String endingTag = "-->";
          int startingPosition = 0;
          int endingPosition = 0;
          startingPosition = RawText.IndexOf(startingTag) + startingTag.Length;
          endingPosition = RawText.IndexOf(endingTag);
          FormConfigXML = RawText.Substring(startingPosition, endingPosition - startingPosition);
        }
      } catch (Exception e) {
        throw new Exception("Didn't find any XML in the first comment.", e);
      }
    }

    private void loadTemplateText() {
      try {
        if (RawText != string.Empty) {
          TemplateText = RawText.Substring(RawText.IndexOf("-->") + 3);
        }
      } catch (Exception e) {
        throw new Exception("Didn't find the end of the xml block.", e);
      }
    }

    public void loadFromXML() {
      if (FormConfigXML == string.Empty) {
        throw new Exception("No form config xml found.");
      }
      XmlDocument root = new XmlDocument();
      root.InnerXml = FormConfigXML;

      XmlNode templateNode = (XmlNode) root.DocumentElement;

      if (templateNode is XmlElement) {
        foreach (XmlNode templateNodeChild in templateNode.ChildNodes) {
          if (templateNodeChild is XmlElement) {
            if (templateNodeChild.Name == "name")
              Name = templateNodeChild.InnerText;
            if (templateNodeChild.Name == "description")
              Description = templateNodeChild.InnerText;
            //Process groups
            if (templateNodeChild.Name == "promptgroups") {
              foreach (XmlNode groupNode in templateNodeChild.ChildNodes) {
                if (groupNode is XmlElement) {
                  XmlElement tPromptGroupNode = groupNode as XmlElement;
                  PromptGroup tPromptGroup = PromptGroup.Factory.Parse(this, tPromptGroupNode);
                  AddPromptGroup(tPromptGroup);
                }
              }
            }
          }
        }

      }
      root = null;
    }

    public string ToXml() {
      XmlDocument doc = new XmlDocument();

      XmlElement templateElement = (XmlElement) doc.AppendChild(doc.CreateElement("template"));
      XmlElement templateNameElement = (XmlElement) templateElement.AppendChild(doc.CreateElement("name"));
      templateNameElement.InnerText = this.Name;
      XmlElement templateDescriptionElement = (XmlElement) templateElement.AppendChild(doc.CreateElement("description"));
      templateDescriptionElement.InnerText = this.Description;
      XmlElement promptGroupsElement = (XmlElement) templateElement.AppendChild(doc.CreateElement("promptgroups"));

      foreach (PromptGroup promptGroup in this) {
        XmlElement promptGroupElement = promptGroup.ToXml(doc);
        promptGroupsElement.AppendChild(promptGroupElement);
      }

      return ToIndentedString(doc);
    }

    // RZ: 5.6.2012
    /// <summary>
    /// Produces an UTF-8 encoded and indented Xml string from a given
    /// Xml document.
    /// </summary>
    /// <param name="doc">Xml document</param>
    /// <returns>UTF-8 encoded and formatted Xml string value.</returns>
    private string ToIndentedString(XmlDocument doc) {
      MemoryStream stringWriter = new MemoryStream();
      UTF8Encoding encoding = new UTF8Encoding();
      XmlTextWriter xmlTextWriter = new XmlTextWriter(stringWriter, encoding) { Formatting = Formatting.Indented };
      doc.Save(xmlTextWriter);
      return encoding.GetString(stringWriter.ToArray());
    }

    public string GetDataModelAsString() {
      string dataModel = "(root)" + "\r\n";

      foreach (PromptGroup promptGroup in _promptGroups) {
        dataModel += " | \r\n";
        dataModel += " +-" + promptGroup.Name + "\r\n";
        if (promptGroup.Repeatable) {
          dataModel += "    | \r\n";
          dataModel += "    +-repeats" + "\r\n";
        }
        foreach (AbstractPrompt prompt in promptGroup) {
          if (promptGroup.Repeatable) {
            dataModel += "       | \r\n";
            dataModel += "       +-" + prompt.Name + "\r\n";
          } else {
            dataModel += "    | \r\n";
            dataModel += "    +-" + prompt.Name + "\r\n";
          }
        }
      }

      return dataModel;
    }

    private void showProgress(int percentage, string action) {
      if (updateProgress != null)
        updateProgress(percentage, action);
    }

    public List<Error> CheckAllVariableReferences() {
      int errorIndex = 0;
      List<Error> errors = new List<Error>();

      // RZ: 5.6.2012 Return to caller if the template is empty.
      if (String.IsNullOrEmpty(this.TemplateText)) {
        return errors;
      }

      //Get all references to a FreeMarker directive
      List<Match> directives = new List<Match>();
      //r = new Regex(@"</?#(?i:list)(?<directive_name>[A-Za-z\S]*)\s?(?<directive_body>.*)>", RegexOptions.Multiline);
      //Regex r = new Regex(@"</?#(?<directive_type>i:)?\s?(?<directive_body>.*)>", RegexOptions.Multiline);
      Regex r = new Regex(@"<(?<directive_opener>/?#)(?<directive_type>[A-Za-z]*)\s?(?<directive_body>.*)>", RegexOptions.Multiline);
      Match m = r.Match(this.TemplateText);
      while (m.Success) {
        directives.Add(m);
        m = m.NextMatch();
      }

      //Get all of the variable references
      List<Capture> referencedVariables = new List<Capture>();
      r = new Regex(@"\$\{(?<variable_name>[A-Za-z]+[A-Za-z0-9/./_]*)\}", RegexOptions.Multiline);
      m = r.Match(this.TemplateText);
      while (m.Success) {
        int i = 0;
        foreach (Capture capture in m.Groups[0].Captures) {
          int percentage = (100 / m.Groups[0].Captures.Count) * (i);
          showProgress(percentage, "Checking: " + capture.ToString());
          referencedVariables.Add(capture);
          Console.WriteLine(capture.ToString());
          bool match = false;
          string[] var = capture.ToString().Replace("$", "").Replace("{", "").Replace("}", "").Split('.');

          foreach (PromptGroup promptGroup in _promptGroups) {
            if (var[0] == promptGroup.Name) {
              foreach (AbstractPrompt prompt in promptGroup) {
                if (var[1] == prompt.Name) {
                  match = true;
                }
              }
            } else {
              //Check for globals
              if (var.Length == 1) {
                if (var[0].Equals("author", StringComparison.CurrentCultureIgnoreCase)) {
                  match = true;
                  break;
                } else if (var[0].Equals("date", StringComparison.CurrentCultureIgnoreCase)) {
                  match = true;
                  break;
                }
              }

              //Check to see if it is in a for loop
              bool betweenCheck = false;
              Match directiveOpener = null;
              foreach (Match directive in directives) {
                if (directive.Groups["directive_type"].ToString().Equals("list", StringComparison.CurrentCultureIgnoreCase)) {
                  if (directive.Groups["directive_opener"].ToString().Equals("#", StringComparison.CurrentCultureIgnoreCase)) {
                    //Opening directive tag
                    if (capture.Index > directive.Index) {
                      directiveOpener = directive;
                      betweenCheck = true;
                    }
                  }
                  if (directive.Groups["directive_opener"].ToString().Equals("/#", StringComparison.CurrentCultureIgnoreCase)) {
                    //Closing directive tag
                    if ((capture.Index < directive.Index) && (betweenCheck)) {
                      //Ex: 
                      //<#list parameter.repeats as parm>
                      //  ${parm.description}
                      //</#list>  
                      string directiveBody = directiveOpener.Groups["directive_body"].ToString();
                      string[] forParts = directiveBody.Split(' ');
                      if (forParts.Length == 3) {
                        string arrayName = forParts[0].Replace(".repeats", "");
                        string localIterName = forParts[2];
                        string[] tempName = capture.ToString().Replace(localIterName, arrayName).Replace("$", "").Replace("{", "").Replace("}", "").Split('.');
                        if (tempName[0] == promptGroup.Name) {
                          foreach (AbstractPrompt prompt in promptGroup) {
                            if (tempName[1] == prompt.Name) {
                              match = true;
                            }
                          }
                        }
                      }
                      break;
                    }
                    betweenCheck = false;
                  }
                }
              }
            }
          }
          if (!match) {
            int column = 0;
            string section = this.TemplateText.Substring(0, capture.Index);
            int line = CountLinesInString(section, out column);
            errorIndex++;
            errors.Add(new Error(errorIndex,
                "Invalid variable referenced: " + capture.ToString(),
                line,
                capture.Index - column,
                capture.Length));
          }
        }
        i++;
        m = m.NextMatch();
      }
      return errors;
    }

    private int CountLinesInString(string s, out int curLineStart) {
      int count = 1;
      int start = 0;
      curLineStart = 0;
      while ((start = s.IndexOf('\n', start)) != -1) {
        count++;
        start++;
        curLineStart = start;
      }
      return count;
    }
  }
}
