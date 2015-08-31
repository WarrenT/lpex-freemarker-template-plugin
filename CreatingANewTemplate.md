# Introduction #

This plugin use the [FreeMarker](http://freemarker.sourceforge.net/) template engine behind the scenes to merge content with the template base. All template files should be stored with the ftl file extension.


# Template Syntax #

Each template should be a valid ftl file that FreeMarker can parse following the guidelines published by FreeMarker in their [quick start guide](http://freemarker.sourceforge.net/docs/dgui_quickstart_template.html).

## Template Prompts ##

The form definition is stored in the ftl template file hidden from the template engine by wrapping it in the FreeMarker comment tags at the top of the template document. The plugin will look for this form definition by looking for the first FreeMarker comment and reading it's contents. Because of this, it is required that the first comment in an ftl template file be the form definition.

The form definition also holds the name and description of the template in the name and description elements.

```
<template>
  <name>Template Name Here</name>
  <description>Template description here.</description>
</template>
```

### Prompt Groups ###
Prompts must be organized into groups. These groups are referenced by the form rendering engine as the designation of which prompts belong together on the screen. The initial form design presents a single screen for each prompt group. However, future development will allow for configurable form styles like a single form with tabs as the form groups, or group widgets that collect prompts together all on a single form.

```
<template>
  <name>Template Name Here</name>
  <description>Template description here.</description>
  <promptgroups>
    <promptgroup name="procedure" repeatable="no" maxRepeats="0">
      <prompt>
        <type>text</type>
        <name>name</name>
        <label>Procedure Name</label>
        <description>Enter a procedure name</description>
        <hint>exampleProcedureName</hint>
      </prompt>
    </promptgroup>
  </promptgroups>
</template>
```

Prompt groups can repeat. For instance, if you have a template intended for building a procedure (or method), a repeatable group could be defined to capture zero or more parameters for that template. You can read more about RepeatableGroups here.

```
<promptgroup name="parameter" repeatable="yes" maxRepeats="10">
  <prompt>
    <type>text</type>
    <name>name</name>
    <label>Parameter Name</label>
    <description>Name of the parameter</description>
    <hint>exampleParameterName</hint>
  </prompt>
</promptgroup>
```

### Prompts ###
Prompt elements define the data collection widgets that will be rendered on screen to collect data from the user to merge with the template. A prompt can be of a number of different types and for a description of each, please see the PromptTypes page.

A prompt consists of multiple elements that describe it to the plugin. The first of which is the name. This name should uniquely describe the prompt without using spaces or special characters. This name is the one used in your template as a variable name to represent the value captured from the user. You can reference the prompt variables by specifying the name of the prompt group it is a part of, then a period, followed by the name of the prompt.

```
procedure.name
```

# Examples #

Below is an example template that will output an RPG procedure definition combined with the ILEDocs header.

```
<#--
  <template>
    <name>Public Procedure Template</name>
    <description>Use this template when you need to create a public facing procedure for an API.</description>
    <promptgroups>
      <promptgroup name="procedure" repeatable="no" maxRepeats="0">
        <prompt>
          <type checkedValue="export" uncheckedValue="">checkbox</type>
          <name>export</name>
          <label>Export this procedure</label>
          <description>Check this box if the procedure should be exported from this module.</description>
          <hint>checked</hint>
        </prompt>
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
     p                 b                   ${procedure.export}
 
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