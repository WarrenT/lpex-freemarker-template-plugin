using System;
using System.Collections.Generic;
using System.Linq;
using System.Windows.Forms;
using CommandLine.Utility;

namespace TemplateBuilder
{
  static class Program
  {
    /// <summary>
    /// The main entry point for the application.
    /// </summary>
    [STAThread]
    static void Main(string[] args) {
      Application.EnableVisualStyles();
      Application.SetCompatibleTextRenderingDefault(false);
      Arguments tCommandLine = new Arguments(args);
      Form1 tForm1 = new Form1();
      tForm1.CommandLine = tCommandLine;
      Application.Run(tForm1);
    }
  }
}
