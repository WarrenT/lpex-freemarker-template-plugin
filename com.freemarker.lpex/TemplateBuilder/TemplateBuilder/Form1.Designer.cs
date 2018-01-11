namespace TemplateBuilder
{
  partial class Form1
  {
    /// <summary>
    /// Required designer variable.
    /// </summary>
    private System.ComponentModel.IContainer components = null;

    /// <summary>
    /// Clean up any resources being used.
    /// </summary>
    /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
    protected override void Dispose(bool disposing) {
      if (disposing && (components != null)) {
        components.Dispose();
      }
      base.Dispose(disposing);
    }

    #region Windows Form Designer generated code

    /// <summary>
    /// Required method for Designer support - do not modify
    /// the contents of this method with the code editor.
    /// </summary>
    private void InitializeComponent() {
      this.components = new System.ComponentModel.Container();
      System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form1));
      this.imageList1 = new System.Windows.Forms.ImageList(this.components);
      this.splitContainer1 = new System.Windows.Forms.SplitContainer();
      this.topTabs = new System.Windows.Forms.TabControl();
      this.tabPage2 = new System.Windows.Forms.TabPage();
      this.topTabs_TemplateEditor = new ICSharpCode.TextEditor.TextEditorControl();
      this.bottomTabs = new System.Windows.Forms.TabControl();
      this.sourceView = new System.Windows.Forms.TabPage();
      this.bottomTab_TemplatePreview = new ICSharpCode.TextEditor.TextEditorControl();
      this.logTab = new System.Windows.Forms.TabPage();
      this.botomTab_DebugLog = new System.Windows.Forms.TextBox();
      this.errors_tab = new System.Windows.Forms.TabPage();
      this.bottomTab_Errors = new System.Windows.Forms.ListView();
      this.icon = ((System.Windows.Forms.ColumnHeader) (new System.Windows.Forms.ColumnHeader()));
      this.index = ((System.Windows.Forms.ColumnHeader) (new System.Windows.Forms.ColumnHeader()));
      this.description = ((System.Windows.Forms.ColumnHeader) (new System.Windows.Forms.ColumnHeader()));
      this.line = ((System.Windows.Forms.ColumnHeader) (new System.Windows.Forms.ColumnHeader()));
      this.column = ((System.Windows.Forms.ColumnHeader) (new System.Windows.Forms.ColumnHeader()));
      this.length = ((System.Windows.Forms.ColumnHeader) (new System.Windows.Forms.ColumnHeader()));
      this.splitContainer2 = new System.Windows.Forms.SplitContainer();
      this.leftTabs = new System.Windows.Forms.TabControl();
      this.tabPage1 = new System.Windows.Forms.TabPage();
      this.splitContainer3 = new System.Windows.Forms.SplitContainer();
      this.leftTabs_DocumentTree = new System.Windows.Forms.TreeView();
      this.toolStrip1 = new System.Windows.Forms.ToolStrip();
      this.btnAddPromptGroup = new System.Windows.Forms.ToolStripButton();
      this.btnRefreshTreeView = new System.Windows.Forms.ToolStripButton();
      this.leftTabs_Properties = new System.Windows.Forms.PropertyGrid();
      this.tabPage3 = new System.Windows.Forms.TabPage();
      this.dataModel = new System.Windows.Forms.TextBox();
      this.mnuHelp_About = new System.Windows.Forms.ToolStripMenuItem();
      this.mnuHelp_SeparatorAfterFreeMarkerDocumentation = new System.Windows.Forms.ToolStripSeparator();
      this.mnuHelp = new System.Windows.Forms.ToolStripMenuItem();
      this.mnuHelp_ProjectPage = new System.Windows.Forms.ToolStripMenuItem();
      this.mnuHelp_FreeMarkerDocumentation = new System.Windows.Forms.ToolStripMenuItem();
      this.toolbar = new System.Windows.Forms.ToolStrip();
      this.toolbar_btnNewFile = new System.Windows.Forms.ToolStripButton();
      this.toolbar_btnOpenFile = new System.Windows.Forms.ToolStripButton();
      this.toolbar_btnSaveFile = new System.Windows.Forms.ToolStripButton();
      this.toolbar_SeparatorAfterSaveFile = new System.Windows.Forms.ToolStripSeparator();
      this.toolbar_btnHelp = new System.Windows.Forms.ToolStripButton();
      this.mnuTools_Options = new System.Windows.Forms.ToolStripMenuItem();
      this.mnuTools_Customize = new System.Windows.Forms.ToolStripMenuItem();
      this.statusBar_progress = new System.Windows.Forms.ToolStripProgressBar();
      this.menuStrip1 = new System.Windows.Forms.MenuStrip();
      this.mnuFile = new System.Windows.Forms.ToolStripMenuItem();
      this.mnuFile_New = new System.Windows.Forms.ToolStripMenuItem();
      this.mnuFile_Open = new System.Windows.Forms.ToolStripMenuItem();
      this.mnuFile_SeparatorAfterOpen = new System.Windows.Forms.ToolStripSeparator();
      this.mnuFile_Save = new System.Windows.Forms.ToolStripMenuItem();
      this.mnuFile_SaveAs = new System.Windows.Forms.ToolStripMenuItem();
      this.mnuFile_SeparatorAfterSaveAs = new System.Windows.Forms.ToolStripSeparator();
      this.mnuFile_RecentFiles = new System.Windows.Forms.ToolStripMenuItem();
      this.mnuFile_SeparatorAfterRecentFiles = new System.Windows.Forms.ToolStripSeparator();
      this.mnuFile_Exit = new System.Windows.Forms.ToolStripMenuItem();
      this.mnuTools = new System.Windows.Forms.ToolStripMenuItem();
      this.mnuTools_SeparatorAfterOptions = new System.Windows.Forms.ToolStripSeparator();
      this.mnuTools_Validate = new System.Windows.Forms.ToolStripMenuItem();
      this.statusStrip1 = new System.Windows.Forms.StatusStrip();
      this.statusBar_status = new System.Windows.Forms.ToolStripStatusLabel();
      this.statusbar_line = new System.Windows.Forms.ToolStripStatusLabel();
      this.menuPromptGroup = new System.Windows.Forms.ContextMenuStrip(this.components);
      this.mnuPromptGroup_Delete = new System.Windows.Forms.ToolStripMenuItem();
      this.mnuPromptGroup_SeparatorAfterDelete = new System.Windows.Forms.ToolStripSeparator();
      this.mnuPromptGroup_MoveUp = new System.Windows.Forms.ToolStripMenuItem();
      this.mnuPromptGroup_MoveDown = new System.Windows.Forms.ToolStripMenuItem();
      this.mnuPromptGroup_SeparatorAfterMoveDown = new System.Windows.Forms.ToolStripSeparator();
      this.mnuPromptGroup_AddPrompt = new System.Windows.Forms.ToolStripMenuItem();
      this.mnuPromptGroup_NewPrompt_Text = new System.Windows.Forms.ToolStripMenuItem();
      this.mnuPromptGroup_NewPrompt_Multiline = new System.Windows.Forms.ToolStripMenuItem();
      this.mnuPromptGroup_NewPrompt_Number = new System.Windows.Forms.ToolStripMenuItem();
      this.mnuPromptGroup_NewPrompt_Date = new System.Windows.Forms.ToolStripMenuItem();
      this.mnuPromptGroup_NewPrompt_Checkbox = new System.Windows.Forms.ToolStripMenuItem();
      this.mnuPromptGroup_NewPrompt_UserDefined = new System.Windows.Forms.ToolStripMenuItem();
      this.menuPrompt = new System.Windows.Forms.ContextMenuStrip(this.components);
      this.insertNewPromptHereToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
      this.mnuPrompt_NewPrompt_Text = new System.Windows.Forms.ToolStripMenuItem();
      this.mnuPrompt_NewPrompt_Multiline = new System.Windows.Forms.ToolStripMenuItem();
      this.mnuPrompt_NewPrompt_Number = new System.Windows.Forms.ToolStripMenuItem();
      this.mnuPrompt_NewPrompt_Date = new System.Windows.Forms.ToolStripMenuItem();
      this.mnuPrompt_NewPrompt_Checkbox = new System.Windows.Forms.ToolStripMenuItem();
      this.mnuPrompt_NewPrompt_UserDefined = new System.Windows.Forms.ToolStripMenuItem();
      this.mnuPrompt_CopyAs = new System.Windows.Forms.ToolStripMenuItem();
      this.mnuPrompt_Delete = new System.Windows.Forms.ToolStripMenuItem();
      this.mnuPrompt_SeparatorAfterDelete = new System.Windows.Forms.ToolStripSeparator();
      this.mnuPrompt_MoveUp = new System.Windows.Forms.ToolStripMenuItem();
      this.mnuPrompt_MoveDown = new System.Windows.Forms.ToolStripMenuItem();
      this.mnuPrompt_SeparatorAfterMoveDown = new System.Windows.Forms.ToolStripSeparator();
      this.mnuPrompt_InsertVariable = new System.Windows.Forms.ToolStripMenuItem();
      ((System.ComponentModel.ISupportInitialize) (this.splitContainer1)).BeginInit();
      this.splitContainer1.Panel1.SuspendLayout();
      this.splitContainer1.Panel2.SuspendLayout();
      this.splitContainer1.SuspendLayout();
      this.topTabs.SuspendLayout();
      this.tabPage2.SuspendLayout();
      this.bottomTabs.SuspendLayout();
      this.sourceView.SuspendLayout();
      this.logTab.SuspendLayout();
      this.errors_tab.SuspendLayout();
      ((System.ComponentModel.ISupportInitialize) (this.splitContainer2)).BeginInit();
      this.splitContainer2.Panel1.SuspendLayout();
      this.splitContainer2.Panel2.SuspendLayout();
      this.splitContainer2.SuspendLayout();
      this.leftTabs.SuspendLayout();
      this.tabPage1.SuspendLayout();
      ((System.ComponentModel.ISupportInitialize) (this.splitContainer3)).BeginInit();
      this.splitContainer3.Panel1.SuspendLayout();
      this.splitContainer3.Panel2.SuspendLayout();
      this.splitContainer3.SuspendLayout();
      this.toolStrip1.SuspendLayout();
      this.tabPage3.SuspendLayout();
      this.toolbar.SuspendLayout();
      this.menuStrip1.SuspendLayout();
      this.statusStrip1.SuspendLayout();
      this.menuPromptGroup.SuspendLayout();
      this.menuPrompt.SuspendLayout();
      this.SuspendLayout();
      // 
      // imageList1
      // 
      this.imageList1.ImageStream = ((System.Windows.Forms.ImageListStreamer) (resources.GetObject("imageList1.ImageStream")));
      this.imageList1.TransparentColor = System.Drawing.Color.Transparent;
      this.imageList1.Images.SetKeyName(0, "database_table.png");
      this.imageList1.Images.SetKeyName(1, "brick.png");
      this.imageList1.Images.SetKeyName(2, "chart_pie.png");
      this.imageList1.Images.SetKeyName(3, "clock.png");
      this.imageList1.Images.SetKeyName(4, "monitor.png");
      this.imageList1.Images.SetKeyName(5, "script.png");
      this.imageList1.Images.SetKeyName(6, "table_edit.png");
      this.imageList1.Images.SetKeyName(7, "table_gear.png");
      this.imageList1.Images.SetKeyName(8, "table_lightning.png");
      this.imageList1.Images.SetKeyName(9, "table_sort.png");
      this.imageList1.Images.SetKeyName(10, "database_add.png");
      this.imageList1.Images.SetKeyName(11, "folder_wrench.png");
      this.imageList1.Images.SetKeyName(12, "wrench.png");
      this.imageList1.Images.SetKeyName(13, "report.png");
      this.imageList1.Images.SetKeyName(14, "textfield.png");
      this.imageList1.Images.SetKeyName(15, "application_xp_terminal.png");
      this.imageList1.Images.SetKeyName(16, "image.png");
      this.imageList1.Images.SetKeyName(17, "images.png");
      this.imageList1.Images.SetKeyName(18, "database.png");
      this.imageList1.Images.SetKeyName(19, "textfield_delete.png");
      this.imageList1.Images.SetKeyName(20, "textfield_add.png");
      this.imageList1.Images.SetKeyName(21, "application_form.png");
      this.imageList1.Images.SetKeyName(22, "application_form_add.png");
      this.imageList1.Images.SetKeyName(23, "application_form_edit.png");
      this.imageList1.Images.SetKeyName(24, "application_form_delete.png");
      this.imageList1.Images.SetKeyName(25, "application_form_magnify.png");
      this.imageList1.Images.SetKeyName(26, "date.png");
      this.imageList1.Images.SetKeyName(27, "text_align_left.png");
      this.imageList1.Images.SetKeyName(28, "checkbox.png");
      this.imageList1.Images.SetKeyName(29, "checkbox.gif");
      this.imageList1.Images.SetKeyName(30, "application_cascade.png");
      this.imageList1.Images.SetKeyName(31, "");
      this.imageList1.Images.SetKeyName(32, "");
      this.imageList1.Images.SetKeyName(33, "bullet_error.png");
      this.imageList1.Images.SetKeyName(34, "textfield_userdefined.png");
      this.imageList1.Images.SetKeyName(35, "textfield_number.png");
      // 
      // splitContainer1
      // 
      this.splitContainer1.Dock = System.Windows.Forms.DockStyle.Fill;
      this.splitContainer1.Location = new System.Drawing.Point(0, 0);
      this.splitContainer1.Name = "splitContainer1";
      this.splitContainer1.Orientation = System.Windows.Forms.Orientation.Horizontal;
      // 
      // splitContainer1.Panel1
      // 
      this.splitContainer1.Panel1.Controls.Add(this.topTabs);
      // 
      // splitContainer1.Panel2
      // 
      this.splitContainer1.Panel2.BackColor = System.Drawing.SystemColors.ControlLight;
      this.splitContainer1.Panel2.Controls.Add(this.bottomTabs);
      this.splitContainer1.Size = new System.Drawing.Size(557, 482);
      this.splitContainer1.SplitterDistance = 324;
      this.splitContainer1.TabIndex = 8;
      // 
      // topTabs
      // 
      this.topTabs.Controls.Add(this.tabPage2);
      this.topTabs.Dock = System.Windows.Forms.DockStyle.Fill;
      this.topTabs.ImageList = this.imageList1;
      this.topTabs.Location = new System.Drawing.Point(0, 0);
      this.topTabs.Name = "topTabs";
      this.topTabs.SelectedIndex = 0;
      this.topTabs.Size = new System.Drawing.Size(557, 324);
      this.topTabs.TabIndex = 1;
      // 
      // tabPage2
      // 
      this.tabPage2.Controls.Add(this.topTabs_TemplateEditor);
      this.tabPage2.ImageIndex = 31;
      this.tabPage2.Location = new System.Drawing.Point(4, 23);
      this.tabPage2.Name = "tabPage2";
      this.tabPage2.Padding = new System.Windows.Forms.Padding(3);
      this.tabPage2.Size = new System.Drawing.Size(549, 297);
      this.tabPage2.TabIndex = 4;
      this.tabPage2.Text = "Template Editor";
      this.tabPage2.UseVisualStyleBackColor = true;
      // 
      // topTabs_TemplateEditor
      // 
      this.topTabs_TemplateEditor.AllowDrop = true;
      this.topTabs_TemplateEditor.Dock = System.Windows.Forms.DockStyle.Fill;
      this.topTabs_TemplateEditor.IsReadOnly = false;
      this.topTabs_TemplateEditor.LineViewerStyle = ICSharpCode.TextEditor.Document.LineViewerStyle.FullRow;
      this.topTabs_TemplateEditor.Location = new System.Drawing.Point(3, 3);
      this.topTabs_TemplateEditor.Name = "topTabs_TemplateEditor";
      this.topTabs_TemplateEditor.ShowVRuler = false;
      this.topTabs_TemplateEditor.Size = new System.Drawing.Size(543, 291);
      this.topTabs_TemplateEditor.TabIndex = 0;
      this.topTabs_TemplateEditor.TextChanged += new System.EventHandler(this.topTabs_TemplateEditor_TextChanged);
      // 
      // bottomTabs
      // 
      this.bottomTabs.Controls.Add(this.sourceView);
      this.bottomTabs.Controls.Add(this.logTab);
      this.bottomTabs.Controls.Add(this.errors_tab);
      this.bottomTabs.Dock = System.Windows.Forms.DockStyle.Fill;
      this.bottomTabs.ImageList = this.imageList1;
      this.bottomTabs.Location = new System.Drawing.Point(0, 0);
      this.bottomTabs.Name = "bottomTabs";
      this.bottomTabs.SelectedIndex = 0;
      this.bottomTabs.Size = new System.Drawing.Size(557, 154);
      this.bottomTabs.TabIndex = 1;
      // 
      // sourceView
      // 
      this.sourceView.Controls.Add(this.bottomTab_TemplatePreview);
      this.sourceView.ImageIndex = 5;
      this.sourceView.Location = new System.Drawing.Point(4, 23);
      this.sourceView.Name = "sourceView";
      this.sourceView.Padding = new System.Windows.Forms.Padding(3);
      this.sourceView.Size = new System.Drawing.Size(549, 127);
      this.sourceView.TabIndex = 0;
      this.sourceView.Text = "Preview";
      this.sourceView.UseVisualStyleBackColor = true;
      // 
      // bottomTab_TemplatePreview
      // 
      this.bottomTab_TemplatePreview.CausesValidation = false;
      this.bottomTab_TemplatePreview.Dock = System.Windows.Forms.DockStyle.Fill;
      this.bottomTab_TemplatePreview.IsReadOnly = true;
      this.bottomTab_TemplatePreview.Location = new System.Drawing.Point(3, 3);
      this.bottomTab_TemplatePreview.Name = "bottomTab_TemplatePreview";
      this.bottomTab_TemplatePreview.ShowVRuler = false;
      this.bottomTab_TemplatePreview.Size = new System.Drawing.Size(543, 121);
      this.bottomTab_TemplatePreview.TabIndex = 0;
      // 
      // logTab
      // 
      this.logTab.Controls.Add(this.botomTab_DebugLog);
      this.logTab.ImageIndex = 16;
      this.logTab.Location = new System.Drawing.Point(4, 23);
      this.logTab.Name = "logTab";
      this.logTab.Padding = new System.Windows.Forms.Padding(3);
      this.logTab.Size = new System.Drawing.Size(549, 127);
      this.logTab.TabIndex = 1;
      this.logTab.Text = "Debug Log";
      this.logTab.UseVisualStyleBackColor = true;
      // 
      // botomTab_DebugLog
      // 
      this.botomTab_DebugLog.BackColor = System.Drawing.SystemColors.Control;
      this.botomTab_DebugLog.Dock = System.Windows.Forms.DockStyle.Fill;
      this.botomTab_DebugLog.Font = new System.Drawing.Font("Courier New", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
      this.botomTab_DebugLog.Location = new System.Drawing.Point(3, 3);
      this.botomTab_DebugLog.MaxLength = 5000;
      this.botomTab_DebugLog.Multiline = true;
      this.botomTab_DebugLog.Name = "botomTab_DebugLog";
      this.botomTab_DebugLog.ReadOnly = true;
      this.botomTab_DebugLog.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
      this.botomTab_DebugLog.Size = new System.Drawing.Size(543, 121);
      this.botomTab_DebugLog.TabIndex = 0;
      // 
      // errors_tab
      // 
      this.errors_tab.Controls.Add(this.bottomTab_Errors);
      this.errors_tab.ImageIndex = 32;
      this.errors_tab.Location = new System.Drawing.Point(4, 23);
      this.errors_tab.Name = "errors_tab";
      this.errors_tab.Padding = new System.Windows.Forms.Padding(3);
      this.errors_tab.Size = new System.Drawing.Size(549, 127);
      this.errors_tab.TabIndex = 2;
      this.errors_tab.Text = "Errors";
      this.errors_tab.UseVisualStyleBackColor = true;
      // 
      // bottomTab_Errors
      // 
      this.bottomTab_Errors.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.icon,
            this.index,
            this.description,
            this.line,
            this.column,
            this.length});
      this.bottomTab_Errors.Dock = System.Windows.Forms.DockStyle.Fill;
      this.bottomTab_Errors.FullRowSelect = true;
      this.bottomTab_Errors.GridLines = true;
      this.bottomTab_Errors.Location = new System.Drawing.Point(3, 3);
      this.bottomTab_Errors.MultiSelect = false;
      this.bottomTab_Errors.Name = "bottomTab_Errors";
      this.bottomTab_Errors.Size = new System.Drawing.Size(543, 121);
      this.bottomTab_Errors.SmallImageList = this.imageList1;
      this.bottomTab_Errors.TabIndex = 0;
      this.bottomTab_Errors.UseCompatibleStateImageBehavior = false;
      this.bottomTab_Errors.View = System.Windows.Forms.View.Details;
      this.bottomTab_Errors.Click += new System.EventHandler(this.bottomTab_Errors_Click);
      // 
      // icon
      // 
      this.icon.Text = "";
      this.icon.Width = 20;
      // 
      // index
      // 
      this.index.Text = "";
      this.index.Width = 20;
      // 
      // description
      // 
      this.description.Text = "Description";
      this.description.Width = 350;
      // 
      // line
      // 
      this.line.Text = "Line";
      this.line.Width = 75;
      // 
      // column
      // 
      this.column.Text = "Column";
      this.column.Width = 75;
      // 
      // length
      // 
      this.length.Text = "Length";
      this.length.Width = 75;
      // 
      // splitContainer2
      // 
      this.splitContainer2.Dock = System.Windows.Forms.DockStyle.Fill;
      this.splitContainer2.Location = new System.Drawing.Point(0, 49);
      this.splitContainer2.Name = "splitContainer2";
      // 
      // splitContainer2.Panel1
      // 
      this.splitContainer2.Panel1.Controls.Add(this.leftTabs);
      // 
      // splitContainer2.Panel2
      // 
      this.splitContainer2.Panel2.Controls.Add(this.splitContainer1);
      this.splitContainer2.Size = new System.Drawing.Size(768, 482);
      this.splitContainer2.SplitterDistance = 207;
      this.splitContainer2.TabIndex = 5;
      // 
      // leftTabs
      // 
      this.leftTabs.Controls.Add(this.tabPage1);
      this.leftTabs.Controls.Add(this.tabPage3);
      this.leftTabs.Dock = System.Windows.Forms.DockStyle.Fill;
      this.leftTabs.ImageList = this.imageList1;
      this.leftTabs.Location = new System.Drawing.Point(0, 0);
      this.leftTabs.Name = "leftTabs";
      this.leftTabs.SelectedIndex = 0;
      this.leftTabs.Size = new System.Drawing.Size(207, 482);
      this.leftTabs.TabIndex = 3;
      // 
      // tabPage1
      // 
      this.tabPage1.Controls.Add(this.splitContainer3);
      this.tabPage1.ImageIndex = 7;
      this.tabPage1.Location = new System.Drawing.Point(4, 23);
      this.tabPage1.Name = "tabPage1";
      this.tabPage1.Padding = new System.Windows.Forms.Padding(3);
      this.tabPage1.Size = new System.Drawing.Size(199, 455);
      this.tabPage1.TabIndex = 0;
      this.tabPage1.Text = "Prompts";
      this.tabPage1.UseVisualStyleBackColor = true;
      // 
      // splitContainer3
      // 
      this.splitContainer3.Dock = System.Windows.Forms.DockStyle.Fill;
      this.splitContainer3.Location = new System.Drawing.Point(3, 3);
      this.splitContainer3.Name = "splitContainer3";
      this.splitContainer3.Orientation = System.Windows.Forms.Orientation.Horizontal;
      // 
      // splitContainer3.Panel1
      // 
      this.splitContainer3.Panel1.Controls.Add(this.leftTabs_DocumentTree);
      this.splitContainer3.Panel1.Controls.Add(this.toolStrip1);
      // 
      // splitContainer3.Panel2
      // 
      this.splitContainer3.Panel2.Controls.Add(this.leftTabs_Properties);
      this.splitContainer3.Size = new System.Drawing.Size(193, 449);
      this.splitContainer3.SplitterDistance = 221;
      this.splitContainer3.TabIndex = 2;
      // 
      // leftTabs_DocumentTree
      // 
      this.leftTabs_DocumentTree.Dock = System.Windows.Forms.DockStyle.Fill;
      this.leftTabs_DocumentTree.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
      this.leftTabs_DocumentTree.ImageIndex = 14;
      this.leftTabs_DocumentTree.ImageList = this.imageList1;
      this.leftTabs_DocumentTree.Indent = 20;
      this.leftTabs_DocumentTree.ItemHeight = 18;
      this.leftTabs_DocumentTree.LineColor = System.Drawing.Color.LightGray;
      this.leftTabs_DocumentTree.Location = new System.Drawing.Point(0, 25);
      this.leftTabs_DocumentTree.Name = "leftTabs_DocumentTree";
      this.leftTabs_DocumentTree.SelectedImageIndex = 0;
      this.leftTabs_DocumentTree.Size = new System.Drawing.Size(193, 196);
      this.leftTabs_DocumentTree.TabIndex = 0;
      this.leftTabs_DocumentTree.AfterSelect += new System.Windows.Forms.TreeViewEventHandler(this.leftTabs_DocumentTree_AfterSelect);
      this.leftTabs_DocumentTree.MouseDoubleClick += new System.Windows.Forms.MouseEventHandler(this.leftTabs_DocumentTree_MouseDoubleClick);
      this.leftTabs_DocumentTree.MouseDown += new System.Windows.Forms.MouseEventHandler(this.leftTabs_DocumentTree_MouseDown);
      this.leftTabs_DocumentTree.MouseMove += new System.Windows.Forms.MouseEventHandler(this.leftTabs_DocumentTree_MouseMove);
      this.leftTabs_DocumentTree.MouseUp += new System.Windows.Forms.MouseEventHandler(this.leftTabs_DocumentTree_MouseUp);
      // 
      // toolStrip1
      // 
      this.toolStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.btnAddPromptGroup,
            this.btnRefreshTreeView});
      this.toolStrip1.Location = new System.Drawing.Point(0, 0);
      this.toolStrip1.Name = "toolStrip1";
      this.toolStrip1.Size = new System.Drawing.Size(193, 25);
      this.toolStrip1.TabIndex = 1;
      this.toolStrip1.Text = "toolStrip1";
      // 
      // btnAddPromptGroup
      // 
      this.btnAddPromptGroup.Image = global::TemplateBuilder.Properties.Resources.application_form_add;
      this.btnAddPromptGroup.ImageTransparentColor = System.Drawing.Color.Magenta;
      this.btnAddPromptGroup.Name = "btnAddPromptGroup";
      this.btnAddPromptGroup.Size = new System.Drawing.Size(115, 22);
      this.btnAddPromptGroup.Text = "Add Prompt Group";
      this.btnAddPromptGroup.Click += new System.EventHandler(this.btnAddPromptGroup_Click);
      // 
      // btnRefreshTreeView
      // 
      this.btnRefreshTreeView.Alignment = System.Windows.Forms.ToolStripItemAlignment.Right;
      this.btnRefreshTreeView.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Image;
      this.btnRefreshTreeView.Image = ((System.Drawing.Image) (resources.GetObject("btnRefreshTreeView.Image")));
      this.btnRefreshTreeView.ImageTransparentColor = System.Drawing.Color.Magenta;
      this.btnRefreshTreeView.Name = "btnRefreshTreeView";
      this.btnRefreshTreeView.Size = new System.Drawing.Size(23, 22);
      this.btnRefreshTreeView.Text = "toolStripButton1";
      this.btnRefreshTreeView.Click += new System.EventHandler(this.btnRefreshTreeView_Click);
      // 
      // leftTabs_Properties
      // 
      this.leftTabs_Properties.Dock = System.Windows.Forms.DockStyle.Fill;
      this.leftTabs_Properties.Location = new System.Drawing.Point(0, 0);
      this.leftTabs_Properties.Name = "leftTabs_Properties";
      this.leftTabs_Properties.Size = new System.Drawing.Size(193, 224);
      this.leftTabs_Properties.TabIndex = 1;
      this.leftTabs_Properties.PropertyValueChanged += new System.Windows.Forms.PropertyValueChangedEventHandler(this.leftTabs_Properties_PropertyValueChanged);
      // 
      // tabPage3
      // 
      this.tabPage3.Controls.Add(this.dataModel);
      this.tabPage3.ImageIndex = 1;
      this.tabPage3.Location = new System.Drawing.Point(4, 23);
      this.tabPage3.Name = "tabPage3";
      this.tabPage3.Padding = new System.Windows.Forms.Padding(3);
      this.tabPage3.Size = new System.Drawing.Size(199, 455);
      this.tabPage3.TabIndex = 1;
      this.tabPage3.Text = "Data Model";
      this.tabPage3.UseVisualStyleBackColor = true;
      // 
      // dataModel
      // 
      this.dataModel.Dock = System.Windows.Forms.DockStyle.Fill;
      this.dataModel.Font = new System.Drawing.Font("Courier New", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
      this.dataModel.Location = new System.Drawing.Point(3, 3);
      this.dataModel.Multiline = true;
      this.dataModel.Name = "dataModel";
      this.dataModel.ReadOnly = true;
      this.dataModel.ScrollBars = System.Windows.Forms.ScrollBars.Both;
      this.dataModel.Size = new System.Drawing.Size(193, 449);
      this.dataModel.TabIndex = 0;
      this.dataModel.WordWrap = false;
      // 
      // mnuHelp_About
      // 
      this.mnuHelp_About.Name = "mnuHelp_About";
      this.mnuHelp_About.Size = new System.Drawing.Size(199, 22);
      this.mnuHelp_About.Text = "&About...";
      this.mnuHelp_About.Click += new System.EventHandler(this.mnuHelp_About_Click);
      // 
      // mnuHelp_SeparatorAfterFreeMarkerDocumentation
      // 
      this.mnuHelp_SeparatorAfterFreeMarkerDocumentation.Name = "mnuHelp_SeparatorAfterFreeMarkerDocumentation";
      this.mnuHelp_SeparatorAfterFreeMarkerDocumentation.Size = new System.Drawing.Size(196, 6);
      // 
      // mnuHelp
      // 
      this.mnuHelp.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.mnuHelp_ProjectPage,
            this.mnuHelp_FreeMarkerDocumentation,
            this.mnuHelp_SeparatorAfterFreeMarkerDocumentation,
            this.mnuHelp_About});
      this.mnuHelp.Name = "mnuHelp";
      this.mnuHelp.Size = new System.Drawing.Size(40, 20);
      this.mnuHelp.Text = "&Help";
      // 
      // mnuHelp_ProjectPage
      // 
      this.mnuHelp_ProjectPage.Name = "mnuHelp_ProjectPage";
      this.mnuHelp_ProjectPage.Size = new System.Drawing.Size(199, 22);
      this.mnuHelp_ProjectPage.Text = "&Project Page";
      this.mnuHelp_ProjectPage.Click += new System.EventHandler(this.mnuHelp_ProjectPage_Click);
      // 
      // mnuHelp_FreeMarkerDocumentation
      // 
      this.mnuHelp_FreeMarkerDocumentation.Name = "mnuHelp_FreeMarkerDocumentation";
      this.mnuHelp_FreeMarkerDocumentation.Size = new System.Drawing.Size(199, 22);
      this.mnuHelp_FreeMarkerDocumentation.Text = "&FreeMarker Doumentation";
      this.mnuHelp_FreeMarkerDocumentation.Click += new System.EventHandler(this.mnuHelp_FreeMarkerDocumentation_Click);
      // 
      // toolbar
      // 
      this.toolbar.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.toolbar_btnNewFile,
            this.toolbar_btnOpenFile,
            this.toolbar_btnSaveFile,
            this.toolbar_SeparatorAfterSaveFile,
            this.toolbar_btnHelp});
      this.toolbar.Location = new System.Drawing.Point(0, 24);
      this.toolbar.Name = "toolbar";
      this.toolbar.Size = new System.Drawing.Size(768, 25);
      this.toolbar.TabIndex = 7;
      // 
      // toolbar_btnNewFile
      // 
      this.toolbar_btnNewFile.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Image;
      this.toolbar_btnNewFile.Image = ((System.Drawing.Image) (resources.GetObject("toolbar_btnNewFile.Image")));
      this.toolbar_btnNewFile.ImageTransparentColor = System.Drawing.Color.Magenta;
      this.toolbar_btnNewFile.Name = "toolbar_btnNewFile";
      this.toolbar_btnNewFile.Size = new System.Drawing.Size(23, 22);
      this.toolbar_btnNewFile.Text = "&New";
      this.toolbar_btnNewFile.Click += new System.EventHandler(this.toolbar_btnNewFile_Click);
      // 
      // toolbar_btnOpenFile
      // 
      this.toolbar_btnOpenFile.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Image;
      this.toolbar_btnOpenFile.Image = ((System.Drawing.Image) (resources.GetObject("toolbar_btnOpenFile.Image")));
      this.toolbar_btnOpenFile.ImageTransparentColor = System.Drawing.Color.Magenta;
      this.toolbar_btnOpenFile.Name = "toolbar_btnOpenFile";
      this.toolbar_btnOpenFile.Size = new System.Drawing.Size(23, 22);
      this.toolbar_btnOpenFile.Text = "&Open";
      this.toolbar_btnOpenFile.Click += new System.EventHandler(this.toolbar_btnOpenFile_Click);
      // 
      // toolbar_btnSaveFile
      // 
      this.toolbar_btnSaveFile.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Image;
      this.toolbar_btnSaveFile.Image = ((System.Drawing.Image) (resources.GetObject("toolbar_btnSaveFile.Image")));
      this.toolbar_btnSaveFile.ImageTransparentColor = System.Drawing.Color.Magenta;
      this.toolbar_btnSaveFile.Name = "toolbar_btnSaveFile";
      this.toolbar_btnSaveFile.Size = new System.Drawing.Size(23, 22);
      this.toolbar_btnSaveFile.Text = "&Save";
      this.toolbar_btnSaveFile.Click += new System.EventHandler(this.toolbar_btnSaveFile_Click);
      // 
      // toolbar_SeparatorAfterSaveFile
      // 
      this.toolbar_SeparatorAfterSaveFile.Name = "toolbar_SeparatorAfterSaveFile";
      this.toolbar_SeparatorAfterSaveFile.Size = new System.Drawing.Size(6, 25);
      // 
      // toolbar_btnHelp
      // 
      this.toolbar_btnHelp.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Image;
      this.toolbar_btnHelp.Image = ((System.Drawing.Image) (resources.GetObject("toolbar_btnHelp.Image")));
      this.toolbar_btnHelp.ImageTransparentColor = System.Drawing.Color.Magenta;
      this.toolbar_btnHelp.Name = "toolbar_btnHelp";
      this.toolbar_btnHelp.Size = new System.Drawing.Size(23, 22);
      this.toolbar_btnHelp.Text = "He&lp";
      this.toolbar_btnHelp.Click += new System.EventHandler(this.toolbar_btnHelp_Click);
      // 
      // mnuTools_Options
      // 
      this.mnuTools_Options.Enabled = false;
      this.mnuTools_Options.Name = "mnuTools_Options";
      this.mnuTools_Options.Size = new System.Drawing.Size(123, 22);
      this.mnuTools_Options.Text = "&Options";
      this.mnuTools_Options.Visible = false;
      // 
      // mnuTools_Customize
      // 
      this.mnuTools_Customize.Enabled = false;
      this.mnuTools_Customize.Name = "mnuTools_Customize";
      this.mnuTools_Customize.Size = new System.Drawing.Size(123, 22);
      this.mnuTools_Customize.Text = "&Customize";
      this.mnuTools_Customize.Visible = false;
      // 
      // statusBar_progress
      // 
      this.statusBar_progress.Margin = new System.Windows.Forms.Padding(0, 3, 0, 2);
      this.statusBar_progress.Name = "statusBar_progress";
      this.statusBar_progress.Padding = new System.Windows.Forms.Padding(0, 3, 0, 3);
      this.statusBar_progress.Size = new System.Drawing.Size(100, 23);
      this.statusBar_progress.Visible = false;
      // 
      // menuStrip1
      // 
      this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.mnuFile,
            this.mnuTools,
            this.mnuHelp});
      this.menuStrip1.Location = new System.Drawing.Point(0, 0);
      this.menuStrip1.Name = "menuStrip1";
      this.menuStrip1.Size = new System.Drawing.Size(768, 24);
      this.menuStrip1.TabIndex = 6;
      this.menuStrip1.Text = "menuStrip1";
      // 
      // mnuFile
      // 
      this.mnuFile.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.mnuFile_New,
            this.mnuFile_Open,
            this.mnuFile_SeparatorAfterOpen,
            this.mnuFile_Save,
            this.mnuFile_SaveAs,
            this.mnuFile_SeparatorAfterSaveAs,
            this.mnuFile_RecentFiles,
            this.mnuFile_SeparatorAfterRecentFiles,
            this.mnuFile_Exit});
      this.mnuFile.Name = "mnuFile";
      this.mnuFile.Size = new System.Drawing.Size(35, 20);
      this.mnuFile.Text = "&File";
      // 
      // mnuFile_New
      // 
      this.mnuFile_New.Image = ((System.Drawing.Image) (resources.GetObject("mnuFile_New.Image")));
      this.mnuFile_New.ImageTransparentColor = System.Drawing.Color.Magenta;
      this.mnuFile_New.Name = "mnuFile_New";
      this.mnuFile_New.ShortcutKeys = ((System.Windows.Forms.Keys) ((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.N)));
      this.mnuFile_New.Size = new System.Drawing.Size(143, 22);
      this.mnuFile_New.Text = "&New";
      this.mnuFile_New.Click += new System.EventHandler(this.mnuFile_New_Click);
      // 
      // mnuFile_Open
      // 
      this.mnuFile_Open.Image = ((System.Drawing.Image) (resources.GetObject("mnuFile_Open.Image")));
      this.mnuFile_Open.ImageTransparentColor = System.Drawing.Color.Magenta;
      this.mnuFile_Open.Name = "mnuFile_Open";
      this.mnuFile_Open.ShortcutKeys = ((System.Windows.Forms.Keys) ((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.O)));
      this.mnuFile_Open.Size = new System.Drawing.Size(143, 22);
      this.mnuFile_Open.Text = "&Open";
      this.mnuFile_Open.Click += new System.EventHandler(this.mnuFile_Open_Click);
      // 
      // mnuFile_SeparatorAfterOpen
      // 
      this.mnuFile_SeparatorAfterOpen.Name = "mnuFile_SeparatorAfterOpen";
      this.mnuFile_SeparatorAfterOpen.Size = new System.Drawing.Size(140, 6);
      // 
      // mnuFile_Save
      // 
      this.mnuFile_Save.Image = ((System.Drawing.Image) (resources.GetObject("mnuFile_Save.Image")));
      this.mnuFile_Save.ImageTransparentColor = System.Drawing.Color.Magenta;
      this.mnuFile_Save.Name = "mnuFile_Save";
      this.mnuFile_Save.ShortcutKeys = ((System.Windows.Forms.Keys) ((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.S)));
      this.mnuFile_Save.Size = new System.Drawing.Size(143, 22);
      this.mnuFile_Save.Text = "&Save";
      this.mnuFile_Save.Click += new System.EventHandler(this.mnuFile_Save_Click);
      // 
      // mnuFile_SaveAs
      // 
      this.mnuFile_SaveAs.Name = "mnuFile_SaveAs";
      this.mnuFile_SaveAs.Size = new System.Drawing.Size(143, 22);
      this.mnuFile_SaveAs.Text = "Save &As";
      this.mnuFile_SaveAs.Click += new System.EventHandler(this.mnuFile_SaveAs_Click);
      // 
      // mnuFile_SeparatorAfterSaveAs
      // 
      this.mnuFile_SeparatorAfterSaveAs.Name = "mnuFile_SeparatorAfterSaveAs";
      this.mnuFile_SeparatorAfterSaveAs.Size = new System.Drawing.Size(140, 6);
      // 
      // mnuFile_RecentFiles
      // 
      this.mnuFile_RecentFiles.Name = "mnuFile_RecentFiles";
      this.mnuFile_RecentFiles.Size = new System.Drawing.Size(143, 22);
      this.mnuFile_RecentFiles.Text = "Recent Files";
      // 
      // mnuFile_SeparatorAfterRecentFiles
      // 
      this.mnuFile_SeparatorAfterRecentFiles.Name = "mnuFile_SeparatorAfterRecentFiles";
      this.mnuFile_SeparatorAfterRecentFiles.Size = new System.Drawing.Size(140, 6);
      // 
      // mnuFile_Exit
      // 
      this.mnuFile_Exit.Name = "mnuFile_Exit";
      this.mnuFile_Exit.Size = new System.Drawing.Size(143, 22);
      this.mnuFile_Exit.Text = "E&xit";
      this.mnuFile_Exit.Click += new System.EventHandler(this.mnuFile_Exit_Click);
      // 
      // mnuTools
      // 
      this.mnuTools.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.mnuTools_Customize,
            this.mnuTools_Options,
            this.mnuTools_SeparatorAfterOptions,
            this.mnuTools_Validate});
      this.mnuTools.Name = "mnuTools";
      this.mnuTools.Size = new System.Drawing.Size(44, 20);
      this.mnuTools.Text = "&Tools";
      // 
      // mnuTools_SeparatorAfterOptions
      // 
      this.mnuTools_SeparatorAfterOptions.Name = "mnuTools_SeparatorAfterOptions";
      this.mnuTools_SeparatorAfterOptions.Size = new System.Drawing.Size(120, 6);
      this.mnuTools_SeparatorAfterOptions.Visible = false;
      // 
      // mnuTools_Validate
      // 
      this.mnuTools_Validate.Enabled = false;
      this.mnuTools_Validate.Name = "mnuTools_Validate";
      this.mnuTools_Validate.Size = new System.Drawing.Size(123, 22);
      this.mnuTools_Validate.Text = "Validate";
      this.mnuTools_Validate.Click += new System.EventHandler(this.mnuTools_Validate_Click);
      // 
      // statusStrip1
      // 
      this.statusStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.statusBar_progress,
            this.statusBar_status,
            this.statusbar_line});
      this.statusStrip1.Location = new System.Drawing.Point(0, 531);
      this.statusStrip1.Name = "statusStrip1";
      this.statusStrip1.Size = new System.Drawing.Size(768, 28);
      this.statusStrip1.TabIndex = 5;
      this.statusStrip1.Text = "statusStrip1";
      // 
      // statusBar_status
      // 
      this.statusBar_status.BorderSides = ((System.Windows.Forms.ToolStripStatusLabelBorderSides) ((((System.Windows.Forms.ToolStripStatusLabelBorderSides.Left | System.Windows.Forms.ToolStripStatusLabelBorderSides.Top)
                  | System.Windows.Forms.ToolStripStatusLabelBorderSides.Right)
                  | System.Windows.Forms.ToolStripStatusLabelBorderSides.Bottom)));
      this.statusBar_status.BorderStyle = System.Windows.Forms.Border3DStyle.SunkenOuter;
      this.statusBar_status.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Text;
      this.statusBar_status.Name = "statusBar_status";
      this.statusBar_status.Padding = new System.Windows.Forms.Padding(0, 3, 0, 3);
      this.statusBar_status.Size = new System.Drawing.Size(615, 23);
      this.statusBar_status.Spring = true;
      this.statusBar_status.Text = "Please open a document to work with.";
      this.statusBar_status.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
      // 
      // statusbar_line
      // 
      this.statusbar_line.BorderSides = ((System.Windows.Forms.ToolStripStatusLabelBorderSides) ((((System.Windows.Forms.ToolStripStatusLabelBorderSides.Left | System.Windows.Forms.ToolStripStatusLabelBorderSides.Top)
                  | System.Windows.Forms.ToolStripStatusLabelBorderSides.Right)
                  | System.Windows.Forms.ToolStripStatusLabelBorderSides.Bottom)));
      this.statusbar_line.BorderStyle = System.Windows.Forms.Border3DStyle.SunkenOuter;
      this.statusbar_line.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Text;
      this.statusbar_line.Name = "statusbar_line";
      this.statusbar_line.Padding = new System.Windows.Forms.Padding(0, 3, 60, 3);
      this.statusbar_line.Size = new System.Drawing.Size(138, 23);
      this.statusbar_line.Text = "Ln : 0   Col : 0";
      this.statusbar_line.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
      // 
      // menuPromptGroup
      // 
      this.menuPromptGroup.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.mnuPromptGroup_Delete,
            this.mnuPromptGroup_SeparatorAfterDelete,
            this.mnuPromptGroup_MoveUp,
            this.mnuPromptGroup_MoveDown,
            this.mnuPromptGroup_SeparatorAfterMoveDown,
            this.mnuPromptGroup_AddPrompt});
      this.menuPromptGroup.Name = "contextMenuStrip1";
      this.menuPromptGroup.Size = new System.Drawing.Size(131, 104);
      // 
      // mnuPromptGroup_Delete
      // 
      this.mnuPromptGroup_Delete.Image = global::TemplateBuilder.Properties.Resources.application_form_delete;
      this.mnuPromptGroup_Delete.Name = "mnuPromptGroup_Delete";
      this.mnuPromptGroup_Delete.Size = new System.Drawing.Size(130, 22);
      this.mnuPromptGroup_Delete.Text = "Delete";
      this.mnuPromptGroup_Delete.Click += new System.EventHandler(this.mnuPromptGroup_Delete_Click);
      // 
      // mnuPromptGroup_SeparatorAfterDelete
      // 
      this.mnuPromptGroup_SeparatorAfterDelete.Name = "mnuPromptGroup_SeparatorAfterDelete";
      this.mnuPromptGroup_SeparatorAfterDelete.Size = new System.Drawing.Size(127, 6);
      // 
      // mnuPromptGroup_MoveUp
      // 
      this.mnuPromptGroup_MoveUp.Name = "mnuPromptGroup_MoveUp";
      this.mnuPromptGroup_MoveUp.Size = new System.Drawing.Size(130, 22);
      this.mnuPromptGroup_MoveUp.Text = "Move Up";
      this.mnuPromptGroup_MoveUp.Click += new System.EventHandler(this.mnuPromptGroup_MoveUp_Click);
      // 
      // mnuPromptGroup_MoveDown
      // 
      this.mnuPromptGroup_MoveDown.Name = "mnuPromptGroup_MoveDown";
      this.mnuPromptGroup_MoveDown.Size = new System.Drawing.Size(130, 22);
      this.mnuPromptGroup_MoveDown.Text = "Move Down";
      this.mnuPromptGroup_MoveDown.Click += new System.EventHandler(this.mnuPromptGroup_MoveDown_Click);
      // 
      // mnuPromptGroup_SeparatorAfterMoveDown
      // 
      this.mnuPromptGroup_SeparatorAfterMoveDown.Name = "mnuPromptGroup_SeparatorAfterMoveDown";
      this.mnuPromptGroup_SeparatorAfterMoveDown.Size = new System.Drawing.Size(127, 6);
      // 
      // mnuPromptGroup_AddPrompt
      // 
      this.mnuPromptGroup_AddPrompt.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.mnuPromptGroup_NewPrompt_Text,
            this.mnuPromptGroup_NewPrompt_Multiline,
            this.mnuPromptGroup_NewPrompt_Number,
            this.mnuPromptGroup_NewPrompt_Date,
            this.mnuPromptGroup_NewPrompt_Checkbox,
            this.mnuPromptGroup_NewPrompt_UserDefined});
      this.mnuPromptGroup_AddPrompt.Image = global::TemplateBuilder.Properties.Resources.application_form_add;
      this.mnuPromptGroup_AddPrompt.Name = "mnuPromptGroup_AddPrompt";
      this.mnuPromptGroup_AddPrompt.Size = new System.Drawing.Size(130, 22);
      this.mnuPromptGroup_AddPrompt.Text = "Add Prompt";
      // 
      // mnuPromptGroup_NewPrompt_Text
      // 
      this.mnuPromptGroup_NewPrompt_Text.Name = "mnuPromptGroup_NewPrompt_Text";
      this.mnuPromptGroup_NewPrompt_Text.Size = new System.Drawing.Size(136, 22);
      this.mnuPromptGroup_NewPrompt_Text.Text = "Text";
      this.mnuPromptGroup_NewPrompt_Text.Click += new System.EventHandler(this.mnuPromptGroup_NewPrompt_Text_Click);
      // 
      // mnuPromptGroup_NewPrompt_Multiline
      // 
      this.mnuPromptGroup_NewPrompt_Multiline.Name = "mnuPromptGroup_NewPrompt_Multiline";
      this.mnuPromptGroup_NewPrompt_Multiline.Size = new System.Drawing.Size(136, 22);
      this.mnuPromptGroup_NewPrompt_Multiline.Text = "Multiline";
      this.mnuPromptGroup_NewPrompt_Multiline.Click += new System.EventHandler(this.mnuPromptGroup_NewPrompt_Multiline_Click);
      // 
      // mnuPromptGroup_NewPrompt_Number
      // 
      this.mnuPromptGroup_NewPrompt_Number.Name = "mnuPromptGroup_NewPrompt_Number";
      this.mnuPromptGroup_NewPrompt_Number.Size = new System.Drawing.Size(136, 22);
      this.mnuPromptGroup_NewPrompt_Number.Text = "Number";
      this.mnuPromptGroup_NewPrompt_Number.Click += new System.EventHandler(this.mnuPromptGroup_NewPrompt_Number_Click);
      // 
      // mnuPromptGroup_NewPrompt_Date
      // 
      this.mnuPromptGroup_NewPrompt_Date.Name = "mnuPromptGroup_NewPrompt_Date";
      this.mnuPromptGroup_NewPrompt_Date.Size = new System.Drawing.Size(136, 22);
      this.mnuPromptGroup_NewPrompt_Date.Text = "Date";
      this.mnuPromptGroup_NewPrompt_Date.Click += new System.EventHandler(this.mnuPromptGroup_NewPrompt_Date_Click);
      // 
      // mnuPromptGroup_NewPrompt_Checkbox
      // 
      this.mnuPromptGroup_NewPrompt_Checkbox.Name = "mnuPromptGroup_NewPrompt_Checkbox";
      this.mnuPromptGroup_NewPrompt_Checkbox.Size = new System.Drawing.Size(136, 22);
      this.mnuPromptGroup_NewPrompt_Checkbox.Text = "Checkbox";
      this.mnuPromptGroup_NewPrompt_Checkbox.Click += new System.EventHandler(this.mnuPromptGroup_NewPrompt_Checkbox_Click);
      // 
      // mnuPromptGroup_NewPrompt_UserDefined
      // 
      this.mnuPromptGroup_NewPrompt_UserDefined.Name = "mnuPromptGroup_NewPrompt_UserDefined";
      this.mnuPromptGroup_NewPrompt_UserDefined.Size = new System.Drawing.Size(136, 22);
      this.mnuPromptGroup_NewPrompt_UserDefined.Text = "User-defined";
      this.mnuPromptGroup_NewPrompt_UserDefined.Click += new System.EventHandler(this.mnuPromptGroup_NewPrompt_UserDefined_Click);
      // 
      // menuPrompt
      // 
      this.menuPrompt.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.insertNewPromptHereToolStripMenuItem,
            this.mnuPrompt_CopyAs,
            this.mnuPrompt_Delete,
            this.mnuPrompt_SeparatorAfterDelete,
            this.mnuPrompt_MoveUp,
            this.mnuPrompt_MoveDown,
            this.mnuPrompt_SeparatorAfterMoveDown,
            this.mnuPrompt_InsertVariable});
      this.menuPrompt.Name = "contextMenuStrip1";
      this.menuPrompt.Size = new System.Drawing.Size(191, 148);
      // 
      // insertNewPromptHereToolStripMenuItem
      // 
      this.insertNewPromptHereToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.mnuPrompt_NewPrompt_Text,
            this.mnuPrompt_NewPrompt_Multiline,
            this.mnuPrompt_NewPrompt_Number,
            this.mnuPrompt_NewPrompt_Date,
            this.mnuPrompt_NewPrompt_Checkbox,
            this.mnuPrompt_NewPrompt_UserDefined});
      this.insertNewPromptHereToolStripMenuItem.Image = global::TemplateBuilder.Properties.Resources.application_form_add;
      this.insertNewPromptHereToolStripMenuItem.Name = "insertNewPromptHereToolStripMenuItem";
      this.insertNewPromptHereToolStripMenuItem.Size = new System.Drawing.Size(190, 22);
      this.insertNewPromptHereToolStripMenuItem.Text = "Insert New Prompt Here";
      // 
      // mnuPrompt_NewPrompt_Text
      // 
      this.mnuPrompt_NewPrompt_Text.Name = "mnuPrompt_NewPrompt_Text";
      this.mnuPrompt_NewPrompt_Text.Size = new System.Drawing.Size(136, 22);
      this.mnuPrompt_NewPrompt_Text.Text = "Text";
      this.mnuPrompt_NewPrompt_Text.Click += new System.EventHandler(this.mnuPrompt_NewPrompt_Text_Click);
      // 
      // mnuPrompt_NewPrompt_Multiline
      // 
      this.mnuPrompt_NewPrompt_Multiline.Name = "mnuPrompt_NewPrompt_Multiline";
      this.mnuPrompt_NewPrompt_Multiline.Size = new System.Drawing.Size(136, 22);
      this.mnuPrompt_NewPrompt_Multiline.Text = "Multiline";
      this.mnuPrompt_NewPrompt_Multiline.Click += new System.EventHandler(this.mnuPrompt_NewPrompt_Multiline_Click);
      // 
      // mnuPrompt_NewPrompt_Number
      // 
      this.mnuPrompt_NewPrompt_Number.Name = "mnuPrompt_NewPrompt_Number";
      this.mnuPrompt_NewPrompt_Number.Size = new System.Drawing.Size(136, 22);
      this.mnuPrompt_NewPrompt_Number.Text = "Number";
      this.mnuPrompt_NewPrompt_Number.Click += new System.EventHandler(this.mnuPrompt_NewPrompt_Number_Click);
      // 
      // mnuPrompt_NewPrompt_Date
      // 
      this.mnuPrompt_NewPrompt_Date.Name = "mnuPrompt_NewPrompt_Date";
      this.mnuPrompt_NewPrompt_Date.Size = new System.Drawing.Size(136, 22);
      this.mnuPrompt_NewPrompt_Date.Text = "Date";
      this.mnuPrompt_NewPrompt_Date.Click += new System.EventHandler(this.mnuPrompt_NewPrompt_Date_Click);
      // 
      // mnuPrompt_NewPrompt_Checkbox
      // 
      this.mnuPrompt_NewPrompt_Checkbox.Name = "mnuPrompt_NewPrompt_Checkbox";
      this.mnuPrompt_NewPrompt_Checkbox.Size = new System.Drawing.Size(136, 22);
      this.mnuPrompt_NewPrompt_Checkbox.Text = "Checkbox";
      this.mnuPrompt_NewPrompt_Checkbox.Click += new System.EventHandler(this.mnuPrompt_NewPrompt_Checkbox_Click);
      // 
      // mnuPrompt_NewPrompt_UserDefined
      // 
      this.mnuPrompt_NewPrompt_UserDefined.Name = "mnuPrompt_NewPrompt_UserDefined";
      this.mnuPrompt_NewPrompt_UserDefined.Size = new System.Drawing.Size(136, 22);
      this.mnuPrompt_NewPrompt_UserDefined.Text = "User-defined";
      this.mnuPrompt_NewPrompt_UserDefined.Click += new System.EventHandler(this.mnuPrompt_NewPrompt_UserDefined_Click);
      // 
      // mnuPrompt_CopyAs
      // 
      this.mnuPrompt_CopyAs.Image = global::TemplateBuilder.Properties.Resources.application_form_add;
      this.mnuPrompt_CopyAs.Name = "mnuPrompt_CopyAs";
      this.mnuPrompt_CopyAs.Size = new System.Drawing.Size(190, 22);
      this.mnuPrompt_CopyAs.Text = "Copy as New Prompt";
      this.mnuPrompt_CopyAs.Click += new System.EventHandler(this.mnuPrompt_CopyAs_Click);
      // 
      // mnuPrompt_Delete
      // 
      this.mnuPrompt_Delete.Image = global::TemplateBuilder.Properties.Resources.application_form_delete;
      this.mnuPrompt_Delete.Name = "mnuPrompt_Delete";
      this.mnuPrompt_Delete.Size = new System.Drawing.Size(190, 22);
      this.mnuPrompt_Delete.Text = "Delete";
      this.mnuPrompt_Delete.Click += new System.EventHandler(this.mnuPrompt_Delete_Click);
      // 
      // mnuPrompt_SeparatorAfterDelete
      // 
      this.mnuPrompt_SeparatorAfterDelete.Name = "mnuPrompt_SeparatorAfterDelete";
      this.mnuPrompt_SeparatorAfterDelete.Size = new System.Drawing.Size(187, 6);
      // 
      // mnuPrompt_MoveUp
      // 
      this.mnuPrompt_MoveUp.Name = "mnuPrompt_MoveUp";
      this.mnuPrompt_MoveUp.Size = new System.Drawing.Size(190, 22);
      this.mnuPrompt_MoveUp.Text = "Move Up";
      this.mnuPrompt_MoveUp.Click += new System.EventHandler(this.mnuPrompt_MoveUp_Click);
      // 
      // mnuPrompt_MoveDown
      // 
      this.mnuPrompt_MoveDown.Name = "mnuPrompt_MoveDown";
      this.mnuPrompt_MoveDown.Size = new System.Drawing.Size(190, 22);
      this.mnuPrompt_MoveDown.Text = "Move Down";
      this.mnuPrompt_MoveDown.Click += new System.EventHandler(this.mnuPrompt_MoveDown_Click);
      // 
      // mnuPrompt_SeparatorAfterMoveDown
      // 
      this.mnuPrompt_SeparatorAfterMoveDown.Name = "mnuPrompt_SeparatorAfterMoveDown";
      this.mnuPrompt_SeparatorAfterMoveDown.Size = new System.Drawing.Size(187, 6);
      // 
      // mnuPrompt_InsertVariable
      // 
      this.mnuPrompt_InsertVariable.Image = global::TemplateBuilder.Properties.Resources.brick_add;
      this.mnuPrompt_InsertVariable.Name = "mnuPrompt_InsertVariable";
      this.mnuPrompt_InsertVariable.Size = new System.Drawing.Size(190, 22);
      this.mnuPrompt_InsertVariable.Text = "Insert Variable";
      this.mnuPrompt_InsertVariable.Click += new System.EventHandler(this.mnuPrompt_InsertVariable_Click);
      // 
      // Form1
      // 
      this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
      this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
      this.ClientSize = new System.Drawing.Size(768, 559);
      this.Controls.Add(this.splitContainer2);
      this.Controls.Add(this.toolbar);
      this.Controls.Add(this.menuStrip1);
      this.Controls.Add(this.statusStrip1);
      this.Name = "Form1";
      this.Text = "LPEX FreeMarker Template Builder";
      this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
      this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.MainForm_FormClosing);
      this.splitContainer1.Panel1.ResumeLayout(false);
      this.splitContainer1.Panel2.ResumeLayout(false);
      ((System.ComponentModel.ISupportInitialize) (this.splitContainer1)).EndInit();
      this.splitContainer1.ResumeLayout(false);
      this.topTabs.ResumeLayout(false);
      this.tabPage2.ResumeLayout(false);
      this.bottomTabs.ResumeLayout(false);
      this.sourceView.ResumeLayout(false);
      this.logTab.ResumeLayout(false);
      this.logTab.PerformLayout();
      this.errors_tab.ResumeLayout(false);
      this.splitContainer2.Panel1.ResumeLayout(false);
      this.splitContainer2.Panel2.ResumeLayout(false);
      ((System.ComponentModel.ISupportInitialize) (this.splitContainer2)).EndInit();
      this.splitContainer2.ResumeLayout(false);
      this.leftTabs.ResumeLayout(false);
      this.tabPage1.ResumeLayout(false);
      this.splitContainer3.Panel1.ResumeLayout(false);
      this.splitContainer3.Panel1.PerformLayout();
      this.splitContainer3.Panel2.ResumeLayout(false);
      ((System.ComponentModel.ISupportInitialize) (this.splitContainer3)).EndInit();
      this.splitContainer3.ResumeLayout(false);
      this.toolStrip1.ResumeLayout(false);
      this.toolStrip1.PerformLayout();
      this.tabPage3.ResumeLayout(false);
      this.tabPage3.PerformLayout();
      this.toolbar.ResumeLayout(false);
      this.toolbar.PerformLayout();
      this.menuStrip1.ResumeLayout(false);
      this.menuStrip1.PerformLayout();
      this.statusStrip1.ResumeLayout(false);
      this.statusStrip1.PerformLayout();
      this.menuPromptGroup.ResumeLayout(false);
      this.menuPrompt.ResumeLayout(false);
      this.ResumeLayout(false);
      this.PerformLayout();

    }

    #endregion

    private System.Windows.Forms.ImageList imageList1;
    private System.Windows.Forms.SplitContainer splitContainer1;
    private System.Windows.Forms.SplitContainer splitContainer2;
    private System.Windows.Forms.TreeView leftTabs_DocumentTree;
    private System.Windows.Forms.ToolStrip toolStrip1;
    private System.Windows.Forms.ToolStripButton btnAddPromptGroup;
    private System.Windows.Forms.TabControl topTabs;
    private System.Windows.Forms.TextBox botomTab_DebugLog;
    private System.Windows.Forms.ToolStripMenuItem mnuHelp_About;
    private System.Windows.Forms.ToolStripSeparator mnuHelp_SeparatorAfterFreeMarkerDocumentation;
    private System.Windows.Forms.ToolStripMenuItem mnuHelp;
    private System.Windows.Forms.ToolStripMenuItem mnuHelp_ProjectPage;
    private System.Windows.Forms.ToolStripMenuItem mnuHelp_FreeMarkerDocumentation;
    private System.Windows.Forms.ToolStripButton toolbar_btnSaveFile;
    private System.Windows.Forms.ToolStripButton toolbar_btnHelp;
    private System.Windows.Forms.ToolStripButton toolbar_btnOpenFile;
    private System.Windows.Forms.ToolStripButton toolbar_btnNewFile;
    private System.Windows.Forms.ToolStrip toolbar;
    private System.Windows.Forms.ToolStripMenuItem mnuTools_Options;
    private System.Windows.Forms.ToolStripMenuItem mnuTools_Customize;
    private System.Windows.Forms.ToolStripProgressBar statusBar_progress;
    private System.Windows.Forms.MenuStrip menuStrip1;
    private System.Windows.Forms.ToolStripMenuItem mnuFile;
    private System.Windows.Forms.ToolStripMenuItem mnuFile_New;
    private System.Windows.Forms.ToolStripMenuItem mnuFile_Open;
    private System.Windows.Forms.ToolStripSeparator mnuFile_SeparatorAfterOpen;
    private System.Windows.Forms.ToolStripMenuItem mnuFile_Save;
    private System.Windows.Forms.ToolStripMenuItem mnuFile_SaveAs;
    private System.Windows.Forms.ToolStripSeparator mnuFile_SeparatorAfterSaveAs;
    private System.Windows.Forms.ToolStripMenuItem mnuFile_RecentFiles;
    private System.Windows.Forms.ToolStripSeparator mnuFile_SeparatorAfterRecentFiles;
    private System.Windows.Forms.ToolStripMenuItem mnuFile_Exit;
    private System.Windows.Forms.ToolStripMenuItem mnuTools;
    private System.Windows.Forms.StatusStrip statusStrip1;
    private System.Windows.Forms.SplitContainer splitContainer3;
    private System.Windows.Forms.PropertyGrid leftTabs_Properties;
    private System.Windows.Forms.ToolStripButton btnRefreshTreeView;
    private System.Windows.Forms.ContextMenuStrip menuPromptGroup;
    private System.Windows.Forms.ToolStripMenuItem mnuPromptGroup_AddPrompt;
    private System.Windows.Forms.ContextMenuStrip menuPrompt;
    private System.Windows.Forms.ToolStripMenuItem mnuPrompt_Delete;
    private System.Windows.Forms.TabPage tabPage2;
    private ICSharpCode.TextEditor.TextEditorControl topTabs_TemplateEditor;
    private System.Windows.Forms.ToolStripMenuItem mnuPrompt_MoveUp;
    private System.Windows.Forms.ToolStripMenuItem mnuPrompt_MoveDown;
    private System.Windows.Forms.ToolStripSeparator mnuPrompt_SeparatorAfterMoveDown;
    private System.Windows.Forms.ToolStripMenuItem mnuPromptGroup_MoveUp;
    private System.Windows.Forms.ToolStripMenuItem mnuPromptGroup_MoveDown;
    private System.Windows.Forms.ToolStripSeparator mnuPromptGroup_SeparatorAfterMoveDown;
    private System.Windows.Forms.ToolStripMenuItem mnuPrompt_InsertVariable;
    private System.Windows.Forms.ToolStripSeparator mnuPrompt_SeparatorAfterDelete;
    private System.Windows.Forms.TabControl bottomTabs;
    private System.Windows.Forms.TabPage sourceView;
    private System.Windows.Forms.TabPage logTab;
    private ICSharpCode.TextEditor.TextEditorControl bottomTab_TemplatePreview;
    private System.Windows.Forms.ToolStripMenuItem mnuPrompt_CopyAs;
    private System.Windows.Forms.ToolStripMenuItem insertNewPromptHereToolStripMenuItem;
    private System.Windows.Forms.TabControl leftTabs;
    private System.Windows.Forms.TabPage tabPage1;
    private System.Windows.Forms.TabPage tabPage3;
    private System.Windows.Forms.TextBox dataModel;
    private System.Windows.Forms.ToolStripSeparator mnuTools_SeparatorAfterOptions;
    private System.Windows.Forms.ToolStripMenuItem mnuTools_Validate;
    private System.Windows.Forms.TabPage errors_tab;
    private System.Windows.Forms.ListView bottomTab_Errors;
    private System.Windows.Forms.ColumnHeader icon;
    private System.Windows.Forms.ColumnHeader index;
    private System.Windows.Forms.ColumnHeader description;
    private System.Windows.Forms.ColumnHeader line;
    private System.Windows.Forms.ColumnHeader column;
    private System.Windows.Forms.ColumnHeader length;
    private System.Windows.Forms.ToolStripMenuItem mnuPromptGroup_NewPrompt_Text;
    private System.Windows.Forms.ToolStripMenuItem mnuPromptGroup_NewPrompt_Multiline;
    private System.Windows.Forms.ToolStripMenuItem mnuPromptGroup_NewPrompt_Date;
    private System.Windows.Forms.ToolStripMenuItem mnuPromptGroup_NewPrompt_Checkbox;
    private System.Windows.Forms.ToolStripMenuItem mnuPromptGroup_NewPrompt_UserDefined;
    private System.Windows.Forms.ToolStripMenuItem mnuPrompt_NewPrompt_Text;
    private System.Windows.Forms.ToolStripMenuItem mnuPrompt_NewPrompt_Multiline;
    private System.Windows.Forms.ToolStripMenuItem mnuPrompt_NewPrompt_Date;
    private System.Windows.Forms.ToolStripMenuItem mnuPrompt_NewPrompt_Checkbox;
    private System.Windows.Forms.ToolStripMenuItem mnuPrompt_NewPrompt_UserDefined;
    private System.Windows.Forms.ToolStripStatusLabel statusbar_line;
    private System.Windows.Forms.ToolStripStatusLabel statusBar_status;
    private System.Windows.Forms.ToolStripMenuItem mnuPromptGroup_Delete;
    private System.Windows.Forms.ToolStripSeparator mnuPromptGroup_SeparatorAfterDelete;
    private System.Windows.Forms.ToolStripSeparator toolbar_SeparatorAfterSaveFile;
    private System.Windows.Forms.ToolStripMenuItem mnuPromptGroup_NewPrompt_Number;
    private System.Windows.Forms.ToolStripMenuItem mnuPrompt_NewPrompt_Number;


  }
}

