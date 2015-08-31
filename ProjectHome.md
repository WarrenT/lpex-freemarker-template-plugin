# LPEX FreeMarker Template Plugin and Template Builder #
This plugin was developed to enhance the productivity of developers of ILE languages for IBM Power Systems, more specifically the RPG language. After becoming increasingly dissatisfied with the snippet and template functionality of RDP, the LPEX FreeMarker Template Plugin was created.

This plugin will give your team the power to define templates with custom prompts that can be invoked directly from within the LPEX editor with a key stroke. Define the format of the form prompts, what data is collected, and the structure of the output template. All this can be done in a single text file compatible with the FreeMarker (`*`.ftl) template format. The prompt forms definition is XML stored within a FreeMarker template comment.

LATEST UPDATE: The latest 0.4 version added central directory sync feature for the templates directory. (performs the sync on startup)

<a href='http://www.youtube.com/watch?feature=player_embedded&v=iMBkhXWDs6o' target='_blank'><img src='http://img.youtube.com/vi/iMBkhXWDs6o/0.jpg' width='425' height=344 /></a>

# Template Builder #
The template builder is a Windows .Net application written in C# for working with the special template files this plugin understands.

![http://lpex-freemarker-template-plugin.googlecode.com/hg/Screenshots/LPEX%20Template%20Builder%20-%20PublicProcedure.ftl_2011-11-03_15-13-06.png](http://lpex-freemarker-template-plugin.googlecode.com/hg/Screenshots/LPEX%20Template%20Builder%20-%20PublicProcedure.ftl_2011-11-03_15-13-06.png)

## Version 0.3 Beta Release ##
### Plugin Changes ###
**Fixed**
  * minor bugs

**Added**
  * default value support
  * configurable mappings for parsers to document types

### Template Builder Changes ###
**Fixed**
  * many bugs with drag drop and treeview controls in general

**Added**
  * ability to double click on prompt item and insert a cursor position
  * ability to reorder items
  * ability to copy items
  * ability to have default text values for items
  * ability to insert items between items
  * ability to preview the template output
  * support for dragging repeatble promptgroup prompts in and get the #list directive   inserted at the same time automatically
  * links to the project page and the FreeMarker docs
  * about page which shows the version

## Original Requirements ##
The system needed to fill the gaps of the existing template and snippets systems that came packaged with the RDP tool from IBM built in Eclipse and using the LPEX editor by default.

  * Injecting a block of code should be capable from a content assist menu regardless of whether the cursor is in a free format block or not.
  * If replaceable variables are found in the code block, it should present an interface for filling those values in.
  * Filling in replaceable variable values should be able to be done all from the keyboard without mouse interaction.
  * Code blocks must be able to be pulled in from a central repository with locally cached copies for offline use.

## Example ##
```
<#--
  <template>
    <name>Public Procedure Template</name>
    <description>Use this template when you need to create a public facing procedure for an API.</description>
    <promptgroups>
      <promptgroup name="procedure" repeatable="no" maxRepeats="0">
        <prompt>
          <type>text</type>
          <name>nameSpace</name>
          <label>Namespace</label>
          <description>Enter a namespace</description>
          <hint>NameSpace</hint>
        </prompt>
        <prompt>
          <type>text</type>
          <name>name</name>
          <label>Procedure Name</label>
          <description>Enter a procedure name</description>
          <hint>exampleProcedureName</hint>
        </prompt>
        <prompt>
          <type>text</type>
          <name>description</name>
          <label>Procedure Description</label>
          <description>Enter a procedure description</description>
          <hint>Example procedure</hint>
        </prompt>
        <prompt>
          <type>multiline</type>
          <name>documentation</name>
          <label>Procedure Documentation</label>
          <description>Documentation for this procedure to describe what it's function is.</description>
          <hint>This procedure will...</hint>
        </prompt>
        <prompt>
          <type>text</type>
          <name>returnDescription</name>
          <label>Return Description</label>
          <description>Return parameter description</description>
          <hint>This procedure returns...</hint>
        </prompt>
      </promptgroup>
      <promptgroup name="parameter" repeatable="yes" maxRepeats="10">
        <prompt>
          <type>text</type>
          <name>name</name>
          <label>Parameter Name</label>
          <description>Name of the parameter (pr_ will be added automatically)</description>
          <hint>exampleParameterName</hint>
        </prompt>
        <prompt>
          <type>text</type>
          <name>description</name>
          <label>Parameter Description</label>
          <description>Description of the parameter</description>
          <hint>Parameter description...</hint>
        </prompt>
      </promptgroup>
    </promptgroups>
  </template>
-->
     /**
      *--------------------------------------------------------------------
      * @brief ${procedure.description}
      *
      * ${procedure.documentation}
      *
      * @author ${author}
      * @date   ${date}
      *
      <#list parameter.repeats as parm>
      * @param  ${parm.description}
      </#list>  
      *
      * @return ${procedure.returnDescription}
      *--------------------------------------------------------------------
      */
 
      // BEGIN PROCEDURE
     p ${procedure.nameSpace}_${procedure.name}...
     p                 b                   export
 
      // Procedure Interface
      /////////////////////////////////
     d ${procedure.nameSpace}_${procedure.name}...
     d                 pi              n
     <#list parameter.repeats as parm>
     d  pr_${parm.name}...
     d                 s               n
     </#list>  
 
      // Define Local Vars
      /////////////////////////////////
      // *None
 
      //Main
      /free
 
       //TODO: Perform some business logic here
 
       return true;
      /end-free
 
     p ${procedure.nameSpace}_${procedure.name}...
     p                 e
      // END PROCEDURE
```

```
{
  procedure = {
    repeats = [
      {
        documentation = "This procedure will...", 
        name = "exampleProcedureName", 
        nameSpace = "NameSpace", 
        returnDescription = "This procedure returns...", 
        description = "Example procedure"
      }
    ],
    documentation = "This procedure will...",
    nameSpace = "NameSpace",
    name = "exampleProcedureName",
    description = "Example procedure",
    returnDescription = "This procedure returns..."
  },
  parameter = {
    repeats = [
      {
        name = "exampleParameterName",
        description = "Parameter description..."
      },
      {
        name = "exampleParameterName2",
        description = "Parameter description..."
      },
      {
        name = "exampleParameterName3",
        description = "Parameter description..."
      }
    ],
    name = "exampleParameterName",
    description = "Parameter description..."
  }
}
```


---

**Special Thanks**
Thanks to Rick Rauterkus for sharing his LPEX template plugin with me through the Midrange WDSC list. It was extremely helpful for me and provided the much needed confidence to take on developing my first Eclipse plugin. It was with his plugin as a guide that I developed this plugin. (Here is the original reply to my request on the Midrange WDSC list http://archive.midrange.com/wdsci-l/201109/msg00012.html)