using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.IO;
using System.Collections.Specialized;

namespace TemplateBuilder
{

  public delegate void NewFileHandler();
  public delegate void OpenedFileHandler();
  public delegate void FileChangedHandler();
  public delegate void RecentFilesChangedHandler(StringCollection recentFiles);
  public delegate void DataChangedHandler();
  public delegate void StatusUpdater(string status);

  public class DocumentManager
  {
    public string FilePath { get; set; }
    public string FileName { get; set; }
    public string FileType { get; set; }
    public string FileTypeName { get; set; }

    private bool IsNew; // { get; set; }

    private string currentDirectory = Application.ExecutablePath;

    public NewFileHandler newFileHandler = null;
    public OpenedFileHandler openedFileHandler = null;
    public FileChangedHandler fileChangedHandler = null; // Update form title
    public RecentFilesChangedHandler recentFilesChangedHandler = null;
    public DataChangedHandler dataChangedHandler = null; // Update preview window
    public StatusUpdater updateStatus = null;

    //
    private string _Data;
    public string Data {
      set {
        this._Data = value;
        if (dataChangedHandler != null)
          dataChangedHandler(); // Update preview window
      }
      get { return this._Data; }
    }

    private StringCollection _RecentFiles;
    public StringCollection RecentFiles {
      set {
        this._RecentFiles = value;
        foreach (string path in _RecentFiles) {
          currentDirectory = Path.GetDirectoryName(path);
          break;
        }
      }
      get { return this._RecentFiles; }
    }

    private bool _Changed;
    public bool Changed {
      set {
        this._Changed = value;
      }
      get { return this._Changed; }
    }

    public DocumentManager(string fileType, string fileTypeName) {
      initialize();
      FileType = fileType;
      FileTypeName = fileTypeName;
      RecentFiles = new StringCollection();
    }

    public string FileTypeFilter {
      get { return FileTypeName + " files (*." + FileType + ")|*." + FileType + ""; }
    }

    public bool IsUnsaved {
      get { return ((Changed) /* || (IsNew == true) */ ); }
    }

    public bool New() {
      if (HandleUnsavedContent()) {
        initialize();
        FilePath = "";
        FileName = "Untitled";
        IsNew = true;
        Data = "";
        if (newFileHandler != null) {
          newFileHandler();
        }
        Changed = false;

        if (fileChangedHandler != null) {
          fileChangedHandler(); // Update form title
        }
        if (updateStatus != null) {
          updateStatus("New file created.");
        }
        return true;
      } else {
        //user opted not to do away with the already open document
        return false;
      }
    }

    public bool Open() {
      //Prompt the user with a file open dialog then return the path
      OpenFileDialog openDlg = new OpenFileDialog();
      if (FilePath == string.Empty) {
        openDlg.InitialDirectory = currentDirectory;
      } else {
        openDlg.InitialDirectory = FilePath;
      }
      openDlg.Title = "Open...";
      openDlg.Filter = FileTypeFilter;
      DialogResult result = openDlg.ShowDialog();
      if (result == DialogResult.OK) {
        Boolean isOpen = Open(openDlg.FileName);
        return Open(openDlg.FileName);
      } else {
        return false;
      }
    }

    public bool Open(string path) {
      if (HandleUnsavedContent()) {
        if (File.Exists(path)) {
          FilePath = path;
          FileName = Path.GetFileName(path);
          Changed = false;
          IsNew = false;
          Data = File.ReadAllText(FilePath);
          if (Data == string.Empty) {
            initialize();
            if (updateStatus != null) {
              updateStatus("The file was empty.");
            }
            return false;
          } else {
            if (openedFileHandler != null) {
              openedFileHandler();
            }
            updateRecentFiles();
            Changed = false;

            if (fileChangedHandler != null) {
              fileChangedHandler(); // Update form title
            }
            if (updateStatus != null) {
              updateStatus("Opened the file.");
            }
            return true;
          }
        } else {
          return false;
        }
      } else {
        //user opted not to do away with the already open document
        return false;
      }
    }

    public bool Save() {
      if ((FilePath == string.Empty) || (IsNew)) {
        if (SaveAs()) {
          IsNew = false;
          Changed = false;
          return true;
        }
        return false;
      } else {
        if (saveToPath(FilePath)) {
          updateRecentFiles();
          IsNew = false;
          Changed = false;

          if (fileChangedHandler != null) {
            fileChangedHandler(); // Update form title
          }
          if (updateStatus != null) {
            updateStatus("Saved the file.");
          }
          return true;
        } else {
          if (updateStatus != null) {
            updateStatus("Failed to save the file.");
          }
          return false;
        }
      }
    }

    public bool SaveAs() {
      if (saveToPath(promptSaveAs())) {
        updateRecentFiles();
        IsNew = false;
        Changed = false;

        if (fileChangedHandler != null) {
          fileChangedHandler(); // Update form title
        }
        if (updateStatus != null) {
          updateStatus("Saved the file.");
        }
        return true;
      } else {
        if (updateStatus != null) {
          updateStatus("Failed to save the file.");
        }
        return false;
      }
    }

    public bool HandleUnsavedContent() {
      if (IsUnsaved) {
        DialogResult res = MessageBox.Show("Save changes to " +
            FileName, "Unsaved Content",
            MessageBoxButtons.YesNoCancel);
        if (res == DialogResult.Yes) {
          return Save();
        }
        if (res == DialogResult.No) {
          return true;
        }
        if (res == DialogResult.Cancel) {
          return false;
        }
      } else {
        return true;
      }
      return false;
    }

    private string promptSaveAs() {
      //Prompt the user with a file save dialog then return the path
      SaveFileDialog saveDlg = new SaveFileDialog();
      saveDlg.InitialDirectory = currentDirectory;
      saveDlg.Title = "Save As...";
      saveDlg.AddExtension = true;
      saveDlg.DefaultExt = FileType;
      //saveDlg.OverwritePrompt = true;
      saveDlg.ValidateNames = true;
      saveDlg.Filter = FileTypeFilter;
      saveDlg.CheckPathExists = true;
      saveDlg.FileName = FilePath;
      DialogResult result = saveDlg.ShowDialog();
      if (result == DialogResult.OK) {
        return saveDlg.FileName;
      } else {
        return "";
      }
    }

    private bool saveToPath(string path) {
      if (path == string.Empty) { return false; }
      Exception error = null;
      try {
        System.IO.FileInfo file = new System.IO.FileInfo(path);
        System.IO.StreamWriter streamWriter = file.CreateText();
        streamWriter.Write(Data);
        streamWriter.Close();
      } catch (System.Exception e) {
        error = e;
      }
      if (error != null) {
        if (updateStatus != null)
          updateStatus("Failed to save file.");
        return false;
      }
      IsNew = false;
      Changed = false;
      FilePath = path;
      FileName = Path.GetFileName(path);
      return true;
    }

    private void initialize() {
      Changed = false;
      IsNew = false;
      FilePath = "";
      Data = "";
    }

    private void updateRecentFiles() {

      //Delete it if it already exists so it can be re-added to the top
      if (RecentFiles.Contains(FilePath)) {
        RecentFiles.Remove(FilePath);
      }

      //Add it to the top
      RecentFiles.Insert(0, FilePath);

      //Only keep up to ten recents
      if (RecentFiles.Count > 10) {
          RecentFiles.RemoveAt(RecentFiles.Count - 1);
      }

      recentFilesChangedHandler(RecentFiles);
    }
  }
}
