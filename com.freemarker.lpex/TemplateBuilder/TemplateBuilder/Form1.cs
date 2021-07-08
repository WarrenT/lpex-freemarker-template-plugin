using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using TemplateBuilder.Properties;
using System.Collections;
using System.Collections.Specialized;
using System.Text.RegularExpressions;
using System.Globalization;
using ICSharpCode.TextEditor.Document;
using ICSharpCode.TextEditor;
using System.Diagnostics;
using Microsoft.Win32;
using CommandLine.Utility;

namespace TemplateBuilder
{
  public delegate void NodeClicker(object obj);

  public partial class Form1 : Form
  {
    private const String formTitleDefault = "LPEX Template Builder";

    private static Template template = new Template();
    private static DocumentManager doc = new DocumentManager("ftl", "FreeMarkerTemplate");

    private bool _highlightingProviderLoaded = false;
    private Arguments commandLine;

    public Form1() {
      InitializeComponent();
      this.Text = formTitleDefault;

      //Visual Studio breaks form rendering if these are in the designer file!
      this.topTabs_TemplateEditor.ActiveTextAreaControl.TextArea.DragDrop += new System.Windows.Forms.DragEventHandler(this.topTabs_TemplateEditor_DragDrop);
      this.topTabs_TemplateEditor.ActiveTextAreaControl.TextArea.DragOver += new System.Windows.Forms.DragEventHandler(this.topTabs_TemplateEditor_DragOver);

      // this.templateEditor.ActiveTextAreaControl.TextArea.Caret.
      this.topTabs_TemplateEditor.ActiveTextAreaControl.TextArea.Caret.PositionChanged += new EventHandler(Caret_PositionChanged);
      this.bottomTab_TemplatePreview.ActiveTextAreaControl.TextArea.Caret.PositionChanged += new EventHandler(Caret_PositionChanged);


      //Document change handlers
      doc.newFileHandler = handleNewFileCreation; // Create template, update views
      doc.openedFileHandler = handleFileOpen; // Update views
      doc.fileChangedHandler = handleNameChanged; // Update form tile
      doc.recentFilesChangedHandler = updateRecentFiles; // Update recent files list
      doc.dataChangedHandler = handleDataChanged;
      doc.updateStatus = updateStatus;

      //Prompt change handlers
      template.promptRenameHandler = handlePromptRename;
      template.beforePromptDeleteHandler = handleBeforePromptDelete;
      template.updateProgress = updateProgress;

      //Get the recent files from the app settings file
      doc.RecentFiles = TemplateBuilder.Properties.Settings.Default.recentDocuments;

      if (template.promptRenameHandler == null) {
        MessageBox.Show("Rename handler erased.");
      }

      TreeViewClickHandler.editor_pg = this.leftTabs_Properties;

      if (!_highlightingProviderLoaded) {
        // Attach to the text editor.
        HighlightingManager.Manager.AddSyntaxModeFileProvider(new AppSyntaxModeProvider());
        _highlightingProviderLoaded = true;
      }

      topTabs_TemplateEditor.SetHighlighting("FTL");
      bottomTab_TemplatePreview.SetHighlighting("XML");

      BuildRecentFilesMenu();

      // Create new and empty template
      doc.New();
    }

    #region Public procedures

    public Arguments CommandLine {
      set {
        commandLine = value;

        // Create new and empty template
        if (commandLine.FileName != null) {
          if (File.Exists(commandLine.FileName)) {
            doc.Open(commandLine.FileName);
          } else {
            MessageBox.Show(this, commandLine.FileName, "File not found", MessageBoxButtons.OK, MessageBoxIcon.Error);
          }
        }
      }
    }

    #endregion

    #region DocumentManager procedures and handlers

    private void handleNewFileCreation() {
      template = new Template();
      template.promptRenameHandler = handlePromptRename;
      template.beforePromptDeleteHandler = handleBeforePromptDelete;
      template.updateProgress = updateProgress;
      doc.Data = template.Output;
      refreshAllFromOpenDocument();
    }

    private void handleFileOpen() {
      refreshAllFromOpenDocument();
    }

    private void handleNameChanged() {
      updateFormTitle(doc.FileName);
    }

    private void updateRecentFiles(StringCollection recentFiles) {
      TemplateBuilder.Properties.Settings.Default.recentDocuments = recentFiles;
      TemplateBuilder.Properties.Settings.Default.Save();
      BuildRecentFilesMenu();
    }

