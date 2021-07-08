using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace TemplateBuilder
{
  public static class TreeViewClickHandler
  {
    public static PropertyGrid editor_pg = null;

    public static void PromptGroup_Click(object data) {
      PromptGroup castedObject = data as PromptGroup;
      editor_pg.SelectedObject = castedObject;
    }

    public static void Prompt_Click(object data) {
      if (data is PromptText) {
        PromptText castedObject = data as PromptText;
        editor_pg.SelectedObject = castedObject;
      } else if (data is PromptMultiline) {
        PromptMultiline castedObject = data as PromptMultiline;
        editor_pg.SelectedObject = castedObject;
      } else if (data is PromptDate) {
        PromptDate castedObject = data as PromptDate;
        editor_pg.SelectedObject = castedObject;
      } else if (data is PromptCheckbox) {
        PromptCheckbox castedObject = data as PromptCheckbox;
        editor_pg.SelectedObject = castedObject;
      } else if (data is PromptUserDefined) {
        PromptUserDefined castedObject = data as PromptUserDefined;
        editor_pg.SelectedObject = castedObject;
      } else {
        AbstractPrompt castedObject = data as AbstractPrompt;
        editor_pg.SelectedObject = castedObject;
      }
    }

    public static void Template_Click(object data) {
      Template castedObject = data as Template;
      editor_pg.SelectedObject = castedObject;
    }
  }
}
