/*
 * Arguments class: application arguments interpreter
 *
 * Authors:		R. LOPES
 * Contributors:	R. LOPES
 * Created:		25 October 2002
 * Modified:		28 October 2002
 *
 * Version:		1.0
 * 
 * Internet: http://www.codeproject.com/Articles/3111/C-NET-Command-Line-Arguments-Parser
*/

using System;
using System.Collections;
using System.Collections.Specialized;
using System.Text.RegularExpressions;
using System.Diagnostics;

namespace CommandLine.Utility
{
  /// <summary>
  /// Arguments class
  /// </summary>
  public class Arguments
  {

    // Constants
    private const String LAST_ARGUMENT = "*";

    // Variables
    private StringDictionary Parameters;

    // Constructor
    public Arguments(string[] Args) {
      Parameters = new StringDictionary();
//      Regex Spliter = new Regex(@"^-{1,2}|^/|=|:", RegexOptions.IgnoreCase | RegexOptions.Compiled);
      Regex Splitter = new Regex(@"^-{1,2}|^/|=", RegexOptions.IgnoreCase | RegexOptions.Compiled);
      Regex Remover = new Regex(@"^['""]?(.*?)['""]?$", RegexOptions.IgnoreCase | RegexOptions.Compiled);
      string Parameter = null;
      string[] Parts;

      // Valid parameters forms:
      // {-,/,--}param{ ,=,:}((",')value(",'))
      // Examples: -param1 value1 --param2 /param3:"Test-:-work" /param4=happy -param5 '--=nice=--'
      String LastParmEntry = null;
      foreach (string Txt in Args) {
        LastParmEntry = null;
        // Look for new parameters (-,/ or --) and a possible enclosed value (=,:)
        Parts = Splitter.Split(Txt, 3);
        switch (Parts.Length) {
          // Found a value (for the last parameter found (space separator))
          case 1:
            if (Parameter != null) {
              if (!Parameters.ContainsKey(Parameter)) {
                Parts[0] = Remover.Replace(Parts[0], "$1");
                Parameters.Add(Parameter, Parts[0]);
              }
              Parameter = null;
            } else {
              LastParmEntry = Remover.Replace(Parts[0], "$1");
            }
            // else Error: no parameter waiting for a value (skipped)
            break;
          // Found just a parameter
          case 2:
            // The last parameter is still waiting. With no value, set it to true.
            if (Parameter != null) {
              if (!Parameters.ContainsKey(Parameter)) Parameters.Add(Parameter, "true");
            }
            Parameter = Parts[1];
            break;
          // Parameter with enclosed value
          case 3:
            // The last parameter is still waiting. With no value, set it to true.
            if (Parameter != null) {
              if (!Parameters.ContainsKey(Parameter)) Parameters.Add(Parameter, "true");
            }
            Parameter = Parts[1];
            // Remove possible enclosing characters (",')
            if (!Parameters.ContainsKey(Parameter)) {
              Parts[2] = Remover.Replace(Parts[2], "$1");
              Parameters.Add(Parameter, Parts[2]);
            }
            Parameter = null;
            break;
        }
      }
      /*
      // In case a parameter is still waiting
      if (Parameter != null) {
        if (!Parameters.ContainsKey(Parameter)) {
          Parameters.Add(Parameter, "true");
        }
      }
       */
      // Treat last argument as file name
      if (LastParmEntry != null) {
        Parameters.Add(LAST_ARGUMENT, LastParmEntry);
      }
    }

    // Retrieve a parameter value if it exists
    public string Get(String Key) {
      return (Parameters[Key]);
    }

    // Retrieve last program argument
    public string FileName {
      get {
        return (Parameters[LAST_ARGUMENT]);
      }
    }

    public void DebugPrint() {
      foreach (DictionaryEntry parameter in Parameters) {
        Debug.Write("Parameter: " + parameter.Key + "=" + Get((String) parameter.Key) + "\n");
      }
      Debug.Write("File name: " + FileName + "\n");
    }

  }
}