    private void updateStatus(string status) {
      statusBar_status.Text = status;
    }

    private void handleDataChanged() {
      this.bottomTab_TemplatePreview.Text = doc.Data;
      doc.Changed = true;

      topTabs_TemplateEditor.Refresh();
      bottomTab_TemplatePreview.Refresh();

      updateFormTitle(doc.FileName);
    }

    private void updateFormTitle(string fileName) {
      string changeIndicator = "";
      if (doc.Changed)
        changeIndicator = "*";
      this.Text = formTitleDefault + " - " + fileName + changeIndicator;
    }

    private void refreshAllFromOpenDocument() {
      template = new Template();
      template.promptRenameHandler = handlePromptRename;
      template.beforePromptDeleteHandler = handleBeforePromptDelete;
      template.updateProgress = updateProgress;
      template.RawText = doc.Data;

      leftTabs_Properties.SelectedObject = null;
      refreshTreeView();
      refreshTemplateText();
    }

    #endregion

    #region Template procedures and handlers

    private void handlePromptRename(AbstractPrompt prompt, string newName) {
      string oldVariableName = prompt.VariableName;
      string newVariableName = prompt.GetVariableName(prompt.Parent, newName);
      template.TemplateText = template.TemplateText.Replace(oldVariableName, newVariableName);
      refreshTemplateText();
      Object tObject = leftTabs_Properties.SelectedObject;
      TreeViewClickHandler.Prompt_Click(tObject);
    }

    private void handleBeforePromptDelete(AbstractPrompt prompt) {
      if (String.IsNullOrEmpty(template.TemplateText)) {
        return;
      }
      string oldVariableName = prompt.VariableName;
      template.TemplateText = template.TemplateText.Replace(oldVariableName, "");
      refreshTemplateText();
    }

    private void updateProgress(int percentage, string action) {
      statusBar_progress.Visible = (percentage > 0);
      statusBar_progress.ProgressBar.Value = percentage;
      statusStrip1.Update();
      if (action != string.Empty) { bottomTab_Errors_Add(action); }
      botomTab_DebugLog.Update();
    }

    private void refreshTemplateText() {
      this.topTabs_TemplateEditor.Text = template.TemplateText;
    }

    #endregion

    #region Handle recent files management

    private void recentFiles_Click(object sender, EventArgs e) {
      ToolStripMenuItem recentFileMenuItem = (ToolStripMenuItem) sender;
      doc.Open((string) recentFileMenuItem.Tag);
    }

    private void BuildRecentFilesMenu() {
      mnuFile_RecentFiles.Enabled = false;
      mnuFile_RecentFiles.DropDownItems.Clear();
      foreach (string path in doc.RecentFiles) {
        mnuFile_RecentFiles.Enabled = true;
        ToolStripMenuItem item = new ToolStripMenuItem(PathShortener(path));
        item.Tag = path;
        item.Click += new System.EventHandler(this.recentFiles_Click);
        mnuFile_RecentFiles.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] { item });
      }
    }

    private static string PathShortener(string path) {
      const string pattern = @"^(\w+:|\\)(\\[^\\]+\\[^\\]+\\).*(\\[^\\]+\\[^\\]+)$";
      const string replacement = "$1$2...$3";
      if (Regex.IsMatch(path, pattern)) {
        return Regex.Replace(path, pattern, replacement);
      } else {
        return path;
      }
    }

    #endregion

    #region Treeview builders and helpers

    private void refreshTreeView() {
      dataModel.Text = template.GetDataModelAsString();
      foreach (PromptGroup pg in template) {
        template.SortPromptGroups();
        foreach (AbstractPrompt p in pg) {
          pg.SortPrompts();
        }
      }
      leftTabs_DocumentTree.BeginUpdate();
      leftTabs_DocumentTree.Nodes.Clear();
      int topNode = 0;
      if (leftTabs_DocumentTree.TopNode != null) {
        topNode = leftTabs_DocumentTree.TopNode.Index;
      }

      TreeNode templateNode = new TreeNode(template.Name, Icons.SCRIPT, Icons.SCRIPT);
      templateNode.Tag = BuildObjectIDPack(template, "template", new NodeClicker(TreeViewClickHandler.Template_Click));
      templateNode.Name = template.Name;

      int icon = 0;
      foreach (PromptGroup promptGroup in template) {
        icon = Icons.FORM;
        if (promptGroup.Repeatable)
          icon = Icons.APPLICATION_CASCADE;
        TreeNode promptGroupNode = new TreeNode(promptGroup.Name, icon, icon);
        promptGroupNode.Tag = BuildObjectIDPack(promptGroup, "promptgroup", new NodeClicker(TreeViewClickHandler.PromptGroup_Click));
        promptGroupNode.Name = promptGroup.Name;

        //Add each prompt
        foreach (AbstractPrompt prompt in promptGroup) {
          icon = prompt.GetIcon();

          TreeNode promptNode = new TreeNode(prompt.Name, icon, icon);
          promptNode.Tag = BuildObjectIDPack(prompt, "prompt", new NodeClicker(TreeViewClickHandler.Prompt_Click));
          promptNode.Name = prompt.Name;
          promptGroupNode.Nodes.Add(promptNode);
        }

        templateNode.Nodes.Add(promptGroupNode);
      }
      leftTabs_DocumentTree.Nodes.Add(templateNode);

      leftTabs_DocumentTree.ExpandAll();
      try {
        leftTabs_DocumentTree.TopNode = leftTabs_DocumentTree.Nodes[topNode];
      } catch { }
      leftTabs_DocumentTree.EndUpdate();

      doc.Data = template.Output;
    }

    private Hashtable BuildObjectIDPack(object data, string type, object clickHandler) {
      Hashtable map = new Hashtable();
      map.Add("Data", data);
      map.Add("Type", type);
      map.Add("ClickHandler", clickHandler);
      return map;
    }

    #endregion

    #region Form element event handlers

    private void MainForm_FormClosing(object sender, FormClosingEventArgs e) {
      if (!doc.HandleUnsavedContent()) {
        e.Cancel = true;
      }
    }
    #endregion

    #region Main menu: File

    private void mnuFile_New_Click(object sender, EventArgs e) {
      doc.New();
    }

    private void mnuFile_Open_Click(object sender, EventArgs e) {
      doc.Open();
    }

    private void mnuFile_Save_Click(object sender, EventArgs e) {
      doc.Save();
    }

    private void mnuFile_SaveAs_Click(object sender, EventArgs e) {
      doc.SaveAs();
    }

    private void mnuFile_Exit_Click(object sender, EventArgs e) {
      this.Close();
    }

    #endregion

    #region Main menu: Tools

    private void mnuTools_Validate_Click(object sender, EventArgs e) {
      bottomTab_Errors_Clear();

      // Check the template for references to variables that do not exist
      List<Error> errors = template.CheckAllVariableReferences();
      displayErrors(errors);

      //TODO Check the model for duplicate fields
    }

    private void displayErrors(List<Error> errors) {
      bottomTab_Errors.Items.Clear();
      foreach (Error error in errors) {
        ListViewItem item = new ListViewItem(new[]{
                    "",
                    error.index.ToString(),
                    error.description,
                    error.line.ToString(),
                    error.column.ToString(),
                    error.length.ToString()});
        item.ImageIndex = Icons.BULLET_ERROR;
        bottomTab_Errors.Items.Add(item);
      }
      errors_tab.Text = "Errors (" + errors.Count + ")";
      bottomTabs.SelectedIndex = 2;
    }

    #endregion

    #region Main menu: Help

    private void mnuHelp_ProjectPage_Click(object sender, EventArgs e) {
      openProjectPage();
    }

    private void mnuHelp_FreeMarkerDocumentation_Click(object sender, EventArgs e) {
      openFreeMarkerDocumentation();
    }

    private void mnuHelp_About_Click(object sender, EventArgs e) {
      AboutBox1 about = new AboutBox1();
      about.ShowDialog();
    }

    #endregion

    #region Toolbar buttons

    private void toolbar_btnNewFile_Click(object sender, EventArgs e) {
      doc.New();
    }

    private void toolbar_btnOpenFile_Click(object sender, EventArgs e) {
      doc.Open();
    }

    private void toolbar_btnSaveFile_Click(object sender, EventArgs e) {
      doc.Save();
    }

    private void toolbar_btnHelp_Click(object sender, EventArgs e) {
      openFreeMarkerDocumentation();
    }

    #endregion

    #region TreeView buttons

    private void btnAddPromptGroup_Click(object sender, EventArgs e) {
      template.AddPromptGroup(PromptGroup.Factory.CreateInstance());
      refreshTreeView();
    }

    private void btnRefreshTreeView_Click(object sender, EventArgs e) {
      refreshTreeView();
    }

    #endregion

    #region Popup menu: Prompt

    private void mnuPrompt_NewPrompt_Text_Click(object sender, EventArgs e) {
      // Add a text prompt
      CreatePromptHandler(typeof(PromptText));
    }

    private void mnuPrompt_NewPrompt_Number_Click(object sender, EventArgs e) {
      // Add a number prompt
      CreatePromptHandler(typeof(PromptNumber));
    }

    private void mnuPrompt_NewPrompt_Multiline_Click(object sender, EventArgs e) {
      // Add a multiline prompt
      CreatePromptHandler(typeof(PromptMultiline));
    }

    private void mnuPrompt_NewPrompt_Date_Click(object sender, EventArgs e) {
      // Add a date prompt
      CreatePromptHandler(typeof(PromptDate));
    }

    private void mnuPrompt_NewPrompt_Checkbox_Click(object sender, EventArgs e) {
      // Add a checkbox prompt
      CreatePromptHandler(typeof(PromptCheckbox));
    }

    private void mnuPrompt_NewPrompt_UserDefined_Click(object sender, EventArgs e) {
      // Add a user-defined prompt
      CreatePromptHandler(typeof(PromptUserDefined));
    }

    private void mnuPrompt_CopyAs_Click(object sender, EventArgs e) {
      TreeNode node = leftTabs_DocumentTree.SelectedNode;
      try {
        Hashtable idPack = node.Tag as Hashtable;
        if ((string) idPack["Type"] == "prompt") {
          AbstractPrompt prompt = (AbstractPrompt) idPack["Data"];
          prompt.Parent.AddPrompt(prompt.Clone());
          refreshTreeView();
        }
      } catch { }
    }

    private void mnuPrompt_Delete_Click(object sender, EventArgs e) {
      try {
        TreeNode node = leftTabs_DocumentTree.SelectedNode;
        Hashtable idPack = node.Tag as Hashtable;
        if ((string) idPack["Type"] == "prompt") {
          AbstractPrompt prompt = (AbstractPrompt) idPack["Data"];
          prompt.Remove();
          refreshTreeView();
        }
      } catch (Exception ex) {
        MessageBox.Show("Error creating prompt: " + ex.Message);
      }
    }

    private void mnuPrompt_MoveUp_Click(object sender, EventArgs e) {
      try {
        TreeNode node = leftTabs_DocumentTree.SelectedNode;
        Hashtable idPack = node.Tag as Hashtable;
        if ((string) idPack["Type"] == "prompt") {
          AbstractPrompt prompt = (AbstractPrompt) idPack["Data"];
          if (prompt.Parent.MoveUpPrompt(prompt)) {
            refreshTreeView();
          };
        }
      } catch (Exception ex) {
        MessageBox.Show("Error reordering: " + ex.Message);
      }
    }

    private void mnuPrompt_MoveDown_Click(object sender, EventArgs e) {
      try {
        TreeNode node = leftTabs_DocumentTree.SelectedNode;
        Hashtable idPack = node.Tag as Hashtable;
        if ((string) idPack["Type"] == "prompt") {
          AbstractPrompt prompt = (AbstractPrompt) idPack["Data"];
          if (prompt.Parent.MoveDownPrompt(prompt)) {
            refreshTreeView();
          }
        }
      } catch (Exception ex) {
        MessageBox.Show("Error reordering: " + ex.Message);
      }
    }

    private void mnuPrompt_InsertVariable_Click(object sender, EventArgs e) {
      TreeNode node = leftTabs_DocumentTree.SelectedNode;
      try {
        Hashtable idPack = node.Tag as Hashtable;
        if ((string) idPack["Type"] == "prompt") {
          AbstractPrompt prompt = (AbstractPrompt) idPack["Data"];
          insertIntoTemplateEditor(this.topTabs_TemplateEditor.ActiveTextAreaControl.TextArea, this.topTabs_TemplateEditor.ActiveTextAreaControl.TextArea.Caret.Offset, prompt.VariableName);
        }
      } catch { }
    }

    private void CreatePromptHandler(Type aType) {
      try {
        TreeNode node = leftTabs_DocumentTree.SelectedNode;
        Hashtable idPack = node.Tag as Hashtable;
        if ((string) idPack["Type"] == "prompt") {
          AbstractPrompt tPrompt = (AbstractPrompt) idPack["Data"];
          PromptGroup tPromptGroup = tPrompt.Parent;
          produceAndAddPrompt(tPromptGroup, aType, tPrompt);
        }
      } catch (Exception ex) {
        MessageBox.Show("Error creating prompt from Prompt menu: " + ex.Message);
      }
    }

    #endregion

    #region Popup menu: Prompt group

    private void mnuPromptGroup_Delete_Click(object sender, EventArgs e) {
      try {
        TreeNode node = leftTabs_DocumentTree.SelectedNode;
        Hashtable idPack = node.Tag as Hashtable;
        if ((string) idPack["Type"] == "promptgroup") {
          PromptGroup promptGroup = (PromptGroup) idPack["Data"];
          if (promptGroup.Parent.RemovePromptGroup(promptGroup)) {
            refreshTreeView();
          }
        }
      } catch (Exception ex) {
        MessageBox.Show("Error deleting prompt group: " + ex.Message);
      }
    }

    private void mnuPromptGroup_MoveUp_Click(object sender, EventArgs e) {
      try {
        TreeNode node = leftTabs_DocumentTree.SelectedNode;
        Hashtable idPack = node.Tag as Hashtable;
        if ((string) idPack["Type"] == "promptgroup") {
          PromptGroup promptGroup = (PromptGroup) idPack["Data"];
          if (promptGroup.Parent.MoveUpPromptGroup(promptGroup)) {
            refreshTreeView();
          }
        }
      } catch (Exception ex) {
        MessageBox.Show("Error moving up prompt group: " + ex.Message);
      }
    }

    private void mnuPromptGroup_MoveDown_Click(object sender, EventArgs e) {
      try {
        TreeNode node = leftTabs_DocumentTree.SelectedNode;
        Hashtable idPack = node.Tag as Hashtable;
        if ((string) idPack["Type"] == "prompt") {
          AbstractPrompt prompt = (AbstractPrompt) idPack["Data"];
          if (prompt.Parent.MoveDownPrompt(prompt)) {
            refreshTreeView();
          }
        } else if ((string) idPack["Type"] == "promptgroup") {
          PromptGroup promptGroup = (PromptGroup) idPack["Data"];
          if (promptGroup.Parent.MoveDownPromptGroup(promptGroup)) {
            refreshTreeView();
          }
        }
      } catch (Exception ex) {
        MessageBox.Show("Error moving down prompt group: " + ex.Message);
      }
    }

    private void mnuPromptGroup_NewPrompt_Text_Click(object sender, EventArgs e) {
      // Add a text prompt
      CreatePromptGroupHandler(typeof(PromptText));
    }

    private void mnuPromptGroup_NewPrompt_Number_Click(object sender, EventArgs e) {
      // Add a number prompt
      CreatePromptGroupHandler(typeof(PromptNumber));
    }

    private void mnuPromptGroup_NewPrompt_Multiline_Click(object sender, EventArgs e) {
      // Add a multiline prompt
      CreatePromptGroupHandler(typeof(PromptMultiline));
    }

    private void mnuPromptGroup_NewPrompt_Date_Click(object sender, EventArgs e) {
      // Add a date prompt
      CreatePromptGroupHandler(typeof(PromptDate));
    }

    private void mnuPromptGroup_NewPrompt_Checkbox_Click(object sender, EventArgs e) {
      // Add a checkbox prompt
      CreatePromptGroupHandler(typeof(PromptCheckbox));
    }

    private void mnuPromptGroup_NewPrompt_UserDefined_Click(object sender, EventArgs e) {
      // Add a user-defined prompt
      CreatePromptGroupHandler(typeof(PromptUserDefined));
    }

    private void CreatePromptGroupHandler(Type aType) {
      try {
        TreeNode node = leftTabs_DocumentTree.SelectedNode;
        Hashtable idPack = node.Tag as Hashtable;
        if ((string) idPack["Type"] == "promptgroup") {
          PromptGroup tPromptGroup = (PromptGroup) idPack["Data"];
          produceAndAddPrompt(tPromptGroup, aType, null);
        }
      } catch (Exception ex) {
        MessageBox.Show("Error creating prompt from PromptGroup menu: " + ex.Message);
      }
    }

    private void produceAndAddPrompt(PromptGroup aPromptGroup, Type aType, AbstractPrompt aReferencePrompt) {
      AbstractPrompt newPrompt = AbstractPrompt.Factory.CreateInstance(aType);
      if (aReferencePrompt != null) {
        aPromptGroup.AddPromptAfter(newPrompt, aReferencePrompt);
      } else {
        aPromptGroup.AddPrompt(newPrompt);
      }
      refreshTreeView();
    }

    #endregion

    #region Open Internet page

    private void openProjectPage() {
      openUrlInDefaultBrowser("http://code.google.com/p/lpex-freemarker-template-plugin/");
    }

    private void openFreeMarkerDocumentation() {
      openUrlInDefaultBrowser("http://freemarker.sourceforge.net/docs/dgui.html");
    }

    private static void openUrlInDefaultBrowser(string url) {
      try {
        string key = @"htmlfile\shell\open\command";
        RegistryKey registryKey =
        Registry.ClassesRoot.OpenSubKey(key, false);
        // get default browser path
        string browserPath = ((string) registryKey.GetValue(null, null)).Split('"')[1];
        // launch default browser
        Process.Start(browserPath, url);
      } catch (Exception exp) {
        MessageBox.Show(exp.Message);
      }
    }

    #endregion

    #region Top tabs: Template editor

    void insertIntoTemplateEditor(TextArea textArea, int offset, string text) {
      textArea.SelectionManager.ClearSelection();
      InsertString(textArea, offset, text);
      textArea.Document.RequestUpdate(new TextAreaUpdate(TextAreaUpdateType.WholeTextArea));
      template.TemplateText = textArea.Document.TextContent;
    }

    void InsertString(ICSharpCode.TextEditor.TextArea textArea, int offset, string str) {
      textArea.Document.Insert(offset, str);
      textArea.Caret.Position = textArea.Document.OffsetToPosition(offset + str.Length);
      textArea.Refresh();
    }

    // RZ: 5.6.2012
    /// <summary>
    /// Enables/disables the "Validate" menu item depending on whether or
    /// not the text editor is empty.
    /// </summary>
    private void topTabs_TemplateEditor_TextChanged(object sender, EventArgs e) {
      if (!(e is ICSharpCode.TextEditor.Document.DocumentEventArgs)) {
        return;
      }
      ICSharpCode.TextEditor.Document.DocumentEventArgs anEvent = (ICSharpCode.TextEditor.Document.DocumentEventArgs) e;
      if (anEvent.Document.TextLength == 0) {
        mnuTools_Validate.Enabled = false;
      } else {
        mnuTools_Validate.Enabled = true;
      }
      template.TemplateText = anEvent.Document.TextContent;
      doc.Data = template.Output;
      return;
    }

    void Caret_PositionChanged(object sender, EventArgs e) {
      const String SPACCE = "   ";
      if (sender is ICSharpCode.TextEditor.Caret) {
        Caret tCaret = (Caret) sender;
        statusbar_line.Text = "Ln : " + (tCaret.Line + 1) + SPACCE + "Col : " + (tCaret.Column + 1) + SPACCE + "Sel : ";
        String tText = this.topTabs_TemplateEditor.ActiveTextAreaControl.TextArea.SelectionManager.SelectedText;
        if (tText != null) {
          statusbar_line.Text = statusbar_line.Text + tText.Length;
        } else {
          statusbar_line.Text = statusbar_line.Text + 0;
        }
      }
      return;
    }

    private void topTabs_TemplateEditor_DragOver(object sender, DragEventArgs e) {
      if (e.Data.GetData(typeof(TreeNode)) != null) {
        TreeNode node = (TreeNode) e.Data.GetData(typeof(TreeNode));
        Hashtable idPack = node.Tag as Hashtable;
        switch (idPack["Type"] as string) {
          case "promptgroup":
            break;
          case "prompt":
            e.Effect = DragDropEffects.Copy;
            break;
        }
      }
    }

    private void topTabs_TemplateEditor_DragDrop(object sender, DragEventArgs e) {
      TreeNode node = (TreeNode) e.Data.GetData(typeof(TreeNode));
      ICSharpCode.TextEditor.TextArea textArea = (ICSharpCode.TextEditor.TextArea) sender;
      try {
        Hashtable idPack = node.Tag as Hashtable;
        switch (idPack["Type"] as string) {
          case "promptgroup":
            return;
          case "prompt":
            break;
        }

        //Build the variable to insert here
        AbstractPrompt prompt = idPack["Data"] as AbstractPrompt;

        Point p = textArea.PointToClient(new Point(e.X, e.Y));
        textArea.BeginUpdate();
        textArea.Document.UndoStack.StartUndoGroup();

        int offset = textArea.Caret.Offset;
        if (e.Data.GetDataPresent(typeof(DefaultSelection))) {
          ISelection sel = (ISelection) e.Data.GetData(typeof(DefaultSelection));
          if (sel.ContainsPosition(textArea.Caret.Position)) {
            return;
          }
          int len = sel.Length;
          textArea.Document.Remove(sel.Offset, len);
          if (sel.Offset < offset) {
            offset -= len;
          }
        }
        insertIntoTemplateEditor(textArea, offset, prompt.VariableName);
      } finally {
        textArea.Document.UndoStack.EndUndoGroup();
        textArea.EndUpdate();
      }
    }

    #endregion

    #region Bottom tabs: Preview, Debug Log, Errors

    private void bottomTab_Errors_Click(object sender, EventArgs e) {
      ListView.SelectedListViewItemCollection selected = this.bottomTab_Errors.SelectedItems;

      int line = 0;
      int column = 0;
      int length = 0;
      foreach (ListViewItem item in selected) {
        line = Int32.Parse(item.SubItems[3].Text);
        column = Int32.Parse(item.SubItems[4].Text);
        length = Int32.Parse(item.SubItems[5].Text);
      }
      //Highlight the error in the text editor
      TextLocation startPoint = new TextLocation(column, line - 1);
      TextLocation endPoint = new TextLocation(column + length, line - 1);
      this.topTabs_TemplateEditor.ActiveTextAreaControl.TextArea.SelectionManager.SetSelection(startPoint, endPoint);
    }

    private void bottomTab_Errors_Clear() {
      this.botomTab_DebugLog.Clear();
    }

    private void bottomTab_Errors_Add(string msg) {
      //Write a message out to the log window
      //this.logText.Text = DateTime.Now.ToString("yyyy.MM.dd hh:mm:ss") + "   " + msg + "\r\n" + this.logText.Text;
      this.botomTab_DebugLog.Text += DateTime.Now.ToString("yyyy.MM.dd hh:mm:ss") + "   " + msg + "\r\n";
      this.botomTab_DebugLog.Select(this.botomTab_DebugLog.Text.Length + 1, 2);
      this.botomTab_DebugLog.ScrollToCaret();
      this.botomTab_DebugLog.Update();
    }

    #endregion

    #region Left tabs: Document tree, Properties

    private void leftTabs_DocumentTree_AfterSelect(object sender, TreeViewEventArgs e) {
      try {
        Hashtable idPack = e.Node.Tag as Hashtable;
        NodeClicker clicker = idPack["ClickHandler"] as NodeClicker;
        leftTabs_Properties.SelectedObject = idPack["Data"];
        clicker(idPack["Data"]);
      } catch { }
    }

    private void leftTabs_DocumentTree_MouseDoubleClick(object sender, MouseEventArgs e) {
      // Point where the mouse is clicked.
      Point p = new Point(e.X, e.Y);

      // Get the node that the user has clicked.
      TreeNode node = leftTabs_DocumentTree.GetNodeAt(p);

      try {
        Hashtable idPack = node.Tag as Hashtable;
        if ((string) idPack["Type"] == "prompt") {
          AbstractPrompt prompt = (AbstractPrompt) idPack["Data"];
          insertIntoTemplateEditor(this.topTabs_TemplateEditor.ActiveTextAreaControl.TextArea, this.topTabs_TemplateEditor.ActiveTextAreaControl.TextArea.Caret.Offset, prompt.VariableName);
        }
      } catch { }
    }

    Rectangle dragBoxFromMouseDown;
    private void leftTabs_DocumentTree_MouseDown(object sender, MouseEventArgs e) {
      // Get the tree.
      TreeView tree = (TreeView) sender;

      // Get the node underneath the mouse.
      TreeNode node = tree.GetNodeAt(e.X, e.Y);
      tree.SelectedNode = node;

      Size dragSize = SystemInformation.DragSize;

      if (tree.SelectedNode != null) {
        // Creates a rectangle using the DragSize, with the mouse position being
        // at the center of the rectangle.
        dragBoxFromMouseDown = new Rectangle(new Point(e.X - (dragSize.Width / 2),
                                                       e.Y - (dragSize.Height / 2)), dragSize);
      } else {
        // Resets the rectangle if the mouse is not over a node in the tree
        dragBoxFromMouseDown = Rectangle.Empty;
      }

      if (e.Button == MouseButtons.Right) {
        return;
      }

      // Start the drag-and-drop operation with a cloned copy of the node.
      if (node != null) {
        //Enable drag from the tree view
        tree.DoDragDrop(node.Clone(), DragDropEffects.Copy);
      }
    }

    private void leftTabs_DocumentTree_MouseMove(object sender, MouseEventArgs e) {
      if ((e.Button & MouseButtons.Left) == MouseButtons.Left) {
        if (leftTabs_DocumentTree.SelectedNode == null) {
          return;
        }
        // If the mouse moves outside the rectangle, start the drag.
        if (dragBoxFromMouseDown != Rectangle.Empty &&
            !dragBoxFromMouseDown.Contains(e.X, e.Y)) {
          Console.WriteLine("Start drag");
          leftTabs_DocumentTree.DoDragDrop(leftTabs_DocumentTree.SelectedNode, DragDropEffects.All);
        }
      }
    }

    private TreeNode m_OldSelectNode;
    private void leftTabs_DocumentTree_MouseUp(object sender, System.Windows.Forms.MouseEventArgs e) {
      // Reset the drag rectangle when the mouse button is raised.
      dragBoxFromMouseDown = Rectangle.Empty;

      // select the node the mouse is over through code. The select event is not fired if its the same
      // node. Only act if it's the RMB.
      if (e.Button != MouseButtons.Right) {
        return;
      }

      Point p = new Point(e.X, e.Y);

      // Store the selected node (can deselect a node).
      leftTabs_DocumentTree.SelectedNode = leftTabs_DocumentTree.GetNodeAt(e.X, e.Y);
      TreeNode node = leftTabs_DocumentTree.SelectedNode;

      // Show menu only if the right mouse button is clicked.
      if (e.Button == MouseButtons.Right) {
        if (node != null) {
          // Select the node the user has clicked.
          // The node appears selected until the menu is displayed on the screen.
          m_OldSelectNode = leftTabs_DocumentTree.SelectedNode;
          leftTabs_DocumentTree.SelectedNode = node;

          // Find the appropriate ContextMenu depending on the selected node.
          Hashtable idPack = node.Tag as Hashtable;
          switch (idPack["Type"] as string) {
            case "promptgroup":
              menuPromptGroup.Show(leftTabs_DocumentTree, p);
              break;
            case "prompt":
              menuPrompt.Show(leftTabs_DocumentTree, p);
              break;
          }

          // Highlight the selected node.
          leftTabs_DocumentTree.SelectedNode = m_OldSelectNode;
          m_OldSelectNode = null;
        }
      }
    }

    private void leftTabs_Properties_PropertyValueChanged(object s, PropertyValueChangedEventArgs e) {
      refreshTreeView();
    }

    #endregion

  }

  #region Validation error
  public class Error
  {
    public int index;
    public string description;
    public int line;
    public int column;
    public int length;
    public Error(int index, string description, int line, int column, int length) {
      this.index = index;
      this.description = description;
      this.line = line;
      this.column = column;
      this.length = length;
    }
  }
  #endregion

  #region Icons
  public static class Icons
  {
    public const int DATABASE_TABLE = 0;
    public const int BRICK = 1;
    public const int CHART_PIE = 2;
    public const int CLOCK = 3;
    public const int MONITOR = 4;
    public const int SCRIPT = 5;
    public const int TABLE_EDIT = 6;
    public const int TABLE_GEAR = 7;
    public const int TABLE_LIGHTNING = 8;
    public const int TABLE_SORT = 9;
    public const int DATABASE_ADD = 10;
    public const int FOLDER_WRENCH = 11;
    public const int WRENCH = 12;
    public const int REPORT = 13;
    public const int TEXT_FIELD = 14;
    public const int APPLICATION_XP_TERMINAL = 15;
    public const int IMAGE = 16;
    public const int IMAGES = 17;
    public const int DATABASE = 18;
    public const int TEXT_FIELD_SUB = 19;
    public const int TEXT_FIELD_ADD = 20;
    public const int FORM = 21;
    public const int FORM_ADD = 22;
    public const int FORM_EDIT = 23;
    public const int FORM_DELETE = 24;
    public const int FORM_MAGNIFY = 25;
    public const int DATE = 26;
    public const int TEXT_ALIGN_LEFT = 27;
    public const int CHECKBOX = 28;
    public const int CHECKBOX_OLD = 29;
    public const int APPLICATION_CASCADE = 30;
    public const int PAGE_WHITE_CODE = 31;
    public const int DELETE = 32;
    public const int BULLET_ERROR = 33;
    public const int USERDEFINED = 34;
    public const int TEXT_NUMBER = 35;
  }
  #endregion
}
